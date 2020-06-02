import html.Body;
import html.Element;
import html.Html;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import mappers.DaysMappers;
import mappers.HtmlMapper;
import mappers.LocationMappers;
import weather.WeatherAsyncWebApi;
import weather.WeatherReactiveService;
import weather.model.DayInfo;
import weather.model.Location;
import weather.utils.HttpRequest;

import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Supplier;

import static html.Elements.h3;
import static html.Elements.table;

public class App {

    private static WeatherAsyncWebApi api = new WeatherAsyncWebApi(new HttpRequest());

    private static WeatherReactiveService service = new WeatherReactiveService(api);


    private static void sendErrorResponse(HttpServerResponse resp, int errorCode) {
        resp.putHeader("content-type", "text/html");
        String msg = "<h1> Error" + errorCode + "! </h1>";
        resp.putHeader("content-length", "" + msg.length());
        resp.write(msg);

        resp.setStatusCode(errorCode);
        resp.end();
        resp.close();

    }

    private static void sendOkResponse(HttpServerResponse resp) {
        resp.end();
        resp.close();
    }


    private static Html html = new Html();
    private static Body body = new Body();

    private static void startDoc(HttpServerResponse resp) {
        resp.write(html.startText(0));
        resp.write(body.startText(1));
    }

    private static void endDoc(HttpServerResponse resp) {
        resp.write(body.endText(1));
        resp.write(html.endText(0));

    }

    private static <T> void buildTableView(
            HttpServerResponse resp,
            Supplier<Element> prefix,
            Observable<T> rows,
            HtmlMapper<T> mapper,
            String... columns) {

        resp.putHeader("content-type", "text/html");
        resp.setStatusCode(200);
        resp.setChunked(true);

        Element table = table(columns);

        rows.subscribeWith(new Observer<>() {
            @Override
            public void onSubscribe(Disposable d) {
                startDoc(resp);
                resp.write(prefix.get().toString(2));
                resp.write(table.startText(2));
            }

            @Override
            public void onNext(T t) {
                resp.write(mapper.map(t).toString(3));
            }

            @Override
            public void onError(Throwable e) {
                sendErrorResponse(resp, 500);
            }

            @Override
            public void onComplete() {
                resp.write(table.endText(2));
                endDoc(resp);
                sendOkResponse(resp);
            }
        });
    }

    private static void getLocation(RoutingContext ctx) {

        HttpServerResponse resp = ctx.response();
        String locationName = ctx.request().getParam("name");
        Optional<String>
            country = Optional.ofNullable(ctx.request().getParam("country"));

        Observable<Location> locs =
                service.search(locationName)
                .filter( loc ->
                        loc.getCountry().equalsIgnoreCase(
                                country.orElse("Portugal")));

        buildTableView(resp,
                () -> h3(""),
                locs,
                LocationMappers::mapToTableRaw,
                "Name", "Country", "Latitude", "Longitude", "Today Info");
    }

    private static void getTodayInfo(RoutingContext ctx)  {
        HttpServerResponse resp = ctx.response();
        String[] latLong = ctx.request().getParam("coords").split(":");

        double latitude, longitude;

        latitude =  Double.valueOf(latLong[0]).doubleValue();
        longitude = Double.valueOf(latLong[1]).doubleValue();

        LocalDate today = LocalDate.now();

        Location loc = Location.of(latitude, longitude);
        Observable<DayInfo>  days =  service.pastDays(loc, today, today);

        buildTableView(resp,
                () -> h3( String.format("WeatherInfo for " + today)),
                days,
                DaysMappers::mapToTableRaw,
        "Date", "Min Temp", "Max Temp", "Moon Phase Description", "Sunrise", "Sunset");
    }

    public static void main(String[] args) throws Exception {

        Router router = Router.router(Vertx.vertx());

        router.route("/location").handler(App::getLocation);
        router.route("/weather/:coords/today").handler(App::getTodayInfo);

        Vertx.vertx()
                .createHttpServer()
                .requestHandler(router::accept)
                .listen(8000);
    }

}
