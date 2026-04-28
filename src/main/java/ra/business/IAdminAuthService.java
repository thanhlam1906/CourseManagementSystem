package ra.business;

import ra.dto.AdminLoginDTO;
import ra.model.Admin;

public interface IAdminAuthService {

    Admin login(AdminLoginDTO dto);
}

