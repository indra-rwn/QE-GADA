package pages.login;

import pages.base.BaseSteps;

public interface LoginSteps extends BaseSteps<LoginPage, LoginModel> {
    void loginPage(LoginType loginType, String email, String password, String phoneNumber);
    void verifyLoginStatus();
    void verifyCredentialLogin(LoginType loginType);
}