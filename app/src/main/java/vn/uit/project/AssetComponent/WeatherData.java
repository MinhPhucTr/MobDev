package vn.uit.project.AssetComponent;

import com.google.gson.annotations.SerializedName;

public class WeatherData {
    @SerializedName("value")
    private Value value;
    @SerializedName("timestamp")
    long timestamp;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }
}
