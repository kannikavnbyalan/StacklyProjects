
package complaintmanagementsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ComplaintService {

    // 1. Register citizen
    public void registerCitizen(
            String name, String email, String phone)
            throws SQLException {

        if (name == null || name.trim().isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        if (email == null ||
                !email.matches("^[\\w.+-]+@[\\w.-]+\\.[A-Za-z]{2,}$")) {
            System.out.println("Enter a valid email.");
            return;
        }

        if (phone == null || !phone.matches("[0-9]{10}")) {
            System.out.println("Phone must contain 10 digits.");
            return;
        }

        String sql =
                "insert into users(name,email,phone) values(?,?,?)";

        try (Connection con = ComplaintTable.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name.trim());
            ps.setString(2, email.trim());
            ps.setString(3, phone);

            ps.executeUpdate();

            System.out.println("Citizen registered successfully.");
        }
    }

    // 2. Register officer
    public void registerOfficer(
            String name, String department, String phone)
            throws SQLException {

        if (name == null || name.trim().isEmpty()
                || department == null || department.trim().isEmpty()) {
            System.out.println("Name and department are required.");
            return;
        }

        if (phone == null || !phone.matches("[0-9]{10}")) {
            System.out.println("Phone must contain 10 digits.");
            return;
        }

        String sql =
                "insert into officers(officer_name,department,phone) " +
                "values(?,?,?)";

        try (Connection con = ComplaintTable.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name.trim());
            ps.setString(2, department.trim());
            ps.setString(3, phone);

            ps.executeUpdate();

            System.out.println("Officer registered successfully.");
        }
    }

    // 3. Register complaint
    public void registerComplaint(
            int userId, String type,
            String description, String location)
            throws SQLException {

        if (userId <= 0 || type == null || type.trim().isEmpty()
                || description == null || description.trim().isEmpty()
                || location == null || location.trim().isEmpty()) {
            System.out.println("Enter valid complaint details.");
            return;
        }

        String sql =
                "insert into complaints " +
                "(user_id,complaint_type,description,location) " +
                "values(?,?,?,?)";

        try (Connection con = ComplaintTable.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setString(2, type.trim());
            ps.setString(3, description.trim());
            ps.setString(4, location.trim());

            ps.executeUpdate();

            System.out.println("Complaint registered successfully.");
        }
    }

    // 4. Assign officer
    public void assignOfficer(
            int complaintId, int officerId)
            throws SQLException {

        if (complaintId <= 0 || officerId <= 0) {
            System.out.println("IDs must be positive numbers.");
            return;
        }

        String sql =
                "update complaints set officer_id=? " +
                "where complaint_id=?";

        try (Connection con = ComplaintTable.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, officerId);
            ps.setInt(2, complaintId);

            if (ps.executeUpdate() > 0) {
                System.out.println("Officer assigned successfully.");
            } else {
                System.out.println("Complaint ID not found.");
            }
        }
    }

    // 5. Update complaint status
    public void updateStatus(
            int id, String status, String resolution)
            throws SQLException {

        if (id <= 0) {
            System.out.println("Invalid complaint ID.");
            return;
        }

        if (status == null ||
                !(status.equals("Pending")
                || status.equals("In Progress")
                || status.equals("Resolved"))) {
            System.out.println("Invalid status.");
            return;
        }

        if (status.equals("Resolved")
                && (resolution == null || resolution.trim().isEmpty())) {
            System.out.println("Resolution is required.");
            return;
        }

        String sql =
                "update complaints set status=?, resolution=? " +
                "where complaint_id=?";

        try (Connection con = ComplaintTable.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setString(2, resolution);
            ps.setInt(3, id);

            if (ps.executeUpdate() > 0) {
                System.out.println("Complaint status updated successfully.");
            } else {
                System.out.println("Complaint ID not found.");
            }
        }
    }

    // 6. View complaints and resolutions
    public void viewComplaints() throws SQLException {

        String sql = """
            SELECT c.complaint_id, u.name AS citizen_name,
                   c.complaint_type, c.description, c.location,
                   o.officer_name, c.status, c.resolution,
                   c.complaint_date
            from complaints c
            join users u ON c.user_id = u.user_id
            left join officers o ON c.officer_id = o.officer_id
            order by c.complaint_id
            """;

        try (Connection con = ComplaintTable.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            boolean found = false;

            while (rs.next()) {
                found = true;

                System.out.println("\n---------------------------");
                System.out.println("Complaint ID: "
                        + rs.getInt("complaint_id"));
                System.out.println("Citizen: "
                        + rs.getString("citizen_name"));
                System.out.println("Type: "
                        + rs.getString("complaint_type"));
                System.out.println("Description: "
                        + rs.getString("description"));
                System.out.println("Location: "
                        + rs.getString("location"));
                System.out.println("Officer: "
                        + rs.getString("officer_name"));
                System.out.println("Status: "
                        + rs.getString("status"));
                System.out.println("Resolution: "
                        + rs.getString("resolution"));
                System.out.println("Date: "
                        + rs.getTimestamp("complaint_date"));
            }

            if (!found) {
                System.out.println("No complaints found.");
            }
        }
    }

    // 7. Delete complaint
    public void deleteComplaint(int id)
            throws SQLException {

        if (id <= 0) {
            System.out.println("Invalid complaint ID.");
            return;
        }

        String sql =
                "delete from complaints where complaint_id=?";

        try (Connection con = ComplaintTable.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            if (ps.executeUpdate() > 0) {
                System.out.println("Complaint deleted successfully.");
            } else {
                System.out.println("Complaint ID not found.");
            }
        }
    }
}
