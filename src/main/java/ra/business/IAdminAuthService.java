package ra.business;

import ra.dto.AdminLoginDTO;
import ra.model.Admin;

public interface IAdminAuthService {
    /**
     * Dang nhap voi vai tro admin.
     * @return Admin neu dang nhap thanh cong, null neu sai thong tin.
     * @throws IllegalArgumentException neu DTO khong hop le.
     */
    Admin login(AdminLoginDTO dto);
}

