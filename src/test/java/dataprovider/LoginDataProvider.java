package dataprovider;

import config.ConfigReader;
import org.testng.annotations.DataProvider;
import utilities.ExcelUtility;
import java.io.IOException;

public class LoginDataProvider {

    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        try (ExcelUtility excel = new ExcelUtility(ConfigReader.getProperty("login.data.file"))) {
            return excel.getDataForDataProvider("Login");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load Excel test data", e);
        }
    }

    @DataProvider(name = "validLoginData")
    public Object[][] validLoginData() {
        try (ExcelUtility excel = new ExcelUtility(ConfigReader.getProperty("login.data.file"))) {
            return excel.getFilteredData(
                    "Login",
                    "Scenario",
                    "VALID_LOGIN");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load Excel test data", e);
        }
    }

    @DataProvider(name = "invalidLoginData",parallel = true)
    public Object[][] invalidLoginData() {
        try (ExcelUtility excel = new ExcelUtility(ConfigReader.getProperty("login.data.file"))) {
            return excel.getFilteredData(
                    "Login",
                    "Scenario",
                    "INVALID");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load Excel test data", e);
        }
    }

    @DataProvider(name = "blankLoginData",parallel = true)
    public Object[][] blankLoginData() {
        try (ExcelUtility excel = new ExcelUtility(ConfigReader.getProperty("login.data.file"))) {
            return excel.getFilteredData(
                    "Login",
                    "Scenario",
                    "BLANK");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load Excel test data", e);
        }
    }
}
