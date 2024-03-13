package net.glitchtechs.starapi;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class WebhookBuilder {

    private final String webhookUrl;
    private String message;

    private WebhookBuilder(String webhookUrl) {
        this.webhookUrl = webhookUrl;
    }

    public static WebhookBuilder create(String webhookUrl) {
        return new WebhookBuilder(webhookUrl);
    }

    public WebhookBuilder addMessage(String message) {
        this.message = message;
        return this;
    }

    public void build() {
        try {
            URL url = new URL(webhookUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String payload = "{\"content\":\"" + message + "\"}";

            try (OutputStream outputStream = connection.getOutputStream()) {
                byte[] input = payload.getBytes(StandardCharsets.UTF_8);
                outputStream.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            System.out.println("HTTP Response Code: " + responseCode);

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

