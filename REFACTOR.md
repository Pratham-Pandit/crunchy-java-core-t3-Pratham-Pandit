# Task 2 — Refactoring Notes

## Project
Student Register CLI

## Overview
In Task 1, the application was implemented mainly in a single `Main` class, with student records stored as arrays. In Task 2, the application was refactored using object-oriented programming principles while preserving the original features.

## What Became Easier

### 1. Managing Student Data
The new `Student` class represents each student as an object with private fields, getters, setters, and a `toString()` method. This makes student data easier to understand and manage than using string arrays.

### 2. Maintaining Application Operations
The `StudentRegisterService` class handles adding, listing, searching, deleting, and summarizing records. Keeping these operations separate from the menu makes the code easier to maintain and test.

### 3. Understanding Responsibilities
The application now has separate responsibilities: `Student` represents student data, `StudentRegisterService` manages records, and `Main` handles menu interaction. This makes the overall structure easier to read and extend.

## What Became Harder

### Keeping Classes Consistent
Moving from arrays to objects required updating how records were created, accessed, searched, and deleted. The menu and service also needed to communicate through method calls, so more care was needed to keep the behaviour consistent with Task 1.

## Behaviour Preservation
The application retains the five original features:
- Add student records
- List student records
- Search by student ID
- Delete student records
- Display a summary with course-wise counts

The application continues to store records in memory, so records are not retained after the program exits.

## Technologies
- Java 21
- Apache Maven
- Git and GitHub