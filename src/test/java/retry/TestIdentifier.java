package retry;

import org.testng.ITestResult;

public final class TestIdentifier {

    private TestIdentifier() {}

    public static String getTestKey(ITestResult result) {
        return result.getMethod().getQualifiedName();
    }
}
