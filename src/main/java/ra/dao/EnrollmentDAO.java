package ra.dao;

import ra.dto.CourseStatisticsDTO;
import ra.model.Enrollment;

import java.util.List;

public interface EnrollmentDAO {
    boolean registerCourse(Enrollment enrollment);
    boolean cancelCourse(Enrollment enrollment);
    List<Enrollment>  listCourseRegistedById(int studentId);
    List<Enrollment> listNameStudentRegistedCourse(int courseId);
    List<Enrollment> listAllEnrollments();
    boolean approveEnrollment(Enrollment enrollment);
    boolean denyEnrollment(Enrollment enrollment);

     boolean deleteEnrollmentByEnrollmentId(Enrollment enrollment);

    List<CourseStatisticsDTO> listCourseStatistics();
}
