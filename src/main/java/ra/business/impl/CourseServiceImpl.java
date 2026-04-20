package ra.business.impl;

import ra.business.ICourseService;
import ra.dao.CourseDAO;
import ra.dao.impl.CourseDAOImpl;
import ra.model.Course;
import ra.model.dto.CourseDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CourseServiceImpl implements ICourseService {
    private final CourseDAO dao;

    public CourseServiceImpl() {
        dao = new CourseDAOImpl();
    }

    @Override
    public List<CourseDTO> getAllCourses() {
        List<Course> courses = dao.getAllCourses();
        List<CourseDTO> courseDTOs = new ArrayList<>();
        if (courses == null || courses.isEmpty()) {
            return courseDTOs;
        }
        for (Course course : courses) {
            courseDTOs.add(toDTO(course));
        }
        return courseDTOs;
    }

    @Override
    public boolean addCourse(CourseDTO courseDTO) {
        if (courseDTO.getCourseName() == null || courseDTO.getCourseName().trim().isEmpty() ) {
            throw new IllegalArgumentException("LỖI: KHÓA HỌC KHÔNG ĐƯỢC ĐỂ TRỐNG");
        }
        if (courseDTO.getDuration() <= 0){
             throw new IllegalArgumentException("LỖI: THỜI LƯỢNG PHẢI LỚN HƠN 0");
        }
        if(courseDTO.getInstructor() == null || courseDTO.getInstructor().trim().isEmpty()){
            throw new IllegalArgumentException("LỖI: GIẢNG VIÊN KHÔNG ĐƯỢC ĐỂ TRỐNG");
        }
        return dao.addCourse(toEntity(courseDTO));
    }

    @Override
    public boolean deleteCourse(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("LỖI: ID KHÔNG ĐƯỢC ĐỂ TRỐNG");
        }

        return dao.deleteCourse(id);
    }

    @Override
    public boolean updateCourse(CourseDTO courseDTO) {
        if (courseDTO.getCourseName() == null || courseDTO.getCourseName().trim().isEmpty() ) {
            throw new IllegalArgumentException("LỖI: KHÓA HỌC KHÔNG ĐƯỢC ĐỂ TRỐNG");
        }
        if (courseDTO.getDuration() <= 0){
            throw new IllegalArgumentException("LỖI: THỜI LƯỢNG PHẢI LỚN HƠN 0");
        }
        if(courseDTO.getInstructor() == null || courseDTO.getInstructor().trim().isEmpty()){
            throw new IllegalArgumentException("LỖI: GIẢNG VIÊN KHÔNG ĐƯỢC ĐỂ TRỐNG");
        }
        return dao.updateCourse(toEntity(courseDTO));
    }

    @Override
    public CourseDTO getCourseById(int id) {
        Course course =  dao.findById(id);
        if(course == null){
            return null;
        }

        return  toDTO(course);
    }

    @Override
    public CourseDTO getCourseByName(String name) {
        if(name == null|| name.trim().isEmpty()){
            return null;
        }
        return  dao.searchByName(name).stream()
                .map(entity -> toDTO(entity))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<CourseDTO> sortNameCourses(){
        List<CourseDTO> courseDTOs = getAllCourses();
        if (courseDTOs == null || courseDTOs.isEmpty()) {
            return new ArrayList<>();
        }
        return courseDTOs.stream()
                .sorted((c1, c2) -> c1.getCourseName().compareToIgnoreCase(c2.getCourseName()))
                .collect(Collectors.toList());
    }

    private  Course toEntity(CourseDTO dto) {
        Course entity = new Course();
        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        entity.setName(dto.getCourseName());
        entity.setDuration(dto.getDuration());
        entity.setInstructor(dto.getInstructor());
        if (dto.getCreateAt() != null) {
            entity.setCreateAt(dto.getCreateAt());
        }
        return entity;
    }

    private CourseDTO toDTO(Course entity) {
        CourseDTO dto = new CourseDTO(entity.getId(), entity.getName(), entity.getDuration(), entity.getInstructor());

        dto.setCourseName(entity.getName());
        dto.setDuration(entity.getDuration());
        dto.setInstructor(entity.getInstructor());
        dto.setCreateAt(entity.getCreateAt());
        return dto;
    }
}
