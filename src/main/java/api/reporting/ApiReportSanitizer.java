package api.reporting;

import io.restassured.http.Header;
import io.restassured.http.Headers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Sanitizes API evidence before it is written to Extent Reports.
 * Reports are persistent artifacts, so credentials and tokens must never be exposed.
 */
public final class ApiReportSanitizer {

    public static final String MASKED_VALUE = "[MASKED]";

    private static final Set<String> SENSITIVE_HEADER_NAMES = Set.of(
            "authorization",
            "proxy-authorization",
            "cookie",
            "set-cookie",
            "x-api-key",
            "api-key"
    );

    private static final Pattern JSON_SENSITIVE_FIELD = Pattern.compile(
            "(?i)(\\\"(?:access_token|refresh_token|client_secret|authorization|api_key|apikey|password|secret|code)\\\"\\s*:\\s*\\\")[^\\\"]*(\\\")"
    );

    private static final Pattern FORM_SENSITIVE_FIELD = Pattern.compile(
            "(?i)(^|[?&\\s])(access_token|refresh_token|client_secret|authorization|api_key|apikey|password|secret|code)=([^&\\s]*)"
    );

    private ApiReportSanitizer() {
    }

    public static String sanitizeHeaders(Headers headers) {
        if (headers == null || headers.asList().isEmpty()) {
            return "{}";
        }

        List<String> sanitized = new ArrayList<>();
        for (Header header : headers.asList()) {
            String value = isSensitiveHeader(header.getName())
                    ? MASKED_VALUE
                    : header.getValue();

            sanitized.add(header.getName() + ": " + value);
        }

        return String.join(System.lineSeparator(), sanitized);
    }

    public static String sanitizeQueryParams(Map<String, ?> queryParams) {
        if (queryParams == null || queryParams.isEmpty()) {
            return "{}";
        }

        StringBuilder builder = new StringBuilder("{");
        boolean first = true;

        for (Map.Entry<String, ?> entry : queryParams.entrySet()) {
            if (!first) {
                builder.append(", ");
            }

            builder.append(entry.getKey())
                    .append("=")
                    .append(isSensitiveName(entry.getKey())
                            ? MASKED_VALUE
                            : String.valueOf(entry.getValue()));

            first = false;
        }

        return builder.append("}").toString();
    }

    public static String sanitizeBody(String body) {
        if (body == null || body.isBlank()) {
            return "<empty>";
        }

        String sanitized = JSON_SENSITIVE_FIELD.matcher(body)
                .replaceAll("$1" + MASKED_VALUE + "$2");

        return FORM_SENSITIVE_FIELD.matcher(sanitized)
                .replaceAll("$1$2=" + MASKED_VALUE);
    }

    public static String sanitizeUri(String uri) {
        if (uri == null || uri.isBlank()) {
            return "<unknown>";
        }

        int queryIndex = uri.indexOf('?');
        if (queryIndex < 0) {
            return uri;
        }

        String base = uri.substring(0, queryIndex);
        String query = uri.substring(queryIndex + 1);

        StringBuilder sanitizedQuery = new StringBuilder();
        boolean first = true;

        for (String parameter : query.split("&")) {
            if (!first) {
                sanitizedQuery.append('&');
            }

            String[] parts = parameter.split("=", 2);
            sanitizedQuery.append(parts[0]);

            if (parts.length == 2) {
                sanitizedQuery.append('=')
                        .append(isSensitiveName(parts[0])
                                ? MASKED_VALUE
                                : parts[1]);
            }

            first = false;
        }

        return base + "?" + sanitizedQuery;
    }

    private static boolean isSensitiveHeader(String name) {
        return name != null && SENSITIVE_HEADER_NAMES.contains(name.toLowerCase());
    }

    private static boolean isSensitiveName(String name) {
        if (name == null) {
            return false;
        }

        String normalized = name.toLowerCase().replace("-", "_");
        return normalized.equals("access_token")
                || normalized.equals("refresh_token")
                || normalized.equals("client_secret")
                || normalized.equals("authorization")
                || normalized.equals("api_key")
                || normalized.equals("apikey")
                || normalized.equals("password")
                || normalized.equals("secret")
                || normalized.equals("code");
    }
}
