
package inventorymanagement;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        InventoryService service =
                new InventoryService();

        int choice;

        do {

            System.out.println("\n INVENTORY MANAGEMENT SYSTEM ");

            System.out.println("1. Add Product");
            System.out.println("2. Search Product by ID");
            System.out.println("3. Update Product Price");
            System.out.println("4. Add Stock");
            System.out.println("5. Sell Product");
            System.out.println("6. Show All Products");
            System.out.println("7. Show Low Stock Products");
            System.out.println("8. Calculate Bill");
            System.out.println("9. Delete Product");
            System.out.println("10. Exit");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            try {

                switch (choice) {

                    case 1:

                        System.out.print("Enter product ID: ");
                        int id = sc.nextInt();

                        sc.nextLine();

                        System.out.print("Enter product name: ");
                        String name = sc.nextLine();

                        System.out.println("Select Category:");
                        System.out.println("1. LAPTOP");
                        System.out.println("2. MOBILE");
                        System.out.println("3. ACCESSORY");
                        System.out.println("4. HOME_APPLIANCE");

                        System.out.print("Enter category: ");
                        int categoryChoice = sc.nextInt();

                        Category category;

                        if (categoryChoice == 1) {

                            category = Category.laptop;

                        } else if (categoryChoice == 2) {

                            category = Category.mobile;

                        } else if (categoryChoice == 3) {

                            category = Category.accessory;

                        } else if (categoryChoice == 4) {

                            category = Category.home_appliance;

                        } else {

                            throw new IllegalArgumentException(
                                    "Invalid category");
                        }

                        System.out.print("Enter price: ");
                        double price = sc.nextDouble();

                        System.out.print("Enter stock: ");
                        int stock = sc.nextInt();

                        if (price < 0 || stock < 0) {

                            throw new IllegalArgumentException(
                                    "Price and stock cannot be negative");
                        }

                        Product product =
                                new Product(
                                        id,
                                        name,
                                        category,
                                        price,
                                        stock
                                );

                        service.addProduct(product);

                        break;


                    case 2:

                        System.out.print(
                                "Enter product ID: ");
                        int searchId = sc.nextInt();
                        Product found =
                               service.searchProductById(searchId);
                        if (found != null) {
                            System.out.println(found);
                        } else {
                            System.out.println(
                                   "Product not found");
                        }
                        break;

                    case 3:

                        System.out.print(
                                "Enter product ID: ");
                        int priceId = sc.nextInt();
                        System.out.print(
                                "Enter new price: ");
                        double newPrice = sc.nextDouble();
                        service.updatePrice(
                                priceId,
                                newPrice
                        );
                        break;

                    case 4:

                        System.out.print(
                                "Enter product ID: ");

                        int stockId = sc.nextInt();

                        System.out.print(
                                "Enter quantity to add: ");

                        int quantity = sc.nextInt();

                        service.addStock(
                                stockId,
                                quantity
                        );
                        break;


                    case 5:

                        System.out.print(
                                "Enter product ID: ");

                        int sellId = sc.nextInt();

                        System.out.print(
                                "Enter quantity to sell: ");

                        int sellQuantity = sc.nextInt();

                        service.sellProduct(
                                sellId,
                                sellQuantity
                        );

                        break;


                    case 6:

                        service.showAllProducts();

                        break;


                    case 7:

                        service.showLowStockProducts();

                        break;


                    case 8:

                        System.out.print(
                                "Enter product ID: ");

                        int billId = sc.nextInt();

                        System.out.print(
                                "Enter quantity: ");

                        int billQuantity = sc.nextInt();

                        Bill bill = new Bill();

                        bill.calculateBillTotal(
                                service.getProducts(),
                                billId,
                                billQuantity
                        );

                        System.out.println(
                                "Bill Total: ₹" +
                                bill.getTotal()
                        );
                        break;

                    case 9:
                        System.out.print(
                                "Enter product ID: ");
                        int deleteId = sc.nextInt();
                        service.deleteProduct(deleteId);
                        break;

                    case 10:

                        System.out.println(
                                "Program ended");
                        break;

                    default:

                        System.out.println(
                                "Invalid choice");
                }

            } catch (IllegalArgumentException e) {

                System.out.println(
                        "Error: " + e.getMessage());
            }

        } while (choice != 10);

        sc.close();
    }
}