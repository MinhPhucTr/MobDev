package vn.uit.project.Spinner;

import vn.uit.project.R;

public enum SpinnerItem {
    TempItem("Temperature", R.drawable.device_thermostat_outlined),
    HumidityItem("Humidity", R.drawable.water_drop_outlined),
    AirItem("Air Speed", R.drawable.air_outlined),
    ;
    private String name;
    private int img;

    SpinnerItem(String name, int img) {
        this.name = name;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
