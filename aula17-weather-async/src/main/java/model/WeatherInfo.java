package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class WeatherInfo {

    private final LocalTime time;
    private final int tempC;
    private final String description;
    private final double  precipMM;
    private final int feelsLikeC;

    public WeatherInfo(LocalTime time, int tempC, String description, double precipMM, int feelsLikeC) {
        this.time = time;
        this.tempC = tempC;
        this.description = description;
        this.precipMM = precipMM;
        this.feelsLikeC = feelsLikeC;
    }

    public LocalTime getLocalTime() { return time; }
    public int getTempC() { return tempC; }
    public String getDescription() { return description; }
    public double getPrecipMM() { return precipMM; }
    public int getFeelsLikeC() { return feelsLikeC; }


    @Override
    public  String toString() {
        return "dto.WeatherInfo{" +
                ", time=" + time +
                ", tempC=" + tempC +
                ", '" + description + '\'' +
                ", precipMM=" + precipMM +
                ", feelsLikeC=" + feelsLikeC +
                '}';
    }
}
