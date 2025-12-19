package com.project.config;

import io.github.cdimascio.dotenv.Dotenv;

public class AppConfig {
    public final String apiKey;
    public final String baseUrl;

    private AppConfig(String apiKey, String baseUrl) {
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
    }

    public static AppConfig load() {
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();

        String apiKey = dotenv.get("GEMINI_API_KEY");
        String baseUrl = dotenv.get("GEMINI_BASE_URL");

        if (apiKey == null) {
            throw new IllegalArgumentException("GEMINI_API_KEY not found in environment. Set it before running the application.");
        }
        if (baseUrl == null) {
            throw new IllegalArgumentException("GEMINI_BASE_URL not found in environment. Set it before running the application.");
        }

        System.out.println("STATUS: Configurations are set.");

        return new AppConfig(apiKey, baseUrl);
    }
}

