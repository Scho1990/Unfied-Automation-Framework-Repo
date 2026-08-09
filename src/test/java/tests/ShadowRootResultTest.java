package tests;

import base.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.ShadowRootPage;
import reports.ExtentLogger;

public class ShadowRootResultTest extends BaseTest {
    private static final Logger logger = LogManager.getLogger(ShadowRootResultTest.class);
    @Test(description = "Verify that both with and without shadow root elements are visible in UI")
    public void verifyShadowRootResult(){
        ExtentLogger.assignCategory("Shadow Root Page");
        ShadowRootPage shadowRootPage = new ShadowRootPage();
        logger.info("Shadow Root Page started");
        shadowRootPage.openShowDomApplication();
        SoftAssert softAssert = new SoftAssert();
        ExtentLogger.info("Checking the text of without shadow dom button is showing "+shadowRootPage.getTextForWithoutShadowButton());
        shadowRootPage.getTextForWithoutShadowButton();
        softAssert.assertTrue(shadowRootPage.getTextForWithoutShadowButton().contains("Here's a basic button example."),"Without Shadow Dom Button is not showing");
        ExtentLogger.info("Checking the text of with shadow dom button is showing "+shadowRootPage.getTextForWithShadowButton());
        shadowRootPage.getTextForWithShadowButton();
        softAssert.assertTrue(shadowRootPage.getTextForWithShadowButton().contains("This button is inside a Shadow DOM."),"With Shadow Dom Button is not showing");
        softAssert.assertAll();
    }
}
