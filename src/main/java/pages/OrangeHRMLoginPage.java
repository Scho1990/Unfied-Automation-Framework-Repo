package pages;

import base.BasePage;
import config.ConfigReader;
import constants.FrameworkConstants;
import org.openqa.selenium.By;
import reports.ExtentLogger;

import java.util.List;

public class OrangeHRMLoginPage extends BasePage {
    // ============================
    // Locators
    // ============================

    private static final By TXT_USERNAME  = By.name("username");
    private static final By TXT_PASSWORD  = By.name("password");
    private static final By BTN_LOGIN  = By.cssSelector("button[type='submit']");
    private static final By LBL_INVALID_CREDENTIALS = By.cssSelector(".oxd-alert-content-text");
    private static final By LBL_REQUIRED_MESSAGES  = By.xpath("//span[text()='Required']");

    // ============================
    // Business Methods
    // ============================

    /**
     * Enters username.
     *
     * @param username username
     */
    private void enterUsername(String username) {
        type(TXT_USERNAME, username);
    }

    /**
     * Enters password.
     *
     * @param password password
     */
    private void enterPassword(String password) {
        type(TXT_PASSWORD, password,true);
    }

    /**
     * Returns true if invalid credential message is displayed.
     */
    public boolean isInvalidCredentialMessageDisplayed() {

        return isDisplayed(LBL_INVALID_CREDENTIALS);

    }

    /**
     * Returns error message.
     */
    public String getInvalidCredentialMessage() {
        return getText(LBL_INVALID_CREDENTIALS);
    }

    /**
     * Returns number of Required validation messages displayed.
     *
     * @return required message count
     */
    public int getRequiredFieldMessageCount() {
        return getElementCount(LBL_REQUIRED_MESSAGES);
    }

    /**
     * Returns all Required validation messages.
     *
     * @return validation messages
     */
    public List<String> getRequiredFieldMessages() {
        return getTexts(LBL_REQUIRED_MESSAGES);
    }


    /**
     * Login as Valid user.
     *
     * @return OrangeHRMDashboardPage
     */
    public OrangeHRMDashboardPage loginAsValidUser(String username, String password){
        ExtentLogger.info("Logging in using valid credentials");
        enterUsername(username);
        enterPassword(password);
        click(BTN_LOGIN);
        waitUntilPageLoads();
        ExtentLogger.pass("Login completed successfully");
        return new OrangeHRMDashboardPage().waitForDashboardToLoad();
    }

    /**
     * Login as Invalid user.
     *
     * @return OrangeHRMLoginPage
     */
    public OrangeHRMLoginPage loginAsInvalidUser(String username, String password){
        ExtentLogger.info("Logging in using invalid credentials");
        enterUsername(username);
        enterPassword(password);
        click(BTN_LOGIN);
        return this;
    }

    public OrangeHRMLoginPage openApplication() {
        ExtentLogger.info("Launching OrangeHRM application");
        super.openApplication(ConfigReader.getProperty(FrameworkConstants.ORANGEHRM_URL));
        getElement(TXT_USERNAME);
        ExtentLogger.pass("OrangeHRM application launched successfully");
        return this;
    }

}
