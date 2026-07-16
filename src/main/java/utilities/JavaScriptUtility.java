package utilities;

import driver.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public final class JavaScriptUtility {
    private JavaScriptUtility() {}

    private static WebDriver getDriver(){
        return DriverManager.getDriver();
    }

    private static JavascriptExecutor getJavascriptExecutor() {
        return (JavascriptExecutor) getDriver();
    }

    /**
     * Click using JavaScript
     */
    public static void click(By locator) {
        getJavascriptExecutor().executeScript("arguments[0].click();",getDriver().findElement(locator));
    }

    /**
     * Scroll element into view
     */
    public static void scrollIntoView(By locator) {
        getJavascriptExecutor().executeScript("arguments[0].scrollIntoView({behavior:'smooth',block:'center'});",getDriver().findElement(locator));
    }

    /**
     * Scroll to bottom of page
     */
    public static void scrollToBottom() {
        getJavascriptExecutor().executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    /**
     * Scroll to top of page
     */
    public static void scrollToTop() {
        getJavascriptExecutor().executeScript("window.scrollTo(0,0);");
    }

    /**
     * Set value using JavaScript
     */
    public static void setValue(By locator, String value) {
        getJavascriptExecutor().executeScript("arguments[0].value=arguments[1];",getDriver().findElement(locator), value);
    }
    /**
     * Highlight element
     */
    public static void highlightElement(By locator) {
        getJavascriptExecutor().executeScript("arguments[0].style.border='3px solid red';",getDriver().findElement(locator));
    }

}
