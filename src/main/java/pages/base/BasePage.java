package pages.base;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import config.GlobalVariable;
import external.helper.CaptureImage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage<C extends BaseModel, E extends BaseLocator>
        implements BaseModuleContract {

    public GlobalVariable globalVariable;
    public WebDriver webDriver;
    public WebDriverWait webDriverWait;
    public ExtentTest extentTest;

    public C mModel;
    public E mLocator;

    public String currentUrl;

    /**
     * How to initialize controller:
     * call this method setupController() in your Module
     * and create the instance
     * Ex : this.mController = LoginProfileController.getInstance()
     */
    public abstract void setupController();

    /**
     * How to initialize path element:
     * call this method setupPathElement() in your Module
     * and create the instance
     * Ex : this.mPathElement = LoginProfilePathElement.getInstance()
     */
    public abstract void setupPathElement();

    @Override
    public void openBrowser() {
        webDriver.get(currentUrl);
    }

    @Override
    public void closeBrowser() {
        webDriver.quit();
    }

    public C getModel() {
        return mModel;
    }

    public void initialInstance(WebDriver driver, WebDriverWait webDriverWait) {
        this.webDriver = driver;
        this.webDriverWait = webDriverWait;
        globalVariable = new GlobalVariable();
        currentUrl = globalVariable.baseUrl;
        setupController();
        setupPathElement();
    }

    public void initReport(ExtentTest test) {
        this.extentTest = test;
    }

    public void logPassed(String label) {
        try {
            extentTest.log(Status.PASS, MarkupHelper.createLabel(label + " PASSED ", ExtentColor.GREEN));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logWarning(String message) {
        try {
            extentTest.log(Status.WARNING, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logInfo(String message) {
        try {
            extentTest.log(Status.INFO, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logFailed(String name, Throwable throwable) {
        try {
            extentTest.log(Status.FAIL, MarkupHelper.createLabel(name + " FAILED ", ExtentColor.RED));
            if (throwable != null) extentTest.fail(throwable);
            String screenShotPath = CaptureImage.capture(webDriver, this.getClass().getSimpleName());
            if (!screenShotPath.isEmpty()) {
                extentTest.fail("Screenshot : " + extentTest.addScreenCaptureFromPath(screenShotPath));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}