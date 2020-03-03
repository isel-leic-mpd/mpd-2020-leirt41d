
import java.time.LocalDate;

public class WeatherInfo {
    // indexes
    private final static int DATE = 0;
    private final static int TEMPC = 2;
    private final static int DESCRIPTION = 10;
    private final static int PRECIP_MM = 11;
    private final static int FEELS_LIKE_C = 24;


    private final LocalDate date;     // index 0
    private final int tempC;          // index 2
    private final String description; // index 10
    private final double  precipMM;   // index 11
    private final int feelsLikeC;     // index 24

    public WeatherInfo(LocalDate date, int tempC, String description, double precipMM, int feelsLikeC) {
        this.date = date;
        this.tempC = tempC;
        this.description = description;
        this.precipMM = precipMM;
        this.feelsLikeC = feelsLikeC;
    }

    public LocalDate getLocalDate() { return date; }
    public int getTempC() { return tempC; }
    public String getDescription() { return description; }
    public double getPrecipMM() { return precipMM; }
    public int getFeelsLikeC() { return feelsLikeC; }

    /**
     * Create a WeatherInfo from a line with csv parts
     * @param line
     * @return A WeatherInfo instance
     */
    public static WeatherInfo valueOf(String line) {
        String[] parts = line.split(",");

        return new WeatherInfo(
                LocalDate.parse(parts[DATE]),
                Integer.parseInt(parts[TEMPC]),
                parts[DESCRIPTION],
                Double.valueOf(parts[PRECIP_MM]),
                Integer.parseInt(parts[FEELS_LIKE_C]));
    }

    @Override
    public  String toString() {
        return "WeatherInfo{" +
                date +
                ", tempC=" + tempC +
                ", '" + description + '\'' +
                ", precipMM=" + precipMM +
                ", feelsLikeC=" + feelsLikeC +
                '}';
    }
}
