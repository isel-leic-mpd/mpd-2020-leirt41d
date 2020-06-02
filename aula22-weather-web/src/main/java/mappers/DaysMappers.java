package mappers;

import html.Element;
import weather.model.DayInfo;


import static html.Elements.*;

public class DaysMappers {

    public static Element mapToTableRaw(DayInfo d) {
        return
            tr (
                td(d.getDate().toString()),
                td(toStr(d.getMinTemp())).align("right"),
                td(toStr(d.getMaxTemp())).align("right"),
                td(d.getMoonPhase()).align("left"),
                td(d.getSunrise().toString()).align("right"),
                td(d.getSunset().toString()).align("right")
            );
    }
}
