package api.client;
import api.authentication.oauth.OAuthConstants;
import api.config.ApiConfig;
import api.manager.TokenManager;
import api.specifications.RequestSpecFactory;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class BaseApiClient {
    private static final Logger logger = LogManager.getLogger(BaseApiClient.class);
    protected RequestSpecification request() {
        return RequestSpecFactory.getRequestSpecification();
    }

    protected RequestSpecification authenticatedRequest() {
        return RequestSpecFactory.getAuthenticatedRequestSpecification();
    }

    protected Response get(String endpoint) {
        return request().get(endpoint);
    }

    protected Response authenticatedGet(String endpoint) {
        return authenticatedRequest().get(endpoint);
    }

    protected Response post(String endpoint,Object requestBody) {
        return request()
                .body(requestBody)
                .post(endpoint);
    }

    protected Response authenticatedPost(String endpoint,Object requestBody) {
        logger.info("Configured Base URI : {}", ApiConfig.getBaseUrl());
        logger.info("Endpoint            : {}", endpoint);
        logger.info("Content-Type        : {}", ApiConfig.getContentType());
        logger.info("Accept              : {}", ApiConfig.getAccept());
        logger.info("Request Body        : {}", requestBody);

        String token = TokenManager.getAccessToken();
        logger.info("Access Token Prefix : {}...", token.substring(0, Math.min(20, token.length())));
        /*return authenticatedRequest()
                .body(requestBody)
                .post(endpoint);*/
        return RestAssured
                .given()
                .baseUri(ApiConfig.getBaseUrl())
                .contentType(ApiConfig.getContentType())
                .accept(ApiConfig.getAccept())
                .header(
                        OAuthConstants.AUTHORIZATION,
                        OAuthConstants.BEARER_PREFIX + TokenManager.getAccessToken())
                .body(requestBody)
                .post(endpoint);
    }

    protected Response put(String endpoint,Object requestBody) {
        return request()
                .body(requestBody)
                .put(endpoint);
    }

    protected Response authenticatedPut(String endpoint,Object requestBody) {
        return authenticatedRequest()
                .body(requestBody)
                .put(endpoint);
    }

    protected Response patch(String endpoint,Object requestBody) {
        return request()
                .body(requestBody)
                .patch(endpoint);
    }

    protected Response authenticatedPatch(String endpoint,Object requestBody) {
        return authenticatedRequest()
                .body(requestBody)
                .patch(endpoint);
    }

    protected Response delete(String endpoint) {
        return request().delete(endpoint);
    }

    protected Response authenticatedDelete(String endpoint) {

        return authenticatedRequest().delete(endpoint);
    }


}
