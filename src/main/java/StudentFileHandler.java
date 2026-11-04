
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class StudentFileHandler {

    private final Path filePath;

    public StudentFileHandler(String fileName) {
        this.filePath = Path.of(fileName).toAbsolutePath();
    }

    // Saves all student records safely to CSV
    public void saveStudents(List<Student> students) throws IOException {

        Path parent = filePath.getParent();
        Path tempFile = parent.resolve(filePath.getFileName() + ".tmp");

        Files.createDirectories(parent);

        try (BufferedWriter writer = Files.newBufferedWriter(
                tempFile, StandardCharsets.UTF_8)) {

            writer.write("id,name,course");
            writer.newLine();

            for (Student student : students) {
                writer.write(escapeCsv(student.getId()));
                writer.write(",");
                writer.write(escapeCsv(student.getName()));
                writer.write(",");
                writer.write(escapeCsv(student.getCourse()));
                writer.newLine();
            }
        }

        try {
            Files.move(
                    tempFile,
                    filePath,
                    StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.ATOMIC_MOVE
            );
        } catch (AtomicMoveNotSupportedException e) {
            Files.move(
                    tempFile,
                    filePath,
                    StandardCopyOption.REPLACE_EXISTING
            );
        }
    }

    // Loads student records from CSV
    public List<Student> loadStudents() throws IOException {

        List<Student> students = new ArrayList<>();

        if (!Files.exists(filePath)) {
            saveStudents(students);
            return students;
        }

        try (BufferedReader reader = Files.newBufferedReader(
                filePath, StandardCharsets.UTF_8)) {

            String line;
            int lineNumber = 0;
            int skippedRecords = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                if (lineNumber == 1 && line.equals("id,name,course")) {
                    continue;
                }

                try {
                    List<String> fields = parseCsvLine(line);

                    if (fields.size() != 3
                            || fields.get(0).isBlank()
                            || fields.get(1).isBlank()
                            || fields.get(2).isBlank()) {
                        throw new IllegalArgumentException();
                    }

                    String id = fields.get(0).trim();
                    String name = fields.get(1).trim();
                    String course = fields.get(2).trim();

                    boolean duplicate = false;

                    for (Student existing : students) {
                        if (existing.getId().equalsIgnoreCase(id)) {
                            duplicate = true;
                            break;
                        }
                    }

                    if (duplicate) {
                        throw new IllegalArgumentException();
                    }

                    students.add(new Student(id, name, course));

                } catch (IllegalArgumentException e) {
                    skippedRecords++;
                    System.out.println(
                            "Warning: Invalid CSV record at line "
                                    + lineNumber + " was skipped."
                    );
                }
            }

            if (skippedRecords > 0) {
                System.out.println(
                        "Total invalid records skipped: " + skippedRecords
                );
            }
        }

        return students;
    }

    // Escapes CSV values
    private String escapeCsv(String value) {

        if (value == null) {
            return "";
        }

        return "\"" + value.replace("\"", "\"\"") + "\"";
    }

    // Parses a CSV line while supporting quoted commas and quotes
    private List<String> parseCsvLine(String line) {

        List<String> fields = new ArrayList<>();
        StringBuilder field = new StringBuilder();

        boolean insideQuotes = false;

        for (int i = 0; i < line.length(); i++) {

            char current = line.charAt(i);

            if (current == '"') {

                if (insideQuotes && i + 1 < line.length()
                        && line.charAt(i + 1) == '"') {

                    field.append('"');
                    i++;

                } else {
                    insideQuotes = !insideQuotes;
                }

            } else if (current == ',' && !insideQuotes) {

                fields.add(field.toString());
                field.setLength(0);

            } else {
                field.append(current);
            }
        }

        if (insideQuotes) {
            throw new IllegalArgumentException();
        }

        fields.add(field.toString());

        return fields;
    }
}
