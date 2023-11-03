package com.example.WeatherReport.controller;

import com.example.WeatherReport.apiCall.WeatherCall;
import com.example.WeatherReport.payload.WeatherInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/weather")
@CrossOrigin(origins = "*")
public class WeatherController {
    // inject the WeatherCall object
    private final WeatherCall weatherCall;

    @Autowired
    public WeatherController(WeatherCall weatherCall){
        this.weatherCall = weatherCall;
    }
    @GetMapping("/info")
    public ResponseEntity<WeatherInfo> sendJokes(@RequestParam String city){
        // call the getWeather method from the WeatherCall object
        WeatherInfo weatherInfo = weatherCall.getWeather(city);
        if (weatherInfo == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND); // if the weatherInfo object is null then return a 404

        return new ResponseEntity<>(weatherInfo, HttpStatus.OK);
    }
}
