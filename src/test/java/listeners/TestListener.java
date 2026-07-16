package listeners;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import core.DirectoryManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import reports.ExtentLogger;
import reports.ExtentManager;
import reports.ExtentTestManager;
import utilities.ScreenshotUtility;

public class TestListener implements ITestListener {
    private static final Logger logger = LogManager.getLogger(TestListener.class);
    @Override
    public void onStart(ITestContext context){
        logger.info("========================================");
        logger.info("Execution Started : {}", context.getName());
        logger.info("========================================");
        DirectoryManager.initializeExecutionDirectories();
        ExtentManager.getExtentReports();
    }

    @Override
    public void onTestStart(ITestResult result){
        String testName = result.getTestClass().getRealClass().getSimpleName()+" :: "+result.getMethod().getMethodName();
        logger.info("STARTED : {}", testName);
        ExtentTestManager.setTest(ExtentManager.getExtentReports().createTest(testName));
        ExtentLogger.info("Test started");
    }
    @Override
    public void onTestSuccess(ITestResult result){
        logger.info("PASSED : {}", result.getMethod().getMethodName());
        ExtentLogger.log(Status.PASS, "Test Passed");
    }
    @Override
    public void onTestFailure(ITestResult result){
        logger.error("FAILED : {}", result.getMethod().getMethodName());
        logger.error(result.getThrowable().getMessage(), result.getThrowable());
        String screenshotPath = ScreenshotUtility.captureScreenshot(result.getMethod().getMethodName());
        ExtentLogger.fail(result.getThrowable());
        try {
            ExtentLogger.fail("Screenshot", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        }
        catch (Exception e) {
            logger.warn("Unable to attach screenshot.");
            ExtentLogger.log(Status.WARNING,"Unable to attach screenshot");
        }
        ExtentLogger.log(Status.INFO, "Test Failed");
    }
    @Override
    public void onTestSkipped(ITestResult result){
        logger.warn("SKIPPED : {}", result.getMethod().getMethodName());
        ExtentLogger.skip(result.getThrowable());
    }
    @Override
    public void onFinish(ITestContext context){
        ExtentManager.getExtentReports().flush();
        ExtentTestManager.unload();
        logger.info("Execution Finished");
        logger.info("========================================");
    }


}
