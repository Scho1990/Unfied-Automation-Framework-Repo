package pages;

import base.BasePage;
import config.ConfigReader;
import constants.FrameworkConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import reports.ExtentLogger;

import java.util.List;

public class GoogleSearchPage extends BasePage {

    public static final Logger logger = LogManager.getLogger(GoogleSearchPage.class);

    private static final By TXT_SEARCHBOX = By.name("q");
    private static final By LST_SEARCHBOX_SUGGESTIONS = By.xpath("//div[@role='presentation']//ul//li");
    private static final By BTN_GOOGLE_SEARCH=By.xpath("//div[contains(@class,'FPdoLc')]//input[@name='btnK']");

    public void enterSearchTerm(String searchTerm) {
        logger.info("Entered search term: {} " ,searchTerm);
        ExtentLogger.info("Entered search term: "+searchTerm);
        type(TXT_SEARCHBOX, searchTerm);
    }

    public void clickOnGoogleSearchSuggestions(String searchTerm, String matchingSearchTerm) {
        enterSearchTerm(searchTerm);
        List<WebElement> suggestions = getElements(LST_SEARCHBOX_SUGGESTIONS);
        logger.info("Suggested search term displayed: {} " ,suggestions);
        for (WebElement suggestion : suggestions) {
           if (suggestion.getText().contains(matchingSearchTerm)) {
               logger.info("Suggestion clicked on {} ",suggestion.getText());
               ExtentLogger.info("Suggestion clicked on : "+suggestion.getText());
               suggestion.click();
               break;
           }
        }
       // click(BTN_GOOGLE_SEARCH);
    }

    public String getSearchResultPageTitle() {
        return getPageTitle();
    }

    public GoogleSearchPage openApplication(){
        ExtentLogger.info("Launching Google Search Page");
        openApplication(ConfigReader.getProperty(FrameworkConstants.GOOGLE_URL));
        ExtentLogger.pass("Google Search Page launched successfully");
        return this;
    }

}
