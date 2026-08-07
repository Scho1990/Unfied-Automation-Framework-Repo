package api.authentication.oauth.manager;

import api.authentication.oauth.model.OAuthToken;
import api.authentication.oauth.service.OAuthServiceFactory;
import api.authentication.oauth.service.SpotifyTokenService;
import api.authentication.oauth.storage.TokenStore;
import api.authentication.oauth.storage.TokenStoreFactory;
import exceptions.api.OAuthException;
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
 *     <li>Store OAuth token.</li>
 *     <li>Load OAuth token.</li>
 *     <li>Persist OAuth token.</li>
 *     <li>Refresh expired access tokens.</li>
 *     <li>Provide thread-safe access</li>
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
     * Clears the cached OAuth token from memory and persistent storage.
     *
     * <p>This method is intended for scenarios such as:
     * <ul>
     *     <li>User logout</li>
     *     <li>Refresh token revocation</li>
     *     <li>Framework cleanup</li>
     *     <li>Test environment reset</li>
     * </ul>
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
     * Determines whether the in-memory OAuth token can be used
     * for authenticated API requests without requiring a refresh.
     *
     * @return true if a valid, non-expired access token is available.
     */
    private static boolean hasValidToken() {
        return cachedToken != null
                && cachedToken.hasAccessToken()
                && !cachedToken.isExpired();
    }

    /**
     * Returns a valid OAuth access token.
     *
     * <p>
     * Fast path:
     * <ul>
     *     <li>If a valid token is already available in memory,
     *     return it immediately without synchronization.</li>
     * </ul>
     *
     * <p>
     * Slow path:
     * <ul>
     *     <li>Acquire the lock.</li>
     *     <li>Double-check the token state.</li>
     *     <li>Refresh the token if it has expired.</li>
     * </ul>
     *
     * @return valid OAuth access token
     * @throws OAuthException if no valid token is available
     */
    public static String getAccessToken() {
        /*
         * Fast path.
         * Avoid synchronization for the common case.
         */
        if (hasValidToken()) {
            return cachedToken.getAccessToken();
        }
        LOCK.lock();
        try {
            logger.debug("Thread [{}] acquired OAuth refresh lock because no valid access token was available.",
                    Thread.currentThread().getName());
            /*
             * Another thread may already have refreshed
             * the token while this thread was waiting for the lock.
             */
            if(hasValidToken()){
                logger.debug(
                        "Thread [{}] detected that another thread already refreshed the OAuth token. Reusing the cached token.",
                        Thread.currentThread().getName());
                return cachedToken.getAccessToken();
            }

            /*
             * No token available.
             */
            if (cachedToken == null) {
                throw new OAuthException(
                        "No OAuth token is available. Manual authorization is required.");
            }
            /*
             * Refresh token missing.
             */
            if (!cachedToken.hasRefreshToken()){
                throw new OAuthException("OAuth refresh token is unavailable. Manual authorization is required.");
            }

            logger.info("Thread [{}] is refreshing the expired OAuth access token.",
                    Thread.currentThread().getName());

            SpotifyTokenService tokenService = OAuthServiceFactory.createSpotifyTokenService();

            OAuthToken refreshedToken = tokenService.refreshAccessToken(cachedToken.getRefreshToken());

            /*
             * Updates both memory cache and persistent storage.
             */
            storeToken(refreshedToken);

            logger.info("Thread [{}] refreshed the OAuth access token successfully.",
                    Thread.currentThread().getName());

            return refreshedToken.getAccessToken();

        } catch (OAuthException ex){
            logger.error("Failed to refresh OAuth access token. ", ex);
            throw ex;
        }

        finally {
            LOCK.unlock();
        }
    }

}
