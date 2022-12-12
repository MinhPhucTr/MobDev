package vn.uit.project.AssetComponent;

import com.google.gson.annotations.SerializedName;

public class ValueLocation {
    @SerializedName("coordinates")
    private double[] coordinates;

    public double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }
}
