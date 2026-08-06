package api.authentication.oauth.utility;

import api.authentication.oauth.configuration.OAuthConstants;
import api.authentication.oauth.configuration.OAuthResponseType;
import exceptions.api.OAuthException;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public final class AuthorizationUrlBuilder {

    private AuthorizationUrlBuilder() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Builds a standards-compliant OAuth 2.0 Authorization URL.
     *
     * @param authorizationEndpoint OAuth Authorization Endpoint
     * @param clientId Client ID
     * @param redirectUri Registered Redirect URI
     * @param scope Requested OAuth scopes
     * @param responseType OAuth Response Type
     * @param state Optional state parameter (recommended for CSRF protection)
     * @return Complete OAuth Authorization URL
     */
    public static String buildAuthorizationUrl(String authorizationEndpoint, String clientId, String redirectUri, String scope, OAuthResponseType responseType, String state) {
       validateRequiredParameters(authorizationEndpoint, clientId, redirectUri, responseType);

       StringBuilder builder = new StringBuilder();
       builder.append(authorizationEndpoint)
               .append("?")
               .append(OAuthConstants.CLIENT_ID)
               .append("=")
               .append(encode(clientId))

               .append("&")
               .append(OAuthConstants.RESPONSE_TYPE)
               .append("=")
               .append(encode(responseType.getValue()))

               .append("&")
               .append(OAuthConstants.REDIRECT_URI)
               .append("=")
               .append(encode(redirectUri));

       if (scope != null && !scope.isBlank()) {
           builder.append("&")
                   .append(OAuthConstants.SCOPE)
                   .append("=")
                   .append(encode(scope));
       }

       if (state != null && !state.isBlank()) {
           builder.append("&")
                   .append(OAuthConstants.STATE)
                   .append("=")
                   .append(encode(state));
       }

       return builder.toString();

    }

    /**
     * URL encodes parameter values using UTF-8.
     */
    private static String encode(String value){
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    /**
     * Validates mandatory OAuth parameters.
     */
    private static void validateRequiredParameters(String authorizationEndpoint,String clientId, String redirectUri,OAuthResponseType responseType){
        if (isBlank(authorizationEndpoint)){
            throw new OAuthException("Authorization endpoint cannot be null or blank.");
        }

        if (isBlank(clientId)){
            throw new OAuthException("Client ID cannot be null or blank.");
        }

        if (isBlank(redirectUri)){
            throw new OAuthException("Redirect URI cannot be null or blank.");
        }

        if (responseType == null){
            throw new OAuthException("Response Type cannot be null.");
        }
    }

    /**
     * Checks whether a String is null or blank.
     */
    private static boolean isBlank(String value){
        return value == null || value.isBlank();
    }
}
