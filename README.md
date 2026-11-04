# Student Register CLI — Task 2

A menu-driven Java command-line application refactored using object-oriented programming principles.

## Project Details
- **Track:** Core
- **Domain:** Student Register CLI
- **Language:** Java 21
- **Build Tool:** Apache Maven

## Features
- Add student records
- List all student records
- Search for a student by ID
- Delete a student record
- Display total records and course-wise counts

## Project Structure
- `Main.java` — handles the menu and user interaction
- `Student.java` — represents a student using private fields, getters, setters, and `toString()`
- `StudentRegisterService.java` — manages student records and application operations

## Requirements
- JDK 21
- Apache Maven
- Git

## How to Run

Compile the project:

    mvn clean compile


Run the application:

    java -cp target/classes Main


## Documentation
- `REFACTOR.md` — describes the refactoring decisions, what became easier, and what became harder
- `docs/class-diagram.png` — class diagram
- `docs/class-diagram.mmd` — Mermaid source for the class diagram

## Storage
Student records are stored in memory and are cleared when the application exits.