package vn.uit.project.AssetComponent;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Location implements Serializable {
    @SerializedName("value")
    private ValueLocation value;

    public ValueLocation getValue() {
        return value;
    }

    public void setValue(ValueLocation value) {
        this.value = value;
    }
}
