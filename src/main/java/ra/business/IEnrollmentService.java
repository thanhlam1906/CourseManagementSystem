package ra.business;

import ra.dto.CourseStatisticsDTO;
import ra.dto.EnrollmentDTO;

import java.util.List;

public interface IEnrollmentService {
    boolean registerCourse(EnrollmentDTO enrollmentDTO);
    List<EnrollmentDTO> listCourseRegistedById(int studentId);
    List<EnrollmentDTO> listCourseRegistedById(int studentId, String sortBy, boolean ascending);
    boolean cancelCourse(EnrollmentDTO enrollmentDTO);
        List<EnrollmentDTO> listNameStudentRegistedCourse(int courseId);
        List<EnrollmentDTO> listAllWaitingEnrollments();
        List<EnrollmentDTO> listAllApprovedEnrollments();
        boolean approveEnrollment(EnrollmentDTO enrollmentDTO);
        boolean denyEnrollment(EnrollmentDTO enrollmentDTO);
        boolean deleteEnrollmentByEnrollmentId(EnrollmentDTO enrollmentDTO);
        List<CourseStatisticsDTO> getCourseStatistics();
}
