package utils;

import dto.WeatherInfo;

public interface WeatherInfoFilter {
    boolean test(WeatherInfo wi);
}
