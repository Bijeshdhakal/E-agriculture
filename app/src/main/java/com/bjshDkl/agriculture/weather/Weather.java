package com.bjshDkl.agriculture.weather;


import com.bjshDkl.agriculture.R;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class Weather {
    private String cityName, countryName;
    private double temperature;
    private String description;
    private double wind, pressure, humidity;
    private Date date, sunrise, sunset, update;
    private int weatherIconId;

    public void parseFromCurrent(JSONObject jsonObject) {
        try {
            //Location
            JSONObject sys = jsonObject.getJSONObject("sys");
            cityName = jsonObject.getString("name");
            countryName = sys.getString("country");

            //Weather information
            JSONObject main = jsonObject.getJSONObject("main");
            temperature = main.getDouble("temp");
            pressure = main.getDouble("pressure");
            humidity = main.getDouble("humidity");

            JSONObject details = jsonObject.getJSONArray("weather").getJSONObject(0);
            description = details.getString("description");

            JSONObject jWin = jsonObject.getJSONObject("wind");
            wind = jWin.getDouble("speed");

            //Time information
            date = new Date(jsonObject.getLong("dt") * 1000);
            sunrise = new Date(sys.getLong("sunrise") * 1000);
            sunset = new Date(sys.getLong("sunset") * 1000);
            update = new Date(jsonObject.getLong("dt") * 1000);

            //Weather icon
            weatherIconId = setWeatherIcon(details.getInt("id"), sys.getLong("sunrise") * 1000, sys.getLong("sunset") * 1000);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void parseFromDaily(JSONObject jsonObject) {
        try {
            //Weather information
            JSONObject tmp = jsonObject.getJSONObject("temp");
            double avgTemp = (tmp.getDouble("min") + tmp.getDouble("max")) / 2;
            temperature = Math.round(avgTemp);
            pressure = jsonObject.getDouble("pressure");
            humidity = jsonObject.getDouble("humidity");

            JSONObject details = jsonObject.getJSONArray("weather").getJSONObject(0);
            description = details.getString("description");

            wind = jsonObject.getDouble("speed");

            //Time information
            date = new Date(jsonObject.getLong("dt") * 1000);
            update = new Date(jsonObject.getLong("dt") * 1000);

            //Weather icon
            weatherIconId = setWeatherIcon(details.getInt("id"), new Date().getTime(), new Date().getTime());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void parseFromHourly(JSONObject jsonObject) {
        try {
            //Weather information
            JSONObject main = jsonObject.getJSONObject("main");
            temperature = main.getDouble("temp");
            pressure = main.getDouble("pressure");
            humidity = main.getDouble("humidity");

            JSONObject details = jsonObject.getJSONArray("weather").getJSONObject(0);
            description = details.getString("description");

            JSONObject jWind = jsonObject.getJSONObject("wind");
            wind = jWind.getDouble("speed");

            //Time information
            date = new Date(jsonObject.getLong("dt") * 1000);
            update = new Date(jsonObject.getLong("dt") * 1000);

            //Weather icon
            weatherIconId = setWeatherIcon(details.getInt("id"), new Date().getTime(), new Date().getTime());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void parseFromWeekly(JSONObject jsonObject){
        try {
            JSONObject main = jsonObject.getJSONObject("main");
            temperature = main.getDouble("temp");
            pressure = main.getDouble("pressure");
            humidity = main.getDouble("humidity");

            JSONObject details = jsonObject.getJSONArray("weather").getJSONObject(0);
            description = details.getString("description");

            JSONObject jWind = jsonObject.getJSONObject("wind");
            wind = jWind.getDouble("speed");


            date = new Date(jsonObject.getLong("dt") * 1000);


        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

    private int setWeatherIcon(int actualId, long sunrise, long sunset) {
        int id = actualId / 100;
        if (actualId == 800) {
            long currentTime = new Date().getTime();
            if (currentTime >= sunrise && currentTime < sunset) {
                return R.string.weather_sunny;
            } else {
                return R.string.weather_clear_night;
            }
        } else {
            switch (id) {
                case 2:
                    return R.string.weather_thunder;
                case 3:
                    return R.string.weather_drizzle;
                case 7:
                    return R.string.weather_foggy;
                case 8:
                    return R.string.weather_cloudy;
                case 6:
                    return R.string.weather_snowy;
                case 5:
                    return R.string.weather_rainy;
            }
        }
        return R.string.weather_sunny;
    }

    public int getWeatherIconId() {
        return weatherIconId;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public double getTemperature() {
        return temperature;
    }

    public String getDescription() {
        return description;
    }

    public double getWind() {
        return wind;
    }

    public double getPressure() {
        return pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public Date getDate() {
        return date;
    }

    public Date getSunrise() {
        return sunrise;
    }

    public Date getSunset() {
        return sunset;
    }

    public Date getUpdate() {
        return update;
    }
}
