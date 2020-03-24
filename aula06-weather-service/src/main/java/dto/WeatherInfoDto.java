package dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class WeatherInfoDto {
    // indexes
    private final static int DATE = 0;
    private final static int TIME = 1;
    private final static int TEMPC = 2;
    private final static int DESCRIPTION = 10;
    private final static int PRECIP_MM = 11;
    private final static int FEELS_LIKE_C = 24;


    private final LocalDate date;     // index 0
    private final LocalTime time;     // index 0
    private final int tempC;          // index 2
    private final String description; // index 10
    private final double  precipMM;   // index 11
    private final int feelsLikeC;     // index 24

    public WeatherInfoDto(LocalDate date, LocalTime time, int tempC, String description, double precipMM, int feelsLikeC) {
        this.date = date;
        this.time = time;
        this.tempC = tempC;
        this.description = description;
        this.precipMM = precipMM;
        this.feelsLikeC = feelsLikeC;
    }

    public LocalDate getLocalDate() { return date; }
    public LocalTime getLocalTime() { return time; }
    public int getTempC() { return tempC; }
    public String getDescription() { return description; }
    public double getPrecipMM() { return precipMM; }
    public int getFeelsLikeC() { return feelsLikeC; }

    /**
     * Create a dto.WeatherInfo from a line with csv parts
     * @param line
     * @return A dto.WeatherInfo instance
     */
    public static WeatherInfoDto valueOf(String line) {
        String[] parts = line.split(",");

        int time = Integer.parseInt(parts[TIME]);
        return new WeatherInfoDto(
                LocalDate.parse(parts[DATE]),
                LocalTime.of(time / 100, time % 100),
                Integer.parseInt(parts[TEMPC]),
                parts[DESCRIPTION],
                Double.valueOf(parts[PRECIP_MM]),
                Integer.parseInt(parts[FEELS_LIKE_C]));
    }

    @Override
    public  String toString() {
        return "dto.WeatherInfo{" +
                date +
                ", time=" + time +
                ", tempC=" + tempC +
                ", '" + description + '\'' +
                ", precipMM=" + precipMM +
                ", feelsLikeC=" + feelsLikeC +
                '}';
    }
}
