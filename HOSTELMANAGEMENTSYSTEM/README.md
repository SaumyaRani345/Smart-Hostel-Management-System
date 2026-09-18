# Smart Hostel Management System

## Project Overview

Smart Hostel Management System is a Java-based console application
designed to simplify common hostel management activities. The system
provides separate access for administrators and students and manages
student records, room allocation, complaints, fees, notices, and hostel
reports.

The project is implemented as a single Java source file, `Main.java`.

## Features

### Admin Module

-   Admin login
-   Student management
    -   View all students
    -   Add student
    -   Search student by ID
    -   Search student by name
    -   Update student
    -   Delete student
-   Room management
    -   View all rooms
    -   Add room
    -   Smart room allocation
    -   Specific room allocation
    -   Vacate room
    -   Check available rooms
-   Complaint management
    -   View all complaints
    -   View pending complaints
    -   Update complaint status
    -   Delete complaint
-   Fee management
    -   View fee records
    -   Add fee records
    -   Record payments
    -   Search fee status
-   Notice management
    -   View notices
    -   Add notices
    -   Delete notices
-   Hostel reports

### Student Module

-   Student login
-   View profile
-   View room details
-   Submit complaints
-   View personal complaints
-   View fee status
-   Make fee payments
-   View hostel notices

## Smart Room Allocation

The system includes a smart room allocation feature. When a student
requests automatic room allocation, the application searches for an
available room and selects a suitable room based on the current number
of available beds.

The system also prevents allocation when a room is full and updates room
occupancy when a room is allocated or vacated.

## Object-Oriented Programming Concepts

The project demonstrates the following Java concepts:

-   Classes and objects
-   Encapsulation through class structure and controlled methods
-   Inheritance using `Person`, `Student`, and `Admin`
-   Method overriding through the `Displayable` interface
-   Interface implementation
-   Method overloading for student search
-   ArrayList collections
-   Exception handling
-   File handling

## Data Persistence

The application stores information in text files inside a `data`
directory:

-   `students.txt`
-   `rooms.txt`
-   `complaints.txt`
-   `fees.txt`
-   `notices.txt`

This allows records to remain available after the program is closed and
opened again.

## Requirements

-   Java Development Kit (JDK)
-   Java-compatible IDE or text editor
-   Command Prompt or Terminal

The project does not require an external database or third-party Java
library.

## How to Run

### Using an IDE

1.  Open the project folder.
2.  Open `Main.java`.
3.  Compile the file.
4.  Run the `Main` class.

### Using Terminal

Open the terminal in the project folder and run:

``` text
javac Main.java
java Main
```

The application will create the `data` folder automatically when
required.

## Login Details

### Admin

Username:

``` text
admin
```

Password:

``` text
1234
```

### Student

Student login uses the student's ID.

A sample student is initially available with:

``` text
Student ID: 101
```

Additional students can be created through the Admin Student Management
module.

## Project Structure

The project uses a single Java file:

``` text
SmartHostelManagementSystem/
│
├── Main.java
├── README.md
├── statement.md
└── data/
    ├── students.txt
    ├── rooms.txt
    ├── complaints.txt
    ├── fees.txt
    └── notices.txt
```

The `data` directory is generated automatically by the application.

## Error Handling

The application validates numeric input and handles invalid entries
without terminating the program. It also checks conditions such as:

-   Duplicate student IDs
-   Duplicate room numbers
-   Invalid room capacity
-   Full rooms
-   Students who already have a room
-   Students without a room when attempting to vacate
-   Invalid fee and payment amounts
-   Invalid complaint categories
-   Missing student, room, complaint, fee, or notice records

## Limitations

-   The application is console-based.
-   Authentication uses fixed admin credentials.
-   Data is stored in text files instead of a relational database.
-   The application is intended for academic demonstration and does not
    implement production-level security.

## Future Enhancements

Possible future improvements include:

-   GUI using Java Swing or JavaFX
-   Database integration using JDBC and MySQL
-   Secure password storage
-   Student registration and authentication
-   Automated fee receipts
-   Advanced room allocation based on preferences
-   Search and filtering across all modules
-   Exportable reports
-   Role-based permissions
-   Email or notification integration

## Author

Smart Hostel Management System

Java Project
