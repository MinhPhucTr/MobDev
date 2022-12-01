package vn.uit.project.AssetComponent;

import com.google.gson.annotations.SerializedName;

public class Main {
    @SerializedName("temp")
    private double temp;
    @SerializedName("humidity")
    private double humidity;

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }
}
