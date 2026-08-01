package api.manager;

import api.authentication.oauth.OAuthToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Manages the lifecycle of the OAuth token used by the framework.
 *
 * <p>
 * Responsibilities:
 * <ul>
 *     <li>Store the current OAuth token.</li>
 *     <li>Provide access to the current token.</li>
 *     <li>Clear the current token.</li>
 *     <li>Support future automatic token refresh.</li>
 * </ul>
 *
 * <p>
 * This class is intentionally implemented as a utility class because
 * the framework maintains a single authenticated session.
 */
public class TokenManager {

    private static final Logger logger = LogManager.getLogger(TokenManager.class);

    /**
     * Holds the current OAuth token.
     * Ensures visibility across threads.
     * Why volatile? e.g. suppose thread A stores new token then immediately thread B reads the latest token
     */
    private static volatile OAuthToken currentToken;

    /**
     * Lock reserved for future token refresh operations.
     */
    private static final ReentrantLock refreshLock = new ReentrantLock();

    private TokenManager() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Stores the current OAuth token.
     *
     * @param token OAuth token to store
     */
    public static void setToken(OAuthToken token) {
        currentToken = Objects.requireNonNull(token, "OAuth Token cannot be null.");
        logger.info("OAuth token stored successfully.");
    }

    /**
     * Returns the current OAuth token.
     *
     * @return current OAuth token
     */
    public static OAuthToken getToken() {
        return currentToken;
    }

    /**
     * Checks whether an OAuth token is available.
     *
     * @return true if a token exists
     */
    public static boolean hasToken() {
        return currentToken != null;
    }

    /**
     * Clears the current OAuth token.
     */
    public static void clear(){
        if (currentToken != null) {
            currentToken = null;
            logger.info("OAuth token cleared.");
        }
    }

    /**
     * Returns the current OAuth access token.
     *
     * @return OAuth access token
     * @throws IllegalStateException if no valid access token is available
     */
    public static String getAccessToken() {
        if (!hasToken()) {
            throw new IllegalStateException("No OAuth token is currently available.");
        }

        String accessToken = currentToken.getAccessToken();

        if (accessToken == null || accessToken.isBlank()) {
            throw new IllegalStateException("OAuth access token is null or blank.");
        }

        return accessToken;
    }

}
