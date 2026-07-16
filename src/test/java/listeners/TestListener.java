package listeners;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import lifecycle.ExecutionSummary;
import lifecycle.FrameworkBootstrap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import reports.ExtentLogger;
import reports.ExtentManager;
import reports.ExtentTestManager;
import utilities.ScreenshotUtility;

import java.time.Instant;

public class TestListener implements ITestListener {
    private static final Logger logger = LogManager.getLogger(TestListener.class);
    @Override
    public void onStart(ITestContext context){
        logger.info("Starting Test Execution : {}", context.getName());
        FrameworkBootstrap.initialize();
    }

    @Override
    public void onTestStart(ITestResult result){
        String testName = getTestName(result);
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
        int passed = context.getPassedTests().size();
        int failed = context.getFailedTests().size();
        int skipped = context.getSkippedTests().size();

        ExecutionSummary.logExecutionSummary(context.getSuite().getName(), passed, failed, skipped, Instant.now());

        ExtentManager.getExtentReports().flush();
        ExtentTestManager.unload();
        logger.info("Execution Finished");
        logger.info("========================================");
    }

    private String getTestName(ITestResult result){
        return result.getTestClass().getRealClass().getSimpleName()+" :: "+result.getMethod().getMethodName();
    }


}
