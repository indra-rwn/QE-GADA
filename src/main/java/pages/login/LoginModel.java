package pages.login;

import com.github.javafaker.Faker;
import pages.base.BaseModel;

import java.util.Locale;

public class LoginModel extends BaseModel {

    public static LoginModel newInstance() {
        return new LoginModel();
    }

    Faker faker = new Faker(new Locale("in-ID"));

    private String mainIdentifier;

    public String validPhoneNumber = "081223456789";
    public String email = "mutia.husnarahmatun@gmail.com";
    public String password = "Mutia12345";
    public String invalidEmail = faker.internet().safeEmailAddress();
    public String invalidPhoneNumber = faker.phoneNumber().phoneNumber();

    public String getMainIdentifier() {
        return mainIdentifier;
    }

    public void setMainIdentifier(String mainIdentifier) {
        this.mainIdentifier = mainIdentifier;
    }
}