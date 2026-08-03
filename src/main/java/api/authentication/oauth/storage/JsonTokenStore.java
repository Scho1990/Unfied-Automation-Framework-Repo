package api.authentication.oauth.storage;

import api.authentication.oauth.model.OAuthToken;
import api.config.ApiConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.api.TokenStoreException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

public final class JsonTokenStore implements TokenStore {

    private static final Logger logger = LogManager.getLogger(JsonTokenStore.class);

    private static final ObjectMapper OBJECT_MAPPER =
            new ObjectMapper()
                    .findAndRegisterModules();

    private final Path tokenPath;

    public JsonTokenStore() {
        this(Path.of(ApiConfig.getOAuthTokenStorePath()));
    }

    JsonTokenStore(Path tokenPath) {
        this.tokenPath = tokenPath;
    }

    @Override
    public void save(OAuthToken token) {
        Objects.requireNonNull(token,
                "OAuth token cannot be null.");
        try {

            Path parent = tokenPath.getParent();

            if (parent != null) {
                Files.createDirectories(parent);
            }

            OBJECT_MAPPER
                    .writerWithDefaultPrettyPrinter()
                    .writeValue(
                            tokenPath.toFile(),
                            token);

            logger.info(
                    "OAuth token saved successfully : {}",
                    tokenPath);

        } catch (IOException ex) {

            throw new TokenStoreException(
                    "Unable to save OAuth token.",
                    ex);
        }
    }

    @Override
    public Optional<OAuthToken> load() {
        if (!exists()) {
            return Optional.empty();
        }

        try {

            OAuthToken token =
                    OBJECT_MAPPER.readValue(
                            tokenPath.toFile(),
                            OAuthToken.class);

            logger.info("OAuth token loaded from : {}",tokenPath);

            return Optional.of(token);

        } catch (IOException ex) {

            throw new TokenStoreException(
                    "Unable to load OAuth token.",
                    ex);
        }
    }

    @Override
    public void delete() {
        try {

            Files.deleteIfExists(tokenPath);

            logger.info("OAuth token deleted successfully from: {}", tokenPath);

        } catch (IOException ex) {

            throw new TokenStoreException(
                    "Unable to delete OAuth token.",
                    ex);
        }
    }

    @Override
    public boolean exists() {
        return Files.exists(tokenPath);
    }
}
