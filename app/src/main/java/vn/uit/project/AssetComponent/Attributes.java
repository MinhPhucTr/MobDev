package vn.uit.project.AssetComponent;

import com.google.gson.annotations.SerializedName;

public class Attributes {
    @SerializedName("weatherData")
    private WeatherData weatherData;

    public WeatherData getWeatherData() {
        return weatherData;
    }

    public void setWeatherData(WeatherData weatherData) {
        this.weatherData = weatherData;
    }
}
