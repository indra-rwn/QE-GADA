package pages.login;

public enum LoginType {
    LOGIN_PHONE_NUMBER("Phone number"),
    LOGIN_USERNAME_EMAIL("Email");

    public String login;

    LoginType(String loginType) {
        this.login = loginType;
    }
}