package travel.agencies.products;

import java.time.LocalDate;

public abstract class Product {
    private String name;
    protected LocalDate startDate, endDate;

    /*
    public static int totalSimpleProducts(Iterable<Product> products) {
        int count = 0;
        for(Product p : products) {
           if (p instanceof SimpleProduct) count++;
           else {
                if (p instanceof Program) {
                    Program prog = (Program) p;
                    count +=  totalSimpleProducts(prog);
                }
           }
        }
        return count;

    }
   */

    public LocalDate getStartDate() { return startDate; }

    public LocalDate getEndDate() { return endDate; }

    public String getName() { return name; }

    public String getDescription( String prefix ){
        return prefix + "de " + startDate + " a " + endDate + ", " + name;
    }

    @Override
    public final String toString() { return getDescription(""); }

    // A COMPLETAR!



}