package driver;

import config.ConfigReader;
import constants.FrameworkConstants;
import enums.BrowserType;
import enums.ExecutionType;
import exceptions.DriverInitializationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.Objects;

public final class DriverFactory {

    private static final Logger logger = LogManager.getLogger(DriverFactory.class);

    private DriverFactory() {
    }

    /**
     * Initialize WebDriver based on execution mode.
     */
    public static void initializeDriver() {

        BrowserType browserType = getBrowserType();
        ExecutionType executionType = getExecutionType();
        boolean headless = ConfigReader.getBooleanProperty(FrameworkConstants.HEADLESS);

        logger.info("====================================================");
        logger.info("Initializing WebDriver");
        logger.info("Execution Type : {}", executionType);
        logger.info("Browser        : {}", browserType);
        logger.info("Headless       : {}", headless);
        logger.info("Thread Id      : {}", Thread.currentThread().threadId());
        logger.info("====================================================");

        WebDriver driver = createDriver(executionType, browserType, headless);
        configureDriver(driver);
        DriverManager.setDriver(driver);
        logger.info("Driver initialized successfully.");
    }

    public static WebDriver getDriver() {
        return DriverManager.getDriver();
    }

    private static WebDriver createDriver(ExecutionType executionType,
                                          BrowserType browserType,
                                          boolean headless) {
        MutableCapabilities options = getBrowserOptions(browserType, headless);
        return switch (executionType) {
            case LOCAL -> createLocalDriver(browserType, options);
            case GRID -> createRemoteDriver(options);
            case BROWSERSTACK ->
                    throw new UnsupportedOperationException("BrowserStack implementation is pending.");
            case SELENOID_GRID ->
                    throw new UnsupportedOperationException("Selenoid Grid implementation is pending.");
        };
    }

    /**
     * Create Local WebDriver
     */
    private static WebDriver createLocalDriver(BrowserType browserType, MutableCapabilities options) {
        boolean headless = ConfigReader.getBooleanProperty(FrameworkConstants.HEADLESS);
        logger.info("Launching {} browser in LOCAL mode", browserType);
        return switch (browserType) {
            case CHROME ->
                    new ChromeDriver(BrowserOptionsFactory.getChromeOptions(headless));
            case FIREFOX ->
                    new FirefoxDriver(BrowserOptionsFactory.getFirefoxOptions(headless));
            case EDGE ->
                    new EdgeDriver(BrowserOptionsFactory.getEdgeOptions(headless));
        };
    }

    /**
     * Create Selenium Grid Driver
     */
    private static WebDriver createRemoteDriver(MutableCapabilities options) {
        logger.info("Launching browser on Selenium Grid");
        try {
            URL gridUrl = new URL(ConfigReader.getProperty(FrameworkConstants.GRID_URL));
            return new RemoteWebDriver(gridUrl, options);
        } catch (MalformedURLException e) {
            throw new DriverInitializationException(
                    "Invalid Selenium Grid URL : "
                            + ConfigReader.getProperty(FrameworkConstants.GRID_URL), e);
        }
    }

    /**
     * Returns Browser Options
     */
    private static MutableCapabilities getBrowserOptions(BrowserType browserType,
                                                         boolean headless) {
        return switch (browserType) {
            case CHROME ->
                    BrowserOptionsFactory.getChromeOptions(headless);
            case FIREFOX ->
                    BrowserOptionsFactory.getFirefoxOptions(headless);
            case EDGE ->
                    BrowserOptionsFactory.getEdgeOptions(headless);
        };
    }

    /**
     * Configure WebDriver
     */
    private static void configureDriver(WebDriver driver) {
        int pageLoadTimeout = ConfigReader.getIntProperty(FrameworkConstants.PAGE_LOAD_TIMEOUT);
        driver.manage()
                .timeouts()
                .pageLoadTimeout(Duration.ofSeconds(pageLoadTimeout));
        driver.manage().window().maximize();
        logger.info("Page Load Timeout : {} seconds", pageLoadTimeout);
    }

    /**
     * Browser Type
     */
    private static BrowserType getBrowserType() {
        String browser = ConfigReader.getPropertyOrSystem(FrameworkConstants.BROWSER);
        try {
            return BrowserType.valueOf(browser.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new DriverInitializationException(
                    String.format("Unsupported browser '%s'. Supported browsers : %s", browser, Arrays.toString(BrowserType.values())), ex);
        }
    }

    /**
     * Execution Type
     */
    private static ExecutionType getExecutionType() {
        String execution = ConfigReader.getPropertyOrSystem(FrameworkConstants.EXECUTION);
        try {
            return ExecutionType.valueOf(execution.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new DriverInitializationException("Invalid execution type : " + execution, ex);
        }
    }

    /**
     * Quit Driver
     */
    public static void quitDriver() {
        if (Objects.nonNull(DriverManager.getDriver())) {
            try {
                logger.info("Closing browser");
                DriverManager.getDriver().quit();
            } finally {
                DriverManager.removeDriver();
                logger.info("Browser closed successfully.");
            }
        }
    }
}