package com.task.weatherApp.controller;


import com.task.weatherApp.client.Client;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller

public class WeatherInfoController {



    @GetMapping("/weather")
    public String weather(@RequestParam(name = "city", required = false) String city, Model model) {
        String text;
        if (city == null) {
            text = "Type \"localhost:8080/weather?city=<city_name>\" in your browser search bar";
            model.addAttribute("text", text);
            return "weather";
        }

        Double temperature = Double.valueOf(Client.getWeather(city));
        System.out.println("Temperature: "+ temperature);
        text = temperature == null ?
                "City not found":
                "Weather in " + city + ": " + temperature + " °C" ;
        model.addAttribute("text", text);
        return "weather";
    }


}
