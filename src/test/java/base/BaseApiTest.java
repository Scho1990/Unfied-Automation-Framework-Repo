package base;

import api.authentication.oauth.manager.TokenManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeSuite;

public abstract class BaseApiTest {

    private static final Logger logger = LogManager.getLogger(BaseApiTest.class);

    @BeforeSuite(alwaysRun = true)
    public void initializeFramework() {
        logger.info("Initializing Framework");
        TokenManager.initialize();
    }
}
