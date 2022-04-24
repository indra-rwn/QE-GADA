package pages.base;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public interface BaseSteps<T extends BasePage, M extends BaseModel> {
    public T init(WebDriver webDriver, WebDriverWait webDriverWait);

    public M getModel();

    public void initReport(ExtentTest extentTest);
}