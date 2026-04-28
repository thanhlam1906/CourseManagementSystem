package ra.dao;

import ra.model.Admin;

public interface IAdminAuthDAO {
    /**
     * Tim admin theo username + password.
     * @return Admin neu thong tin dung, null neu khong tim thay.
     */
    Admin findByUsernameAndPassword(String username, String password);
}

