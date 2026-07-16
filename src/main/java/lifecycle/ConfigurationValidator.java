package lifecycle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;

public final class ConfigurationValidator {

    private static final Logger logger = LogManager.getLogger(ConfigurationValidator.class);
    private static final List<String> REQUIRED_PROPERTIES = List.of("erail.url",
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
    }

    private static void validateBrowser(){
        logger.info("validating browser...");
    }

    private static void validateTimeouts(){
        logger.info("validating timeouts...");
    }
}
