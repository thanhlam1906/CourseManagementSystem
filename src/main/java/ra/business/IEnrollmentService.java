package ra.business;

import ra.dto.EnrollmentDTO;
import ra.model.Enrollment;

import java.util.List;

public interface IEnrollmentService {
    boolean registerCourse(EnrollmentDTO enrollmentDTO);
    List<EnrollmentDTO> listCourseRegistedById(int studentId);
    List<EnrollmentDTO> listCourseRegistedById(int studentId, String sortBy, boolean ascending);
    boolean cancelCourse(EnrollmentDTO enrollmentDTO);
}
