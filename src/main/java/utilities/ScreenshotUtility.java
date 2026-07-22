package utilities;

import core.FrameworkPaths;
import driver.DriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ScreenshotUtility {
    private ScreenshotUtility() {
    }

    /**
     * Capture screenshot and return file Name.
     */
    public static String captureScreenshot(String testName) {
        try {

            WebDriver driver = DriverManager.getDriver();

            if (driver == null) {
                return null;
            }

            String screenshotDirectory =
                    FrameworkPaths.getScreenshotDirectory().toString();

            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS"));

            long threadId = Thread.currentThread().threadId();

            String fileName =
                    testName + "_Thread-" + threadId + "_" + timestamp + ".png";

            File source =
                    ((TakesScreenshot) driver)
                            .getScreenshotAs(OutputType.FILE);

            File destination =
                    new File(screenshotDirectory, fileName);

            FileUtils.copyFile(source, destination);

            return "screenshots/" + fileName;

        } catch (Exception e) {

            LogManager.getLogger(ScreenshotUtility.class)
                    .warn("Unable to capture screenshot", e);

            return null;

        }
    }
}
