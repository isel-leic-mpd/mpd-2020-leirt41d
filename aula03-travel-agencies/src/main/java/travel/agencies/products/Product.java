package travel.agencies.products;

import java.time.LocalDate;
import java.util.Iterator;


public abstract class Product implements
        Comparable<Product>,
        Iterable<Product>{
    private String name;
    protected LocalDate startDate, endDate;

    private static class EmptyIterator implements Iterator<Product> {
        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Product next() {
            throw new IllegalStateException();
        }
    }

    protected static EmptyIterator emptyIterator =
            new EmptyIterator();

    public static int totalSimpleProducts(Iterable<Product> products) {
        int count = 0;
        for(Product p : products) {
           if (p instanceof SimpleProduct) count++;
           else {
               /*
                if (p instanceof Program) {
                    Program prog = (Program) p;
                    count +=  totalSimpleProducts(prog);
                }
                */
               count +=  totalSimpleProducts(p);
           }
        }
        return count;
    }


    public LocalDate getStartDate() { return startDate; }

    public LocalDate getEndDate() { return endDate; }

    public String getName() { return name; }

    public String getDescription( String prefix ){
        return prefix + "de " + startDate + " a " + endDate + ", " + name;
    }

    @Override
    public final String toString() { return getDescription(""); }

    public Product(String name,
                   LocalDate startDate,
                   LocalDate endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public abstract  double getPrice();


    @Override
    public int compareTo(Product p) {
        return name.compareTo(p.getName());
    }

    @Override
    public Iterator<Product> iterator() {
        return  emptyIterator;
    }

}