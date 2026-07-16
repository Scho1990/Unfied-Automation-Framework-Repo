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

    private static void validate(){
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
                logger.info("Required property {} not found.",requiredProperty);
                throw new ConfigurationException("Required property '%s' not found.".formatted(requiredProperty));
            }
        }
    }

    private static void validateBrowser(){
        logger.info("validating browser...");
        String browser = ConfigReader.getProperty("browser");
        try {
            BrowserType.valueOf(browser.toUpperCase());
        }
        catch (IllegalArgumentException e){
            logger.info("Browser {} not found.",browser);
            throw new ConfigurationException("Browser '%s' not found.".formatted(browser));
        }
    }

    private static void validateTimeouts(){
        logger.info("validating timeouts...");
        validatePositiveNumber("implicit.wait");
        validatePositiveNumber("explicit.wait");
        validatePositiveNumber("page.load.timeout");
    }

    private static void validatePositiveNumber(String property) {
        int value = ConfigReader.getIntProperty(property);
        if (value <= 0) {
            throw new ConfigurationException("'%s' must be greater than zero.".formatted(property));
        }
    }
}
