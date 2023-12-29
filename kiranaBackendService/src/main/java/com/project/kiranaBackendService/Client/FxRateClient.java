package com.project.kiranaBackendService.Client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FxRateClient {
    private static final String API_URL = "https://api.fxratesapi.com/latest";


    public static JsonObject getAllRates() throws IOException {
        try{
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

            // Parse JSON response using Gson
                JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();

            // Pretty print JSON
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String prettyJson = gson.toJson(jsonResponse);
                return jsonResponse;
            } else {
                System.out.println("Error: " + responseCode);
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
