package ra.business.impl;

import ra.business.IStudentAuthService;
import ra.dao.IStudentAuthDAO;
import ra.dao.impl.StudentAuthDAOImpl;
import ra.dto.StudentLoginDTO;
import ra.model.Student;

import java.util.Objects;
import java.util.regex.Pattern;

public class StudentAuthServiceImpl implements IStudentAuthService {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private final IStudentAuthDAO studentAuthDAO;

    public StudentAuthServiceImpl() {
        this.studentAuthDAO = new StudentAuthDAOImpl();
    }

    public StudentAuthServiceImpl(IStudentAuthDAO studentAuthDAO) {
        this.studentAuthDAO = Objects.requireNonNull(studentAuthDAO, "studentAuthDAO khong duoc null");
    }

    @Override
    public Student login(StudentLoginDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Thong tin dang nhap khong duoc de trong.");
        }
        String email = dto.getEmail();
        String password = dto.getPassword();
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email khong duoc de trong.");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Mat khau khong duoc de trong.");
        }
        String trimmedEmail = email.trim();
        if (!EMAIL_PATTERN.matcher(trimmedEmail).matches()) {
            throw new IllegalArgumentException("Email khong dung dinh dang.");
        }
        return studentAuthDAO.findByEmailAndPassword(trimmedEmail, password.trim());
    }
}

