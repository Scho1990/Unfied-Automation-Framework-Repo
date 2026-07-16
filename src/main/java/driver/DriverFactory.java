package driver;

import config.ConfigReader;
import enums.BrowserType;
import exceptions.DriverInitializationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;
import java.util.Objects;

public final class DriverFactory {
    private static final Logger logger = LogManager.getLogger(DriverFactory.class);
    private DriverFactory() {}

    public static void initializeDriver(){
        logger.info("Initializing WebDriver...");
        BrowserType browserType = getBrowserType();
        boolean headless = ConfigReader.getBooleanProperty("headless");
        WebDriver driver;
        switch (browserType) {
            case CHROME:
                logger.info("Launching Chrome browser");
                driver = new ChromeDriver(BrowserOptionsFactory.getChromeOptions(headless));
                logger.info("Chrome browser launched successfully");
                break;

            case FIREFOX:
                logger.info("Launching Firefox browser");
                driver = new FirefoxDriver( BrowserOptionsFactory.getFirefoxOptions(headless));
                driver.manage().window().maximize();
                logger.info("Firefox browser launched successfully");
                break;

                default:
                    throw new DriverInitializationException("Unsupported browser type: %s".formatted(browserType));
        }
        int pageLoadTimeout = ConfigReader.getIntProperty("page.load.timeout");
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoadTimeout));
        DriverManager.setDriver(driver);
        logger.info("Page load timeout configured as {} seconds", pageLoadTimeout);
    }

    public static WebDriver getDriver() {
        return DriverManager.getDriver();
    }

    private static BrowserType getBrowserType(){
        String browser = ConfigReader.getPropertyOrSystem("browser");
        BrowserType browserType;
        try {
            browserType = BrowserType.valueOf(browser.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new DriverInitializationException("Unsupported browser '%s'. Supported browsers are: %s."
                            .formatted(browser, java.util.Arrays.toString(BrowserType.values())), ex);
        }
        return browserType;
    }

    public static void quitDriver(){
        if (Objects.nonNull(DriverManager.getDriver())) {
            logger.info("Closing browser");
            DriverManager.getDriver().quit();
            DriverManager.removeDriver();
            logger.info("Browser closed successfully");
        }
    }
}
