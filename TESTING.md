
# Testing Documentation

## Project: Student Register CLI

This document describes the testing performed on the Student Register CLI application.

The application was tested with valid, invalid, and unexpected inputs to verify input validation, exception handling, and CSV persistence.

## Hostile Input Test Cases

| Test Case | Input | Expected Result | Status |
|---|---|---|---|
| TC01 | Enter alphabets such as `abc` as the menu choice | Display an error and ask for the menu choice again | PASS |
| TC02 | Enter a menu choice outside the range, such as `9` | Display an invalid choice message and re-prompt | PASS |
| TC03 | Leave the student ID empty | Display an error and ask for the ID again | PASS |
| TC04 | Leave the student name or course empty | Display an error and ask for the missing field again | PASS |
| TC05 | Enter an already registered student ID | Display a duplicate ID error and ask for student details again | PASS |

## Additional Functional Tests

1. Added a student record successfully.
2. Listed all student records.
3. Searched for an existing student.
4. Deleted an existing student.
5. Displayed the register summary.
6. Verified that student records were saved to CSV.
7. Verified that saved records were loaded when the application restarted.

## Conclusion

The application successfully handles the tested hostile inputs without crashing.

Input validation and custom exception handling provide meaningful error messages. CSV persistence allows student records to remain available after application restarts.
