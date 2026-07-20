package driver;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.HashMap;
import java.util.Map;

public final class BrowserOptionsFactory {
    private BrowserOptionsFactory() {}

    public static ChromeOptions getChromeOptions(boolean headless){
        ChromeOptions chromeOptions = new ChromeOptions();
        addCommonChromiumArguments(chromeOptions, headless);
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
        // Disable notification permission popup
        prefs.put("profile.default_content_setting_values.notifications", 2);
        // Block location permission popup
        prefs.put("profile.default_content_setting_values.geolocation", 2);
        return prefs;
    }


    public static EdgeOptions getEdgeOptions(boolean headless) {
        EdgeOptions edgeOptions = new EdgeOptions();
        addCommonChromiumArguments(edgeOptions,headless);
        edgeOptions.setExperimentalOption("prefs", getChromiumPreferences());
        return edgeOptions;
    }

    private static void addCommonChromiumArguments(ChromiumOptions<?> options,boolean headless) {
        if(headless){
            options.addArguments("--headless=new");
        }
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--start-maximized");
        options.setAcceptInsecureCerts(true);

    }
}
