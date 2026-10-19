package onlineexaminationsystem;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Databaseconnection {

    private static final String server_url =
            "jdbc:mysql://localhost:3306/";
    private static final String  db_url =
            "jdbc:mysql://localhost:3306/online_exams";
    private static final String user = "root";

    // Replace with your MySQL password.
    private static final String password = "kannikakannika@1234";

    public static Connection getConnection()
            throws SQLException {
        return DriverManager.getConnection(
               db_url,user, password);
    }

    public static void createTables() throws SQLException {

        // Create database automatically.
        try (Connection con = DriverManager.getConnection(
                    server_url, user,password);
             Statement st = con.createStatement()) {

            st.executeUpdate(
                    "create database if not exists online_exams");
        }

        try (Connection con = getConnection();
             Statement st = con.createStatement()) {

        	st.executeUpdate("""
        		    create table if not exists students (
        		        student_id int primary key auto_increment,
        		        name varchar(100) not null,
        		        email varchar(150) unique not null,
        		        password varchar(255) not null
        		    )
        		    """);

        		st.executeUpdate("""
        		    create table if not exists exams (
        		        exam_id int primary key auto_increment,
        		        exam_name varchar(100) not null,
        		        total_marks int not null,
        		        duration_minutes int not null,
        		        check (total_marks > 0),
        		        check (duration_minutes > 0)
        		    )
        		    """);

        		st.executeUpdate("""
        		    create table if not exists questions (
        		        question_id int primary key auto_increment,
        		        exam_id int not null,
        		        question_text varchar(500) not null,
        		        option_a varchar(255) not null,
        		        option_b varchar(255) not null,
        		        option_c varchar(255) not null,
        		        option_d varchar(255) not null,
        		        correct_option char(1) not null,
        		        marks int not null,
        		        foreign key (exam_id)
        		            references exams(exam_id)
        		            on delete cascade,
        		        check (correct_option in ('A', 'B', 'C', 'D')),
        		        check (marks > 0)
        		    )
        		    """);

        		st.executeUpdate("""
        		    create table if not exists results (
        		        result_id int primary key auto_increment,
        		        student_id int not null,
        		        exam_id int not null,
        		        marks_obtained int not null,
        		        total_marks int not null,
        		        percentage decimal(5,2) not null,
        		        attempted_at timestamp default current_timestamp,
        		        unique (student_id, exam_id),
        		        foreign key (student_id)
        		            references students(student_id),
        		        foreign key (exam_id)
        		            references exams(exam_id),
        		        check (marks_obtained >= 0),
        		        check (total_marks > 0)
        		    )
                """);

            st.executeUpdate("""
                CREATE TABLE IF NOT EXISTS results (
                    result_id INT PRIMARY KEY AUTO_INCREMENT,
                    student_id INT NOT NULL,
                    exam_id INT NOT NULL,
                    marks_obtained INT NOT NULL,
                    total_marks INT NOT NULL,
                    percentage DECIMAL(5,2) NOT NULL,
                    attempted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    UNIQUE (student_id, exam_id),
                    FOREIGN KEY (student_id)
                        REFERENCES students(student_id),
                    FOREIGN KEY (exam_id)
                        REFERENCES exams(exam_id),
                    CHECK (marks_obtained >= 0),
                    CHECK (total_marks > 0)
                )
                """);
        }

        System.out.println(
                "Database and all four tables created successfully.");
    }
}
