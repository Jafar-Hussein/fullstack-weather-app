package com.example.WeatherReport.apiCall;

import com.example.WeatherReport.payload.WeatherInfo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
@Service
public class WeatherCall {

    public WeatherInfo getWeather(String city) {
        // create a dotenv object to get the api key from the .env file
        Dotenv dotenv = Dotenv.load();
        String apiKey = dotenv.get("apiKey");
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=metric"; // create the url to call the api
        try{
            URL newUrl = new URL(url);
            HttpURLConnection con =(HttpURLConnection) newUrl.openConnection(); // open the connection

            int responseCode = con.getResponseCode(); // get the response code

            if (responseCode == HttpURLConnection.HTTP_OK){ // if the response code is 200 then read the response
                BufferedReader in = new BufferedReader(new java.io.InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null){ // while there is a line to read append it to the response
                    response.append(inputLine);
                }
                in.close();

                // create an object mapper to map the json response to the WeatherInfo object
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(response.toString());

                WeatherInfo weatherInfo = new WeatherInfo();
                // set the values of the weatherInfo object from the json response into the WeatherInfo object
                weatherInfo.setCityName(city);
                weatherInfo.setTemperature(rootNode.get("main").get("temp").asDouble());
                weatherInfo.setWindSpeed(rootNode.get("wind").get("speed").asDouble());
                weatherInfo.setHumidity(rootNode.get("main").get("humidity").asText());

                return weatherInfo;
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

}
