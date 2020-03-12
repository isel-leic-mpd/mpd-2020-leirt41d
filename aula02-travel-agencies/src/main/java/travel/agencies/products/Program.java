package travel.agencies.products;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.function.ToIntBiFunction;
import java.util.function.ToIntFunction;

import static travel.agencies.utils.ListUtils.smaller;
import static travel.agencies.utils.ListUtils.larger;
import static travel.agencies.utils.ListUtils.sum;

public class Program extends Product {
    private List<Product> products;

    // Possible solution for class Program in Exercise 1
    private static LocalDate getFirstDate(Product[] products) {
        return smaller(Arrays.asList(products),
                (p1,p2) -> p1.getStartDate().compareTo(p2.getStartDate())).startDate;
    }

    private static LocalDate getLastDate(Product[] products) {
        return larger(Arrays.asList(products),
                (p1,p2) -> p1.getEndDate().compareTo(p2.getEndDate())).endDate;
    }

    public Program(String name, Product ... products) {
        super(name, getFirstDate(products), getLastDate(products));
        this.products  = Arrays.asList(products);
    }

    @Override
    public double getPrice() {
        return getPrice3();
    }


    /**
     * Example:
     * Product vAcores =
     *   new SimpleProduct("Açores",
     *     LocalDate.of(2012,3,1),  LocalDate.of(2012,3,14), 700);
     * Product vMadeira =
     *   new SimpleProduct("Madeira",
     *      LocalDate.of(2012, 3, 15), LocalDate.of(2012, 3, 22), 75), 525);
     *
     * Product packIslands =
     *      new Program("Descubra Açores e Madeira", vAcores, vMadeira);
     * Product vLisbon = new SimpleProduct( "Lisboa",
     *    LocalDate.of(2012,3,23), LocalDate.of(2012,3,30), 600);
     *
     * Product packPortugal =
     *    new Program("Portugal e ilhas",packIslands,vLisbon);
     *
     * System.out.println(packPortugal);
     *
     * Generates the following output:
     *  De 2012-03-01 a 2012-03-30, Portugal e ilhas
     *    De 2012-03-01 a 2012-03-22, Descubra os Açores e a Madeira
     *      De 2012-03-01 a 2012-03-14, Açores, 700€
     *      De 2012-03-15 a 2012-03-22, Madeira, 525€
     *    TOTAL: 1225€
     *    De 2012-03-23 a 2012-03-30, Lisboa, 600€
     *  TOTAL: 1825€
     *
     * @param prefix
     * @return
     */
    @Override
    public String getDescription( String prefix ) {
        StringBuilder sb =
                new StringBuilder(super.getDescription(prefix));
        //sb.append(System.lineSeparator());
        sb.append('\n');
        for(Product p : products) {
            sb.append(p.getDescription(prefix + "  "));
            //sb.append(System.lineSeparator());
            sb.append('\n');
        }
        sb.append(String.format("%sTOTAL: %.0f€", prefix, getPrice()));
        return sb.toString();
    }


    // private class implementing  ToIntFunction<Product>
    private class ProductToInt implements
            ToIntFunction<Product> {

        @Override
        public int applyAsInt(Product value) {
            return (int) value.getPrice();
        }
    }

    //@Override
    public double getPrice0() {
        ToIntFunction<Product> converter = new ProductToInt();
        return sum(products, converter);
    }

    // version with anonymous class
    public double getPrice1() {

        ToIntFunction<Product> converter =
                new ToIntFunction<Product>() {
                    @Override
                    public int applyAsInt(Product value) {
                        return (int) value.getPrice();
                    }
                };

        return sum(products, converter );
    }

    // version with lambdas
    public double getPrice2() {
        /*
        Verbose (with types) lambda
        ToIntFunction<Product> converter =
            (Product p) -> {
                return (int) p.getPrice();
            };

         */
        // lambda expression with types inference
        ToIntFunction<Product> converter =
                p -> (int) p.getPrice();
        return sum(products, converter);
    }

    // version with method reference
    public double getPrice3() {
        ToIntFunction<Product> converter =
                Product::getIntPrice;
        return sum(products, converter);
    }

}
