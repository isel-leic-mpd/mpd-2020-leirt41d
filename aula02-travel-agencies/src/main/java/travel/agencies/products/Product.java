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
    // completado na aula de 10/03

    // to make it compatible with ListUtils.sum method!
    public static int getIntPrice(Product p) {
        return (int) p.getPrice();
    }

    public Product(String name,
                         LocalDate startDate,
                         LocalDate endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public abstract  double getPrice();

}
