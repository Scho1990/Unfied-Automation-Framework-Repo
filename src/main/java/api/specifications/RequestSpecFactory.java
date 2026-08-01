package api.specifications;

import api.config.ApiConfig;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;


public final class RequestSpecFactory {
    private RequestSpecFactory() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static RequestSpecification getRequestSpecification() {
        return new RequestSpecBuilder()
                .setBaseUri(ApiConfig.getBaseUrl())
                .setContentType(ApiConfig.getContentType())
                .setAccept(ApiConfig.getAccept())
                .setRelaxedHTTPSValidation()
                .addHeader("x-api-key",ApiConfig.getApiKey())
                .build();
    }

    //If you are testing different baseurl for integrating multiple data flow from one application to another
    public static RequestSpecification getRequestSpecification(String baseUrl) {
        return new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setContentType(ApiConfig.getContentType())
                .setAccept(ApiConfig.getAccept())
                .setRelaxedHTTPSValidation()
                .addHeader("x-api-key",ApiConfig.getApiKey())
                .build();
    }
}
