package dto;/*
   #The day information is available in following format:-
   #date,maxtempC,maxtempF,mintempC,mintempF,sunrise,sunset,moonrise,moonset,moon_phase,moon_illumination
   #2019-01-01,17,63,8,46,07:55 AM,05:26 PM,03:25 AM,02:25 PM,Waning Crescent,34
*/

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DayInfoDto {
    // indexes
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
    private int maxTempC;
    private int minTempC ;
    private LocalTime sunrise;
    private LocalTime sunset;
    private LocalTime moonrise;
    private LocalTime moonset;
    private String moon_phase;
    private int moon_illumination;

    public DayInfoDto(LocalDate date, int maxTempC, int minTempC,
                      LocalTime sunrise, LocalTime sunset,
                      LocalTime moonrise, LocalTime moonset,
                      String moon_phase, int moon_illumination) {
        this.date = date;
        this.maxTempC = maxTempC;
        this.minTempC = minTempC;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.moonrise = moonrise;
        this.moonset = moonset;
        this.moon_phase = moon_phase;
        this.moon_illumination = moon_illumination;
    }

    // accessors
    public LocalDate getDate()      { return date; }
    public int getMaxTemp()         { return maxTempC; }
    public int getMinTemp()         { return minTempC; }
    public LocalTime getSunrise()   { return sunrise; }
    public LocalTime getSunset()    { return sunset; }
    public LocalTime getMoonrise()  { return moonrise; }
    public LocalTime getMoonset()   { return moonset; }
    public String getMoonPhase()     { return moon_phase; }
    public int getMoonIllumination() { return moon_illumination; }


    @Override
    public String toString() {
        return "{"
                + date
                + ", max="      + maxTempC
                + ", min="      + minTempC
                + ", sunrise="  + sunrise
                + ", sunset="   + sunset
                + ", moonrise=" + moonrise
                + ", moonset="  + moonset
                + ", illum="    + moon_illumination
                + "}";
    }

    private static LocalTime parseTime(String time) {
        try {
            return LocalTime.parse(time, DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH));
        }
        catch(Exception e) {
            return LocalTime.of(0,0,0);
        }
    }

    public static DayInfoDto valueOf(String line) {
        String[] parts = line.split(",");
        return new DayInfoDto(
                LocalDate.parse(parts[DATE]),
                Integer.parseInt(parts[MAX_TEMPC]),
                Integer.parseInt(parts[MIN_TEMPC]),
                parseTime(parts[SUNRISE]),
                parseTime(parts[SUNSET]),
                parseTime(parts[MOONRISE]),
                parseTime(parts[MOONSET]),
                parts[MOON_PHASE],
                Integer.parseInt(parts[MOON_ILLUM])
        );

    }
}
