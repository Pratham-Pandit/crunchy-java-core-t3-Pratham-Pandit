# Student Register CLI — Task 3

A menu-driven Java command-line application that manages student records using object-oriented programming, CSV file persistence, custom exception handling, and input validation.

## Project Details

- **Track:** Core
- **Domain:** Student Register CLI
- **Language:** Java 21
- **Build Tool:** Apache Maven
- **Storage:** CSV file

## Features

- Add student records
- List all student records
- Search for a student by ID
- Delete student records
- Display total records and course-wise counts
- Automatically save records to a CSV file
- Load previously saved records when the application starts
- Validate student details and reject duplicate IDs
- Handle invalid inputs with readable error messages and re-prompting
- Handle missing and corrupted CSV records safely
- Use a custom exception for invalid student data

## Project Structure

```text
crunchy-java-core-t3-Pratham-Pandit/
├── src/
│   └── main/
│       └── java/
│           ├── Main.java
│           ├── Student.java
│           ├── StudentRegisterService.java
│           ├── StudentFileHandler.java
│           └── InvalidStudentDataException.java
├── docs/
├── students.csv
├── pom.xml
├── README.md
├── REFACTOR.md
└── TESTING.md
```

## Requirements

- JDK 21
- Apache Maven
- Git

## How to Run

### 1. Compile the project

```bash
mvn clean compile
```

### 2. Run the application

```bash
java -cp target/classes Main
```

## CSV Persistence

Student records are stored in `students.csv` in the project root directory.

- Existing records are loaded automatically at startup.
- New records are saved after successful addition.
- Deleted records are removed from the saved data.
- If the CSV file does not exist, an empty file with a header is created.
- Invalid or duplicate CSV records are skipped with warnings.
- Saving uses a temporary file before replacing the original CSV file.

The CSV format is:

```csv
id,name,course
"101","Rahul","CSE"
"102","Priya","ECE"
```

CSV fields support commas and escaped quotation marks. Records are stored one per line; multiline fields are not supported.

## Exception Handling and Input Validation

The application uses `InvalidStudentDataException` to handle invalid student details, including:

- Empty student ID
- Empty student name
- Empty course
- Duplicate student ID

User inputs are validated, and the application re-prompts when invalid or empty input is entered.

File operations use try-with-resources, and file errors are reported without displaying stack traces to the user.

## Testing

Testing documentation is available in `TESTING.md`.

It includes five hostile-input test cases for the Core track, along with additional functional and persistence tests.

## Documentation

- `TESTING.md` — hostile-input and functional testing documentation
- `REFACTOR.md` — refactoring decisions from Task 2
- `docs/class-diagram.png` — class diagram
- `docs/class-diagram.mmd` — Mermaid source for the class diagram
