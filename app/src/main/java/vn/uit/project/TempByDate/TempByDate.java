package vn.uit.project.TempByDate;

import java.time.LocalDate;

public class TempByDate {
    private String localDate;
    private double temp;

    public TempByDate(double temp) {
        this.localDate = LocalDate.now() + "";
        this.temp = temp;
    }

    public String getLocalDate() {
        return localDate;
    }

    public void setLocalDate(String localDate) {
        this.localDate = localDate;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }
}
