
package studentmanagement;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        StudentService service = new StudentService();
        StudentFileService fileService =
                new StudentFileService();

        // Load existing students

        try {

            service.setStudents(
                    fileService.loadStudents()
            );

            System.out.println("Student details loaded");

        } catch (IOException e) {

            System.out.println("Error loading file");
        }

        int choice;

        do {

            System.out.println("\n STUDENT MANAGEMENT SYSTEM");

            System.out.println("1. Add Student");
            System.out.println("2. Display All Students");
            System.out.println("3. Search Student by ID");
            System.out.println("4. Search Student by Name");
            System.out.println("5. Update Marks");
            System.out.println("6. Update Attendance");
            System.out.println("7. Remove Student");
            System.out.println("8. Display Topper");
            System.out.println("9. Save Students");
            System.out.println("10. Exit");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            try {

                switch (choice) {

                    case 1:

                        System.out.print("Enter student ID: ");
                        int id = sc.nextInt();

                        sc.nextLine();

                        System.out.print("Enter name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter email: ");
                        String email = sc.nextLine();

                        System.out.print("Enter marks: ");
                        double marks = sc.nextDouble();

                        System.out.print("Enter attendance: ");
                        int attendance = sc.nextInt();

                        System.out.println("Select Status:");
                        System.out.println("1. ACTIVE");
                        System.out.println("2. INACTIVE");
                        System.out.println("3. COMPLETED");

                        int statusChoice = sc.nextInt();

                        StudentStatus status;

                        if (statusChoice == 1) {
                            status = StudentStatus.active;
                        } else if (statusChoice == 2) {
                            status = StudentStatus.inactive;
                        } else {
                            status = StudentStatus.completed;
                        }

                        sc.nextLine();

                        System.out.print("Enter course ID: ");
                        int courseId = sc.nextInt();

                        sc.nextLine();

                        System.out.print("Enter course name: ");
                        String courseName = sc.nextLine();

                        Course course =
                                new Course(courseId, courseName);

                        Student student =
                                new Student(
                                        id,
                                        name,
                                        email,
                                        marks,
                                        attendance,
                                        status,
                                        course
                                );

                        service.addStudent(student);

                        break;


                    case 2:

                        service.displayAllStudents();

                        break;


                    case 3:

                        System.out.print("Enter student ID: ");
                        int searchId = sc.nextInt();

                        Student found =
                                service.searchById(searchId);

                        if (found != null) {
                            System.out.println(found);
                        } else {
                            System.out.println("Student not found");
                        }

                        break;


                    case 4:

                        sc.nextLine();

                        System.out.print("Enter student name: ");
                        String searchName = sc.nextLine();

                        service.searchByName(searchName);

                        break;


                    case 5:

                        System.out.print("Enter student ID: ");
                        int markId = sc.nextInt();

                        System.out.print("Enter new marks: ");
                        double newMarks = sc.nextDouble();

                        service.updateMarks(markId, newMarks);

                        break;


                    case 6:

                        System.out.print("Enter student ID: ");
                        int attendanceId = sc.nextInt();

                        System.out.print("Enter new attendance: ");
                        int newAttendance = sc.nextInt();

                        service.updateAttendance(
                                attendanceId,
                                newAttendance
                        );

                        break;


                    case 7:

                        System.out.print("Enter student ID: ");
                        int removeId = sc.nextInt();

                        service.removeStudent(removeId);

                        break;


                    case 8:

                        service.displayTopper();

                        break;


                    case 9:

                        fileService.saveStudents(
                                service.getStudents()
                        );

                        break;


                    case 10:

                        fileService.saveStudents(
                                service.getStudents()
                        );

                        System.out.println(
                                "Data saved. Program ended."
                        );

                        break;


                    default:

                        System.out.println("Invalid choice");
                }

            } catch (IllegalArgumentException e) {

                System.out.println("Error: " + e.getMessage());

            } catch (IOException e) {

                System.out.println(
                        "File error: " + e.getMessage()
                );
            }

        } while (choice != 10);

        sc.close();
    }
}