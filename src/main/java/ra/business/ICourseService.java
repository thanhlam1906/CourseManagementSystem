package ra.business;

import ra.dto.CourseDTO;

import java.util.List;

public interface ICourseService {
    List<CourseDTO> getAllCourses();
    boolean addCourse(CourseDTO courseDTO);
    boolean deleteCourse(String id);
    boolean updateCourse(CourseDTO courseDTO);
    CourseDTO getCourseById(int id);
    CourseDTO getCourseByName(String name);
    List<CourseDTO> sortNameCourses();
}
