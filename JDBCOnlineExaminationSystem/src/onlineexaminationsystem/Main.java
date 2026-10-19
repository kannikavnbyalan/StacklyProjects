package onlineexaminationsystem;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ExamService service = new ExamService();

        // Create database and tables automatically.
        try {
        	Databaseconnection.createTables();
        } catch (SQLException e) {
            System.out.println("Database connection failed: "
                    + e.getMessage());
            System.out.println(
                    "Check MySQL Server, credentials and JDBC driver.");
            sc.close();
            return;
        }

        boolean running = true;
        while (running) {
            System.out.println("\n ONLINE EXAMINATION SYSTEM");
            System.out.println("1. Student Registration");
            System.out.println("2. Student Login");
            System.out.println("3. Admin Login");
            System.out.println("0. Exit");

            int choice = readChoice(sc, 0, 3);

            try {
                switch (choice) {
                    case 1:
                        service.registerStudent(sc);
                        break;

                    case 2:
                        int studentId = service.studentLogin(sc);

                        if (studentId != -1) {
                            studentMenu(sc, service, studentId);
                        }
                        break;

                    case 3:
                        if (service.adminLogin(sc)) {
                            adminMenu(sc, service);
                        }
                        break;

                    case 0:
                        running = false;
                        System.out.println("Application closed.");
                        break;

                    default:
                        System.out.println("Invalid choice.");
                }

            } catch (SQLException e) {
                System.out.println("Database error: "
                        + e.getMessage());
            }
        }

        sc.close();
    }

    // ---------- STUDENT MENU ----------

    private static void studentMenu(
            Scanner sc, ExamService service, int studentId) {

        boolean loggedIn = true;

        while (loggedIn) {
            System.out.println("\n===== STUDENT MENU =====");
            System.out.println("1. View Student Details");
            System.out.println("2. Display Available Exams");
            System.out.println("3. Take Exam");
            System.out.println("4. View My Results");
            System.out.println("0. Logout");

            int choice = readChoice(sc, 0, 4);

            try {
                switch (choice) {
                    case 1:
                        service.viewStudent(studentId);
                        break;

                    case 2:
                        service.displayExams();
                        break;

                    case 3:
                        service.takeExam(sc, studentId);
                        break;

                    case 4:
                        service.viewMyResults(studentId);
                        break;

                    case 0:
                        loggedIn = false;
                        System.out.println("Student logged out.");
                        break;

                    default:
                        System.out.println("Invalid choice.");
                }

            } catch (SQLException e) {
                System.out.println("Database error: "
                        + e.getMessage());
            }
        }
    }

    // ---------- ADMIN MENU ----------

    private static void adminMenu(
            Scanner sc, ExamService service) {

        boolean loggedIn = true;

        while (loggedIn) {
            System.out.println("\nADMIN MENU");
            System.out.println("--- Exam Management ---");
            System.out.println("1. Create Exam");
            System.out.println("2. Display Exams");
            System.out.println("3. Update Exam");
            System.out.println("4. Delete Exam");

            System.out.println("--- Question Management ---");
            System.out.println("5. Add Question");
            System.out.println("6. Display Questions");
            System.out.println("7. Update Question");
            System.out.println("8. Delete Question");

            System.out.println("--- Result Reports ---");
            System.out.println("9. All Results (INNER JOIN)");
            System.out.println("10. Aggregate Functions");
            System.out.println("11. Students and Results (LEFT JOIN)");

            System.out.println("0. Logout");

            int choice = readChoice(sc, 0, 11);

            try {
                switch (choice) {
                    case 1:
                        service.addExam(sc);
                        break;

                    case 2:
                        service.displayExams();
                        break;

                    case 3:
                        service.updateExam(sc);
                        break;

                    case 4:
                        service.deleteExam(sc);
                        break;

                    case 5:
                        service.addQuestion(sc);
                        break;

                    case 6:
                        service.displayQuestions(sc);
                        break;

                    case 7:
                        service.updateQuestion(sc);
                        break;

                    case 8:
                        service.deleteQuestion(sc);
                        break;

                    case 9:
                        service.allResults();
                        break;

                    case 10:
                        service.aggregateReport();
                        break;

                    case 11:
                        service.studentResultReport();
                        break;

                    case 0:
                        loggedIn = false;
                        System.out.println("Admin logged out.");
                        break;

                    default:
                        System.out.println("Invalid choice.");
                }

            } catch (SQLException e) {
                System.out.println("Database error: "
                        + e.getMessage());
            }
        }
    }

    // ---------- MENU INPUT VALIDATION ----------

    private static int readChoice(
            Scanner sc, int min, int max) {

        while (true) {
            System.out.print("Enter choice: ");

            try {
                int choice = Integer.parseInt(
                        sc.nextLine().trim());

                if (choice >= min && choice <= max) {
                    return choice;
                }
            } catch (NumberFormatException e) {
            }

            System.out.println(
                    "Enter a valid choice from "
                    + min + " to " + max + ".");
        }
    }
}
