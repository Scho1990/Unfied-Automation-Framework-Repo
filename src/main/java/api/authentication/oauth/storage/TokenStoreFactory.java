package api.authentication.oauth.storage;

import api.config.ApiConfig;
import exceptions.api.ApiConfigurationException;

/**
 * Factory responsible for creating TokenStore implementations.
 */
public final class TokenStoreFactory {

    private static final TokenStore TOKEN_STORE = createInternal();

    private TokenStoreFactory() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static TokenStore getInstance() {
        return TOKEN_STORE;
    }

    public static TokenStore createInternal() {

        return switch (ApiConfig.getTokenStoreType()){

            case JSON -> new JsonTokenStore();

            case REDIS -> throw new ApiConfigurationException("Redis token store not yet supported.");

            case VAULT -> throw new ApiConfigurationException("Vault token store not yet supported.");

            case AWS_SECRET_MANAGER -> throw new ApiConfigurationException("AWS secret manager token store not yet supported.");

            case DATABASE ->  throw new ApiConfigurationException("Database token store not yet supported.");
        };
    }
}
