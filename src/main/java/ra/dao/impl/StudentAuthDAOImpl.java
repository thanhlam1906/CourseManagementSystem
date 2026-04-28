package ra.dao.impl;

import ra.dao.IStudentAuthDAO;
import ra.model.Student;
import ra.utils.DBUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentAuthDAOImpl implements IStudentAuthDAO {

    @Override
    public Student findByEmailAndPassword(String email, String password) {
        String sql = "select id, name, dob, email, sex, phone, password, create_at " +
                "from student where email = ? and password = ?";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Date dob = rs.getDate("dob");
                    Date createAt = rs.getDate("create_at");
                    return new Student(
                            rs.getInt("id"),
                            rs.getString("name"),
                            dob == null ? null : dob.toLocalDate(),
                            rs.getString("email"),
                            rs.getBoolean("sex"),
                            rs.getString("phone"),
                            rs.getString("password"),
                            createAt == null ? null : createAt.toLocalDate()
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Khong the kiem tra dang nhap hoc vien: " + e.getMessage(), e);
        }
        return null;
    }
}

