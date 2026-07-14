package driver;

import config.ConfigReader;
import enums.BrowserType;
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
        BrowserType browserType = BrowserType.valueOf(ConfigReader.getPropertyOrSystem("browser").toUpperCase());
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
                    throw new IllegalArgumentException("Unsupported browser: " + browserType);
        }

        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(
                ConfigReader.getIntProperty("page.load.timeout")));
        DriverManager.setDriver(driver);
        logger.info("Page load timeout configured as {} seconds",
                ConfigReader.getIntProperty("page.load.timeout"));
    }

    public static WebDriver getDriver() {
        return DriverManager.getDriver();
    }

    public static void quitDriver(){
        if (Objects.nonNull(DriverManager.getDriver())) {
            logger.info("Closing browser");
            DriverManager.getDriver().quit();
            DriverManager.unloadDriver();
            logger.info("Browser closed successfully");
        }
    }
}
