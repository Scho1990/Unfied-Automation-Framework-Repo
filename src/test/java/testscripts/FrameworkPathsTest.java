package testscripts;

import base.BaseTest;
import core.DirectoryManager;
import core.FrameworkPaths;
import org.testng.annotations.Test;

public class FrameworkPathsTest extends BaseTest {

    @Test
    public void verifyFrameworkPaths() {
        DirectoryManager.initializeExecutionDirectories();
        System.out.println(FrameworkPaths.getExecutionDirectory());
      //  System.out.println(FrameworkPaths.getExtentReportPath());
    }
}
