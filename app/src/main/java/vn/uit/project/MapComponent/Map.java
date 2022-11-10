package vn.uit.project.MapComponent;

import com.google.gson.annotations.SerializedName;

public class Map {
    @SerializedName("options")
    private Option options;
    @SerializedName("version")
    private int version;
    @SerializedName("sources")
    private Object sources;

    public Option getOptions() {
        return options;
    }

    public void setOptions(Option options) {
        this.options = options;
    }
}
