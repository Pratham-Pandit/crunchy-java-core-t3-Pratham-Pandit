
import java.util.ArrayList;

public class StudentRegisterService {

    private final ArrayList<Student> students = new ArrayList<>();

    // Adds a student if the ID is valid and not already registered
    public boolean addStudent(Student student) {
        if (student == null
                || student.getId() == null
                || student.getId().trim().isEmpty()
                || student.getName() == null
                || student.getName().trim().isEmpty()
                || student.getCourse() == null
                || student.getCourse().trim().isEmpty()) {
            return false;
        }

        if (findStudentIndex(student.getId()) != -1) {
            return false;
        }

        students.add(student);
        return true;
    }

    // Returns all students
    public ArrayList<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    // Searches for a student by ID
    public Student searchStudent(String id) {
        int index = findStudentIndex(id);

        if (index != -1) {
            return students.get(index);
        }

        return null;
    }

    // Deletes a student by ID
    public boolean deleteStudent(String id) {
        int index = findStudentIndex(id);

        if (index != -1) {
            students.remove(index);
            return true;
        }

        return false;
    }

    // Displays the total number of students and course-wise counts
    public int showSummary() {
        if (students.isEmpty()) {
            System.out.println("The register is empty.");
            return 0;
        }

        System.out.println("\n========== REGISTER SUMMARY ==========");
        System.out.println("Total records: " + students.size());
        System.out.println("\nRecords by course:");

        ArrayList<String> displayedCourses = new ArrayList<>();

        for (Student student : students) {
            String course = student.getCourse();
            boolean alreadyDisplayed = false;

            for (String displayed : displayedCourses) {
                if (displayed.equalsIgnoreCase(course)) {
                    alreadyDisplayed = true;
                    break;
                }
            }

            if (!alreadyDisplayed) {
                int count = countCourse(course);
                System.out.println(course + ": " + count);
                displayedCourses.add(course);
            }
        }

        System.out.println("=======================================");
        return students.size();
    }

    // Counts students registered in a particular course
    public int countCourse(String course) {
        if (course == null || course.trim().isEmpty()) {
            return 0;
        }

        int count = 0;

        for (Student student : students) {
            if (student.getCourse().equalsIgnoreCase(course.trim())) {
                count++;
            }
        }

        return count;
    }

    // Finds the index of a student using their ID
    private int findStudentIndex(String id) {
        if (id == null || id.trim().isEmpty()) {
            return -1;
        }

        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equalsIgnoreCase(id.trim())) {
                return i;
            }
        }

        return -1;
    }
}
