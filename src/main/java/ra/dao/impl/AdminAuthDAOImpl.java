package ra.dao.impl;

import ra.dao.IAdminAuthDAO;
import ra.model.Admin;
import ra.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminAuthDAOImpl implements IAdminAuthDAO {

    @Override
    public Admin findByUsernameAndPassword(String username, String password) {
        String sql = "select id, username, password from admin where username = ? and password = ?";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Admin(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("password")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Khong the kiem tra dang nhap admin: " + e.getMessage(), e);
        }
        return null;
    }
}

