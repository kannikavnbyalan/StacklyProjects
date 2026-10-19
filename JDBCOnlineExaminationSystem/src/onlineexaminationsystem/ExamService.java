
package onlineexaminationsystem;

import java.sql.*;
import java.util.Scanner;

public class ExamService {

    // ---------- STUDENT REGISTRATION ----------

    public void registerStudent(Scanner sc) throws SQLException {
        System.out.print("Enter name: ");
        String name = sc.nextLine();

        System.out.print("Enter email: ");
        String email = sc.nextLine();

        System.out.print("Enter password: ");
        String password = sc.nextLine();

        String sql = "INSERT INTO students(name,email,password) VALUES(?,?,?)";

        try (Connection con = Databaseconnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);

            ps.executeUpdate();
            System.out.println("Student registered successfully.");

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Email already exists.");
        }
    }

    // ---------- STUDENT LOGIN ----------

    public int studentLogin(Scanner sc) throws SQLException {
        System.out.print("Enter email: ");
        String email = sc.nextLine();

        System.out.print("Enter password: ");
        String password = sc.nextLine();

        String sql = "SELECT student_id FROM students WHERE email=? AND password=?";

        try (Connection con = Databaseconnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Student login successful.");
                    return rs.getInt("student_id");
                }
            }
        }

        System.out.println("Invalid email or password.");
        return -1;
    }

    // ---------- ADMIN LOGIN ----------

    public boolean adminLogin(Scanner sc) {
        System.out.print("Enter admin username: ");
        String username = sc.nextLine();

        System.out.print("Enter admin password: ");
        String password = sc.nextLine();

        if (username.equals("admin") && password.equals("admin123")) {
            System.out.println("Admin login successful.");
            return true;
        }

        System.out.println("Invalid admin credentials.");
        return false;
    }

    // ---------- VIEW STUDENT DETAILS ----------

    public void viewStudent(int studentId) throws SQLException {
        String sql = "SELECT student_id,name,email FROM students WHERE student_id=?";

        try (Connection con = Databaseconnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, studentId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Student ID: " + rs.getInt("student_id"));
                    System.out.println("Name: " + rs.getString("name"));
                    System.out.println("Email: " + rs.getString("email"));
                }
            }
        }
    }

    // ---------- DISPLAY EXAMS ----------

    public void displayExams() throws SQLException {
        String sql = "SELECT * FROM exams";

        try (Connection con = Databaseconnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\n--- Available Exams ---");

            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.println(
                    "ID: " + rs.getInt("exam_id")
                    + " | Name: " + rs.getString("exam_name")
                    + " | Total Marks: " + rs.getInt("total_marks")
                    + " | Duration: " + rs.getInt("duration_minutes") + " minutes"
                );
            }

            if (!found) {
                System.out.println("No exams available.");
            }
        }
    }

    // ---------- CREATE EXAM ----------

    public void addExam(Scanner sc) throws SQLException {
        System.out.print("Enter exam name: ");
        String name = sc.nextLine();

        int totalMarks = readPositiveInt(sc, "Enter total marks: ");
        int duration = readPositiveInt(sc, "Enter duration in minutes: ");

        String sql = "insert into exams(exam_name,total_marks,duration_minutes) values(?,?,?)";

        try (Connection con = Databaseconnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setInt(2, totalMarks);
            ps.setInt(3, duration);
            ps.executeUpdate();

            System.out.println("Exam created successfully.");
        }
    }

    // ---------- UPDATE EXAM ----------

    public void updateExam(Scanner sc) throws SQLException {
        System.out.print("Enter exam ID to update: ");
        int id = readPositiveInt(sc, "");

        System.out.print("Enter new exam name: ");
        String name = sc.nextLine();

        int marks = readPositiveInt(sc, "Enter new total marks: ");
        int duration = readPositiveInt(sc, "Enter new duration: ");

        String sql = "update exams set exam_name=?,total_marks=?,duration_minutes=? where exam_id=?";

        try (Connection con = Databaseconnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setInt(2, marks);
            ps.setInt(3, duration);
            ps.setInt(4, id);

            System.out.println(ps.executeUpdate() > 0
                    ? "Exam updated successfully."
                    : "Exam ID not found.");
        }
    }

    // ---------- DELETE EXAM ----------

    public void deleteExam(Scanner sc) throws SQLException {
        System.out.print("Enter exam ID to delete: ");
        int id = readPositiveInt(sc, "");

        String sql = "DELETE FROM exams WHERE exam_id=?";

        try (Connection con = Databaseconnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            System.out.println(ps.executeUpdate() > 0
                    ? "Exam deleted successfully."
                    : "Exam ID not found.");
        }
    }

    // ---------- ADD QUESTION ----------

    public void addQuestion(Scanner sc) throws SQLException {
        displayExams();

        int examId = readPositiveInt(sc, "Enter exam ID: ");

        System.out.print("Enter question: ");
        String question = sc.nextLine();

        System.out.print("Option A: ");
        String a = sc.nextLine();
        System.out.print("Option B: ");
        String b = sc.nextLine();
        System.out.print("Option C: ");
        String c = sc.nextLine();
        System.out.print("Option D: ");
        String d = sc.nextLine();

        String correct;
        while (true) {
            System.out.print("Correct option (A/B/C/D): ");
            correct = sc.nextLine().trim().toUpperCase();
            if (correct.matches("[ABCD]")) break;
            System.out.println("Enter A, B, C, or D.");
        }

        int marks = readPositiveInt(sc, "Enter question marks: ");

        String sql = "insert into questions(exam_id,question_text,option_a,option_b,option_c,option_d,correct_option,marks) values(?,?,?,?,?,?,?,?)";

        try (Connection con = Databaseconnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, examId);
            ps.setString(2, question);
            ps.setString(3, a);
            ps.setString(4, b);
            ps.setString(5, c);
            ps.setString(6, d);
            ps.setString(7, correct);
            ps.setInt(8, marks);

            ps.executeUpdate();
            System.out.println("Question added successfully.");
        }
    }

    // ---------- DISPLAY QUESTIONS ----------

    public void displayQuestions(Scanner sc) throws SQLException {
        System.out.print("Enter exam ID: ");
        int examId = readPositiveInt(sc, "");

        String sql = "SELECT * FROM questions WHERE exam_id=?";

        try (Connection con = Databaseconnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, examId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    System.out.println("\nQuestion ID: " + rs.getInt("question_id"));
                    System.out.println(rs.getString("question_text"));
                    System.out.println("A. " + rs.getString("option_a"));
                    System.out.println("B. " + rs.getString("option_b"));
                    System.out.println("C. " + rs.getString("option_c"));
                    System.out.println("D. " + rs.getString("option_d"));
                    System.out.println("Correct answer: " + rs.getString("correct_option"));
                    System.out.println("Marks: " + rs.getInt("marks"));
                }
            }
        }
    }

    // ---------- UPDATE QUESTION ----------

    public void updateQuestion(Scanner sc) throws SQLException {
        System.out.print("Enter question ID: ");
        int id = readPositiveInt(sc, "");

        System.out.print("Enter new question text: ");
        String question = sc.nextLine();

        System.out.print("Option A: ");
        String a = sc.nextLine();
        System.out.print("Option B: ");
        String b = sc.nextLine();
        System.out.print("Option C: ");
        String c = sc.nextLine();
        System.out.print("Option D: ");
        String d = sc.nextLine();

        String correct;
        while (true) {
            System.out.print("Correct option (A/B/C/D): ");
            correct = sc.nextLine().trim().toUpperCase();
            if (correct.matches("[ABCD]")) break;
            System.out.println("Enter A, B, C, or D.");
        }

        int marks = readPositiveInt(sc, "Enter marks: ");

        String sql = "update questions set question_text=?,option_a=?,option_b=?,option_c=?,option_d=?,correct_option=?,marks=? where question_id=?";

        try (Connection con = Databaseconnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, question);
            ps.setString(2, a);
            ps.setString(3, b);
            ps.setString(4, c);
            ps.setString(5, d);
            ps.setString(6, correct);
            ps.setInt(7, marks);
            ps.setInt(8, id);

            System.out.println(ps.executeUpdate() > 0
                    ? "Question updated successfully."
                    : "Question ID not found.");
        }
    }

    // ---------- DELETE QUESTION ----------

    public void deleteQuestion(Scanner sc) throws SQLException {
        System.out.print("Enter question ID: ");
        int id = readPositiveInt(sc, "");

        String sql = "DELETE FROM questions WHERE question_id=?";

        try (Connection con = Databaseconnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            System.out.println(ps.executeUpdate() > 0
                    ? "Question deleted successfully."
                    : "Question ID not found.");
        }
    }

    // ---------- TAKE EXAM ----------

    public void takeExam(Scanner sc, int studentId) throws SQLException {
        displayExams();

        int examId = readPositiveInt(sc, "Enter exam ID to take: ");

        String check = "SELECT exam_name,total_marks,duration_minutes FROM exams WHERE exam_id=?";

        try (Connection con = Databaseconnection.getConnection();
             PreparedStatement ps = con.prepareStatement(check)) {

            ps.setInt(1, examId);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    System.out.println("Exam not found.");
                    return;
                }

                System.out.println("\nExam: " + rs.getString("exam_name"));
                System.out.println("Duration: " + rs.getInt("duration_minutes") + " minutes");
            }
        }

        String existing = "select result_id FROM results WHERE student_id=? AND exam_id=?";

        try (Connection con = Databaseconnection.getConnection();
             PreparedStatement ps = con.prepareStatement(existing)) {

            ps.setInt(1, studentId);
            ps.setInt(2, examId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    System.out.println("You have already attempted this exam.");
                    return;
                }
            }
        }

        String sql = "select * FROM questions where exam_id=?";
        int score = 0;
        int total = 0;
        int count = 0;

        try (Connection con = Databaseconnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, examId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    count++;
                    System.out.println("\nQ" + count + ". " + rs.getString("question_text"));
                    System.out.println("A. " + rs.getString("option_a"));
                    System.out.println("B. " + rs.getString("option_b"));
                    System.out.println("C. " + rs.getString("option_c"));
                    System.out.println("D. " + rs.getString("option_d"));

                    String answer;
                    while (true) {
                        System.out.print("Your answer (A/B/C/D): ");
                        answer = sc.nextLine().trim().toUpperCase();
                        if (answer.matches("[ABCD]")) break;
                        System.out.println("Enter A, B, C, or D.");
                    }

                    int marks = rs.getInt("marks");
                    total += marks;

                    if (answer.equals(rs.getString("correct_option"))) {
                        score += marks;
                    }
                }
            }
        }

        if (count == 0) {
            System.out.println("This exam has no questions.");
            return;
        }

        double percentage = score * 100.0 / total;

        String insert = "insert into results(student_id,exam_id,marks_obtained,total_marks,percentage) values(?,?,?,?,?)";

        try (Connection con = Databaseconnection.getConnection();
             PreparedStatement ps = con.prepareStatement(insert)) {

            ps.setInt(1, studentId);
            ps.setInt(2, examId);
            ps.setInt(3, score);
            ps.setInt(4, total);
            ps.setDouble(5, percentage);
            ps.executeUpdate();
        }

        System.out.println("\nExam completed!");
        System.out.println("Marks obtained: " + score + "/" + total);
        System.out.printf("Percentage: %.2f%%%n", percentage);
    }

    // ---------- STUDENT RESULTS ----------

    public void viewMyResults(int studentId) throws SQLException {
        String sql = "select e.exam_name,r.marks_obtained,r.total_marks,r.percentage,r.attempted_at from results r join exams e on r.exam_id=e.exam_id where r.student_id=?";

        try (Connection con = Databaseconnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, studentId);

            try (ResultSet rs = ps.executeQuery()) {
                boolean found = false;

                while (rs.next()) {
                    found = true;
                    System.out.println("\nExam: " + rs.getString("exam_name"));
                    System.out.println("Marks: " + rs.getInt("marks_obtained")
                            + "/" + rs.getInt("total_marks"));
                    System.out.printf("Percentage: %.2f%%%n",
                            rs.getDouble("percentage"));
                    System.out.println("Attempted at: " + rs.getTimestamp("attempted_at"));
                }

                if (!found) System.out.println("No results found.");
            }
        }
    }

    // ---------- ALL RESULTS: INNER JOIN ----------

    public void allResults() throws SQLException {
        String sql = "SELECT s.name,e.exam_name,r.marks_obtained,r.total_marks,r.percentage FROM results r INNER JOIN students s ON r.student_id=s.student_id INNER JOIN exams e ON r.exam_id=e.exam_id";

        try (Connection con = Databaseconnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println(
                    "Student: " + rs.getString("name")
                    + " - Exam: " + rs.getString("exam_name")
                    + " - Marks: " + rs.getInt("marks_obtained")
                    + "/" + rs.getInt("total_marks")
                    + " - Percentage: " + rs.getDouble("percentage") + "%"
                );
            }
        }
    }

    // ---------- AGGREGATE FUNCTIONS ----------

    public void aggregateReport() throws SQLException {
        String sql = "select COUNT(*) AS attempts, AVG(percentage) AS average_percentage, MAX(percentage) AS highest_percentage, MIN(percentage) AS lowest_percentage FROM results";

        try (Connection con = Databaseconnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) {
                System.out.println("Total attempts: " + rs.getInt("attempts"));
                System.out.printf("Average percentage: %.2f%%%n",
                        rs.getDouble("average_percentage"));
                System.out.printf("Highest percentage: %.2f%%%n",
                        rs.getDouble("highest_percentage"));
                System.out.printf("Lowest percentage: %.2f%%%n",
                        rs.getDouble("lowest_percentage"));
            }
        }
    }

    // ---------- LEFT JOIN REPORT ----------

    public void studentResultReport() throws SQLException {
        String sql = "select s.student_id,s.name,e.exam_name,r.marks_obtained,r.total_marks,r.percentage from students s left join  results r on s.student_id=r.student_id left join exams e on r.exam_id=e.exam_id order by s.student_id";

        try (Connection con = Databaseconnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                System.out.print("Student ID: " + rs.getInt("student_id"));
                System.out.print(" | Name: " + rs.getString("name"));

                if (rs.getString("exam_name") == null) {
                    System.out.println(" | No exam attempted.");
                } else {
                    System.out.println(
                        " | Exam: " + rs.getString("exam_name")
                        + " | Marks: " + rs.getInt("marks_obtained")
                        + "/" + rs.getInt("total_marks")
                        + " | Percentage: " + rs.getDouble("percentage") + "%"
                    );
                }
            }
        }
    }

    // ---------- INPUT HELPER ----------

    private int readPositiveInt(Scanner sc, String prompt) {
        while (true) {
            if (!prompt.isEmpty()) System.out.print(prompt);

            try {
                int value = Integer.parseInt(sc.nextLine().trim());
                if (value > 0) return value;
            } catch (NumberFormatException e) {
    
            }

            System.out.println("Enter a valid positive number.");
        }
    }
}
