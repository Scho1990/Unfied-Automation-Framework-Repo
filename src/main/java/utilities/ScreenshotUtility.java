package utilities;

import config.ConfigReader;
import core.FrameworkPaths;
import driver.DriverManager;
import exceptions.ScreenshotException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ScreenshotUtility {
    private ScreenshotUtility() {}

    /**
     * Capture screenshot and return file Name.
     */
    public static String captureScreenshot(String testName) {
        String screenshotDirectory = FrameworkPaths.getScreenshotDirectory().toString();
        String timeStamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS"));
        long threadId = Thread.currentThread().threadId();
        String fileName = testName + "_Thread-" + threadId + "_" + timeStamp + ".png";
        File sourceFile = ((TakesScreenshot) DriverManager.getDriver())
                .getScreenshotAs(OutputType.FILE);
        File destinationFile = new File(screenshotDirectory, fileName);
        try {
            FileUtils.copyFile(sourceFile, destinationFile);
        } catch (IOException e) {
            throw new ScreenshotException(
                    "Unable to capture screenshot.", e);
        }
        return "screenshots/" + fileName;
    }
}
