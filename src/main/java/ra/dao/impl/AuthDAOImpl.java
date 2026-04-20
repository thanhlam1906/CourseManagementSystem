package ra.dao.impl;

import ra.dao.AuthDAO;
import ra.model.LoginResult;
import ra.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthDAOImpl implements AuthDAO {
    @Override
    public LoginResult checkLogin(String email, String password) {
        String sql = "select found_role, found_id from check_login(?, ?)";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new LoginResult(
                            rs.getString("found_role"),
                            rs.getInt("found_id")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Khong the kiem tra dang nhap: " + e.getMessage(), e);
        }
        return null;
    }
}
