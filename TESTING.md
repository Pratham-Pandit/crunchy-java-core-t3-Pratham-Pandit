# Testing Documentation

## Project: Student Register CLI — Task 3

This document describes the testing performed on the Student Register CLI application.

The application was tested using valid inputs, hostile inputs, corrupted CSV data, and file-operation failures to verify input validation, exception handling, and data persistence.

## 1. Hostile Input Test Cases

| Test Case | Input | Expected Result | Status |
|---|---|---|---|
| TC01 | Enter alphabets such as `abc` as the menu choice | Display an error and re-prompt for the menu choice | PASS |
| TC02 | Enter a menu choice outside the range, such as `9` | Display an invalid choice message and re-prompt | PASS |
| TC03 | Leave the student ID empty | Display an error and re-prompt for the ID | PASS |
| TC04 | Leave the student name or course empty | Display an error and re-prompt for the missing field | PASS |
| TC05 | Enter an already registered student ID | Display a duplicate ID error and request student details again | PASS |

## 2. Functional Tests

| Test | Description | Status |
|---|---|---|
| FT01 | Add a student record | PASS |
| FT02 | List all student records | PASS |
| FT03 | Search for a student using ID | PASS |
| FT04 | Delete a student record | PASS |
| FT05 | Display total records and course-wise counts | PASS |
| FT06 | Save student records to CSV | PASS |
| FT07 | Load saved records after application restart | PASS |

## 3. File Handling and Persistence Tests

| Test | Description | Expected Result | Status |
|---|---|---|---|
| PT01 | Start the application when `students.csv` is missing | Automatically create an empty CSV file with a header | PASS |
| PT02 | Load a CSV containing malformed records | Skip invalid records and display line-number warnings | PASS |
| PT03 | Attempt to save records when the CSV file is read-only | Display a saving error and roll back the addition | PASS |
| PT04 | Save and reload names containing commas | Preserve the comma correctly | PASS |
| PT05 | Save and reload names containing quotation marks | Preserve escaped quotation marks correctly | PASS |
| PT06 | Check for leftover temporary files after saving | Remove the temporary file | PASS |

## 4. Additional Validation

- Duplicate student IDs are rejected.
- Empty student details are rejected.
- Invalid menu choices are handled without crashing.
- Corrupted CSV records do not prevent valid records from loading.
- File operations use try-with-resources.
- A temporary file is used before replacing the original CSV file.

## 5. Conclusion

The Student Register CLI successfully passed the documented hostile-input, functional, and persistence tests.

Custom exception handling and input validation provide meaningful error messages. CSV persistence allows student records to remain available after application restarts.

The application also handles missing files, corrupted records, and failed save operations without intentionally overwriting the original data with incomplete records.