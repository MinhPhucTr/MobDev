package vn.uit.project.MapComponent;

import com.google.gson.annotations.SerializedName;

public class Option {
    @SerializedName("default")
    private Default myDefault;

    public Default getMyDefault() {
        return myDefault;
    }

    public void setMyDefault(Default myDefault) {
        this.myDefault = myDefault;
    }
}
