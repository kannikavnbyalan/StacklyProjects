
package inventorymanagement;

import java.util.HashMap;
import java.util.Map;

public class InventoryService {

    private Map<Integer, Product> products;

    public InventoryService() {

        products = new HashMap<>();
    }

    // Add product

    public void addProduct(Product product) {

        if (products.containsKey(product.getProductId())) {

            throw new IllegalArgumentException(
                    "Product ID already exists");
        }

        products.put(product.getProductId(), product);

        System.out.println("Product added successfully");
    }

    // Search product by ID

    public Product searchProductById(int id) {

        return products.get(id);
    }

    // Update price

    public void updatePrice(int id, double price) {

        Product product = products.get(id);

        if (product == null) {

            System.out.println("Product not found");
            return;
        }

        product.setPrice(price);

        System.out.println("Price updated successfully");
    }

    // Add stock

    public void addStock(int id, int quantity) {

        Product product = products.get(id);

        if (product == null) {

            System.out.println("Product not found");
            return;
        }

        product.addStock(quantity);

        System.out.println("Stock added successfully");
    }

    // Sell product

    public void sellProduct(int id, int quantity) {

        Product product = products.get(id);

        if (product == null) {

            System.out.println("Product not found");
            return;
        }

        product.sellProduct(quantity);

        System.out.println("Product sold successfully");
    }

    // Show all products

    public void showAllProducts() {

        if (products.isEmpty()) {

            System.out.println("No products available");
            return;
        }

        for (Product product : products.values()) {

            System.out.println(product);
        }
    }

    // Products with stock below 5

    public void showLowStockProducts() {

        boolean found = false;

        for (Product product : products.values()) {

            if (product.getStock() < 5) {

                System.out.println(product);

                found = true;
            }
        }

        if (!found) {

            System.out.println(
                    "No products have stock below 5");
        }
    }

    // Delete product

    public void deleteProduct(int id) {

        Product product = products.remove(id);

        if (product == null) {

            System.out.println("Product not found");

        } else {

            System.out.println(
                    "Product deleted successfully");
        }
    }

    // Get products

    public Map<Integer, Product> getProducts() {

        return products;
    }
}