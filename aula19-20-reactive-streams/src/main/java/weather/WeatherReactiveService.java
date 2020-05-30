package weather;

import io.reactivex.Observable;
import weather.dto.*;
import weather.model.*;

import static io.reactivex.Observable.fromFuture;
import static weather.utils.ObservableUtils.*;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;


public class WeatherReactiveService {
    private WeatherAsyncWebApi api;

    public WeatherReactiveService(WeatherAsyncWebApi api) {
        this.api = api;
    }


    public Observable<Location>
    search(String query) {
        return fromCompletableFuture(api.search(query))
                .map(s -> s.toArray(sz-> new LocationDto[sz]))
                .flatMap(a -> Observable.fromArray(a))
                .map(this::dtoToLocation);
    }


    public Observable<WeatherInfo> pastWeather(
            Location loc, LocalDate from, LocalDate to, int period) {

        return fromCompletableFuture(
                api.pastWeather(loc.getLatitude(), loc.getLongitude(), from, to, period)
                )
                .map(s -> s.toArray(sz -> new WeatherInfoDto[sz]))
                .flatMap(a -> Observable.fromArray(a))
                .map(dto-> dtoToWeatherInfo(dto));
    }

    public Observable<DayInfo>
    pastDays(Location loc, LocalDate from, LocalDate to) {
        return fromCompletableFuture(
                api.pastDays(loc.getLatitude(), loc.getLongitude(), from,to))
                .map(s -> s.toArray(sz -> new DayInfoDto[sz]))
                .flatMap(a -> Observable.fromArray(a))
                .map(dto-> dtoToDayInfo(dto, loc));

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
