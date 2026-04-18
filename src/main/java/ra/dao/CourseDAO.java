package ra.dao;

import ra.business.impl.CourseServiceImpl;
import ra.model.Course;
import ra.model.dto.CourseDTO;

import java.util.List;

public interface CourseDAO {
    List<Course> getAllCourses();
    boolean addCourse(Course course);
    boolean deleteCourse(String id);
    boolean updateCourse(Course course);
    Course findById(int id);
    List<Course> searchByName(String name);
}
