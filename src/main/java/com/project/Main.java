package com.project;

import com.project.client.GeminiClient;
import com.project.config.AppConfig;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("-----APP-STARTED-----");

        AppConfig config = AppConfig.load();

        GeminiClient client = new GeminiClient(config);

        byte[] imageBytes = client.generateImage("A white horse");

        client.processAndDisplayImage(imageBytes);
    }
}
