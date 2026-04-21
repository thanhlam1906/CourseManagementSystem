package ra.dao;

import ra.model.Enrollment;

import java.util.List;

public interface EnrollmentDAO {
    boolean registerCourse(Enrollment enrollment);
    boolean cancelCourse(Enrollment enrollment);
    List<Enrollment>  listCourseRegistedById(int studentId);
}
