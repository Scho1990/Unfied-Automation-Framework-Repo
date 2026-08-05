package api.authentication.oauth.utility;

import api.constants.ApiConstants;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public final class BasicAuthorizationHeaderBuilder{

    private BasicAuthorizationHeaderBuilder(){
        throw new UnsupportedOperationException("Utility class");
    }

    public static String build(String clientId, String clientSecret) {
        String credentials =
                clientId + ":" + clientSecret;

        String encoded =
                Base64.getEncoder()
                        .encodeToString(
                                credentials.getBytes(StandardCharsets.UTF_8));

        return ApiConstants.BASIC + encoded;
    }
}
