package website.login;

import base.BaseWebDriver;
import org.testng.annotations.Test;
import pages.login.LoginModel;
import pages.login.LoginPage;
import pages.login.LoginType;

public class LoginTest extends BaseWebDriver implements LoginTestCases {

    LoginPage loginPage;
    LoginModel loginModel;

    @Override
    public void initInstance() {
        loginPage = LoginPage.newInstance(webDriver, webDriverWait);
        loginModel = LoginModel.newInstance();
    }

    @Override
    public void initPageReport() {
        loginPage.initReport(extentTest);
    }

    @Test(priority = 1)
    @Override
    public void successLoginWithEmailAndPassword() {
        loginPage.loginPage(LoginType.LOGIN_USERNAME_EMAIL, loginModel.email, loginModel.password, null);
        loginPage.verifyLoginStatus();
    }

    @Test(priority = 2)
    @Override
    public void successLoginWithValidPhoneNumber() {
        loginPage.loginPage(LoginType.LOGIN_PHONE_NUMBER, "", "", loginModel.validPhoneNumber);
        loginPage.verifyLoginStatus();
    }

    @Test(priority = 3)
    @Override
    public void failedLoginWithInvalidCredential() {
        loginPage.loginPage(LoginType.LOGIN_USERNAME_EMAIL, loginModel.invalidEmail, loginModel.password, null);
        loginPage.verifyCredentialLogin(LoginType.LOGIN_USERNAME_EMAIL);
    }

    @Test(priority = 4)
    @Override
    public void failedLoginWithEmptyEmail() {
        loginPage.loginPage(LoginType.LOGIN_USERNAME_EMAIL, "", loginModel.password, null);
        loginPage.verifyCredentialLogin(LoginType.LOGIN_USERNAME_EMAIL);
    }

    @Test(priority = 5)
    @Override
    public void failedLoginWithEmptyPassword() {
        loginPage.loginPage(LoginType.LOGIN_USERNAME_EMAIL, loginModel.email, "", null);
        loginPage.verifyCredentialLogin(LoginType.LOGIN_USERNAME_EMAIL);
    }

    @Test(priority = 6)
    @Override
    public void failedLoginWithEmptyAllField() {
        loginPage.loginPage(LoginType.LOGIN_USERNAME_EMAIL, "", "", null);
        loginPage.verifyCredentialLogin(LoginType.LOGIN_USERNAME_EMAIL);
    }

    @Test(priority = 7)
    @Override
    public void failedLoginWithInvalidPhoneNumber() {
        loginPage.loginPage(LoginType.LOGIN_PHONE_NUMBER, null, null, loginModel.invalidPhoneNumber);
        loginPage.verifyCredentialLogin(LoginType.LOGIN_PHONE_NUMBER);
    }

    @Test(priority = 8)
    @Override
    public void failedLoginWithEmptyPhoneNumber() {
        loginPage.loginPage(LoginType.LOGIN_PHONE_NUMBER, null, null, "");
        loginPage.verifyCredentialLogin(LoginType.LOGIN_PHONE_NUMBER);
    }
}