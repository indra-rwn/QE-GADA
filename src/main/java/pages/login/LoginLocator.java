package pages.login;

import org.openqa.selenium.By;
import pages.base.BaseLocator;

public class LoginLocator extends BaseLocator {

    public static LoginLocator newInstance() {
        return new LoginLocator();
    }

    public final By phoneNumber = getElementById("phoneNumber");
    public final By inputEmail = getElementById("email");
    public final By inputPassword = getElementById("password");
    public final By alertSnackbar = getElementById("notistack-snackbar");

    public By tabUserNameEmail = getElementByXpath("//*[.=\"Username/Email\"]");
    public By buttonMasukPhone = getElementByXpath("(//*[.=\"Masuk\"])[1]");
    public By buttonMasukEmail = getElementByXpath("(//*[.=\"Masuk\"])[2]");
    public By alerPhoneNumber = getElementByXpath("//p[@id=\"phoneNumber\"]");
    public By alertEmail = getElementByXpath("//*[@class=\"MuiFormHelperText-root Mui-error MuiFormHelperText-sizeMedium MuiFormHelperText-contained css-1qe7bg7\"]");

    public By alertEmailNotification(int i) {
        return getElementByXpath("(//*[@class=\"MuiFormHelperText-root Mui-error MuiFormHelperText-sizeMedium MuiFormHelperText-contained css-1qe7bg7\"])[" + i + "]");
    }

    /** this locator below is fake, if you want to verify this locator change this value */
    public final By bannerPage = getElementById("banner-home");
    public final By statusLogin = getElementById("status-login");
}