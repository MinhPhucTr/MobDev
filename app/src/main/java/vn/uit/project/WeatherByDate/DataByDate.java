package vn.uit.project.WeatherByDate;

import java.util.Date;

public class DataByDate {
    private Date updateTime;
    private double value;

    public DataByDate(double value, Date updateTime) {
        this.value = value;
        this.updateTime = updateTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
