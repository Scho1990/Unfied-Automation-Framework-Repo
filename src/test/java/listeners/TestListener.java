package listeners;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import constants.FrameworkConstants;
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
import retry.*;
import utilities.ScreenshotUtility;

import java.time.Instant;
import java.util.Map;

public class TestListener implements ITestListener {
    private static final Logger logger = LogManager.getLogger(TestListener.class);
    @Override
    public void onStart(ITestContext context){
        logger.info("Starting Test Execution : {}", context.getName());
        RetryStatistics.reset();
        FrameworkBootstrap.initialize();
    }

    @Override
    public void onTestStart(ITestResult result){
        String testName = getDisplayName(result);
        logger.info("STARTED : {}", testName);
        ExtentTestManager.setTest(ExtentManager.getExtentReports().createTest(testName));
        ExtentLogger.info("Test started");
    }
    @Override
    public void onTestSuccess(ITestResult result){
        String testName = getTestName(result);
        logger.info("PASSED : {}", testName);
        ExtentLogger.log(Status.PASS, "Test Passed");
        if (wasRetried(testName)){
            RetryStatistics.incrementPassedAfterRetry();
            logger.info(
                    "PASSED : '{}' after {} retry attempt(s).",
                    testName,
                    RetryStatistics.getRetryCount(testName)
            );
        }

    }
    @Override
    public void onTestFailure(ITestResult result){
        String testName = getTestName(result);
        logger.info("RetryScheduled Attribute : {}",
                result.getAttribute(RetryConstants.RETRY_SCHEDULED));
        logger.info("Retry Count : {}",
                RetryStatistics.getRetryCount(testName));

        logger.info("Was Retried : {}",
                wasRetried(testName));
        Boolean retryScheduled = (Boolean) result.getAttribute(RetryConstants.RETRY_SCHEDULED);
        if (Boolean.FALSE.equals(retryScheduled)) {
            if (wasRetried(testName)) {
                RetryStatistics.incrementFailedAfterRetry();
                logger.error(
                        "Test '{}' failed after exhausting {} retry attempt(s).",
                        testName,
                        RetryStatistics.getRetryCount(testName)
                );
            }
        }
        logger.error("FAILED : {}", testName);
        logger.error(result.getThrowable().getMessage(), result.getThrowable());
        String screenshotPath=null;
        try {
            screenshotPath = ScreenshotUtility.captureScreenshot(testName);
        }
        catch (Exception e){
            logger.warn("Screenshot capture failed",e);
        }
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
        logger.warn("SKIPPED : {}", getTestName(result));
        ExtentLogger.skip(result.getThrowable());
    }
    @Override
    public void onFinish(ITestContext context){
        int passed = context.getPassedTests().size();
        int failed = context.getFailedTests().size();
        int skipped = context.getSkippedTests().size();

        ExecutionSummary.logExecutionSummary(context.getSuite().getName(), passed, failed, skipped, Instant.now());
        RetrySummary.logSummary();

        ExtentManager.getExtentReports().flush();
        ExtentTestManager.unload();
        logger.info("Execution Finished");
        logger.info("========================================");
    }

    private String getDisplayName(ITestResult result){
        return result.getTestClass().getRealClass().getSimpleName()+" :: "+result.getMethod().getMethodName();
    }

    private String getTestName(ITestResult result){
        return TestIdentifier.getTestKey(result);
    }

    /**
     * Checks whether the specified test method was retried at least once.
     *
     * @param testName Name of the TestNG test method.
     * @return true if the test was retried, otherwise false.
     */
    private boolean wasRetried(String testName){
        return RetryStatistics.getRetryCount(testName) > 0;
    }


}
