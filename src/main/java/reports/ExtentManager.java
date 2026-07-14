package reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
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
        return sparkReporter;
    }

    private static void attachReporters() {
        ExtentSparkReporter sparkReporter = createSparkReporter();
        extentReports.attachReporter(sparkReporter);
    }

    // Extent Reports System Information
    private static void addSystemInformation(){
        extentReports.setSystemInfo("Framework", "Unified Automation Framework v2.0");
        extentReports.setSystemInfo("Environment", System.getProperty("env","QA"));
        extentReports.setSystemInfo("Browser", System.getProperty("browser","Chrome"));
        extentReports.setSystemInfo("Java Version", System.getProperty("java.version"));
        extentReports.setSystemInfo("OS", System.getProperty("os.name"));
        extentReports.setSystemInfo("Execution Id", ExecutionContext.getExecutionId());
    }
}
