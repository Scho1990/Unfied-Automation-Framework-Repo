package pages;

import base.BasePage;
import config.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import reports.ExtentLogger;
import utilities.DateUtility;

import java.time.LocalDate;
import java.util.List;

public class ERailHomePage extends BasePage {
    private static final Logger logger = LogManager.getLogger(ERailHomePage.class);

    private static final By TXT_FROM_STATION  = By.id("txtStationFrom");

    private static final By LST_STATION_SUGGESTIONS  =  By.cssSelector(".autocomplete > div");

    private static final By BTN_SORT_ON_DATE  = By.cssSelector("#tdDateFromTo input[type='button']");

    private static final By DIVCALENDAR = By.id("divCalender");

    private static final By BTN_GET_TRAINS = By.id("buttonFromTo");


    public ERailHomePage openApplication(){
        ExtentLogger.info("Launching ERail application");
        super.openApplication(ConfigReader.getProperty("erail.url"));
        getElement(TXT_FROM_STATION);
        ExtentLogger.pass("ERail application launched successfully");
        return this;
    }

    public ERailHomePage clearFromStation(){
        clear(TXT_FROM_STATION);
        return this;
    }

    public ERailHomePage enterFromStation(String station){
        ExtentLogger.info("Entering source station : " + station);
        type(TXT_FROM_STATION, station);
        ExtentLogger.pass("Source station entered successfully");
        return this;
    }

    public String selectFromStationByIndex(int index){
        ExtentLogger.pass("Selecting "+(index+1) + "th position station in the dropdown");
        return selectSuggestionByIndex(LST_STATION_SUGGESTIONS, index,"title");
    }

    public List<String> getFromStationSuggestions(){
        ExtentLogger.info("Capturing station suggestions");
        return getSuggestionTexts(LST_STATION_SUGGESTIONS,"title");
    }

    private By getJourneyDateLocator(LocalDate date){
        String monthYear = DateUtility.getMonthYear(date);
        logger.info("monthYear  : {}", monthYear);
        int day = DateUtility.getDay(date);
        String xpath = "//table[@class='Month']//tr//td[normalize-space()='"+monthYear+"']//..//following-sibling::tr//td[normalize-space()='"+day+"']";
        return By.xpath(xpath);
    }

    public ERailHomePage selectJourneyDate(LocalDate journeyDate){
        logger.info("Selecting Journey Date : {}", journeyDate);
        ExtentLogger.info("Selecting journey date : " + journeyDate);
        click(BTN_SORT_ON_DATE);
        getElement(DIVCALENDAR);
        click(getJourneyDateLocator(journeyDate));
        ExtentLogger.pass("Journey date selected successfully");
        return this;
    }

    public ERailResultPage clickGetTrains() {
        logger.info("Clicking Get Trains");
        ExtentLogger.info("Searching available trains");
        click(BTN_GET_TRAINS);
        ExtentLogger.pass("Train search completed successfully");
        return new ERailResultPage().waitForResults();
    }

}
