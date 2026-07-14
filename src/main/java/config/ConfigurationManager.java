package config;

public final class ConfigurationManager {

    private ConfigurationManager() {
    }

    private static final String CONFIG_FOLDER = "src/main/resources/config/";
    private static final String DEFAULT_ENVIRONMENT = "qa";

    public static String getConfigFilePath() {

        String env = System.getProperty("env");

        if (env == null || env.trim().isEmpty()) {
            env = DEFAULT_ENVIRONMENT;
        }

        return CONFIG_FOLDER + "config-" + env.toLowerCase() + ".properties";
    }
}
