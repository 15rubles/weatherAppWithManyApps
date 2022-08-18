package com.task.weatherApp.cadence.activities;

import com.task.weatherApp.model.Weather;
import com.task.weatherApp.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class ActivitiesImpl implements PostWeatherInfActivity {

    private final WeatherRepository weatherRepository;

    @Autowired
    public ActivitiesImpl(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }


    @Override
    public void postWeatherInf(String city, Double temperature) {
        if (temperature != null) {
            weatherRepository.save(new Weather(LocalTime.now(), LocalDate.now(), city, temperature));
        }
    }

}