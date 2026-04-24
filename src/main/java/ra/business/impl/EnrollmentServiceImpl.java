package ra.business.impl;

import ra.business.IEnrollmentService;
import ra.dao.EnrollmentDAO;
import ra.dao.impl.EnrollmentDAOImpl;
import ra.dto.CourseStatisticsDTO;
import ra.dto.EnrollmentDTO;
import ra.model.Course;
import ra.model.Enrollment;
import ra.model.Student;

import java.util.Comparator;
import java.util.List;

public class EnrollmentServiceImpl implements IEnrollmentService {
	private final EnrollmentDAO enrollmentDAO;

	public EnrollmentServiceImpl() {
		this.enrollmentDAO = new EnrollmentDAOImpl();
	}

	@Override
	public boolean registerCourse(EnrollmentDTO enrollmentDTO) {
		if (enrollmentDTO == null) {
			throw new IllegalArgumentException("Thong tin dang ky khong duoc de trong.");
		}
		if (enrollmentDTO.getStudent_id() == null || enrollmentDTO.getStudent_id() <= 0) {
			throw new IllegalArgumentException("ID hoc vien khong hop le.");
		}
		if (enrollmentDTO.getCourse_id() == null || enrollmentDTO.getCourse_id() <= 0) {
			throw new IllegalArgumentException("ID khoa hoc khong hop le.");
		}

		Enrollment enrollment = toEntity(enrollmentDTO);
		return enrollmentDAO.registerCourse(enrollment);
	}

	@Override
	public List<EnrollmentDTO> listCourseRegistedById(int studentId) {
		if (studentId <= 0) {
			throw new IllegalArgumentException("ID hoc vien khong hop le.");
		}

		return enrollmentDAO.listCourseRegistedById(studentId)
				.stream()
				.map(this::toDTO)
				.toList();
	}

	@Override
	public List<EnrollmentDTO> listCourseRegistedById(int studentId, String sortBy, boolean ascending) {
		List<EnrollmentDTO> enrollmentDTOS = listCourseRegistedById(studentId);

		Comparator<EnrollmentDTO> comparator;
		if ("date".equalsIgnoreCase(sortBy)) {
			comparator = Comparator.comparing(EnrollmentDTO::getCreateAt,
					Comparator.nullsLast(Comparator.naturalOrder()));
		} else {
			comparator = Comparator.comparing(dto -> dto.getCourseName() == null ? "" : dto.getCourseName().toLowerCase(),
					Comparator.nullsLast(Comparator.naturalOrder()));
		}

		if (!ascending) {
			comparator = comparator.reversed();
		}

		return enrollmentDTOS.stream()
				.sorted(comparator)
				.toList();
	}

	@Override
	public boolean cancelCourse(EnrollmentDTO enrollmentDTO) {
		if (enrollmentDTO == null) {
			throw new IllegalArgumentException("Thong tin huy dang ky khong duoc de trong.");
		}
		if (enrollmentDTO.getStudent_id() == null || enrollmentDTO.getStudent_id() <= 0) {
			throw new IllegalArgumentException("ID hoc vien khong hop le.");
		}
		if (enrollmentDTO.getCourse_id() == null || enrollmentDTO.getCourse_id() <= 0) {
			throw new IllegalArgumentException("ID khoa hoc khong hop le.");
		}
		return enrollmentDAO.cancelCourse(toEntity(enrollmentDTO));
	}

	@Override
	public List<EnrollmentDTO> listNameStudentRegistedCourse(int courseId) {
		if (courseId <= 0) {
			throw new IllegalArgumentException("ID khoa hoc khong hop le.");
		}
		return enrollmentDAO.listNameStudentRegistedCourse(courseId)
				.stream()
				.map(enrollment -> toDTO(enrollment))
				.toList();
	}

	@Override
	public List<EnrollmentDTO> listAllWaitingEnrollments() {

		return enrollmentDAO.listAllEnrollments()
				.stream()
				.map(enrollment -> toDTO(enrollment)).filter(enrollmentDTO -> "waiting".equalsIgnoreCase(enrollmentDTO.getStatus()))
				.toList();
	}

	@Override
	public List<EnrollmentDTO> listAllApprovedEnrollments() {
		return enrollmentDAO.listAllEnrollments()
				.stream()
				.map(enrollment -> toDTO(enrollment)).filter(enrollmentDTO -> "confirm".equalsIgnoreCase(enrollmentDTO.getStatus()))
				.toList();
	}

	@Override
	public boolean approveEnrollment(EnrollmentDTO enrollmentDTO) {
		 if (enrollmentDTO == null) {
			 throw new IllegalArgumentException("Thong tin duyet dang ky khong duoc de trong.");
		 }
		 if (enrollmentDTO.getEnrollment_id() == null || enrollmentDTO.getEnrollment_id() <= 0) {
			 throw new IllegalArgumentException("ID phien dang ky khong hop le.");
		 }
		 return enrollmentDAO.approveEnrollment(toEntity(enrollmentDTO));

	}

	@Override
	public boolean denyEnrollment(EnrollmentDTO enrollmentDTO) {
		if (enrollmentDTO == null) {
			throw new IllegalArgumentException("Thong tin tu choi dang ky khong duoc de trong.");
		}
		if (enrollmentDTO.getEnrollment_id() == null || enrollmentDTO.getEnrollment_id() <= 0) {
			throw new IllegalArgumentException("ID phien dang ky khong hop le.");
		}
		return enrollmentDAO.denyEnrollment(toEntity(enrollmentDTO));
	}

	@Override
	public boolean deleteEnrollmentByEnrollmentId(EnrollmentDTO enrollmentDTO) {
		if (enrollmentDTO == null) {
			throw new IllegalArgumentException("Thong tin xoa dang ky khong duoc de trong.");
		}
		if (enrollmentDTO.getEnrollment_id() == null || enrollmentDTO.getEnrollment_id() <= 0) {
			throw new IllegalArgumentException("ID phien dang ky khong hop le.");
		}

		return enrollmentDAO.deleteEnrollmentByEnrollmentId(toEntity(enrollmentDTO));
	}

	@Override
	public List<CourseStatisticsDTO> getCourseStatistics() {
		return enrollmentDAO.listCourseStatistics().stream()
				.map(dto -> new CourseStatisticsDTO(
						dto.getCourseId(),
						dto.getCourseName() == null ? "(khong co ten)" : dto.getCourseName(),
						dto.getTotalStudents() == null ? 0 : dto.getTotalStudents()))
				.toList();
	}

	private Enrollment toEntity(EnrollmentDTO enrollmentDTO) {
		Enrollment enrollment = new Enrollment();
		enrollment.setId(enrollmentDTO.getEnrollment_id() == null ? 0 : enrollmentDTO.getEnrollment_id());
		enrollment.setStudentId(enrollmentDTO.getStudent_id());
		enrollment.setCourseId(enrollmentDTO.getCourse_id());
		return enrollment;
	}

	private EnrollmentDTO toDTO(Enrollment enrollment) {
		EnrollmentDTO dto = new EnrollmentDTO();
		dto.setEnrollment_id(enrollment.getId());
		dto.setStudent_id(enrollment.getStudentId());
		dto.setCourse_id(enrollment.getCourseId());
		dto.setCreateAt(enrollment.getRegisteredAt());
		dto.setStatus(enrollment.getStatus() == null ? null : enrollment.getStatus().name());
		if (enrollment.getCourse() != null) {
			dto.setCourseName(enrollment.getCourse().getName());
			Course course = new Course();
			course.setId(enrollment.getCourseId());
			course.setName(enrollment.getCourse().getName());
			dto.setCourse(course);
		}
		if (enrollment.getStudent() != null) {
			Student student = new Student();
			student.setId(enrollment.getStudentId());
			student.setName(enrollment.getStudent().getName());
			dto.setStudent(student);
		}
		return dto;
	}
}
