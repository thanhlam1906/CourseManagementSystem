package ra.business;

import ra.model.LoginResult;

public interface IAuthService {
    LoginResult login(String email, String password);
}
