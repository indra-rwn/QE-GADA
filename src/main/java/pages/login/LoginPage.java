package pages.login;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.base.BasePage;

import java.util.concurrent.TimeUnit;


public class LoginPage extends BasePage<LoginModel, LoginLocator> implements LoginSteps {

    public static LoginPage newInstance(WebDriver webDriver, WebDriverWait webDriverWait) {
        return new LoginPage().init(webDriver, webDriverWait);
    }

    @Override
    public LoginPage init(WebDriver webDriver, WebDriverWait webDriverWait) {
        initialInstance(webDriver, webDriverWait);
        return this;
    }

    @Override
    public void setupController() {
        this.mModel = LoginModel.newInstance();

    }

    @Override
    public void setupPathElement() {
        this.mLocator = LoginLocator.newInstance();
    }

    @Override
    public void loginPage(LoginType loginType, String email, String password, String phoneNumber) {
        switch (loginType.login) {
            case "Phone number":
                webDriverWait.until(ExpectedConditions.elementToBeClickable(mLocator.phoneNumber));
                webDriver.findElement(mLocator.phoneNumber).sendKeys(phoneNumber);
                webDriver.findElement(mLocator.buttonMasukPhone).click();
                break;
            case "Email":
                webDriverWait.until(ExpectedConditions.elementToBeClickable(mLocator.tabUserNameEmail));
                webDriver.findElement(mLocator.tabUserNameEmail).click();
                webDriver.findElement(mLocator.inputEmail).sendKeys(email);
                webDriver.findElement(mLocator.inputPassword).sendKeys(password);
                webDriver.findElement(mLocator.buttonMasukEmail).click();
                break;
        }
    }

    @Override
    public void verifyLoginStatus() {
        webDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        if (webDriver.findElements(mLocator.bannerPage).size() != 0) {
            String getStatusMessage = webDriver.findElement(mLocator.statusLogin).getText();
            switch (getStatusMessage) {
                case "PENDING":
                    logInfo("Verification is in progress");
                    break;
                case "FAILED":
                    logInfo("Verification is failed");
                    break;
            }
        } else {
            logPassed("Login successfully");
        }
    }

    @Override
    public void verifyCredentialLogin(LoginType loginType) {
        webDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        switch (loginType.login) {
            case "Phone number":
                if (webDriver.findElements(mLocator.alerPhoneNumber).size() != 0) {
                    String errorMessage = webDriver.findElement(mLocator.alerPhoneNumber).getText();
                    logPassed("Error message: " + errorMessage);
                } else {
                    String getErrorMessage = webDriver.findElement(mLocator.alertSnackbar).getText();
                    logPassed("Error message: " + getErrorMessage);
                }
                break;
            case "Email":
                if (webDriver.findElements(mLocator.alertEmail).size() !=0 ) {
                    int getSizeErrorMessage = webDriver.findElements(mLocator.alertEmail).size();
                    for (int i = 1; i <= getSizeErrorMessage; i++) {
                        String getErrorMessage = webDriver.findElement(mLocator.alertEmailNotification(i)).getText();
                        logPassed("Error message: " + getErrorMessage);
                    }
                } else {
                    String getErrorMessage = webDriver.findElement(mLocator.alertSnackbar).getText();
                    logPassed("Error message: " + getErrorMessage);
                }
                break;
        }
    }
}