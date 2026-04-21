package ra.dao;

import ra.model.LoginResult;

public interface AuthDAO {
    LoginResult checkLogin(String email, String password);

}
