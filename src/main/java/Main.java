
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentRegisterService service
            = new StudentRegisterService();

    private static final StudentFileHandler fileHandler
            = new StudentFileHandler("students.csv");

    public static void main(String[] args) {

        // Load previously saved records
        try {
            service.loadStudents(fileHandler.loadStudents());
            System.out.println("Student records loaded successfully!");
            System.out.println(
                    "Total loaded records: "
                    + service.getAllStudents().size());

        } catch (IOException e) {
            System.out.println(
                    "Error loading student records: " + e.getMessage());
            System.out.println(
                    "Application cannot continue to avoid accidental data loss.");
            scanner.close();
            return;
        }

        int choice;

        do {
            displayMenu();
            choice = readChoice();

            switch (choice) {
                case 1:
                    handleAddStudent();
                    break;

                case 2:
                    listStudents();
                    break;

                case 3:
                    handleSearchStudent();
                    break;

                case 4:
                    handleDeleteStudent();
                    break;

                case 5:
                    service.showSummary();
                    break;

                case 0:
                    System.out.println(
                            "Exiting Student Register. Goodbye!");
                    break;

                default:
                    System.out.println(
                            "Invalid choice. Enter a number from 0 to 5.");
            }

        } while (choice != 0);

        scanner.close();
    }

    // Displays the main menu
    private static void displayMenu() {
        System.out.println("\n===== STUDENT REGISTER CLI =====");
        System.out.println("1. Add record");
        System.out.println("2. List records");
        System.out.println("3. Search record");
        System.out.println("4. Delete record");
        System.out.println("5. Show summary");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    // Reads the user's menu choice
    // Reads a valid menu choice
    private static int readChoice() {

        while (true) {

            String input = scanner.nextLine().trim();

            try {
                int choice = Integer.parseInt(input);

                if (choice >= 0 && choice <= 5) {
                    return choice;
                }

            } catch (NumberFormatException e) {
                // Display validation message below
            }

            System.out.print(
                    "Invalid choice. Enter a number from 0 to 5: ");
        }
    }

    // Saves the current student list to CSV
    private static boolean saveRecords() {
        try {
            fileHandler.saveStudents(service.getAllStudents());
            return true;

        } catch (IOException e) {
            System.out.println(
                    "Error saving records: " + e.getMessage());
            return false;
        }
    }

    // Reads input and repeatedly asks until a non-empty value is entered
    private static String readRequiredInput(String message) {

        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println("Input cannot be empty. Please try again.");
        }
    }

    // Collects student details and adds a record
    // Collects student details and adds a record
    // Collects student details and adds a record
    private static void handleAddStudent() {

        while (true) {

            String id = readRequiredInput("Enter student ID: ");
            String name = readRequiredInput("Enter student name: ");
            String course = readRequiredInput("Enter course: ");

            Student student = new Student(id, name, course);

            try {
                service.addStudent(student);

                if (saveRecords()) {
                    System.out.println("Record added successfully!");
                } else {
                    service.deleteStudent(id);
                    System.out.println(
                            "Record was not added because saving failed.");
                }

                return;

            } catch (InvalidStudentDataException e) {
                System.out.println(
                        "Invalid student data: " + e.getMessage());
                System.out.println("Please enter the student details again.\n");
            }
        }
    }

    // Displays all registered students
    private static void listStudents() {
        ArrayList<Student> students = service.getAllStudents();

        if (students.isEmpty()) {
            System.out.println("No records found.");
            return;
        }

        System.out.println("\n========== STUDENT RECORDS ==========");
        System.out.printf("%-12s %-20s %-20s%n",
                "Student ID", "Name", "Course");
        System.out.println("------------------------------------------------");

        for (Student student : students) {
            System.out.printf("%-12s %-20s %-20s%n",
                    student.getId(),
                    student.getName(),
                    student.getCourse());
        }

        System.out.println("------------------------------------------------");
        System.out.println("Total records: " + students.size());
    }

    // Searches for a student using their ID
    private static void handleSearchStudent() {
        String id = readRequiredInput("Enter student ID to search: ");

        if (id.isEmpty()) {
            System.out.println("Student ID cannot be empty.");
            return;
        }

        Student student = service.searchStudent(id);

        if (student != null) {
            System.out.println("\nRecord found!");
            System.out.println(student);
        } else {
            System.out.println("No record found with ID: " + id);
        }
    }

    // Deletes a student using their ID
    // Deletes a student using their ID
    private static void handleDeleteStudent() {
        String id = readRequiredInput("Enter student ID to delete: ");

        if (id.isEmpty()) {
            System.out.println("Student ID cannot be empty.");
            return;
        }

        Student student = service.searchStudent(id);

        if (student == null) {
            System.out.println("No record found with ID: " + id);
            return;
        }

        if (service.deleteStudent(id)) {

            if (saveRecords()) {
                System.out.println("Record deleted successfully!");
            } else {
                // Restore the record if saving fails
                try {
                    service.addStudent(student);
                    System.out.println(
                            "Deletion cancelled because saving failed.");
                } catch (InvalidStudentDataException e) {
                    System.out.println(
                            "Could not restore student: " + e.getMessage());
                }
            }
        }
    }

}
