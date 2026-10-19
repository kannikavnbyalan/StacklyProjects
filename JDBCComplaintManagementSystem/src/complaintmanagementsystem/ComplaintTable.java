
package complaintmanagementsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ComplaintTable {
    private static final String server_url =
            "jdbc:mysql://localhost:3306/";
    private static final String db_url =
            "jdbc:mysql://localhost:3306/complaint_management";
    private static final String user = "root";
    private static final String password = "kannikakannika@1234";

    // Get database connection
    public static Connection getConnection()
            throws SQLException {

        return DriverManager.getConnection(
                db_url, user, password);
    }

    // Create database and all tables
    public static void createTables()
            throws SQLException {

        // Create database
        try (Connection con =
                     DriverManager.getConnection(
                             server_url, user, password);
             Statement st = con.createStatement()) {

            st.executeUpdate(
                    "create database if not exists complaint_management");
        }

        // Create tables
        try (Connection con = getConnection();
             Statement st = con.createStatement()) {

            // 1. Users table
        	st.executeUpdate("""
        		    create table if not exists users (
        		        user_id int primary key auto_increment,
        		        name varchar(100) not null,
        		        email varchar(150) unique not null,
        		        phone varchar(15) not null
        		    )
        		    """);

        		 // 2. officers table
        		st.executeUpdate("""
        		    create table if not exists officers (
        		        officer_id int primary key auto_increment,
        		        officer_name varchar(100) not null,
        		        department varchar(100) not null,
        		        phone varchar(15) not null
        		    )
        		    """);

        		 // 3. complaints table
        		st.executeUpdate("""
        		    create table if not exists complaints (
        		        complaint_id int primary key auto_increment,
        		        user_id int not null,
        		        officer_id int null,
        		        complaint_type varchar(100) not null,
        		        description varchar(500) not null,
        		        location varchar(200) not null,
        		        status varchar(30) default 'Pending',
        		        resolution varchar(500),
        		        complaint_date timestamp default current_timestamp,

        		        foreign key (user_id)
        		            references users(user_id),

        		        foreign key (officer_id)
        		            references officers(officer_id),

        		        check (status in
        		            ('Pending', 'In Progress', 'Resolved'))
        		    )
                """);
        }

        System.out.println(
                "Database and all three tables created successfully.");
    }
}
