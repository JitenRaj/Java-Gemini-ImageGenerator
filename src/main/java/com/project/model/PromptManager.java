package com.project.model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class PromptManager {

    private static final String DEFAULT_TEMPLATE_PATH = "image_prompt_template.txt";

    /**
     * Loads the base prompt template from the classpath (resources directory).
     */
    public static String loadBasePrompt() {
        try (InputStream is = PromptManager.class
                .getClassLoader()
                .getResourceAsStream(DEFAULT_TEMPLATE_PATH)) {

            if (is == null) {
                throw new IllegalStateException("Prompt template not found in resources");
            }

            return new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));

        } catch (Exception e) {
            System.err.println("Failed to load prompt template. Using fallback. Reason: " + e.getMessage());
            return fallbackPrompt();
        }
    }

    /**
     * Formats the prompt by injecting user input into the template.
     */
    public static String formatPrompt(String userInput) {
        String basePrompt = loadBasePrompt();
        return basePrompt.replace("{user_input}", sanitize(userInput));
    }

    /**
     * Prevents accidental template breaking.
     */
    private static String sanitize(String input) {
        return input == null ? "" : input.trim();
    }

    /**
     * Fallback prompt used when template loading fails.
     */
    private static String fallbackPrompt() {
        return """
        # ROLE
        Lead Graphic Designer

        # THEME
        {user_input}

        # TASK
        Design a high-quality, visually elegant event banner featuring only the event name and tagline.

        # DESIGN REQUIREMENTS
        - Color Palette: Muted gold, deep navy blue, charcoal black, ivory white
        - Style: Sophisticated, refined, and elegant
        - Typography:
          - Event Name: Serif font
          - Tagline: Sans-serif font
        - Composition: Balanced layout with generous spacing

        # OUTPUT REQUIREMENTS
        Suitable for social media, websites, and print. High resolution and professional quality.
        """;
    }
}
