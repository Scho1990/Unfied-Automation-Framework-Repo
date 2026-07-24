package pages;

import base.BasePage;
import config.ConfigReader;
import constants.FrameworkConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import reports.ExtentLogger;

public class ShadowRootPage extends BasePage {
    private static final Logger logger = LogManager.getLogger(ShadowRootPage.class);

    private static final By BTN_BASIC = By.cssSelector(".btn.btn-primary.mb-2");
    private static final By SHADOW_HOST = By.cssSelector("#shadow-host");
    private static final By BTN_SHADOW = By.cssSelector("button#my-btn[type='button']");

    public ShadowRootPage openShowDomApplication() {
        ExtentLogger.info("Launching Shadow DOM Page");
        super.openApplication(ConfigReader.getProperty(FrameworkConstants.SHADOWDOM_URL));
        ExtentLogger.pass("Shadow DOM Page launched successfully");
        return this;
    }

    public String getTextForWithoutShadowButton() {
        ExtentLogger.info("Getting Text for Without Shadow DOM Button");
        return getText(BTN_BASIC);
    }

    public String getTextForWithShadowButton() {
        ExtentLogger.info("Getting Text for With Shadow DOM Button");
        WebElement shadowHost = getElement(SHADOW_HOST);
        SearchContext shadowRoot = shadowHost.getShadowRoot();
        return shadowRoot.findElement(BTN_SHADOW).getText();

    }


}
