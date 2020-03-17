package travel.agencies.products;

import java.time.LocalDate;
import java.util.Iterator;

public class SimpleProduct extends Product  {



    private double price;
    public SimpleProduct(String name,
                         LocalDate startDate,
                         LocalDate endDate,
                         double price) {
        super(name, startDate, endDate);
        this.price = price;
    }

    @Override
    public double getPrice() {
        return price;
    }

    /**
     * Example:
     * Product vAcores =
     *  new SimpleProduct("Açores", new LocalDate(2012,3,1), new LocalDate(2012,3,14), 700);
     * System.out.println(vAcores)
     *
     * Generates the following output:
     * De 2012-03-01 a 2012-03-14, Açores, 700€
     *
     * @param prefix
     * @return
     */
    @Override
    public String getDescription( String prefix ){
        return String.format("%s, %.0f€",
                super.getDescription(prefix),getPrice());
    }


}
