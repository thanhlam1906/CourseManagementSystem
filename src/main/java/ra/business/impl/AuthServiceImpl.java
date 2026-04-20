package ra.business.impl;

import ra.business.IAuthService;
import ra.dao.AuthDAO;
import ra.dao.impl.AuthDAOImpl;
import ra.model.LoginResult;

public class AuthServiceImpl implements IAuthService {
    private final AuthDAO authDAO;

    public AuthServiceImpl() {
        this.authDAO = new AuthDAOImpl();
    }

    @Override
    public LoginResult login(String email, String password) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email/ten dang nhap khong duoc de trong.");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Mat khau khong duoc de trong.");
        }
        return authDAO.checkLogin(email.trim(), password.trim());
    }
}
