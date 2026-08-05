package api.specifications;

import api.authentication.oauth.configuration.OAuthConfiguration;
import api.authentication.oauth.configuration.OAuthConstants;
import api.authentication.oauth.utility.BasicAuthorizationHeaderBuilder;
import api.config.ApiConfig;
import api.constants.ApiConstants;
import api.authentication.oauth.manager.TokenManager;
import config.ConfigReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public final class RequestSpecFactory {

    private static final Logger logger = LogManager.getLogger(RequestSpecFactory.class);

    private RequestSpecFactory() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static RequestSpecification getRequestSpecification() {

        return getBaseBuilder(ApiConfig.getBaseUrl())
                .addHeader(ApiConstants.API_KEY,ApiConfig.getApiKey())
                .build();
    }

    //If you are testing different baseurl for integrating multiple data flow from one application to another
    public static RequestSpecification getRequestSpecification(String baseUrl) {
        return getBaseBuilder(baseUrl)
                .addHeader(ApiConstants.API_KEY,ApiConfig.getApiKey())
                .build();
    }

    public static RequestSpecification getAuthenticatedRequestSpecification() {
        return getBaseBuilder(ApiConfig.getBaseUrl())
                .addHeader(
                        OAuthConstants.AUTHORIZATION,
                        OAuthConstants.BEARER_PREFIX +
                                   TokenManager.getAccessToken())
                .build();
    }

    public static RequestSpecification getOAuthRequestSpecification() {

        OAuthConfiguration configuration = ApiConfig.getSpotifyOAuthConfiguration();
        return getBaseBuilder(configuration.getTokenUrl())
                .setContentType(ContentType.URLENC)
                .addHeader(OAuthConstants.AUTHORIZATION, BasicAuthorizationHeaderBuilder.build(
                        configuration.getClientId(),
                        configuration.getClientSecret()))
                .build();
    }

    private static RequestSpecBuilder getBaseBuilder(String baseUrl) {
        return new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setContentType(ApiConfig.getContentType())
                .setAccept(ApiConfig.getAccept());

    }
}
