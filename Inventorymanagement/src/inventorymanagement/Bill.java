
package inventorymanagement;

import java.util.Map;

public class Bill {

    private double total;

    public Bill() {

        total = 0;
    }

    // Calculate bill total

    public void calculateBillTotal(
            Map<Integer, Product> products,
            int productId,
            int quantity) {

        Product product = products.get(productId);

        if (product == null) {

            throw new IllegalArgumentException(
                    "Product not found");
        }

        if (quantity <= 0) {

            throw new IllegalArgumentException(
                    "Quantity must be greater than 0");
        }

        total = product.getPrice() * quantity;
    }

    public double getTotal() {

        return total;
    }
}