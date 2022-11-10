package vn.uit.project.MapComponent;

import com.google.gson.annotations.SerializedName;

public class Default {
    @SerializedName("center")
    private double[] center;
    @SerializedName("bounds")
    private double[] bounds;
    @SerializedName("zoom")
    private int zoom;
    @SerializedName("maxZoom")
    private int maxZoom;
    @SerializedName("boxZoom")
    private boolean boxZoom;
    @SerializedName("geocodeUrl")
    private String geocodeUrl;
    @SerializedName("minZoom")
    private int minZoom;

    public double[] getCenter() {
        return center;
    }

    public void setCenter(double[] center) {
        this.center = center;
    }

    public double[] getBounds() {
        return bounds;
    }

    public void setBounds(double[] bounds) {
        this.bounds = bounds;
    }

    public int getZoom() {
        return zoom;
    }

    public void setZoom(int zoom) {
        this.zoom = zoom;
    }

    public int getMaxZoom() {
        return maxZoom;
    }

    public void setMaxZoom(int maxZoom) {
        this.maxZoom = maxZoom;
    }

    public int getMinZoom() {
        return minZoom;
    }

    public void setMinZoom(int minZoom) {
        this.minZoom = minZoom;
    }
}
