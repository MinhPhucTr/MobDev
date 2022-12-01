package vn.uit.project.AssetComponent;

import com.google.gson.annotations.SerializedName;

public class Value {
    @SerializedName("main")
    private Main main;
    @SerializedName("wind")
    private Wind wind;
    @SerializedName("coord")
    private Coord coord;

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }
}
