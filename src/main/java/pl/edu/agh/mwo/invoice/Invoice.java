package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {

    private Map<Product, Integer> products = new HashMap<Product, Integer>();
    private int invoiceNumber = 0;
    private static int nextNumber = 1;

    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public Invoice(){
        this.invoiceNumber = nextNumber++;
    }

    public void addProduct(Product product) {
        addProduct(product, 1);
    }

    public void addProduct(Product product, Integer quantity) {
        if (product == null || quantity <= 0) {
            throw new IllegalArgumentException();
        }
        products.put(product, quantity);
    }

    public BigDecimal getNetTotal() {
        BigDecimal totalNet = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalNet = totalNet.add(product.getPrice().multiply(quantity));
        }
        return totalNet;
    }

    public BigDecimal getTaxTotal() {
        return getGrossTotal().subtract(getNetTotal());
    }

    public BigDecimal getGrossTotal() {
        BigDecimal totalGross = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalGross = totalGross.add(product.getPriceWithTax().multiply(quantity));
        }
        return totalGross;
    }

    public String getAsText(Invoice invoice) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("nr " + this.invoiceNumber);
        for (Product product : invoice.products.keySet()){
            stringBuilder.append("\n");
            stringBuilder.append(product.getName());
            stringBuilder.append(" szt. ");
            stringBuilder.append(products.get(product).toString());
            stringBuilder.append(", cena: ");
            stringBuilder.append(product.getPrice());
            stringBuilder.append(" PLN/szt.");

        }
        stringBuilder.append(("\nLiczba pozycji: "));
        stringBuilder.append(this.products.size());

        return stringBuilder.toString();
    }
}
