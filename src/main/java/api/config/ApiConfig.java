package api.config;

import api.constants.ApiConstants;
import config.ConfigReader;

public final class ApiConfig {

    private ApiConfig() {
    }

    public static String getBaseUrl() {
        return ConfigReader.getPropertyOrSystem("api.base.url");
    }

    public static String getContentType() {
        return ConfigReader.getProperty("api.content.type");
    }
    public static String getAccept() {
        return ConfigReader.getProperty("api.accept");
    }

    public static int getConnectionTimeout() {
        return ConfigReader.getIntProperty("api.connect.timeout");
    }

    public static int getReadTimeout() {
        return ConfigReader.getIntProperty("api.read.timeout");
    }

    public static boolean isRequestLoggingEnabled() {
        return ConfigReader.getBooleanProperty("api.log.request");
    }

    public static boolean isResponseLoggingEnabled() {
        return ConfigReader.getBooleanProperty("api.log.response");
    }

    public static String getApiKey(){
        return ConfigReader.getProperty("api.key");
    }

}

