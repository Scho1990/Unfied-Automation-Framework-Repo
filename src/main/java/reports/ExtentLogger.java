package reports;

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

    public static void warning(String message) {
        ExtentTestManager.getTest().warning(message);
    }

    public static void assignCategory(String category) {
        ExtentTestManager.getTest().assignCategory(category);
    }

}
