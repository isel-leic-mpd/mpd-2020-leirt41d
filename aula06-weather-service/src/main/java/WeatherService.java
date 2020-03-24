import dto.DayInfoDto;
import dto.LocationDto;
import dto.WeatherInfoDto;
import model.DayInfo;
import model.Location;
import model.WeatherInfo;

import java.time.LocalDate;

import static queries.naive.EagerQueries.map;

public class WeatherService {
    private WeatherWebApi api;

    public WeatherService(WeatherWebApi api) {
        this.api = api;
    }

    public Iterable<Location> search(String query) {
        return map (
                api.search(query),
                //this::dtoToLocation
                dto -> dtoToLocation(dto)
        );
    }

    public Iterable<WeatherInfo> pastWeather(Location loc, LocalDate from, LocalDate to, int period) {
        return map(
                api.pastWeather(loc.getLatitude(),loc.getLongitude(), from, to, period),
                //this::dtoToWeatherInfo
                dto -> dtoToWeatherInfo(dto)

        );
    }

    public Iterable<DayInfo> pastDays(Location loc, LocalDate from, LocalDate to) {
        return map (
                api.pastDays(loc.getLatitude(), loc.getLongitude(), from,to),
                di -> dtoToDayInfo(di,loc)
                );
    }

    public  Location dtoToLocation(LocationDto dto) {
        return new Location(dto.getName(),
                dto.getCountry(),
                dto.getLatitude(),
                dto.getLongitude(),
                //this::pastDays
                (loc, d1, d2) -> pastDays(loc, d1, d2)
                );
    }

    public  WeatherInfo dtoToWeatherInfo(WeatherInfoDto dto) {
        return new WeatherInfo(
                dto.getLocalTime(),
                dto.getTempC(),
                dto.getDescription(),
                dto.getPrecipMM(),
                dto.getFeelsLikeC());
    }

    public DayInfo dtoToDayInfo(DayInfoDto dto, Location loc) {
        return new DayInfo(
                dto.getDate(),
                dto.getMaxTemp(),
                dto.getMinTemp(),
                dto.getSunrise(),
                dto.getSunset(),
                dto.getMoonrise(),
                dto.getMoonset(),
                dto.getMoonPhase(),
                dto.getMoonIllumination(),
                () -> pastWeather(loc, dto.getDate(),
                        dto.getDate(), 1)
        );
    }

}
