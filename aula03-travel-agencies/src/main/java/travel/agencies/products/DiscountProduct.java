package travel.agencies.products;

import java.util.Iterator;

public class DiscountProduct extends Product {
    private Product promoProduct;
    int discount;

    private class PromoIterator implements Iterator<Product> {

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Product next() {
           return null;
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
