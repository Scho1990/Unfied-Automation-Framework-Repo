package base;

import driver.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import utilities.JavaScriptUtility;
import utilities.WaitUtility;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public abstract class BasePage {
    private final Logger logger = LogManager.getLogger(getClass());

    protected WebDriver getDriver() {
        return DriverManager.getDriver();
    }

    private Actions getActions() {
        return new Actions(getDriver());
    }

    private Select getSelect(By locator) {
        return new Select(getVisibleElement(locator));
    }

    private WebElement getVisibleElement(By locator) {
        return WaitUtility.waitForVisibility(locator);
    }

    private List<WebElement> getAllVisibleElements(By locator) {
        return WaitUtility.waitForAllVisibleElements(locator);
    }

    private WebElement getClickableElement(By locator) {
        return WaitUtility.waitForClickable(locator);
    }

    protected void click(By locator) {
        logger.info("Clicking on element: {}", locator);
        getClickableElement(locator).click();
    }

    protected void type(By locator, String text) {
        type(locator, text, false);
    }

    protected void type(By locator, String text, boolean maskValue) {
        if (maskValue) {
            logger.info("Entering masked value into: {}", locator);
        } else {
            logger.info("Entering '{}' into: {}", text, locator);
        }
        WebElement element = getVisibleElement(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected void clear(By locator) {
        getVisibleElement(locator).clear();
    }

    protected String getText(By locator) {
        return getVisibleElement(locator).getText().trim();
    }

    protected String getAttributeValue(By locator, String attributeName) {
        return getVisibleElement(locator).getAttribute(attributeName);
    }

    protected boolean isDisplayed(By locator) {
        try {
            return getVisibleElement(locator).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    protected boolean isEnabled(By locator) {
        return getVisibleElement(locator).isEnabled();
    }

    protected boolean isSelected(By locator) {
        return getVisibleElement(locator).isSelected();
    }

    protected void selectByVisibleText(By locator, String text) {
        logger.info("Selecting '{}' from dropdown: {}", text, locator);
        getSelect(locator).selectByVisibleText(text);
    }

    protected void selectByValue(By locator, String value) {
        getSelect(locator).selectByValue(value);
    }

    protected void selectByIndex(By locator, int index) {
        getSelect(locator).selectByIndex(index);
    }

    protected void jsClick(By locator) {
        logger.info("Performing JavaScript click on: {}", locator);
        JavaScriptUtility.click(locator);
    }

    protected void scrollIntoView(By locator) {
        logger.info("Scrolling to element: {}", locator);
        JavaScriptUtility.scrollIntoView(locator);
    }

    protected void moveToElement(By locator) {
        logger.info("Moving mouse to element: {}", locator);
        getActions().moveToElement(getVisibleElement(locator)).perform();
    }

    protected void doubleClick(By locator) {
        getActions().doubleClick(getVisibleElement(locator)).perform();
    }

    protected void rightClick(By locator) {
        getActions().contextClick(getVisibleElement(locator)).perform();
    }

    protected void dragAndDrop(By source, By destination) {
        logger.info("Dragging element {} to {}", source, destination);
        getActions().dragAndDrop(getVisibleElement(source), getVisibleElement(destination)).perform();
    }

    protected String getPageTitle() {
        return getDriver().getTitle();
    }

    protected String getCurrentUrl() {
        return getDriver().getCurrentUrl();
    }

    protected void navigateForward() {
        getDriver().navigate().forward();
    }

    protected void navigateBack() {
        getDriver().navigate().back();
    }

    protected void refreshPage() {
        getDriver().navigate().refresh();
    }

    protected WebElement getElement(By locator) {
        return getVisibleElement(locator);
    }

    protected List<WebElement> getElements(By locator) {
        return getAllVisibleElements(locator);
    }

    protected int getElementCount(By locator) {
        return getElements(locator).size();
    }

    //Returns text from all matching elements
    protected List<String> getTexts(By locator) {
        return getElements(locator)
                .stream()
                .map(WebElement::getText)
                .map(String::trim)
                .filter(text -> !text.isEmpty())
                .collect(Collectors.toList());
    }

    //Click element by index.
    protected void clickElementByIndex(By locator, int index) {
        List<WebElement> elements = getElements(locator);
        if (index < 0 || index >= elements.size()) {
            throw new IllegalArgumentException("Invalid index : " + index + ". Available elements : " + elements.size());
        }
        elements.get(index).click();
    }

    /**
     * Returns true if at least one matching element exists.
     */
    protected boolean isElementPresent(By locator) {
        return !getDriver()
                .findElements(locator)
                .isEmpty();
    }

    /**
     * Wait until page is fully loaded.
     */
    protected void waitUntilPageLoads() {
        WaitUtility.waitForPageLoad();
    }

    /**
     * Launch application.
     */
    protected void openApplication(String url) {
        logger.info("Opening application: {}", url);
        getDriver().get(url);
        waitUntilPageLoads();
        logger.info("Application loaded successfully");
    }

    /**
     * Selects a suggestion from an auto-suggestion list by index.
     *
     * @param suggestionsLocator locator representing all suggestion elements
     * @param index              zero-based index of suggestion
     * @return selected suggestion text
     */
    protected String selectSuggestionByIndex(By suggestionsLocator, int index) {
        List<WebElement> suggestions = WaitUtility.waitForAllVisibleElements(suggestionsLocator);
        if (index < 0 || index >= suggestions.size()) {
            throw new IllegalArgumentException("Invalid suggestion index : " + index + ". Available suggestions : " + suggestions.size());
        }
        WebElement suggestion = suggestions.get(index);
        String selectedText = suggestion.getText().trim();
        logger.info("Selecting suggestion [{}] : {}", index, selectedText);
        suggestion.click();
        return selectedText;
    }

    /**
     * Selects a suggestion by index using either visible text or a specified attribute.
     *
     * @param suggestionsLocator locator representing suggestion elements
     * @param index              zero-based index
     * @param attributeName      attribute to read (e.g., "title"). If null/blank, getText() is used.
     * @return selected suggestion value
     */
    protected String selectSuggestionByIndex(By suggestionsLocator, int index, String attributeName) {
        List<WebElement> suggestions = WaitUtility.waitForAllVisibleElements(suggestionsLocator);
        if (index < 0 || index >= suggestions.size()) {
            throw new IllegalArgumentException("Invalid suggestion index : " + index + ". Available suggestions : " + suggestions.size());
        }
        WebElement suggestion = suggestions.get(index);
        String selectedValue;
        if (attributeName == null || attributeName.isBlank()) {
            selectedValue = suggestion.getText().trim();
        } else {
            selectedValue = suggestion.getAttribute(attributeName).trim();
        }
        logger.info("Selecting suggestion [{}] : {}", index, selectedValue);
        suggestion.click();
        return selectedValue;
    }

    /**
     * Returns all visible auto-suggestion texts.
     *
     * @param suggestionsLocator locator representing all suggestion elements
     * @return list of suggestion texts
     */
    protected List<String> getSuggestionTexts(By suggestionsLocator) {
        List<WebElement> suggestions = WaitUtility.waitForAllVisibleElements(suggestionsLocator);
        return suggestions.stream()
                .map(WebElement::getText)
                .map(String::trim)
                .filter(text -> !text.isEmpty())
                .toList();
    }

    /**
     * Returns suggestion texts using either visible text or a specified attribute.
     *
     * @param suggestionsLocator locator representing suggestion elements
     * @param attributeName      attribute to read (e.g., "title"). If null/blank, getText() is used.
     * @return list of suggestion values
     */
    /*protected List<String> getSuggestionTexts(By suggestionsLocator, String attributeName) {
        List<WebElement> suggestions =
                WaitUtility.waitForAllVisibleElements(suggestionsLocator);
        logger.info("Suggestion Count : {}", suggestions.size());
        return suggestions.stream()
                .map(element -> {
                    if (attributeName == null || attributeName.isBlank()) {
                        return element.getText().trim();
                    }
                    return element.getAttribute(attributeName).trim();
                })
                .filter(text -> !text.isEmpty())
                .toList();
    }*/
    protected List<String> getSuggestionTexts(By suggestionsLocator, String attributeName) {
        List<WebElement> suggestions = WaitUtility.waitForAllVisibleElements(suggestionsLocator);
        List<String> suggestionTexts = new ArrayList<>();
        for (WebElement suggestion : suggestions) {
            String value;
            if (attributeName == null || attributeName.isBlank()) {
                value = suggestion.getText().trim();
            } else {
                value = suggestion.getAttribute(attributeName).trim();
            }
            if (!value.isEmpty()) {
                suggestionTexts.add(value);
            }
        }
        return suggestionTexts;
    }

}
