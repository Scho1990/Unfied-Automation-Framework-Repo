package dataprovider;

import config.ConfigReader;
import exceptions.data.DataProviderException;
import org.testng.annotations.DataProvider;
import utilities.ExcelUtility;
import java.io.IOException;

public class LoginDataProvider {

    private static final String SHEET_NAME = "Login";
    private static final String FILTER_COLUMN = "Scenario";

    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        try (ExcelUtility excel = createExcelUtility()) {
            return excel.getDataForDataProvider(SHEET_NAME);
        } catch (IOException e) {
            throw new DataProviderException("Failed to load test data", e);
        }
    }

    @DataProvider(name = "validLoginData")
    public Object[][] validLoginData() {
            return getFilteredData("VALID_LOGIN");
    }

    @DataProvider(name = "invalidLoginData",
            parallel = true)
    public Object[][] invalidLoginData() {
            return getFilteredData("INVALID");
    }

    @DataProvider(name = "blankLoginData",
            parallel = true)
    public Object[][] blankLoginData() {
            return getFilteredData("BLANK");
    }

    private ExcelUtility createExcelUtility() throws IOException {
        return new ExcelUtility(
                ConfigReader.getProperty("login.data.file"));
    }

    private Object[][] getFilteredData(String scenario) {
        try (ExcelUtility excel = createExcelUtility()) {
            return excel.getFilteredData(
                    SHEET_NAME,
                    FILTER_COLUMN,
                    scenario);
        } catch (IOException e) {
            throw new DataProviderException(
                    "Failed to load login test data.", e);
        }
    }
}
