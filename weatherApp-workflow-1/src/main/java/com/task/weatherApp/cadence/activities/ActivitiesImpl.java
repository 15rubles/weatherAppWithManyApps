package com.task.weatherApp.cadence.activities;

import com.task.weatherApp.client.Client;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
@RequiredArgsConstructor
public class ActivitiesImpl implements GetWeatherRespondActivity, LaunchPostWeatherWorkflowActivity {


    @Override
    public Double getResponse(String city) {

        String url = "https://api.openweathermap.org/data/2.5/weather?" +
                "q=" + city +
                "&units=metric" +
                "&appid=538a46f6d1e21875b18fce87008adcaa";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        String resStr = response.body();
        JSONObject res = new JSONObject(resStr);

        if (res.getInt("cod") == 200) {
            return res.getJSONObject("main").getDouble("temp");
        } else {
            return null;
        }
    }

    @Override
    public void  launchWorkflow(String city, Double temperature) {
        Client.postWeather(city, temperature);
    }
}