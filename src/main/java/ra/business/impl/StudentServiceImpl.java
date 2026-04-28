package ra.business.impl;

import ra.business.IStudentService;
import ra.dao.StudentDAO;
import ra.dao.impl.StudentDAOImpl;
import ra.model.Student;
import ra.dto.StudentDTO;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;



public class StudentServiceImpl implements IStudentService {
    private final StudentDAO dao;

    public StudentServiceImpl() {
        dao = new StudentDAOImpl();
    }

    public StudentServiceImpl(StudentDAO dao) {
        this.dao = Objects.requireNonNull(dao, "studentDAO khong duoc null");
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        return dao.getAllStudents().stream()
                .map(student -> toDTO(student))
                .toList();
    }

    @Override
    public boolean addStudent(StudentDTO studentDTO) {
        if (studentDTO == null) {
            throw new IllegalArgumentException("LOI: THONG TIN SINH VIEN KHONG DUOC TRONG");
        }
        // FIX: validate truong null/empty TRUOC khi check trung lap (truoc day .equals NPE)
        if (studentDTO.getName() == null || studentDTO.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("LOI: TEN KHONG DUOC DE TRONG");
        }
        if (studentDTO.getDob() == null) {
            throw new IllegalArgumentException("LOI: NGAY SINH KHONG DUOC DE TRONG");
        }
        if (studentDTO.getDob().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("LOI: NGAY SINH KHONG HOP LE");
        }
        if (studentDTO.getSex() == null) {
            throw new IllegalArgumentException("LOI: GIOI TINH KHONG HOP LE");
        }
        if (studentDTO.getPhone() == null || studentDTO.getPhone().trim().isEmpty()) {
            throw new IllegalArgumentException("LOI: SO DIEN THOAI KHONG DUOC DE TRONG");
        }
        if (studentDTO.getEmail() == null || studentDTO.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("LOI: EMAIL KHONG DUOC DE TRONG");
        }
        if (studentDTO.getPassword() == null || studentDTO.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("LOI: MAT KHAU KHONG DUOC DE TRONG");
        }

        // FIX: chi goi getAllStudents 1 lan, kiem tra null-safe
        List<Student> existingStudents = dao.getAllStudents();
        String email = studentDTO.getEmail().trim();
        String phone = studentDTO.getPhone().trim();
        for (Student s : existingStudents) {
            if (email.equalsIgnoreCase(s.getEmail())) {
                throw new IllegalArgumentException("LOI: EMAIL DA TON TAI");
            }
            if (phone.equals(s.getPhone())) {
                throw new IllegalArgumentException("LOI: SO DIEN THOAI DA TON TAI");
            }
        }

        if (studentDTO.getCreateAt() == null) {
            studentDTO.setCreateAt(LocalDate.now());
        }

        return dao.addStudent(toEntity(studentDTO));
    }

    @Override
    public boolean deleteStudent(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("LOI: ID KHONG DUOC DE TRONG");
        }

        int studentId;
        try {
            studentId = Integer.parseInt(id.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("LOI: ID PHAI LA SO NGUYEN");
        }

        boolean isDeleted = dao.deleteStudent(studentId);
        if (!isDeleted) {
            throw new IllegalArgumentException("LOI: KHONG TIM THAY SINH VIEN VOI ID: " + id);
        }
        return true;
    }

    @Override
    public boolean updateStudent(StudentDTO studentDTO) {
        if(studentDTO.getName() == null || studentDTO.getName().trim().isEmpty()){
            throw new IllegalArgumentException("LOI: TEN KHONG DUOC DE TRONG");
        }
        if(studentDTO.getEmail() == null || studentDTO.getEmail().trim().isEmpty()){
            throw new IllegalArgumentException("LOI: EMAIL KHONG DUOC DE TRONG");
        }
        if (studentDTO.getDob() == null) {
            throw new IllegalArgumentException("LOI: NGAY SINH KHONG DUOC DE TRONG");
        }
        if(studentDTO.getPhone() == null || studentDTO.getPhone().trim().isEmpty()){
            throw new IllegalArgumentException("LOI: SO DIEN THOAI KHONG DUOC DE TRONG");
        }
        if (studentDTO.getEmail() == null || studentDTO.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("LOI: EMAIL KHONG DUOC DE TRONG");
        }
        if (studentDTO.getSex() == null) {
            throw new IllegalArgumentException("LOI: GIOI TINH KHONG HOP LE");
        }
        if (studentDTO.getPassword() == null || studentDTO.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("LOI: MAT KHAU KHONG DUOC DE TRONG");
        }

        return dao.updateStudent(toEntity(studentDTO));
    }

    @Override
    public StudentDTO getStudentById(int id) {
        Student student = dao.findById(id);
        if (student == null) {
            throw new IllegalArgumentException("LOI: KHONG TIM THAY SINH VIEN VOI ID: " + id);
        }
        return toDTO(student);
    }



    @Override
    public List<StudentDTO> sortNameStudents(boolean isAscending) {
        Comparator<Student> comparator = Comparator.comparing(Student::getName, String.CASE_INSENSITIVE_ORDER);
        if(!isAscending ){
            comparator = comparator.reversed();
        }
        return dao.getAllStudents().stream()
                .sorted(comparator)
                .map(student -> toDTO(student))
                .toList();

    }

    @Override
    public boolean changePassword(StudentDTO studentDTO) {
        if (studentDTO == null || studentDTO.getId() == null || studentDTO.getId() <= 0) {
            throw new IllegalArgumentException("LOI: ID SINH VIEN KHONG HOP LE");
        }
        if (studentDTO.getOldPassword() == null || studentDTO.getOldPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("LOI: MAT KHAU HIEN TAI KHONG DUOC DE TRONG");
        }
        if (studentDTO.getPassword() == null || studentDTO.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("LOI: MAT KHAU MOI KHONG DUOC DE TRONG");
        }
        if (studentDTO.getOldPassword().equals(studentDTO.getPassword())) {
            throw new IllegalArgumentException("LOI: MAT KHAU MOI PHAI KHAC MAT KHAU HIEN TAI");
        }

        Student student = dao.findById(studentDTO.getId());
        if (student == null) {
            throw new IllegalArgumentException("LOI: KHONG TIM THAY SINH VIEN VOI ID: " + studentDTO.getId());
        }
        // FIX: xac thuc mat khau hien tai truoc khi cho doi (bao mat)
        if (!studentDTO.getOldPassword().equals(student.getPassword())) {
            throw new IllegalArgumentException("LOI: MAT KHAU HIEN TAI KHONG DUNG");
        }

        student.setPassword(studentDTO.getPassword());
        return dao.changePassword(student);
    }

    private StudentDTO toDTO(Student student) {
        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setDob(student.getDob());
        dto.setEmail(student.getEmail());
        dto.setSex(student.isSex());
        dto.setPhone(student.getPhone());
        dto.setPassword(student.getPassword());
        dto.setCreateAt(student.getCreateAt());
        return dto;
    }

    private Student toEntity(StudentDTO studentDTO) {
        Student entity = new Student();
        entity.setId(studentDTO.getId());
        entity.setName(studentDTO.getName());
        entity.setDob(studentDTO.getDob());
        entity.setEmail(studentDTO.getEmail());
        entity.setSex(studentDTO.getSex());
        entity.setPhone(studentDTO.getPhone());
        entity.setPassword(studentDTO.getPassword());
        entity.setCreateAt(studentDTO.getCreateAt());
        return entity;
    }
}
