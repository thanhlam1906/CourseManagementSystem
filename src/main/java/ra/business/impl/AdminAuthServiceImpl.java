package ra.business.impl;

import ra.business.IAdminAuthService;
import ra.dao.IAdminAuthDAO;
import ra.dao.impl.AdminAuthDAOImpl;
import ra.dto.AdminLoginDTO;
import ra.model.Admin;

import java.util.Objects;

public class AdminAuthServiceImpl implements IAdminAuthService {
    private final IAdminAuthDAO adminAuthDAO;

    public AdminAuthServiceImpl() {
        this.adminAuthDAO = new AdminAuthDAOImpl();
    }

    public AdminAuthServiceImpl(IAdminAuthDAO adminAuthDAO) {
        this.adminAuthDAO = Objects.requireNonNull(adminAuthDAO, "adminAuthDAO khong duoc null");
    }

    @Override
    public Admin login(AdminLoginDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Thong tin dang nhap khong duoc de trong.");
        }
        String username = dto.getUsername();
        String password = dto.getPassword();
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Ten dang nhap khong duoc de trong.");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Mat khau khong duoc de trong.");
        }
        return adminAuthDAO.findByUsernameAndPassword(username.trim(), password.trim());
    }
}

