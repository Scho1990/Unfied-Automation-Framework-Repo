package api.specifications;

import api.authentication.oauth.OAuthConstants;
import api.config.ApiConfig;
import api.constants.ApiConstants;
import api.manager.TokenManager;
import io.restassured.builder.RequestSpecBuilder;
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

    private static RequestSpecBuilder getBaseBuilder(String baseUrl) {
        return new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setContentType(ApiConfig.getContentType())
                .setAccept(ApiConfig.getAccept());

    }
}
