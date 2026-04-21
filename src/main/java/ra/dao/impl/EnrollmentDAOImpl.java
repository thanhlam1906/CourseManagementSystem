package ra.dao.impl;

import ra.dao.EnrollmentDAO;
import ra.model.Course;
import ra.model.Enrollment;
import ra.model.EnrollmentStatus;
import ra.utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDAOImpl implements EnrollmentDAO {
    @Override
    public boolean registerCourse(Enrollment enrollment) {
        String sql = "{ CALL register_course(?, ?, ?, ?) }";
        try (Connection connection = DBUtil.getConnection();
             CallableStatement cs = connection.prepareCall(sql)) {
            cs.setInt(1, enrollment.getStudentId());
            cs.setInt(2, enrollment.getCourseId());
            cs.registerOutParameter(3, Types.VARCHAR);
            cs.registerOutParameter(4, Types.BOOLEAN);

            cs.execute();

            String message = cs.getString(3);
            boolean status = cs.getBoolean(4);

            if (!status && message != null && !message.isEmpty()) {
                System.out.println(message);
            }
            return status;
        } catch (SQLException e) {
            throw new RuntimeException("Khong the dang ky khoa hoc: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean cancelCourse(Enrollment enrollment) {
        String sql = "delete from enrollment where student_id = ? and course_id = ? and status = 'waiting'";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement ps = connection.prepareCall(sql)) {
            ps.setInt(1, enrollment.getStudentId());
            ps.setInt(2, enrollment.getCourseId());
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Khong the huy dang ky khoa hoc: " + e.getMessage());
        }

    }

    @Override
    public List<Enrollment> listCourseRegistedById(int studentId) {
        List<Enrollment> enrollments = new ArrayList<>();
        String sql = "select e.id, e.student_id, e.course_id, e.status, e.registered_at, c.name as course_name " +
                "from enrollment e join course c on c.id = e.course_id where e.student_id = ?";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try(ResultSet resultSet = ps.executeQuery()){
                while(resultSet.next()){
                    Enrollment enrollment = new Enrollment();
                    enrollment.setId(resultSet.getInt("id"));
                    enrollment.setStudentId(resultSet.getInt("student_id"));
                    enrollment.setCourseId(resultSet.getInt("course_id"));

                    String statusText = resultSet.getString("status");
                    if (statusText != null) {
                        enrollment.setStatus(EnrollmentStatus.valueOf(statusText.trim().toUpperCase()));
                    }

                    Timestamp registeredAt = resultSet.getTimestamp("registered_at");
                    if (registeredAt != null) {
                        enrollment.setRegisteredAt(registeredAt.toLocalDateTime());
                    }

                    Course course = new Course();
                    course.setId(resultSet.getInt("course_id"));
                    course.setName(resultSet.getString("course_name"));
                    enrollment.setCourse(course);

                    enrollments.add(enrollment);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return enrollments;
    }
}