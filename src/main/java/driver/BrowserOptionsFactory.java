package driver;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.HashMap;
import java.util.Map;

public final class BrowserOptionsFactory {
    private BrowserOptionsFactory() {}

    public static ChromeOptions getChromeOptions(boolean headless){
        ChromeOptions chromeOptions = new ChromeOptions();
        if(headless){
            chromeOptions.addArguments("--headless=new");
        }
        chromeOptions.addArguments("--disable-popup-blocking");
        chromeOptions.addArguments("--disable-notifications");
        chromeOptions.addArguments("--start-maximized");
        chromeOptions.setExperimentalOption("prefs", getChromiumPreferences());
        return chromeOptions;
    }

    public static FirefoxOptions getFirefoxOptions(boolean headless){
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        if(headless){
            firefoxOptions.addArguments("--headless");
        }
        firefoxOptions.addPreference("dom.webnotifications.enabled", false);
        firefoxOptions.addPreference("permissions.default.geo", 2);
        firefoxOptions.addPreference("geo.enabled", false);
        firefoxOptions.addPreference("signon.rememberSignons", false);
        return firefoxOptions;
    }


    private static Map<String, Object> getChromiumPreferences() {
        Map<String, Object> prefs = new HashMap<>();
        // Block location permission popup
        prefs.put("profile.default_content_setting_values.notifications", 2);
        // Disable notification permission popup
        prefs.put("profile.default_content_setting_values.geolocation", 2);
        return prefs;
    }



}
