package api.reporting;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reports.ExtentLogger;

/**
 * Captures API request/response evidence centrally for the unified Extent report.
 * Authentication/token endpoints are not wired to this filter, so OAuth credentials
 * are not reported here. Sensitive values are still sanitized defensively.
 */
public final class ExtentApiReportFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(ExtentApiReportFilter.class);

    @Override
    public Response filter(
            FilterableRequestSpecification requestSpec,
            FilterableResponseSpecification responseSpec,
            FilterContext context) {

        long startNanos = System.nanoTime();

        logRequest(requestSpec);

        try {
            Response response = context.next(requestSpec, responseSpec);
            long durationMillis = (System.nanoTime() - startNanos) / 1_000_000;
            logResponse(response, durationMillis);
            return response;
        } catch (RuntimeException e) {
            long durationMillis = (System.nanoTime() - startNanos) / 1_000_000;

            logger.warn(
                    "API request failed before receiving a response. Method: {}, URI: {}, Duration: {} ms",
                    requestSpec.getMethod(),
                    ApiReportSanitizer.sanitizeUri(requestSpec.getURI()),
                    durationMillis,
                    e
            );

            ExtentLogger.log(
                    com.aventstack.extentreports.Status.FAIL,
                    "API execution failed before a response was received. Duration: "
                            + durationMillis + " ms"
            );

            return throwException(e);
        }
    }

    private void logRequest(FilterableRequestSpecification requestSpec) {
        String request = "Method: " + requestSpec.getMethod() + System.lineSeparator()
                + "URL: " + ApiReportSanitizer.sanitizeUri(requestSpec.getURI()) + System.lineSeparator()
                + "Query Parameters: " + ApiReportSanitizer.sanitizeQueryParams(requestSpec.getQueryParams()) + System.lineSeparator()
                + "Headers:" + System.lineSeparator()
                + ApiReportSanitizer.sanitizeHeaders(requestSpec.getHeaders()) + System.lineSeparator()
                + "Body:" + System.lineSeparator()
                + ApiReportSanitizer.sanitizeBody(requestSpec.getBody());

        ExtentLogger.codeBlock("API Request", request);
    }

    private void logResponse(Response response, long durationMillis) {
        String responseDetails = "Status Code: " + response.getStatusCode() + System.lineSeparator()
                + "Status Line: " + response.getStatusLine() + System.lineSeparator()
                + "Response Time: " + durationMillis + " ms" + System.lineSeparator()
                + "Headers:" + System.lineSeparator()
                + ApiReportSanitizer.sanitizeHeaders(response.getHeaders()) + System.lineSeparator()
                + "Body:" + System.lineSeparator()
                + ApiReportSanitizer.sanitizeBody(response.getBody().asPrettyString());

        ExtentLogger.codeBlock("API Response", responseDetails);
    }

    private Response throwException(RuntimeException exception) {
        throw exception;
    }
}
