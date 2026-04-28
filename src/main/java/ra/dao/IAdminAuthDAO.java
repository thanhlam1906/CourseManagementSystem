package ra.dao;

import ra.model.Admin;

public interface IAdminAuthDAO {

    Admin findByUsernameAndPassword(String username, String password);
}

