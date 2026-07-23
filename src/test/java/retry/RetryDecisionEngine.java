package retry;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;

public final class RetryDecisionEngine {

    private RetryDecisionEngine() {}

    public static boolean shouldRetry(Throwable throwable) {

        if (throwable == null) {
            return false;
        }

        return throwable instanceof TimeoutException
                || throwable instanceof StaleElementReferenceException
                || throwable instanceof ElementClickInterceptedException
                || throwable instanceof WebDriverException;

    }
}
