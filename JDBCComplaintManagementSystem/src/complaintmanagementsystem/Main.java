
package complaintmanagementsystem;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ComplaintService service = new ComplaintService();

        // Create database and tables automatically
        try {
            ComplaintTable.createTables();
        } catch (SQLException e) {
            System.out.println("Database connection failed: "
                    + e.getMessage());
            System.out.println(
                    "Check MySQL Server, password and JDBC driver.");
            sc.close();
            return;
        }
        boolean running = true;
        while (running) {
            System.out.println("\n COMPLAINT MANAGEMENT SYSTEM ");
            System.out.println("1. Register Citizen");
            System.out.println("2. Register Officer");
            System.out.println("3. Register Complaint");
            System.out.println("4. Assign Officer");
            System.out.println("5. Update Complaint Status");
            System.out.println("6. View Complaints and Resolutions");
            System.out.println("7. Delete Complaint");
            System.out.println("0. Exit");
            int choice = readChoice(sc, 0, 7);

            try {
                switch (choice) {

                    case 1:
                        System.out.print("Enter citizen name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter email: ");
                        String email = sc.nextLine();

                        System.out.print("Enter 10-digit phone: ");
                        String phone = sc.nextLine();

                        service.registerCitizen(name, email, phone);
                        break;

                    case 2:
                        System.out.print("Enter officer name: ");
                        String officerName = sc.nextLine();

                        System.out.print("Enter department: ");
                        String department = sc.nextLine();

                        System.out.print("Enter 10-digit phone: ");
                        String officerPhone = sc.nextLine();

                        service.registerOfficer(
                                officerName, department, officerPhone);
                        break;

                    case 3:
                        System.out.print("Enter citizen ID: ");
                        int userId = readPositiveInt(sc);

                        System.out.print("Enter complaint type: ");
                        String type = sc.nextLine();

                        System.out.print("Enter description: ");
                        String description = sc.nextLine();

                        System.out.print("Enter location: ");
                        String location = sc.nextLine();

                        service.registerComplaint(
                                userId, type, description, location);
                        break;

                    case 4:
                        System.out.print("Enter complaint ID: ");
                        int complaintId = readPositiveInt(sc);

                        System.out.print("Enter officer ID: ");
                        int officerId = readPositiveInt(sc);

                        service.assignOfficer(complaintId, officerId);
                        break;

                    case 5:
                        System.out.print("Enter complaint ID: ");
                        int id = readPositiveInt(sc);

                        System.out.println(
                                "1. Pending\n2. In Progress\n3. Resolved");
                        System.out.print("Choose status: ");
                        int statusChoice = readChoice(sc, 1, 3);

                        String status;

                        switch (statusChoice) {
                            case 1:
                                status = "Pending";
                                break;
                            case 2:
                                status = "In Progress";
                                break;
                            default:
                                status = "Resolved";
                        }

                        System.out.print(
                                "Enter resolution (required when resolved): ");
                        String resolution = sc.nextLine();

                        service.updateStatus(id, status, resolution);
                        break;

                    case 6:
                        service.viewComplaints();
                        break;

                    case 7:
                        System.out.print("Enter complaint ID to delete: ");
                        int deleteId = readPositiveInt(sc);

                        service.deleteComplaint(deleteId);
                        break;

                    case 0:
                        running = false;
                        System.out.println("Application closed.");
                        break;

                    default:
                        System.out.println("Invalid choice.");
                }

            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            }
        }

        sc.close();
    }

    // Validate menu choice
    private static int readChoice(
            Scanner sc, int min, int max) {

        while (true) {
            System.out.print("Enter choice: ");

            try {
                int choice = Integer.parseInt(sc.nextLine().trim());

                if (choice >= min && choice <= max) {
                    return choice;
                }
            } catch (NumberFormatException e) {
            }

            System.out.println(
                    "Enter a valid choice from " + min + " to " + max + ".");
        }
    }

    private static int readPositiveInt(Scanner sc) {

        while (true) {
            try {
                int value = Integer.parseInt(sc.nextLine().trim());

                if (value > 0) {
                    return value;
                }
            } catch (NumberFormatException e) {
            }

            System.out.print("Enter a positive number: ");
        }
    }
}
