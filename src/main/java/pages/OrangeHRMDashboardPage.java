package pages;

import base.BasePage;
import org.openqa.selenium.By;
import reports.ExtentLogger;

public class OrangeHRMDashboardPage extends BasePage {

    //=========================================
    // Dashboard Locators
    //=========================================

    private static final By LBL_DASHBOARD  = By.xpath("//h6[normalize-space(.) = 'Dashboard']");
    private static final By IMG_PROFILE = By.cssSelector(".oxd-userdropdown-tab");
    private static final By LNK_LOGOUT = By.xpath("//a[text()='Logout']");
    private static final By LBL_LOGGED_IN_USER = By.cssSelector(".oxd-userdropdown-name");

    /**
     * Wait until dashboard is displayed.
     */
    public OrangeHRMDashboardPage waitForDashboardToLoad() {
        ExtentLogger.info("Verifying Dashboard");
        if(!isDisplayed(LBL_DASHBOARD)){
            throw  new RuntimeException("Dashboard page is not displayed");
        }
        return this;
    }
    /**
     * Returns logged in username.
     */
    public String getLoggedInUser(){
        return getText(LBL_LOGGED_IN_USER);
    }

    /**
     * Logout from application.
     */
    public OrangeHRMLoginPage logout() {
        click(IMG_PROFILE);
        click(LNK_LOGOUT);
        waitUntilPageLoads();
        return new OrangeHRMLoginPage();

    }

    /**
     * Wait until dashboard is displayed.
     */
    public boolean isDashboardDisplayed() {

        return isDisplayed(LBL_DASHBOARD);

    }

}
