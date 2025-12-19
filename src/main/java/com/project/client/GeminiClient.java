package com.project.client;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.project.config.AppConfig;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.Base64;

public class GeminiClient {

    private final String apiKey;
    private final String baseUrl;
    private final ObjectMapper mapper = new ObjectMapper();

    public GeminiClient(AppConfig config) {
        this.apiKey = config.apiKey;
        this.baseUrl = config.baseUrl;

        System.out.println("STATUS: CLIENT INITIALIZED");
    }

    public byte[] generateImage(String prompt) throws Exception {
        // Construct JSON using ObjectMapper
        ObjectNode rootNode = mapper.createObjectNode();

        // contents: [{ parts: [{ text: "..." }] }]
        ArrayNode contents = rootNode.putArray("contents");
        ObjectNode userTurn = contents.addObject();
        ArrayNode parts = userTurn.putArray("parts");
        parts.addObject().put("text", prompt);

        // generationConfig: { response_modalities: ["IMAGE"] }
        ObjectNode genConfig = rootNode.putObject("generationConfig");
        ArrayNode modalities = genConfig.putArray("response_modalities");
        modalities.add("IMAGE");

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // Append API key to the URL
            HttpPost request = new HttpPost(baseUrl + "?key=" + apiKey);
            request.addHeader("Content-Type", "application/json");
            request.setEntity(new StringEntity(mapper.writeValueAsString(rootNode)));

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int status = response.getStatusLine().getStatusCode();
                HttpEntity entity = response.getEntity();
                String responseBody = (entity != null) ? EntityUtils.toString(entity) : "";

                if (status != 200) {
                    System.err.println("API Error " + status + ": " + responseBody);
                    throw new RuntimeException("API Call Failed with status " + status);
                }

                JsonNode root = mapper.readTree(responseBody);
                JsonNode predictions = root.path("predictions");

                if (predictions.isMissingNode() || predictions.isEmpty()) {
                    throw new RuntimeException("No images returned from API. Check prompt safety filters.");
                }

                String imageBase64 = predictions.get(0).path("bytesBase64Encoded").asText();
                return Base64.getDecoder().decode(imageBase64);
            }
        }
    }

    public void processAndDisplayImage(byte[] imageBytes) throws IOException {
        // System.getProperty("user.dir") ensures we start at the project root
        Path rootPath = Paths.get(System.getProperty("user.dir"));
        Path targetDir = rootPath.resolve("public/images");

        Files.createDirectories(targetDir);

        // Using a timestamp so you don't overwrite the same image every time
        String fileName = "output-" + System.currentTimeMillis() + ".jpg";
        Path outputPath = targetDir.resolve(fileName);

        try (FileOutputStream fos = new FileOutputStream(outputPath.toFile())) {
            fos.write(imageBytes);
        }
        System.out.println("Image saved to: " + outputPath.toAbsolutePath());
    }
}