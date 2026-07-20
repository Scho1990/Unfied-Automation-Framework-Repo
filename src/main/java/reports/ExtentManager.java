package reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import core.ExecutionContext;
import core.FrameworkPaths;
import reports.constants.ReportConstants;

import java.nio.file.Path;

public final class ExtentManager {

    private static ExtentReports extentReports;
    private ExtentManager() {}

    public static ExtentReports getExtentReports() {
        if (extentReports == null) {
            synchronized (ExtentManager.class) {
                if (extentReports == null) {
                    extentReports = new ExtentReports();
                    attachReporters();
                    addSystemInformation();
                }
            }
        }
        return extentReports;
    }

    private static ExtentSparkReporter createSparkReporter(){
        Path reportFile = FrameworkPaths.getExecutionDirectory().resolve(ReportConstants.REPORT_FILE_NAME );
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportFile.toString());
        sparkReporter.config().setDocumentTitle(ReportConstants.DOCUMENT_TITLE);
        sparkReporter.config().setReportName(ReportConstants.REPORT_NAME);
        sparkReporter.config().setTheme(Theme.DARK);
        return sparkReporter;
    }

    private static void attachReporters() {
        ExtentSparkReporter sparkReporter = createSparkReporter();
        extentReports.attachReporter(sparkReporter);
    }

    // Extent Reports System Information
    private static void addSystemInformation(){
        extentReports.setSystemInfo("Framework", "Unified Automation Framework "+ExecutionContext.getFrameworkVersion());
        extentReports.setSystemInfo("Framework Version", ExecutionContext.getFrameworkVersion());
        extentReports.setSystemInfo("Username", ExecutionContext.getUserName());
        extentReports.setSystemInfo("Environment", ExecutionContext.getEnvironment().toUpperCase());
        extentReports.setSystemInfo("Build Number",System.getenv().getOrDefault("BUILD_NUMBER", ExecutionContext.getExecutionMode().toUpperCase()));
        extentReports.setSystemInfo("Job Name",System.getenv().getOrDefault("JOB_NAME",ExecutionContext.getExecutionMode().toUpperCase()));
        extentReports.setSystemInfo("Build URL",System.getenv().getOrDefault("BUILD_URL",ExecutionContext.getExecutionMode().toUpperCase()));
        extentReports.setSystemInfo("Browser", ExecutionContext.getBrowser().toString());
        extentReports.setSystemInfo("Java Version", ExecutionContext.getJavaVersion());
        extentReports.setSystemInfo("OS", ExecutionContext.getOsName());
        extentReports.setSystemInfo("OS Version", ExecutionContext.getOsVersion());
        extentReports.setSystemInfo("Execution Id", ExecutionContext.getExecutionId());
    }
}
