import dto.DayInfoDto;
import dto.LocationDto;
import dto.WeatherInfoDto;
import model.DayInfo;
import model.Location;
import model.WeatherInfo;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class WeatherAsyncService {
    private WeatherAsyncWebApi api;

    public WeatherAsyncService(WeatherAsyncWebApi api) {
        this.api = api;
    }


    public CompletableFuture<Stream<Location>>
    search(String query) {
        return api.search(query)
                .thenApply(stream -> stream.map(this::dtoToLocation));
    }


    public CompletableFuture<Stream<WeatherInfo>> pastWeather(
            Location loc, LocalDate from, LocalDate to, int period) {
       return api.pastWeather(loc.getLatitude(), loc.getLongitude(),
               from, to, period)
            .thenApply(stream ->
                    stream.map(this::dtoToWeatherInfo));
    }

    public CompletableFuture<Stream<DayInfo>> pastDays(Location loc, LocalDate from, LocalDate to) {
        return
            api.pastDays(loc.getLatitude(), loc.getLongitude(), from,to)
            .thenApply( stream ->
                    stream.map(dto -> dtoToDayInfo(dto, loc)));
    }


    public  Location dtoToLocation(LocationDto dto) {
        return new Location(dto.getName(),
                dto.getCountry(),
                dto.getLatitude(),
                dto.getLongitude(),
                this::pastDays
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
