package api.authentication.oauth.utility;

import api.constants.ApiConstants;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

/**
 * Utility class for building HTTP Basic Authorization headers.
 */
public final class BasicAuthorizationHeaderBuilder{

    private BasicAuthorizationHeaderBuilder(){
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Builds an HTTP Basic Authorization header.
     *
     * @param clientId Spotify Client ID
     * @param clientSecret Spotify Client Secret
     * @return Authorization header value
     */
    public static String build(String clientId, String clientSecret) {

        Objects.requireNonNull(clientId,
                "Client ID cannot be null.");

        Objects.requireNonNull(clientSecret,
                "Client Secret cannot be null.");

        String credentials =
                clientId + ":" + clientSecret;

        String encodedCredentials  =
                Base64.getEncoder()
                        .encodeToString(
                                credentials.getBytes(StandardCharsets.UTF_8));

        return ApiConstants.BASIC + encodedCredentials;
    }
}
