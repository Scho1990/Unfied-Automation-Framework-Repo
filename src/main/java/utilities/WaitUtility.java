package utilities;

import config.ConfigReader;
import driver.DriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public final class WaitUtility {
    private WaitUtility() {
    }

    private static WebDriver getDriver() {
        return DriverManager.getDriver();
    }

    private static WebDriverWait getWait() {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(ConfigReader.getIntProperty("explicit.wait")));
    }

    public static WebElement waitForVisibility(By locator) {
        try {
            return getWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
        }
        catch (TimeoutException e) {
            throw new RuntimeException("Element not visible : " + locator, e);
        }
    }

    /**
     * Waits until all matching elements are visible.
     *
     * @param locator locator
     * @return visible elements
     */
    public static List<WebElement> waitForAllVisibleElements(By locator) {
        return getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    public static WebElement waitForClickable(By locator) {
        return getWait().until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static WebElement waitForPresence(By locator) {
        return getWait().until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static boolean waitForInvisibility(By locator) {
        return getWait().until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public static Alert waitForAlert() {
        return getWait().until(ExpectedConditions.alertIsPresent());
    }

    public static void waitForPageLoad() {
        getWait().until(driver ->
                ((String)((JavascriptExecutor) driver)
                        .executeScript("return document.readyState"))
                        .equals("complete"));
    }

    public static FluentWait<?> getFluentWait() {
        return new FluentWait<>(getDriver())
                .withTimeout(Duration.ofSeconds(
                        ConfigReader.getIntProperty("explicit.wait")))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(Exception.class);
    }

    //TRY TO AVOID THIS STATIC WAIT IN ANYWHERE IN FRAMEWORK
    public static void sleep(long seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }


}
