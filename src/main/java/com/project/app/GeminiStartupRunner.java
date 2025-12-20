package com.project.app;

import com.project.client.GeminiClient;
import com.project.config.AppConfig;
import com.project.model.PromptManager;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class GeminiStartupRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) {
        try {
            AppConfig config = AppConfig.load();
            GeminiClient client = new GeminiClient(config);

            String prompt = PromptManager.formatPrompt("A white horse");

            byte[] imageBytes = client.generateImage(prompt);

            if (imageBytes != null) {
                client.processAndDisplayImage(imageBytes);
            } else {
                System.out.println("Image generation skipped (quota exhausted).");
            }

        } catch (Exception e) {
            System.err.println("Gemini initialization failed: " + e.getMessage());
        }
    }
}
