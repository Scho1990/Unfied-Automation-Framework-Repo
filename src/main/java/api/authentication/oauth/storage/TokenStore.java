package api.authentication.oauth.storage;

import api.authentication.oauth.model.OAuthToken;

import java.io.IOException;
import java.util.Optional;

/**
 * Contract for persisting OAuth tokens.
 * <p>
 * Implementations may store tokens in:
 * <ul>
 *     <li>JSON File</li>
 *     <li>Redis</li>
 *     <li>HashiCorp Vault</li>
 *     <li>AWS Secrets Manager</li>
 *     <li>Database</li>
 * </ul>
 *
 * This interface is intentionally storage-agnostic.
 */
public interface TokenStore {

    /**
     * Persist OAuth token.
     *
     * @param token OAuth token
     */
    void save(OAuthToken token);

    /**
     * Load OAuth token.
     *
     * @return Optional containing token if present
     */
    Optional<OAuthToken> load();

    /**
     * Delete persisted OAuth token.
     */
    void delete();

    /**
     * Checks whether a persisted token exists.
     *
     * @return true if token exists
     */
    boolean exists();
}