
import java.util.ArrayList;
import java.util.List;

public class StudentRegisterService {

    private final ArrayList<Student> students = new ArrayList<>();

    // Adds a student after validating the details
    public boolean addStudent(Student student)
            throws InvalidStudentDataException {

        if (student == null) {
            throw new InvalidStudentDataException(
                    "Student details cannot be null.");
        }

        if (student.getId() == null
                || student.getId().isBlank()) {
            throw new InvalidStudentDataException(
                    "Student ID cannot be empty.");
        }

        if (student.getName() == null
                || student.getName().isBlank()) {
            throw new InvalidStudentDataException(
                    "Student name cannot be empty.");
        }

        if (student.getCourse() == null
                || student.getCourse().isBlank()) {
            throw new InvalidStudentDataException(
                    "Course cannot be empty.");
        }

        if (findStudentIndex(student.getId()) != -1) {
            throw new InvalidStudentDataException(
                    "A student with this ID already exists.");
        }

        students.add(student);
        return true;
    }

    // Loads validated records into the service
    public void loadStudents(List<Student> loadedStudents) {

        if (loadedStudents == null) {
            throw new IllegalArgumentException(
                    "Student list cannot be null.");
        }

        ArrayList<Student> validatedStudents = new ArrayList<>();

        for (Student student : loadedStudents) {

            if (student == null
                    || student.getId() == null
                    || student.getId().isBlank()
                    || student.getName() == null
                    || student.getName().isBlank()
                    || student.getCourse() == null
                    || student.getCourse().isBlank()) {

                throw new IllegalArgumentException(
                        "Loaded student data contains invalid records.");
            }

            for (Student existing : validatedStudents) {
                if (existing.getId().equalsIgnoreCase(
                        student.getId().trim())) {

                    throw new IllegalArgumentException(
                            "Loaded student data contains duplicate IDs.");
                }
            }

            validatedStudents.add(student);
        }

        // Replace existing records only after validation succeeds
        students.clear();
        students.addAll(validatedStudents);
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

    // Displays total students and course-wise counts
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

        if (course == null || course.isBlank()) {
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

        if (id == null || id.isBlank()) {
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
