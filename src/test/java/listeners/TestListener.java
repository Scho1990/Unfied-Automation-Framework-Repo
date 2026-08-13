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
import reports.*;
import retry.*;
import utilities.ScreenshotUtility;
import java.time.Instant;

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
        TestType testType = TestTypeResolver.resolve(result);

        logger.info("STARTED : {} | Test Type : {} | Thread ID : {}",
                testName,
                testType,
                Thread.currentThread().threadId());

        ExtentTestManager.setTest(ExtentManager.getExtentReports().createTest(testName));
        ExtentLogger.assignCategory(testType.name());
        ExtentLogger.info("Test started");
        ExtentLogger.info("Test Type : "+ testType);
        ExtentLogger.info("Thread ID : " + Thread.currentThread().threadId());
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
        TestType testType = TestTypeResolver.resolve(result);

        logger.debug("RetryScheduled Attribute : {}",
                result.getAttribute(RetryConstants.RETRY_SCHEDULED));
        logger.debug("Retry Count : {}",
                RetryStatistics.getRetryCount(testName));
        logger.debug("Was Retried : {}",
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

        ExtentLogger.fail(result.getThrowable());

        if(testType == TestType.UI){
            attachScreenshot(testName);
        }
        else if(testType == TestType.API){
            ExtentLogger.info("API test failure detected. UI screenshot is not applicable.");
        }
        else {
            ExtentLogger.warning("Unable to determine test type. Screenshot was not attempted.");
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

        ExecutionSummary.logSummary(context.getSuite().getName(), passed, failed, skipped, Instant.now());

        ExtentTestManager.setTest(
                ExtentManager.getExtentReports()
                        .createTest("Retry Summary"));
        RetrySummary.logSummary();

        ExtentManager.getExtentReports().flush();
        ExtentTestManager.unload();
        logger.info("Execution Finished");
        logger.info("========================================");
    }

    // UI/logging
    private String getDisplayName(ITestResult result){
        return result.getTestClass().getRealClass().getSimpleName()+" :: "+result.getMethod().getMethodName();
    }
    // framework internals
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

    private void attachScreenshot(String testName) {
        try {
            String screenshotPath = ScreenshotUtility.captureScreenshot(testName);

            if (screenshotPath == null || screenshotPath.isBlank()) {
                ExtentLogger.warning("Screenshot was not available.");
                return;
            }

            ExtentLogger.fail(
                    "Screenshot",
                    MediaEntityBuilder
                            .createScreenCaptureFromPath(screenshotPath)
                            .build()
            );
        } catch (Exception e) {
            logger.warn("Unable to attach screenshot.", e);
            ExtentLogger.warning("Unable to attach screenshot.");
        }
    }



}
