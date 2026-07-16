package reports;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.model.Media;

public final class ExtentLogger {

    private ExtentLogger() {}

    public static void info(String message) {

        ExtentTestManager.getTest().info(message);
    }

    public static void pass(String message) {

        ExtentTestManager.getTest().pass(message);
    }

    public static void fail(String message) {

        ExtentTestManager.getTest().fail(message);
    }

    public static void fail(Throwable e) {

        ExtentTestManager.getTest().fail(e);
    }

    public static void fail(String details, Media media) {

        ExtentTestManager.getTest().fail(details, media);
    }

    public static void warning(String message) {

        ExtentTestManager.getTest().warning(message);

    }

    public static void skip(Throwable e) {

        ExtentTestManager.getTest().skip(e);

    }


    public static void log(Status status, String message) {

        ExtentTestManager.getTest().log(status, message);

    }

    public static void assignCategory(String category) {
        ExtentTestManager.getTest().assignCategory(category);
    }

}
