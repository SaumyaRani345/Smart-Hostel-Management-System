# Project Statement

## Project Title

Smart Hostel Management System

## Problem Statement

Managing hostel activities manually can make it difficult to maintain
accurate student records, monitor room occupancy, track complaints,
manage fee payments, and communicate important notices.

The Smart Hostel Management System is developed as a Java console
application to organize these hostel management activities in one
system.

## Objective

The main objective of this project is to develop a simple hostel
management application that can:

1.  Maintain student information.
2.  Manage hostel rooms and occupancy.
3.  Allocate rooms to students.
4.  Vacate rooms when required.
5.  Receive and manage student complaints.
6.  Maintain hostel fee records and payments.
7.  Publish hostel notices.
8.  Provide hostel summary reports.
9.  Demonstrate object-oriented programming concepts in Java.
10. Store application data for future use.

## Scope

The system provides two types of users:

### Administrator

The administrator can manage students, rooms, complaints, fees, notices,
and reports.

### Student

Students can view their profile and room information, submit and track
complaints, view fee information, make fee payments, and read hostel
notices.

## Functional Requirements

### Student Management

The system shall allow the administrator to:

-   Add a student.
-   View all students.
-   Search students by ID.
-   Search students by name.
-   Update student information.
-   Delete student records.

### Room Management

The system shall allow the administrator to:

-   View rooms.
-   Add rooms.
-   Allocate rooms.
-   Automatically allocate an available room.
-   Vacate rooms.
-   Check room availability.
-   Track room capacity and occupancy.

### Complaint Management

The system shall allow students to submit complaints and administrators
to:

-   View complaints.
-   View unresolved complaints.
-   Change complaint status.
-   Delete complaint records.

Complaint statuses include:

-   Pending
-   In Progress
-   Resolved

### Fee Management

The system shall allow administrators to:

-   Add fee records.
-   View fee records.
-   Search a student's fee status.
-   Record payments.

Students shall be able to view their fee status and make payments
through the application.

### Notice Management

The administrator shall be able to:

-   Add notices.
-   View notices.
-   Delete notices.

Students shall be able to view hostel notices.

### Reporting

The system shall generate a hostel summary containing information such
as:

-   Total students
-   Total rooms
-   Total beds
-   Occupied beds
-   Available beds
-   Total complaints
-   Pending complaints
-   Resolved complaints
-   Total fees
-   Collected fees
-   Pending fees
-   Total notices

## Non-Functional Requirements

### Usability

The system should provide a simple menu-driven interface that is easy to
operate.

### Reliability

The application should validate user input and handle invalid numeric
input without terminating unexpectedly.

### Maintainability

The project is organized into classes and methods so that individual
modules can be modified or extended.

### Portability

The application can run on systems with a compatible Java Development
Kit.

## Technologies Used

-   Programming Language: Java
-   Application Type: Console-based application
-   Data Storage: Text files
-   Collection Framework: ArrayList
-   File Handling: Java NIO
-   Date Handling: `LocalDate`

## Object-Oriented Concepts Used

The project demonstrates:

-   Classes and objects
-   Inheritance
-   Interfaces
-   Method overriding
-   Method overloading
-   Encapsulation
-   Collections
-   Exception handling

## Smart Feature

The smart feature of the system is automatic room allocation. The
application checks the available rooms and selects an appropriate room
based on current room occupancy. This reduces the need for manual room
selection and helps maintain accurate occupancy information.

## Expected Outcome

The completed system should provide a functional console-based
environment for managing common hostel operations. It should maintain
student, room, complaint, fee, and notice information while
demonstrating core Java programming and object-oriented programming
concepts.

## Conclusion

The Smart Hostel Management System provides a centralized approach to
basic hostel administration. It combines student management, room
management, complaint tracking, fee management, notices, reporting, file
handling, and object-oriented Java concepts in a single application.
