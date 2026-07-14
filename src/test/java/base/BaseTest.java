package base;

import core.DirectoryManager;
import driver.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public class BaseTest {

    @BeforeSuite
    public void SuiteSetUp(){

    }

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        DriverFactory.initializeDriver();
    }

    @AfterMethod(alwaysRun = true,enabled = true)
    public void tearDown() {
        DriverFactory.quitDriver();
    }

    protected WebDriver getDriver() {
        return DriverFactory.getDriver();
    }

}
