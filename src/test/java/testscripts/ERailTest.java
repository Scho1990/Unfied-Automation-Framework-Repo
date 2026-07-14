package testscripts;

import base.BaseTest;
import config.ConfigReader;
import driver.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ERailHomePage;
import pages.ERailResultPage;
import reports.ExtentLogger;
import utilities.DateUtility;
import utilities.ExcelUtility;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class ERailTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(ERailTest.class);

    private static final String FROM_STATION = "DEL";
    private static final int STATION_INDEX = 3;
    private static final int FUTURE_DAYS = 30;

    @Test(description = "Verify station suggestions and train search functionality")
    public void verifyFromStationSuggestions() {
        ExtentLogger.assignCategory("ERail");
        ERailHomePage homePage = new ERailHomePage();

        homePage.openApplication()
                .clearFromStation()
                .enterFromStation(FROM_STATION);

        List<String> stationSuggestions = homePage.getFromStationSuggestions();
        ExtentLogger.pass("Captured " + stationSuggestions.size() + " station suggestions.");
        String selectedStation = homePage.selectFromStationByIndex(STATION_INDEX);
        ExtentLogger.pass("Station suggestion selected: " + selectedStation);
        
        validateStationSuggestions(stationSuggestions);
        LocalDate journeyDate = DateUtility.getFutureDate(FUTURE_DAYS);
        homePage.selectJourneyDate(journeyDate);
        ERailResultPage resultPage = homePage.clickGetTrains();
        Assert.assertTrue(resultPage.isTrainListDisplayed(),"Train list is not displayed");
        ExtentLogger.pass("Train results displayed successfully");
    }

    private void validateStationSuggestions(List<String> stationSuggestions) {
        try (ExcelUtility excelUtility =
                     new ExcelUtility(ConfigReader.getProperty("expected.station.file"))) {
            excelUtility.writeColumn("ActualStations", 0, stationSuggestions, true);
            excelUtility.save();
            ExtentLogger.info("Comparing actual station suggestions with expected Excel data");
            Assert.assertTrue(
                    excelUtility.compareColumns(
                            "ExpectedStations",
                            0,
                            "ActualStations",
                            0),
                    "Expected and Actual stations are not equal");

            ExtentLogger.pass("Station suggestions matched successfully");
        } catch (IOException e) {
            throw new RuntimeException("Unable to compare station suggestions", e);
        }
    }
}
