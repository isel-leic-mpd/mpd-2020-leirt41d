package travel.agencies.products;

import java.util.Iterator;

public class DiscountProduct extends Product {
    private Product promoProduct;
    int discount;

    private class PromoIterator implements Iterator<Product> {
        private boolean first = true;
        @Override
        public boolean hasNext() {
            return first;
        }

        @Override
        public Product next() {
           if (!hasNext())
               throw new IllegalStateException();
           first = false;
           return promoProduct;
        }
    }

    public DiscountProduct(Product promoProd, int discount) {
        super("Promo " + promoProd.getName(),
              promoProd.getStartDate(), promoProd.getEndDate());
        this.promoProduct = promoProd;
        this.discount = discount;
    }
    @Override
    public double getPrice() {
        return ((100 -discount)*promoProduct.getPrice())/100;
    }

    @Override
    public String getDescription( String prefix ){
        return String.format("%s(desconto de %d%%), %.0f€",
                super.getDescription(prefix), discount, getPrice());
    }


}
