/*
   #The day information is available in following format:-
   #date,maxtempC,maxtempF,mintempC,mintempF,sunrise,sunset,moonrise,moonset,moon_phase,moon_illumination
   #2019-01-01,17,63,8,46,07:55 AM,05:26 PM,03:25 AM,02:25 PM,Waning Crescent,34
*/

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DayInfo {
    // indexes corresponding to fields to use
    private static final int DATE       =   0;
    private static final int MAX_TEMPC  =   1;
    private static final int MIN_TEMPC  =   3;
    private static final int SUNRISE    =   5;
    private static final int SUNSET     =   6;
    private static final int MOONRISE   =   7;
    private static final int MOONSET    =   8;
    private static final int MOON_PHASE =   9;
    private static final int MOON_ILLUM =   10;

    private LocalDate date;
    private int minTemp,maxTemp;
    private LocalTime sunRise, sunSet;
    private LocalTime moonRise, moonSet;
    private String moonPhase;
    private int moonIllum; // percentage

    public DayInfo(LocalDate date, int minTemp, int maxTemp,
                   LocalTime sunRise, LocalTime sunSet,
                   LocalTime moonRise, LocalTime moonSet,
                   String moonPhase, int moonIllum) {
        this.date = date;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.sunRise = sunRise;
        this.sunSet = sunSet;
        this.moonRise = moonRise;
        this.moonSet = moonSet;
        this.moonPhase = moonPhase;
        this.moonIllum = moonIllum;
    }

    // acessors

    public LocalDate getDate() { return date; }
    public int getMinTemp() { return minTemp; }
    public int getMaxTemp() { return maxTemp; }
    public LocalTime getSunRise() { return sunRise; }
    public LocalTime getSunSet() { return sunSet; }
    public LocalTime getMoonRise() { return moonRise; }
    public LocalTime getMoonSet() { return moonRise; }
    public String getMoonPhase() { return moonPhase; }
    public int getMoonIllum() { return moonIllum; }

    /**
     * Use this method the time information to a LocalTime
     * @param time
     * @return
     */
    private static LocalTime parseTime(String time) {
        try {
            return LocalTime.parse(time,
                    DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH));
        }
        catch(Exception e) {
            return LocalTime.of(0,0,0);
        }
    }
    /**
     * Create a DayInfo from a line with csv parts
     * @param line
     * @return A DayInfo instance
     */
    public static DayInfo valueOf(String line) {
        String[] parts = line.split(",");

        return new DayInfo(
            LocalDate.parse(parts[DATE]),
            Integer.parseInt(parts[MIN_TEMPC]),
            Integer.parseInt(parts[MAX_TEMPC]),
            parseTime(parts[SUNRISE]),
            parseTime(parts[SUNSET]),
            parseTime(parts[MOONRISE]),
            parseTime(parts[MOONSET]),
            parts[MOON_PHASE],
            Integer.parseInt(parts[MOON_ILLUM]));
    }

    @Override
    public String toString() {
        return "DayInfo { " +
                date + ',' +
                minTemp + ',' +
                maxTemp + ',' +
                sunRise + ',' +
                sunSet + ',' +
                moonRise + ',' +
                moonSet + ',' +
                moonPhase + ',' +
                moonIllum  +
                '}';
    }

}
