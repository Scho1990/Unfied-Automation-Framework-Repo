package base;

import driver.DriverFactory;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.apache.logging.log4j.LogManager;

public class BaseTest {
private static final Logger logger = LogManager.getLogger(BaseTest.class);
    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        logger.info("BeforeMethod -> Thread ID : {}", Thread.currentThread().threadId());
        DriverFactory.initializeDriver();
    }

    @AfterMethod(alwaysRun = true,enabled = true)
    public void tearDown() {
        logger.info("AfterMethod -> Thread ID : {}", Thread.currentThread().threadId());
        DriverFactory.quitDriver();
    }

    protected WebDriver getDriver() {
        return DriverFactory.getDriver();
    }

}
