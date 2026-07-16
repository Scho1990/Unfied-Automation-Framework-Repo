package lifecycle;

import config.ConfigReader;
import enums.BrowserType;
import exceptions.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;

public final class ConfigurationValidator {

    private static final Logger logger = LogManager.getLogger(ConfigurationValidator.class);
    private static final List<String> REQUIRED_PROPERTIES = List.of(
            "erail.url",
            "orangehrm.url",
            "browser",
            "headless",
            "explicit.wait",
            "page.load.timeout",
            "expected.station.file",
            "login.data.file");

    private ConfigurationValidator() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static void validate(){
        logger.info("Validating framework configuration...");
        validateRequiredProperties();
        validateBrowser();
        validateTimeouts();
        logger.info("Framework configuration validated successfully.");
    }

    private static void validateRequiredProperties(){
        logger.info("Validating required properties...");
        for(String requiredProperty : REQUIRED_PROPERTIES){
            String value  = ConfigReader.getProperty(requiredProperty);
            if(value == null || value.isBlank()){
                logger.error("Required property {} not found.",requiredProperty);
                throw new ConfigurationException("Required configuration property '%s' is missing or empty.".formatted(requiredProperty));
            }
        }
    }

    private static void validateBrowser(){
        logger.info("validating browser...");
        String browser = ConfigReader.getPropertyOrSystem("browser");
        try {
            BrowserType.valueOf(browser.toUpperCase());
        }
        catch (IllegalArgumentException e){
            logger.error("Browser {} not found.",browser);
            throw new ConfigurationException("Unsupported browser '%s'. Supported browsers are : %s."
                    .formatted(browser,java.util.Arrays.toString(BrowserType.values())));
        }
    }

    private static void validateTimeouts(){
        logger.info("validating timeouts...");
        validatePositiveNumber("explicit.wait");
        validatePositiveNumber("page.load.timeout");
    }

    private static void validatePositiveNumber(String property) {
        int value = ConfigReader.getIntProperty(property);
        if (value <= 0) {
            logger.error("Property '{}' must be greater than zero.",property);
            throw new ConfigurationException("'%s' must be greater than zero.".formatted(property));
        }
    }
}
