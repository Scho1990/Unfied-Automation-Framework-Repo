package api.specifications;

import api.authentication.oauth.configuration.OAuthConfiguration;
import api.authentication.oauth.configuration.OAuthConstants;
import api.authentication.oauth.utility.BasicAuthorizationHeaderBuilder;
import api.config.ApiConfig;
import api.constants.ApiConstants;
import api.authentication.oauth.manager.TokenManager;
import api.reporting.ExtentApiReportFilter;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;


public final class RequestSpecFactory {

    private RequestSpecFactory() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static RequestSpecification getRequestSpecification() {

        return getBaseBuilder(ApiConfig.getBaseUrl())
                .addHeader(ApiConstants.API_KEY,ApiConfig.getApiKey())
                .addFilter(new ExtentApiReportFilter())
                .build();
    }

    //If you are testing different baseurl for integrating multiple data flow from one application to another
    public static RequestSpecification getRequestSpecification(String baseUrl) {
        return getBaseBuilder(baseUrl)
                .addHeader(ApiConstants.API_KEY,ApiConfig.getApiKey())
                .addFilter(new ExtentApiReportFilter())
                .build();
    }

    public static RequestSpecification getAuthenticatedRequestSpecification() {
        return getBaseBuilder(ApiConfig.getBaseUrl())
                .addHeader(
                        OAuthConstants.AUTHORIZATION,
                        OAuthConstants.BEARER_PREFIX +
                                   TokenManager.getAccessToken())
                .addFilter(new ExtentApiReportFilter())
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
