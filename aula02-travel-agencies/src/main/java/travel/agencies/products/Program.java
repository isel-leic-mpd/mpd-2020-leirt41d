package travel.agencies.products;

import java.util.Arrays;
import java.util.List;
import static travel.agencies.utils.ListUtils.smaller;
import static travel.agencies.utils.ListUtils.larger;
import static travel.agencies.utils.ListUtils.sum;

public class Program extends Product {
    private List<Product> products;

    public Program(String name, Product ... products) {
        // A COMPLETAR!
    }

    /**
     * Example:
     * Product vAcores = new SimpleProduct("Açores", new Date(2012,3,1), new Date(2012,3,14), 700);
     * Product vMadeira = new SimpleProduct("Madeira", new Date(2012, 3, 15), 7, 75), 525);
     *
     * Product packIslands =
     *      new Program("Descubra Açores e Madeira", vAcores, vMadeira);
     * Product vLisbon = new SimpleProduct( "Lisboa", new Date(2012,3,23), new Date(2012,3,30), 600);
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
    public String getDescription( String prefix ){
        // A COMPLETAR!
        return null;
    }
    // A COMPLETAR!
}
