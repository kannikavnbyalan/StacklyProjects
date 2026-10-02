
package studentmanagement;

import java.io.*;
import java.util.HashMap;

public class StudentFileService {

    private String fileName = "C:/Users/vnkin/Downloads/newstudents.txt";

    // Save students

    public void saveStudents(HashMap<Integer, Student> students)
            throws IOException {

        FileWriter fw = new FileWriter(fileName);

        for (Student student : students.values()) {

            fw.write(
                    student.getId() + "," +
                    student.getName() + "," +
                    student.getEmail() + "," +
                    student.getMarks() + "," +
                    student.getAttendance() + "," +
                    student.getStatus() + "," +
                    student.getCourse().getCourseId() + "," +
                    student.getCourse().getCourseName() +
                    "\n"
            );
        }

        fw.close();

        System.out.println("Student details saved to file");
    }

    // Load students

    public HashMap<Integer, Student> loadStudents()
            throws IOException {

        HashMap<Integer, Student> students = new HashMap<>();

        File file = new File(fileName);

        if (!file.exists()) {
            return students;
        }

        BufferedReader br = new BufferedReader(
                new FileReader(fileName));

        String line;

        while ((line = br.readLine()) != null) {

            String[] data = line.split(",");

            int id = Integer.parseInt(data[0]);
            String name = data[1];
            String email = data[2];
            double marks = Double.parseDouble(data[3]);
            int attendance = Integer.parseInt(data[4]);

            StudentStatus status =
                    StudentStatus.valueOf(data[5]);

            int courseId = Integer.parseInt(data[6]);
            String courseName = data[7];

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

            students.put(id, student);
        }

        br.close();

        return students;
    }
}