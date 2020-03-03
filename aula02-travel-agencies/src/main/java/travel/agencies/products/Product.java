package travel.agencies.products;

import java.time.LocalDate;

public abstract class Product {
    private String name;
    protected LocalDate startDate, endDate;


    public LocalDate getStartDate() { return startDate; }

    public LocalDate getEndDate() { return endDate; }

    public String getDescription( String prefix ){
        return prefix + "de " + startDate + " a " + endDate + ", " + name;
    }

    @Override
    public final String toString() { return getDescription(""); }

    // A COMPLETAR!

}
