package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import config.GlobalVariable;
import external.helper.CaptureImage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public abstract class BaseWebDriver {

    public WebDriver webDriver;
    public WebDriverWait webDriverWait;
    public static GlobalVariable globalVariable;
    public static ExtentHtmlReporter htmlReporter;
    public static ExtentReports extentReports;
    public ExtentTest extentTest;
    public ChromeOptions options;
    public int responseCode;
    public String currentUrl;

    public abstract void initInstance();

    public abstract void initPageReport();

    @BeforeSuite
    public void onBeforeSuite(ITestContext context) {
        globalVariable = new GlobalVariable();
        currentUrl = globalVariable.baseUrl;
        initReport(context);
    }

    @Parameters({"Browser"})
    @BeforeMethod
    public void onBeforeMethod(Method method, @Optional("Optional Headless") String browser) {
        createTestReport(method.getName(), "");
        initWebDriver(browser);
        initInstance();
        openBrowser();
        initPageReport();
    }

    @AfterMethod
    public void onAfterMethod(ITestResult result) throws Exception {
        switch (result.getStatus()) {
            case ITestResult.FAILURE:
                logFailed(result.getName(), result.getThrowable());
                break;
            case ITestResult.SUCCESS:
                logPassed(result.getName());
                break;
        }
        webDriver.quit();
    }

    @AfterTest
    public void onAfterTest() {

    }

    @AfterSuite
    public void onAfterSuite() {
        System.out.println("flush report");
        extentReports.flush();
        webDriver.quit();
    }

    public void logFailed(String name, Throwable throwable) throws Exception {
        extentTest.log(Status.FAIL, MarkupHelper.createLabel(name, ExtentColor.RED));
        if (throwable != null) extentTest.fail(throwable);
        String screenShotPath = CaptureImage.capture(webDriver, this.getClass().getSimpleName());
        if (!screenShotPath.isEmpty()) {
            extentTest.fail("Screenshot : " + extentTest.addScreenCaptureFromPath(screenShotPath));
        }
    }

    public void logPassed(String methodName) {
        extentTest.log(Status.PASS, MarkupHelper.createLabel(methodName, ExtentColor.GREEN));
    }

    public void logInfo(String methodName) {
        extentTest.log(Status.INFO, MarkupHelper.createLabel(methodName, ExtentColor.GREY));
    }

    public void createTestReport(String name, String desription) {
        extentTest = extentReports.createTest(name, desription);
    }

    private void openBrowser() {
        webDriver.navigate().to(currentUrl);
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(webDriver.getCurrentUrl()).openConnection();
            httpURLConnection.setRequestMethod("HEAD");
            httpURLConnection.connect();
            responseCode = httpURLConnection.getResponseCode();
            logInfo("Http response code: " + responseCode);
        } catch (Exception e) {
            e.printStackTrace();
            responseCode = 0;
            webDriver.quit();
        }

        if (responseCode == 0 || responseCode >= 500) {
            try {
                logFailed("Http response code: " + responseCode, null);
                webDriver.quit();
            } catch (Exception e) {
                e.printStackTrace();
                webDriver.quit();
            }
        }
    }

    private void setSystemProperties() {
        String operatingSystem = System.getProperty("os.name").toLowerCase();
        if (operatingSystem.indexOf("win") >= 0) {
            System.out.println("This is Windows");
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/lib/chromedriver/chromedriver_windows.exe");
        } else if (operatingSystem.indexOf("mac") >= 0) {
            System.out.println("This is Mac");
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/lib/chromedriver/chromedriver_mac");
        } else if (operatingSystem.indexOf("nix") >= 0
                || operatingSystem.indexOf("nux") >= 0
                || operatingSystem.indexOf("aix") > 0) {
            System.out.println("This is Unix or Linux");
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/lib/chromedriver/chromedriver_linux");
        }
    }

    private void initWebDriver(String browser) {
        setSystemProperties();
        Map<String, Object> prefs = new HashMap<>();
        //1-Allow, 2-Block, 0-default
        prefs.put("profile.default_content_setting_values.notifications", 1);
        if (browser.equalsIgnoreCase("--headless")) {
            options = new ChromeOptions();
            options.setHeadless(true);
            options.addArguments("--no-sandbox");
            options.addArguments("--window-size=1280,1024");
            options.setExperimentalOption("prefs", prefs);
        } else {
            options = new ChromeOptions();
            options.setExperimentalOption("prefs", prefs);
        }
        webDriver = new ChromeDriver(options);
        if (!browser.equalsIgnoreCase("--headless")) {
            webDriver.manage().window().maximize();
        }
        webDriverWait = new WebDriverWait(webDriver, 15);
    }

    private void initReport(ITestContext context) {
        if (extentReports == null) {
            String datePattern = "EEEE-dd-MMM-yyyy_HH:mm:ss.SSS";
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern(datePattern).withLocale(new Locale("id")).withZone(ZoneId.systemDefault());
            Instant now = Instant.now();
            String formatted = myFormatObj.format(now);
            System.out.println(formatted);
            String filename = context.getCurrentXmlTest().getSuite().getName();
            if (filename == null || filename.isEmpty() || filename.toLowerCase().contains("default")) {
                filename = this.getClass().getSimpleName();
            }
            htmlReporter = new ExtentHtmlReporter(
                    System.getProperty("user.dir") + "/report/" +
                            filename + "_" +
                            formatted +
                            ".html"
            );
            htmlReporter.config().setReportName("QE-GADA");
            htmlReporter.config().setTheme(Theme.DARK);
            htmlReporter.config().setTimeStampFormat(datePattern);
            extentReports = new ExtentReports();
            extentReports.attachReporter(htmlReporter);
        }
    }
}