
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentRegisterService service = new StudentRegisterService();

    public static void main(String[] args) {
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
    private static int readChoice() {
        String input = scanner.nextLine().trim();

        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // Collects student details and delegates creation to the service
    private static void handleAddStudent() {
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine().trim();

        System.out.print("Enter student name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter course: ");
        String course = scanner.nextLine().trim();

        Student student = new Student(id, name, course);

        if (service.addStudent(student)) {
            System.out.println("Record added successfully!");
        } else {
            System.out.println("Could not add record. Check the details and ensure the ID is unique.");
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
        System.out.print("Enter student ID to search: ");
        String id = scanner.nextLine().trim();

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
    private static void handleDeleteStudent() {
        System.out.print("Enter student ID to delete: ");
        String id = scanner.nextLine().trim();

        if (id.isEmpty()) {
            System.out.println("Student ID cannot be empty.");
            return;
        }

        if (service.deleteStudent(id)) {
            System.out.println("Record deleted successfully!");
        } else {
            System.out.println("No record found with ID: " + id);
        }
    }
}
