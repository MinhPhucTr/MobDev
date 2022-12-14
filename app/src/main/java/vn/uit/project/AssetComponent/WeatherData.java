package vn.uit.project.AssetComponent;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class WeatherData implements Serializable {
    @SerializedName("value")
    private Value value;
    @SerializedName("timestamp")
    long timestamp;
    @SerializedName("name")
    String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
