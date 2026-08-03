package api.authentication.oauth.storage;

/**
 * Supported OAuth token storage implementations.
 */
public enum TokenStoreType {
    JSON,

    REDIS,

    VAULT,

    AWS_SECRET_MANAGER,

    DATABASE
}
