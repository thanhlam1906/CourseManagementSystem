package ra.business.impl;

import ra.business.IEnrollmentService;
import ra.dao.EnrollmentDAO;
import ra.dao.impl.EnrollmentDAOImpl;
import ra.dto.EnrollmentDTO;
import ra.model.Enrollment;

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

	private Enrollment toEntity(EnrollmentDTO enrollmentDTO) {
		Enrollment enrollment = new Enrollment();
		enrollment.setStudentId(enrollmentDTO.getStudent_id());
		enrollment.setCourseId(enrollmentDTO.getCourse_id());
		return enrollment;
	}

	private EnrollmentDTO toDTO(Enrollment enrollment) {
		EnrollmentDTO dto = new EnrollmentDTO();
		dto.setStudent_id(enrollment.getStudentId());
		dto.setCourse_id(enrollment.getCourseId());
		dto.setCreateAt(enrollment.getRegisteredAt());
		dto.setStatus(enrollment.getStatus() == null ? null : enrollment.getStatus().name());
		if (enrollment.getCourse() != null) {
			dto.setCourseName(enrollment.getCourse().getName());
		}
		return dto;
	}
}
