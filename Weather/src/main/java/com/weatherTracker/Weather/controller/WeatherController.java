package com.weatherTracker.Weather.controller;

import java.util.Map;
import java.util.HashMap;
import java.util.HashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class WeatherController {
    
    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/weather")
    public Map<String,Object> getWeather(@RequestParam String city){
        // Step 1: Geocode city name -> lat/long
        String geoUrl = "https://geocoding-api.open-meteo.com/v1/search?name=" + city;
        Map geoResponse = restTemplate.getForObject(geoUrl, Map.class);

        if (geoResponse == null || !geoResponse.containsKey("results")) {
            return Map.of("error", "City not found");
        }
        Map firstResult = ((java.util.List<Map>) geoResponse.get("results")).get(0);
        double latitude = (double) firstResult.get("latitude");
        double longitude = (double) firstResult.get("longitude");

        // Step 2: Fetch weather for coordinates
        String weatherUrl = String.format(
            "https://api.open-meteo.com/v1/forecast?latitude=%f&longitude=%f&current_weather=true",
            latitude, longitude
        );
        Map weatherResponse = restTemplate.getForObject(weatherUrl, Map.class);

        return Map.of(
            "city", city,
            "latitude", latitude,
            "longitude", longitude,
            "weather", weatherResponse
        );
    }

    @GetMapping("/weather2")
    public Map<String,Object> getWeather2(){
       Map<String,Object> dummy = new HashMap<>(); 
       dummy.put("city","Lisbon");
       dummy.put("temp","25");
       return dummy;
    }


    }