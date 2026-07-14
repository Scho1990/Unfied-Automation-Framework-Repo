package reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import core.FrameworkPaths;

public final class ExtentManager {

    private static ExtentReports extentReports;
    private ExtentManager() {}

    public static ExtentReports getExtentReports() {
        if (extentReports == null) {
            synchronized (ExtentManager.class) {
                if (extentReports == null) {
                    ExtentSparkReporter sparkReporter = new ExtentSparkReporter(FrameworkPaths.getReportPath().toString());
                    sparkReporter.config().setDocumentTitle("Execution Report");
                    sparkReporter.config().setReportName("QA Assignment Report");

                    extentReports = new ExtentReports();
                    extentReports.attachReporter(sparkReporter);
                    extentReports.setSystemInfo("Framework", "Selenium Java");
                    extentReports.setSystemInfo("Environment", System.getProperty("env","QA"));
                    extentReports.setSystemInfo("Browser", System.getProperty("browser"));
                }
            }
        }
        return extentReports;
    }
}
