package testscripts;

import base.BaseTest;
import dataprovider.LoginDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.OrangeHRMDashboardPage;
import pages.OrangeHRMLoginPage;
import reports.ExtentLogger;

import java.util.List;

public class OrangeHRMLoginTest extends BaseTest {

    @Test(
            description = "Verify successful login with valid credentials",
            dataProvider = "validLoginData",
            dataProviderClass = LoginDataProvider.class
    )
    public void verifyValidLogin(String scenario, String username, String password, String expectedMessage, String expectedCount) {
        ExtentLogger.assignCategory(scenario);
        ExtentLogger.info("Username : " + (username.isBlank() ? "<blank>" : username));
        ExtentLogger.info("Password : " + (password.isBlank() ? "<blank>" : "********"));
        OrangeHRMLoginPage loginPage = new OrangeHRMLoginPage();
        OrangeHRMDashboardPage dashboardPage = loginPage
                .openApplication()
                .loginAsValidUser(username, password);
        Assert.assertTrue(dashboardPage.isDashboardDisplayed(),"Dashboard is not displayed after successful login.");
        ExtentLogger.pass("Dashboard displayed successfully");
    }

    @Test(
            description = "Verify login with invalid credentials",
            dataProvider = "invalidLoginData",
            dataProviderClass = LoginDataProvider.class
    )
    public void verifyInvalidLogin(String scenario, String username, String password, String expectedMessage, String expectedCount) {
        ExtentLogger.assignCategory(scenario);
        ExtentLogger.info("Username : " + (username.isBlank() ? "<blank>" : username));
        ExtentLogger.info("Password : " + (password.isBlank() ? "<blank>" : "********"));
        OrangeHRMLoginPage loginPage = new OrangeHRMLoginPage();
        loginPage
                .openApplication()
                .loginAsInvalidUser(username, password);
        Assert.assertTrue(loginPage.isInvalidCredentialMessageDisplayed(),"Invalid credential message is not displayed after invalid login.");
        ExtentLogger.info("Expected Error : " + expectedMessage);
        ExtentLogger.info("Actual Error : " + loginPage.getInvalidCredentialMessage());
        Assert.assertEquals(loginPage.getInvalidCredentialMessage(),expectedMessage,"Error message mismatch.");
        ExtentLogger.pass("Error message verified successfully");
    }

    @Test(
            description = "Verify mandatory field validation",
            dataProvider = "blankLoginData",
            dataProviderClass = LoginDataProvider.class
    )
    public void verifyMandatoryFieldValidation(String scenario, String username, String password, String expectedMessage,String expectedCount) {
        ExtentLogger.assignCategory(scenario);
        ExtentLogger.info("Username : " + (username.isBlank() ? "<blank>" : username));
        ExtentLogger.info("Password : " + (password.isBlank() ? "<blank>" : "********"));
        OrangeHRMLoginPage loginPage = new OrangeHRMLoginPage();
        loginPage.openApplication()
                .loginAsInvalidUser(username, password);
        Assert.assertEquals(loginPage.getRequiredFieldMessageCount(), Integer.parseInt(expectedCount), "Required message count mismatch.");
        ExtentLogger.info("Expected Validation : Required");
        ExtentLogger.info("Actual Validation : " + loginPage.getRequiredFieldMessages());
        List<String> messages =  loginPage.getRequiredFieldMessages();
        Assert.assertTrue(messages.stream().allMatch(message -> message.equals(expectedMessage)), "Mandatory validation message is not displayed.");
        ExtentLogger.pass("Mandatory field validation verified successfully");
    }
}
