package vn.uit.project.AssetComponent;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ValueLocation implements Serializable {
    @SerializedName("coordinates")
    private double[] coordinates;

    public double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }
}
