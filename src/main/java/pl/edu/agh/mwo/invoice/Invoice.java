package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {


    private Map<Product, Integer> products = new HashMap<>();

    public void addProduct(Product product){


        this.addProduct(product, 1);
    }

    public void addProduct(Product product, Integer quantity) {
        if(quantity <= 0){
            throw new IllegalArgumentException("Quantity cannot be 0 or negative");
        }
        this.products.put(product, quantity);
    }

    public BigDecimal getNetTotal() {
        BigDecimal sum = BigDecimal.ZERO;
        for(Product product : this.products.keySet()){
            Integer quantity = this.products.get(product);
            BigDecimal quantityAsBigDecimal = BigDecimal.valueOf(quantity);
            sum = sum.add(product.getPrice().multiply(quantityAsBigDecimal));
        }
        return sum;

    }

    public BigDecimal getTax() {

        BigDecimal sum = BigDecimal.ZERO;
        for(Product product : this.products.keySet()) {
            Integer quantity = this.products.get(product);
            if (quantity > 0) {
                BigDecimal quantityAsBigDecimal = BigDecimal.valueOf(quantity);
                sum = sum.add(product.getPrice().multiply(quantityAsBigDecimal).multiply(product.getTaxPercent()));
            }
        }

        return sum;
    }

    public BigDecimal getTotal() {
        return getNetTotal().add(getTax());
    }
}
