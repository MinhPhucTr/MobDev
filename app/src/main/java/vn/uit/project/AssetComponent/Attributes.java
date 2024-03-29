package vn.uit.project.AssetComponent;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Attributes implements Serializable {
    @SerializedName("weatherData")
    private WeatherData weatherData;
    @SerializedName("location")
    private Location location;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public WeatherData getWeatherData() {
        return weatherData;
    }

    public void setWeatherData(WeatherData weatherData) {
        this.weatherData = weatherData;
    }
}
