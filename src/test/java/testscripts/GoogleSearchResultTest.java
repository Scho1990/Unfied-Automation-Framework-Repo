package testscripts;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.GoogleSearchPage;
import reports.ExtentLogger;

public class GoogleSearchResultTest extends BaseTest {
    String searchTerm= "rapifuzz";
    String expectedSearchResult = "rapifuzz pvt ltd";

    @Test(description = "Verify successful result search on google page")
    public void verifyGoogleSearchResult() {
        ExtentLogger.assignCategory("Google Search");
        GoogleSearchPage googleSearchPage = new GoogleSearchPage();
        googleSearchPage.openApplication()
                .clickOnGoogleSearchSuggestions(searchTerm,expectedSearchResult);

        Assert.assertEquals(googleSearchPage.getSearchResultPageTitle(),expectedSearchResult,"Search results not found");



    }
}
