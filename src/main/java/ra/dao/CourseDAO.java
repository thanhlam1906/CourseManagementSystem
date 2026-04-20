package ra.dao;

import ra.model.Course;

import java.util.List;

public interface CourseDAO {
    List<Course> getAllCourses();
    boolean addCourse(Course course);
    boolean deleteCourse(String id);
    boolean updateCourse(Course course);
    Course findById(int id);
    List<Course> searchByName(String name);
}
