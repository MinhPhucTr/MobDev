package vn.uit.project.AssetComponent;

import com.google.gson.annotations.SerializedName;

public class Wind {
    @SerializedName("speed")
    private double speed;

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
