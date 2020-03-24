package model;/*
   #The day information is available in following format:-
   #date,maxtempC,maxtempF,mintempC,mintempF,sunrise,sunset,moonrise,moonset,moon_phase,moon_illumination
   #2019-01-01,17,63,8,46,07:55 AM,05:26 PM,03:25 AM,02:25 PM,Waning Crescent,34
*/

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.function.Function;
import java.util.function.Supplier;

public class DayInfo {
    private LocalDate date;
    private int maxTempC;
    private int minTempC ;
    private LocalTime sunrise;
    private LocalTime sunset;
    private LocalTime moonrise;
    private LocalTime moonset;
    private String moon_phase;
    private int moon_illumination;

    private Supplier<Iterable<WeatherInfo>> temperatures;

    public DayInfo(LocalDate date, int maxTempC, int minTempC,
                   LocalTime sunrise, LocalTime sunset,
                   LocalTime moonrise, LocalTime moonset,
                   String moon_phase, int moon_illumination,
                   Supplier<Iterable<WeatherInfo>> temperatures) {
        this.date = date;
        this.maxTempC = maxTempC;
        this.minTempC = minTempC;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.moonrise = moonrise;
        this.moonset = moonset;
        this.moon_phase = moon_phase;
        this.moon_illumination = moon_illumination;
        this.temperatures = temperatures;
    }

    // accessors
    public LocalDate getDate()      { return date; }
    public int getMaxTemp()         { return maxTempC; }
    public int getMinTemp()         { return minTempC; }
    public LocalTime getSunrise()   { return sunrise; }
    public LocalTime getSunset()    { return sunset; }
    public LocalTime getMoonrise()  { return moonrise; }
    public LocalTime getMoonset()   { return moonset; }
    public String getMoonPhase()    { return moon_phase; }
    public int getMoon_illumination() { return moon_illumination; }

    public Iterable<WeatherInfo> getTemperatures() {

        return temperatures.get();
    }

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


}
