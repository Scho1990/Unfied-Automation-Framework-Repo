package pages;

import base.BasePage;
import org.openqa.selenium.By;
import reports.ExtentLogger;

public class ERailResultPage extends BasePage {

    private static final By TBL_RESULTS = By.id("divTrainsList");

    public ERailResultPage waitForResults() {
        waitUntilPageLoads();
        return this;
    }

    public boolean isTrainListDisplayed() {
        ExtentLogger.info("Verifying train search results");
        return isDisplayed(TBL_RESULTS);
    }

}
