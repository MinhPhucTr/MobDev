package vn.uit.project.AssetComponent;

import com.google.gson.annotations.SerializedName;

public class WeatherData {
    @SerializedName("value")
    private Value value;

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }
}
