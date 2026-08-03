package api.authentication.oauth.manager;

import api.authentication.oauth.model.OAuthToken;
import api.authentication.oauth.storage.TokenStore;
import api.authentication.oauth.storage.TokenStoreFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;
import java.util.Optional;
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
public final class TokenManager {

    private static final Logger logger = LogManager.getLogger(TokenManager.class);

    /**
     * Cached OAuth token held in memory.
     * The reference is declared volatile so that all threads
     * always observe the latest token after it has been updated.
     */
    private static volatile OAuthToken cachedToken;

    /**
     * Persistent storage.
     */
    private static final TokenStore TOKEN_STORE = TokenStoreFactory.getInstance();

    /**
     * Synchronizes token updates.
     */
    private static final ReentrantLock LOCK = new ReentrantLock();

    private TokenManager() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static void initialize() {
        LOCK.lock();
        try {
            Optional<OAuthToken> token = TOKEN_STORE.load();
            if (token.isPresent()) {
                cachedToken = token.get();
                logger.info("OAuth token loaded into memory.");
            }
            else {
                logger.info("No OAuth token found in storage.");
            }
        } finally {
            LOCK.unlock();
        }
    }

    /**
     * Stores the current OAuth token.
     *
     * @param token OAuth token to store
     */
    public static void storeToken(OAuthToken token) {
        LOCK.lock();
        try {
            cachedToken = Objects.requireNonNull(token, "OAuth Token cannot be null.");
            TOKEN_STORE.save(token);
            logger.info("OAuth token stored successfully.");
        } finally {
            LOCK.unlock();
        }

    }

    /**
     * Clears the current OAuth token.
     */
    public static void clear(){
        LOCK.lock();
       try {
            cachedToken = null;
            TOKEN_STORE.delete();
            logger.info("OAuth token cleared.");
        } finally {
           LOCK.unlock();
       }
    }

    /**
     * Checks whether an OAuth token is available.
     *
     * @return true if a token exists
     */
    public static boolean hasToken() {
        return cachedToken != null;
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

        String accessToken = cachedToken.getAccessToken();

        if (accessToken == null || accessToken.isBlank()) {
            throw new IllegalStateException("OAuth access token is null or blank.");
        }

        return accessToken;
    }

}
