package api.client;

import api.specifications.RequestSpecFactory;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import static io.restassured.RestAssured.given;

public abstract class BaseApiClient {

    private static final Logger logger = LogManager.getLogger(BaseApiClient.class);

    protected RequestSpecification request() {

        return given()
                .spec(RequestSpecFactory.getRequestSpecification());
    }

    protected RequestSpecification authenticatedRequest() {

        return given()
                .spec(RequestSpecFactory.getAuthenticatedRequestSpecification());
    }

    protected Response get(String endpoint) {
        return request()
                .when()
                .get(endpoint);
    }

    protected Response authenticatedGet(String endpoint) {

        return authenticatedRequest()
                .when()
                .get(endpoint);
    }

    protected Response authenticatedGetWithPathParams(String endpoint, Map<String, ?> pathParams) {

        return authenticatedRequest()
                .pathParams(pathParams)
                .when()
                .get(endpoint);
    }

    protected Response authenticatedGetWithQueryParams(String endpoint, Map<String, ?> queryParams) {

        return authenticatedRequest()
                .queryParams(queryParams)
                .when()
                .get(endpoint);
    }

    protected Response authenticatedGetWithQueryAndPathParams(String endpoint,Map<String, ?>pathParams, Map<String, ?> queryParams) {

        return authenticatedRequest()
                .pathParams(pathParams)
                .queryParams(queryParams)
                .when()
                .get(endpoint);
    }

    protected Response post(String endpoint,Object requestBody) {
        return request()
                .body(requestBody)
                .when()
                .post(endpoint);
    }

    protected Response authenticatedPost(String endpoint, Object requestBody) {
        return  authenticatedRequest()
                .body(requestBody)
                .when()
                .post(endpoint);
    }

    protected Response authenticatedPostWithPathParam(String endpoint, Object requestBody, Map<String, ?> pathParams) {
        return  authenticatedRequest()
                .pathParams(pathParams)
                .body(requestBody)
                .when()
                .post(endpoint);
    }

    protected Response put(String endpoint,Object requestBody) {
        return request()
                .body(requestBody)
                .when()
                .put(endpoint);
    }

    protected Response authenticatedPut(String endpoint,Object requestBody) {
        return authenticatedRequest()
                .body(requestBody)
                .when()
                .put(endpoint);
    }

    protected Response authenticatedPutWithPathParams(String endpoint,Object requestBody,Map<String, ?> pathParams) {
        return authenticatedRequest()
                .pathParams(pathParams)
                .body(requestBody)
                .when()
                .put(endpoint);
    }

    protected Response patch(String endpoint,Object requestBody) {
        return request()
                .body(requestBody)
                .when()
                .patch(endpoint);
    }

    protected Response authenticatedPatch(String endpoint,Object requestBody) {
        return authenticatedRequest()
                .body(requestBody)
                .when()
                .patch(endpoint);
    }

    protected Response delete(String endpoint) {

        return request()
                .when()
                .delete(endpoint);
    }

    protected Response authenticatedDelete(String endpoint) {

        return authenticatedRequest()
                .when()
                .delete(endpoint);
    }

    protected Response authenticatedDelete(String endpoint, Map<String, ?> queryParams) {

        return authenticatedRequest()
                .queryParams(queryParams)
                .when()
                .delete(endpoint);
    }

    protected Response authenticatedDelete(String endpoint, Object requestBody, Map<String, ?> pathParams) {

        return authenticatedRequest()
                .pathParams(pathParams)
                .body(requestBody)
                .when()
                .delete(endpoint);
    }



}
