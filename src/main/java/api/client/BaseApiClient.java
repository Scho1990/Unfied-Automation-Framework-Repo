package api.client;

import api.config.ApiConfig;
import api.specifications.RequestSpecFactory;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public abstract class BaseApiClient {

    protected RequestSpecification request() {
        return RequestSpecFactory.getRequestSpecification();
    }

    protected Response get(String endpoint) {
        return request().get(endpoint);
    }

    protected abstract Response get(String endpoint, Map<String, ?> queryParams);

    protected abstract Response get(String endpoint, Map<String, ?> queryParams, Map<String, ?> headers);

    protected Response post(String endpoint,Object requestBody) {
        return request()
                .body(requestBody)
                .post(endpoint);
    }

    /*protected Response get(String endpoint) {

        System.out.println("Base URI : " + ApiConfig.getBaseUrl());
        System.out.println("Endpoint : " + endpoint);

        return RestAssured
                .given()
                .baseUri(ApiConfig.getBaseUrl())
                .accept(ApiConfig.getAccept())
                .contentType(ApiConfig.getContentType())
                .log().all()
                .get(endpoint);
    }*/

    protected Response put(String endpoint,Object requestBody) {
        return request()
                .body(requestBody)
                .put(endpoint);
    }

    protected Response patch(String endpoint,Object requestBody) {
        return request()
                .body(requestBody)
                .patch(endpoint);
    }

    protected Response delete(String endpoint) {
        return request().delete(endpoint);
    }


}
