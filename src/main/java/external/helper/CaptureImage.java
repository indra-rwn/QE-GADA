package external.helper;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CaptureImage {

    public static String capture(WebDriver webDriver, String screenshotname) {
        String path = "";
        try {
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH:mm:ss.SSS").withLocale(Locale.getDefault()).withZone(ZoneId.systemDefault());
            Instant now = Instant.now();
            String formatted = myFormatObj.format(now);

            TakesScreenshot ts = (TakesScreenshot) webDriver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            path = System.getProperty("user.dir") +
                    "/screenshots/" +
                    screenshotname + "_" + formatted + ".png";
            File destination = new File(path);
            FileUtils.copyFile(source, destination);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }
}