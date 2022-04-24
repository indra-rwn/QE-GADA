package website.login;

public interface LoginTestCases {

    void successLoginWithEmailAndPassword();
    void successLoginWithValidPhoneNumber();
    void failedLoginWithInvalidCredential();
    void failedLoginWithEmptyEmail();
    void failedLoginWithEmptyPassword();
    void failedLoginWithEmptyAllField();
    void failedLoginWithInvalidPhoneNumber();
    void failedLoginWithEmptyPhoneNumber();
}