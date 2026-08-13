package reports;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.MarkupHelper;
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

    /**
     * Adds a formatted code block to the current Extent test.
     * Useful for API request/response payloads where preserving formatting
     * is more readable than writing the content as a single log line.
     */
    public static void codeBlock(String title, String content) {
       ExtentTest node = ExtentTestManager.getTest()
                .createNode(title);

        node.log(
                Status.INFO,
                MarkupHelper.createCodeBlock(
                        content == null ? "" : content
                )
        );

        node.getModel().setStatus(Status.INFO);
    }


}
