import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;

public class Main {

    static final Scanner sc = new Scanner(System.in);

    static final String DATA_DIR = "data";
    static final String STUDENT_FILE = DATA_DIR + "/students.txt";
    static final String ROOM_FILE = DATA_DIR + "/rooms.txt";
    static final String COMPLAINT_FILE = DATA_DIR + "/complaints.txt";
    static final String FEE_FILE = DATA_DIR + "/fees.txt";
    static final String NOTICE_FILE = DATA_DIR + "/notices.txt";

    interface Displayable {
        void display();
    }

    static class Person implements Displayable {
        protected String name;
        protected String phone;

        Person(String name, String phone) {
            this.name = name;
            this.phone = phone;
        }

        @Override
        public void display() {
            System.out.println("Name       : " + name);
            System.out.println("Phone      : " + phone);
        }
    }

    static class Student extends Person {
        int studentId;
        int age;
        String course;
        int year;
        String roomNumber;

        Student(int studentId, String name, String phone, int age,
                String course, int year, String roomNumber) {
            super(name, phone);
            this.studentId = studentId;
            this.age = age;
            this.course = course;
            this.year = year;
            this.roomNumber = roomNumber;
        }

        @Override
        public void display() {
            System.out.println("\n----- STUDENT DETAILS -----");
            System.out.println("Student ID : " + studentId);
            System.out.println("Name       : " + name);
            System.out.println("Phone      : " + phone);
            System.out.println("Age        : " + age);
            System.out.println("Course     : " + course);
            System.out.println("Year       : " + year);
            System.out.println("Room       : " + roomNumber);
        }
    }

    static class Admin extends Person {
        Admin(String name, String phone) {
            super(name, phone);
        }

        @Override
        public void display() {
            System.out.println("Admin Name  : " + name);
            System.out.println("Admin Phone : " + phone);
        }
    }

    static class Room implements Displayable {
        String roomNumber;
        int capacity;
        int occupied;

        Room(String roomNumber, int capacity) {
            this.roomNumber = roomNumber;
            this.capacity = capacity;
            this.occupied = 0;
        }

        boolean isAvailable() {
            return occupied < capacity;
        }

        int availableBeds() {
            return capacity - occupied;
        }

        boolean allocateBed() {
            if (isAvailable()) {
                occupied++;
                return true;
            }
            return false;
        }

        boolean vacateBed() {
            if (occupied > 0) {
                occupied--;
                return true;
            }
            return false;
        }

        @Override
        public void display() {
            System.out.println(
                roomNumber +
                " | Capacity: " + capacity +
                " | Occupied: " + occupied +
                " | Available: " + availableBeds()
            );
        }
    }

    static class Complaint implements Displayable {
        int complaintId;
        int studentId;
        String category;
        String description;
        String status;
        String date;

        Complaint(int complaintId, int studentId,
                  String category, String description) {
            this.complaintId = complaintId;
            this.studentId = studentId;
            this.category = category;
            this.description = description;
            this.status = "Pending";
            this.date = LocalDate.now().toString();
        }

        @Override
        public void display() {
            System.out.println("\nComplaint ID : " + complaintId);
            System.out.println("Student ID   : " + studentId);
            System.out.println("Category     : " + category);
            System.out.println("Description  : " + description);
            System.out.println("Date         : " + date);
            System.out.println("Status       : " + status);
        }
    }

    static class Fee implements Displayable {
        int studentId;
        double totalFee;
        double paidAmount;

        Fee(int studentId, double totalFee, double paidAmount) {
            this.studentId = studentId;
            this.totalFee = totalFee;
            this.paidAmount = paidAmount;
        }

        double pendingAmount() {
            return Math.max(0, totalFee - paidAmount);
        }

        @Override
        public void display() {
            System.out.println("Student ID  : " + studentId);
            System.out.printf("Total Fee   : ₹%.2f%n", totalFee);
            System.out.printf("Paid        : ₹%.2f%n", paidAmount);
            System.out.printf("Pending     : ₹%.2f%n", pendingAmount());

            if (pendingAmount() == 0) {
                System.out.println("Status      : PAID");
            } else {
                System.out.println("Status      : PENDING");
            }
        }
    }

    static class Notice implements Displayable {
        int noticeId;
        String title;
        String message;
        String date;

        Notice(int noticeId, String title, String message) {
            this.noticeId = noticeId;
            this.title = title;
            this.message = message;
            this.date = LocalDate.now().toString();
        }

        @Override
        public void display() {
            System.out.println("\n----- HOSTEL NOTICE -----");
            System.out.println("Notice ID : " + noticeId);
            System.out.println("Title     : " + title);
            System.out.println("Date      : " + date);
            System.out.println("Message   : " + message);
        }
    }

    static ArrayList<Student> students = new ArrayList<>();
    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Complaint> complaints = new ArrayList<>();
    static ArrayList<Fee> fees = new ArrayList<>();
    static ArrayList<Notice> notices = new ArrayList<>();

    public static void main(String[] args) {
        initializeData();
        mainMenu();
        sc.close();
    }

    static void initializeData() {
        try {
            Files.createDirectories(Paths.get(DATA_DIR));
        } catch (IOException e) {
            System.out.println("Warning: Could not create data folder.");
        }

        loadAll();

        if (rooms.isEmpty()) {
            rooms.add(new Room("A-101", 3));
            rooms.add(new Room("A-102", 3));
            rooms.add(new Room("A-103", 2));
            rooms.add(new Room("A-104", 2));
            saveRooms();
        }

        if (students.isEmpty()) {
            students.add(new Student(
                101, "Rahul", "9876543210", 20,
                "B.Tech CSE", 2, "A-101"
            ));
            saveStudents();
        }

        if (fees.isEmpty()) {
            fees.add(new Fee(101, 60000, 40000));
            saveFees();
        }

        if (complaints.isEmpty()) {
            complaints.add(new Complaint(
                1, 101, "Electrical", "Fan is not working"
            ));
            saveComplaints();
        }

        if (notices.isEmpty()) {
            notices.add(new Notice(
                1,
                "Hostel Inspection",
                "Hostel inspection will be conducted on Sunday."
            ));
            saveNotices();
        }

        syncRoomOccupancy();
    }

    static void mainMenu() {
        while (true) {
            printHeader();

            System.out.println("1. Admin Login");
            System.out.println("2. Student Login");
            System.out.println("3. Exit");

            int choice = readInt("Enter choice: ");

            switch (choice) {
                case 1:
                    adminLogin();
                    break;
                case 2:
                    studentLogin();
                    break;
                case 3:
                    System.out.println(
                        "\nThank you for using Smart Hostel Management System!"
                    );
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void adminLogin() {
        System.out.println("\n========== ADMIN LOGIN ==========");

        String username = readLine("Enter Admin Username: ");
        String password = readLine("Enter Admin Password: ");

        if (username.equals("admin") && password.equals("1234")) {
            adminDashboard();
        } else {
            System.out.println("Invalid username or password!");
        }
    }

    static void adminDashboard() {
        while (true) {
            System.out.println("\n========== ADMIN DASHBOARD ==========");
            System.out.println("1. Student Management");
            System.out.println("2. Room Management");
            System.out.println("3. Complaint Management");
            System.out.println("4. Fee Management");
            System.out.println("5. Notice Management");
            System.out.println("6. Reports");
            System.out.println("7. Logout");

            int choice = readInt("Enter choice: ");

            switch (choice) {
                case 1:
                    studentManagement();
                    break;
                case 2:
                    roomManagement();
                    break;
                case 3:
                    complaintManagement();
                    break;
                case 4:
                    feeManagement();
                    break;
                case 5:
                    noticeManagement();
                    break;
                case 6:
                    reports();
                    break;
                case 7:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void studentManagement() {
        while (true) {
            System.out.println("\n========== STUDENT MANAGEMENT ==========");
            System.out.println("1. View All Students");
            System.out.println("2. Add Student");
            System.out.println("3. Search Student by ID");
            System.out.println("4. Search Student by Name");
            System.out.println("5. Update Student");
            System.out.println("6. Delete Student");
            System.out.println("7. Back");

            int choice = readInt("Enter choice: ");

            switch (choice) {
                case 1:
                    viewStudents();
                    break;
                case 2:
                    addStudent();
                    break;
                case 3:
                    searchStudent(readInt("Enter Student ID: "));
                    break;
                case 4:
                    searchStudent(readLine("Enter Student Name: "));
                    break;
                case 5:
                    updateStudent();
                    break;
                case 6:
                    deleteStudent();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void viewStudents() {
        System.out.println("\n========== ALL STUDENTS ==========");

        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        for (Student s : students) {
            s.display();
        }
    }

    static void addStudent() {
        System.out.println("\n========== ADD STUDENT ==========");

        int id = readInt("Student ID: ");

        if (findStudent(id) != null) {
            System.out.println("Student ID already exists!");
            return;
        }

        String name = readLine("Name: ");
        String phone = readLine("Phone: ");
        int age = readInt("Age: ");
        String course = readLine("Course: ");
        int year = readInt("Year: ");

        Student s = new Student(
            id, name, phone, age, course, year, "Not Allocated"
        );

        students.add(s);

        System.out.println("\nDo you want to allocate a room now?");
        System.out.println("1. Yes - Smart Allocation");
        System.out.println("2. No");

        int roomChoice = readInt("Enter choice: ");

        if (roomChoice == 1) {
            Room selected = smartAllocateRoom();

            if (selected != null) {
                selected.allocateBed();
                s.roomNumber = selected.roomNumber;

                System.out.println(
                    "Room " + selected.roomNumber +
                    " allocated successfully!"
                );
            } else {
                System.out.println("No available room.");
            }
        }

        saveStudents();
        saveRooms();

        System.out.println("Student added successfully!");
    }

    static void searchStudent(int id) {
        Student s = findStudent(id);

        if (s == null) {
            System.out.println("Student not found!");
        } else {
            s.display();
        }
    }

    static void searchStudent(String name) {
        boolean found = false;

        for (Student s : students) {
            if (s.name.equalsIgnoreCase(name)) {
                s.display();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No student found with that name.");
        }
    }

    static void updateStudent() {
        int id = readInt("Enter Student ID to update: ");

        Student s = findStudent(id);

        if (s == null) {
            System.out.println("Student not found!");
            return;
        }

        s.name = readLine("New name: ");
        s.phone = readLine("New phone: ");
        s.age = readInt("New age: ");
        s.course = readLine("New course: ");
        s.year = readInt("New year: ");

        saveStudents();

        System.out.println("Student updated successfully!");
    }

    static void deleteStudent() {
        int id = readInt("Enter Student ID to delete: ");

        Student s = findStudent(id);

        if (s == null) {
            System.out.println("Student not found!");
            return;
        }

        if (!s.roomNumber.equalsIgnoreCase("Not Allocated")) {
            Room r = findRoom(s.roomNumber);

            if (r != null) {
                r.vacateBed();
            }
        }

        students.remove(s);

        fees.removeIf(f -> f.studentId == id);
        complaints.removeIf(c -> c.studentId == id);

        saveStudents();
        saveRooms();
        saveFees();
        saveComplaints();

        System.out.println("Student deleted successfully!");
    }

    static void roomManagement() {
        while (true) {
            System.out.println("\n========== ROOM MANAGEMENT ==========");
            System.out.println("1. View All Rooms");
            System.out.println("2. Add Room");
            System.out.println("3. Smart Allocate Room");
            System.out.println("4. Allocate Specific Room");
            System.out.println("5. Vacate Room");
            System.out.println("6. Check Available Rooms");
            System.out.println("7. Back");

            int choice = readInt("Enter choice: ");

            switch (choice) {
                case 1:
                    viewRooms();
                    break;
                case 2:
                    addRoom();
                    break;
                case 3:
                    smartAllocateForStudent();
                    break;
                case 4:
                    allocateSpecificRoom();
                    break;
                case 5:
                    vacateStudentRoom();
                    break;
                case 6:
                    availableRooms();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void viewRooms() {
        System.out.println("\n========== ALL ROOMS ==========");

        if (rooms.isEmpty()) {
            System.out.println("No rooms available.");
            return;
        }

        for (Room r : rooms) {
            r.display();
        }
    }

    static void addRoom() {
        String number = readLine("Enter Room Number: ");

        if (findRoom(number) != null) {
            System.out.println("Room already exists!");
            return;
        }

        int capacity = readInt("Enter Room Capacity: ");

        if (capacity <= 0) {
            System.out.println("Capacity must be greater than 0.");
            return;
        }

        rooms.add(new Room(number, capacity));
        saveRooms();

        System.out.println("Room added successfully!");
    }

    static void smartAllocateForStudent() {
        int id = readInt("Enter Student ID: ");

        Student s = findStudent(id);

        if (s == null) {
            System.out.println("Student not found!");
            return;
        }

        if (!s.roomNumber.equalsIgnoreCase("Not Allocated")) {
            System.out.println(
                "Student already has Room " + s.roomNumber
            );
            return;
        }

        Room selected = smartAllocateRoom();

        if (selected == null) {
            System.out.println("No room available.");
            return;
        }

        selected.allocateBed();
        s.roomNumber = selected.roomNumber;

        saveStudents();
        saveRooms();

        System.out.println("\nSMART ALLOCATION SUCCESSFUL!");
        System.out.println("Student : " + s.name);
        System.out.println("Room    : " + selected.roomNumber);
    }

    static Room smartAllocateRoom() {
        Room best = null;

        for (Room r : rooms) {
            if (r.isAvailable()) {
                if (best == null ||
                    r.availableBeds() < best.availableBeds()) {
                    best = r;
                }
            }
        }

        return best;
    }

    static void allocateSpecificRoom() {
        int id = readInt("Enter Student ID: ");

        Student s = findStudent(id);

        if (s == null) {
            System.out.println("Student not found!");
            return;
        }

        if (!s.roomNumber.equalsIgnoreCase("Not Allocated")) {
            System.out.println(
                "Student already has Room " + s.roomNumber
            );
            return;
        }

        String number = readLine("Enter Room Number: ");
        Room r = findRoom(number);

        if (r == null) {
            System.out.println("Room not found!");
            return;
        }

        if (!r.isAvailable()) {
            System.out.println("Sorry! This room is full.");
            return;
        }

        r.allocateBed();
        s.roomNumber = r.roomNumber;

        saveStudents();
        saveRooms();

        System.out.println("Room allocated successfully!");
    }

    static void vacateStudentRoom() {
        int id = readInt("Enter Student ID: ");

        Student s = findStudent(id);

        if (s == null) {
            System.out.println("Student not found!");
            return;
        }

        if (s.roomNumber.equalsIgnoreCase("Not Allocated")) {
            System.out.println("Student has no room.");
            return;
        }

        Room r = findRoom(s.roomNumber);

        if (r != null) {
            r.vacateBed();
        }

        String oldRoom = s.roomNumber;
        s.roomNumber = "Not Allocated";

        saveStudents();
        saveRooms();

        System.out.println(
            "Room " + oldRoom + " vacated successfully!"
        );
    }

    static void availableRooms() {
        System.out.println("\n========== AVAILABLE ROOMS ==========");

        boolean found = false;

        for (Room r : rooms) {
            if (r.isAvailable()) {
                r.display();
                found = true;
            }
        }

        if (!found) {
            System.out.println(
                "No rooms currently have available beds."
            );
        }
    }

    static void complaintManagement() {
        while (true) {
            System.out.println(
                "\n========== COMPLAINT MANAGEMENT =========="
            );

            System.out.println("1. View All Complaints");
            System.out.println("2. View Pending Complaints");
            System.out.println("3. Update Complaint Status");
            System.out.println("4. Delete Complaint");
            System.out.println("5. Back");

            int choice = readInt("Enter choice: ");

            switch (choice) {
                case 1:
                    viewComplaints();
                    break;
                case 2:
                    viewPendingComplaints();
                    break;
                case 3:
                    updateComplaintStatus();
                    break;
                case 4:
                    deleteComplaint();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void viewComplaints() {
        System.out.println(
            "\n========== ALL COMPLAINTS =========="
        );

        if (complaints.isEmpty()) {
            System.out.println("No complaints found.");
            return;
        }

        for (Complaint c : complaints) {
            c.display();
        }
    }

    static void viewPendingComplaints() {
        boolean found = false;

        for (Complaint c : complaints) {
            if (!c.status.equals("Resolved")) {
                c.display();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No pending complaints.");
        }
    }

    static void updateComplaintStatus() {
        int id = readInt("Enter Complaint ID: ");

        Complaint c = findComplaint(id);

        if (c == null) {
            System.out.println("Complaint not found!");
            return;
        }

        System.out.println("1. Pending");
        System.out.println("2. In Progress");
        System.out.println("3. Resolved");

        int status = readInt("Choose new status: ");

        switch (status) {
            case 1:
                c.status = "Pending";
                break;
            case 2:
                c.status = "In Progress";
                break;
            case 3:
                c.status = "Resolved";
                break;
            default:
                System.out.println("Invalid status!");
                return;
        }

        saveComplaints();

        System.out.println(
            "Complaint status updated successfully!"
        );
    }

    static void deleteComplaint() {
        int id = readInt("Enter Complaint ID: ");

        Complaint c = findComplaint(id);

        if (c == null) {
            System.out.println("Complaint not found!");
            return;
        }

        complaints.remove(c);
        saveComplaints();

        System.out.println("Complaint deleted successfully!");
    }

    static void studentLogin() {
        System.out.println(
            "\n========== STUDENT LOGIN =========="
        );

        int id = readInt("Enter Student ID: ");

        Student s = findStudent(id);

        if (s == null) {
            System.out.println("Student not found!");
            return;
        }

        studentDashboard(s);
    }

    static void studentDashboard(Student s) {
        while (true) {
            System.out.println(
                "\n========== STUDENT DASHBOARD =========="
            );

            System.out.println("Welcome, " + s.name + "!");
            System.out.println("1. View Profile");
            System.out.println("2. View Room Details");
            System.out.println("3. Submit Complaint");
            System.out.println("4. View My Complaints");
            System.out.println("5. View My Fee Status");
            System.out.println("6. Make Fee Payment");
            System.out.println("7. View Notices");
            System.out.println("8. Logout");

            int choice = readInt("Enter choice: ");

            switch (choice) {
                case 1:
                    s.display();
                    break;
                case 2:
                    viewStudentRoom(s);
                    break;
                case 3:
                    submitComplaint(s);
                    break;
                case 4:
                    viewMyComplaints(s);
                    break;
                case 5:
                    viewMyFee(s);
                    break;
                case 6:
                    makePayment(s);
                    break;
                case 7:
                    viewNotices();
                    break;
                case 8:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void viewStudentRoom(Student s) {
        System.out.println("\n========== ROOM DETAILS ==========");

        if (s.roomNumber.equalsIgnoreCase("Not Allocated")) {
            System.out.println("No room allocated.");
            return;
        }

        Room r = findRoom(s.roomNumber);

        if (r != null) {
            r.display();
        } else {
            System.out.println("Room record not found.");
        }
    }

    static void submitComplaint(Student s) {
        System.out.println(
            "\n========== SUBMIT COMPLAINT =========="
        );

        System.out.println("1. Electrical");
        System.out.println("2. Plumbing");
        System.out.println("3. Cleaning");
        System.out.println("4. Internet");
        System.out.println("5. Furniture");
        System.out.println("6. Other");

        int categoryChoice =
            readInt("Choose category: ");

        String[] categories = {
            "",
            "Electrical",
            "Plumbing",
            "Cleaning",
            "Internet",
            "Furniture",
            "Other"
        };

        if (categoryChoice < 1 || categoryChoice > 6) {
            System.out.println("Invalid category!");
            return;
        }

        String description =
            readLine("Describe your complaint: ");

        int nextId = nextComplaintId();

        complaints.add(
            new Complaint(
                nextId,
                s.studentId,
                categories[categoryChoice],
                description
            )
        );

        saveComplaints();

        System.out.println(
            "\nComplaint submitted successfully!"
        );

        System.out.println(
            "Complaint ID: " + nextId
        );
    }

    static void viewMyComplaints(Student s) {
        System.out.println(
            "\n========== MY COMPLAINTS =========="
        );

        boolean found = false;

        for (Complaint c : complaints) {
            if (c.studentId == s.studentId) {
                c.display();
                found = true;
            }
        }

        if (!found) {
            System.out.println(
                "You have not submitted any complaints."
            );
        }
    }

    static void feeManagement() {
        while (true) {
            System.out.println(
                "\n========== FEE MANAGEMENT =========="
            );

            System.out.println("1. View All Fee Records");
            System.out.println("2. Add Fee Record");
            System.out.println("3. Update Payment");
            System.out.println("4. Search Student Fee");
            System.out.println("5. Back");

            int choice = readInt("Enter choice: ");

            switch (choice) {
                case 1:
                    viewAllFees();
                    break;
                case 2:
                    addFeeRecord();
                    break;
                case 3:
                    updatePayment();
                    break;
                case 4:
                    searchFee();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void viewAllFees() {
        System.out.println(
            "\n========== ALL FEE RECORDS =========="
        );

        if (fees.isEmpty()) {
            System.out.println("No fee records found.");
            return;
        }

        for (Fee f : fees) {
            System.out.println();
            f.display();
        }
    }

    static void addFeeRecord() {
        int id = readInt("Student ID: ");

        if (findStudent(id) == null) {
            System.out.println("Student not found!");
            return;
        }

        if (findFee(id) != null) {
            System.out.println(
                "Fee record already exists."
            );
            return;
        }

        double total = readDouble("Total Fee: ");
        double paid = readDouble("Paid Amount: ");

        if (total < 0 || paid < 0 || paid > total) {
            System.out.println("Invalid fee values!");
            return;
        }

        fees.add(new Fee(id, total, paid));
        saveFees();

        System.out.println(
            "Fee record added successfully!"
        );
    }

    static void updatePayment() {
        int id = readInt("Student ID: ");

        Fee f = findFee(id);

        if (f == null) {
            System.out.println("Fee record not found!");
            return;
        }

        double amount =
            readDouble("Enter additional payment amount: ");

        if (amount <= 0 ||
            f.paidAmount + amount > f.totalFee) {

            System.out.println(
                "Invalid payment amount!"
            );

            return;
        }

        f.paidAmount += amount;

        saveFees();

        System.out.println(
            "Payment recorded successfully!"
        );
    }

    static void searchFee() {
        int id = readInt("Student ID: ");

        Fee f = findFee(id);

        if (f == null) {
            System.out.println("Fee record not found!");
        } else {
            f.display();
        }
    }

    static void viewMyFee(Student s) {
        Fee f = findFee(s.studentId);

        System.out.println(
            "\n========== MY FEE STATUS =========="
        );

        if (f == null) {
            System.out.println("No fee record found.");
        } else {
            f.display();
        }
    }

    static void makePayment(Student s) {
        Fee f = findFee(s.studentId);

        if (f == null) {
            System.out.println("No fee record found.");
            return;
        }

        if (f.pendingAmount() == 0) {
            System.out.println(
                "Your fees are already fully paid."
            );
            return;
        }

        double amount =
            readDouble("Enter payment amount: ");

        if (amount <= 0 ||
            amount > f.pendingAmount()) {

            System.out.println(
                "Invalid payment amount!"
            );

            return;
        }

        f.paidAmount += amount;

        saveFees();

        System.out.println("Payment successful!");

        System.out.printf(
            "Remaining Fee: ₹%.2f%n",
            f.pendingAmount()
        );
    }

    static void noticeManagement() {
        while (true) {
            System.out.println(
                "\n========== NOTICE MANAGEMENT =========="
            );

            System.out.println("1. View Notices");
            System.out.println("2. Add Notice");
            System.out.println("3. Delete Notice");
            System.out.println("4. Back");

            int choice = readInt("Enter choice: ");

            switch (choice) {
                case 1:
                    viewNotices();
                    break;
                case 2:
                    addNotice();
                    break;
                case 3:
                    deleteNotice();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void viewNotices() {
        System.out.println(
            "\n========== HOSTEL NOTICES =========="
        );

        if (notices.isEmpty()) {
            System.out.println("No notices available.");
            return;
        }

        for (Notice n : notices) {
            n.display();
        }
    }

    static void addNotice() {
        int id = nextNoticeId();

        String title =
            readLine("Notice Title: ");

        String message =
            readLine("Notice Message: ");

        notices.add(
            new Notice(id, title, message)
        );

        saveNotices();

        System.out.println(
            "Notice added successfully!"
        );

        System.out.println(
            "Notice ID: " + id
        );
    }

    static void deleteNotice() {
        int id = readInt("Notice ID to delete: ");

        Notice n = findNotice(id);

        if (n == null) {
            System.out.println("Notice not found!");
            return;
        }

        notices.remove(n);
        saveNotices();

        System.out.println(
            "Notice deleted successfully!"
        );
    }

    static void reports() {
        int totalBeds = 0;
        int occupiedBeds = 0;

        for (Room r : rooms) {
            totalBeds += r.capacity;
            occupiedBeds += r.occupied;
        }

        int pendingComplaints = 0;
        int resolvedComplaints = 0;

        for (Complaint c : complaints) {
            if (c.status.equals("Resolved")) {
                resolvedComplaints++;
            } else {
                pendingComplaints++;
            }
        }

        double totalFees = 0;
        double paidFees = 0;
        double pendingFees = 0;

        for (Fee f : fees) {
            totalFees += f.totalFee;
            paidFees += f.paidAmount;
            pendingFees += f.pendingAmount();
        }

        System.out.println(
            "\n========================================"
        );
        System.out.println(
            "           HOSTEL REPORT"
        );
        System.out.println(
            "========================================"
        );

        System.out.println(
            "Total Students       : " + students.size()
        );

        System.out.println(
            "Total Rooms          : " + rooms.size()
        );

        System.out.println(
            "Total Beds           : " + totalBeds
        );

        System.out.println(
            "Occupied Beds        : " + occupiedBeds
        );

        System.out.println(
            "Available Beds       : " +
            (totalBeds - occupiedBeds)
        );

        System.out.println(
            "Total Complaints     : " +
            complaints.size()
        );

        System.out.println(
            "Pending Complaints   : " +
            pendingComplaints
        );

        System.out.println(
            "Resolved Complaints  : " +
            resolvedComplaints
        );

        System.out.printf(
            "Total Fees           : ₹%.2f%n",
            totalFees
        );

        System.out.printf(
            "Fees Collected       : ₹%.2f%n",
            paidFees
        );

        System.out.printf(
            "Fees Pending         : ₹%.2f%n",
            pendingFees
        );

        System.out.println(
            "Total Notices        : " +
            notices.size()
        );
    }

    static Student findStudent(int id) {
        for (Student s : students) {
            if (s.studentId == id) {
                return s;
            }
        }
        return null;
    }

    static Room findRoom(String number) {
        for (Room r : rooms) {
            if (r.roomNumber.equalsIgnoreCase(number)) {
                return r;
            }
        }
        return null;
    }

    static Complaint findComplaint(int id) {
        for (Complaint c : complaints) {
            if (c.complaintId == id) {
                return c;
            }
        }
        return null;
    }

    static Fee findFee(int id) {
        for (Fee f : fees) {
            if (f.studentId == id) {
                return f;
            }
        }
        return null;
    }

    static Notice findNotice(int id) {
        for (Notice n : notices) {
            if (n.noticeId == id) {
                return n;
            }
        }
        return null;
    }

    static int nextComplaintId() {
        int max = 0;

        for (Complaint c : complaints) {
            if (c.complaintId > max) {
                max = c.complaintId;
            }
        }

        return max + 1;
    }

    static int nextNoticeId() {
        int max = 0;

        for (Notice n : notices) {
            if (n.noticeId > max) {
                max = n.noticeId;
            }
        }

        return max + 1;
    }

    static void syncRoomOccupancy() {
        for (Room r : rooms) {
            r.occupied = 0;
        }

        for (Student s : students) {
            if (!s.roomNumber.equalsIgnoreCase("Not Allocated")) {
                Room r = findRoom(s.roomNumber);

                if (r != null && r.occupied < r.capacity) {
                    r.occupied++;
                }
            }
        }

        saveRooms();
    }

    static String clean(String text) {
        return text
            .replace("|", "/")
            .replace("\n", " ")
            .replace("\r", " ");
    }

    static void loadAll() {
        loadStudents();
        loadRooms();
        loadComplaints();
        loadFees();
        loadNotices();
    }

    static void loadStudents() {
        students.clear();

        Path path = Paths.get(STUDENT_FILE);

        if (!Files.exists(path)) {
            return;
        }

        try {
            for (String line : Files.readAllLines(path)) {
                String[] a = line.split("\\|", -1);

                if (a.length >= 7) {
                    students.add(
                        new Student(
                            Integer.parseInt(a[0]),
                            a[1],
                            a[2],
                            Integer.parseInt(a[3]),
                            a[4],
                            Integer.parseInt(a[5]),
                            a[6]
                        )
                    );
                }
            }
        } catch (Exception e) {
            System.out.println(
                "Could not load student data."
            );
        }
    }

    static void loadRooms() {
        rooms.clear();

        Path path = Paths.get(ROOM_FILE);

        if (!Files.exists(path)) {
            return;
        }

        try {
            for (String line : Files.readAllLines(path)) {
                String[] a = line.split("\\|", -1);

                if (a.length >= 3) {
                    Room r =
                        new Room(
                            a[0],
                            Integer.parseInt(a[1])
                        );

                    r.occupied =
                        Integer.parseInt(a[2]);

                    rooms.add(r);
                }
            }
        } catch (Exception e) {
            System.out.println(
                "Could not load room data."
            );
        }
    }

    static void loadComplaints() {
        complaints.clear();

        Path path =
            Paths.get(COMPLAINT_FILE);

        if (!Files.exists(path)) {
            return;
        }

        try {
            for (String line :
                 Files.readAllLines(path)) {

                String[] a =
                    line.split("\\|", -1);

                if (a.length >= 6) {

                    Complaint c =
                        new Complaint(
                            Integer.parseInt(a[0]),
                            Integer.parseInt(a[1]),
                            a[2],
                            a[3]
                        );

                    c.status = a[4];
                    c.date = a[5];

                    complaints.add(c);
                }
            }
        } catch (Exception e) {
            System.out.println(
                "Could not load complaint data."
            );
        }
    }

    static void loadFees() {
        fees.clear();

        Path path =
            Paths.get(FEE_FILE);

        if (!Files.exists(path)) {
            return;
        }

        try {
            for (String line :
                 Files.readAllLines(path)) {

                String[] a =
                    line.split("\\|", -1);

                if (a.length >= 3) {

                    fees.add(
                        new Fee(
                            Integer.parseInt(a[0]),
                            Double.parseDouble(a[1]),
                            Double.parseDouble(a[2])
                        )
                    );
                }
            }
        } catch (Exception e) {
            System.out.println(
                "Could not load fee data."
            );
        }
    }

    static void loadNotices() {
        notices.clear();

        Path path =
            Paths.get(NOTICE_FILE);

        if (!Files.exists(path)) {
            return;
        }

        try {
            for (String line :
                 Files.readAllLines(path)) {

                String[] a =
                    line.split("\\|", -1);

                if (a.length >= 4) {

                    Notice n =
                        new Notice(
                            Integer.parseInt(a[0]),
                            a[1],
                            a[2]
                        );

                    n.date = a[3];

                    notices.add(n);
                }
            }
        } catch (Exception e) {
            System.out.println(
                "Could not load notice data."
            );
        }
    }

    static void saveStudents() {
        ArrayList<String> lines =
            new ArrayList<>();

        for (Student s : students) {
            lines.add(
                s.studentId +
                "|" + clean(s.name) +
                "|" + clean(s.phone) +
                "|" + s.age +
                "|" + clean(s.course) +
                "|" + s.year +
                "|" + clean(s.roomNumber)
            );
        }

        writeFile(STUDENT_FILE, lines);
    }

    static void saveRooms() {
        ArrayList<String> lines =
            new ArrayList<>();

        for (Room r : rooms) {
            lines.add(
                clean(r.roomNumber) +
                "|" + r.capacity +
                "|" + r.occupied
            );
        }

        writeFile(ROOM_FILE, lines);
    }

    static void saveComplaints() {
        ArrayList<String> lines =
            new ArrayList<>();

        for (Complaint c : complaints) {
            lines.add(
                c.complaintId +
                "|" + c.studentId +
                "|" + clean(c.category) +
                "|" + clean(c.description) +
                "|" + clean(c.status) +
                "|" + c.date
            );
        }

        writeFile(COMPLAINT_FILE, lines);
    }

    static void saveFees() {
        ArrayList<String> lines =
            new ArrayList<>();

        for (Fee f : fees) {
            lines.add(
                f.studentId +
                "|" + f.totalFee +
                "|" + f.paidAmount
            );
        }

        writeFile(FEE_FILE, lines);
    }

    static void saveNotices() {
        ArrayList<String> lines =
            new ArrayList<>();

        for (Notice n : notices) {
            lines.add(
                n.noticeId +
                "|" + clean(n.title) +
                "|" + clean(n.message) +
                "|" + n.date
            );
        }

        writeFile(NOTICE_FILE, lines);
    }

    static void writeFile(
            String filename,
            List<String> lines) {

        try {
            Files.write(
                Paths.get(filename),
                lines
            );
        } catch (IOException e) {
            System.out.println(
                "Warning: Could not save " +
                filename
            );
        }
    }

    static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);

            String input =
                sc.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println(
                    "Invalid input! Please enter a whole number."
                );
            }
        }
    }

    static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);

            String input =
                sc.nextLine().trim();

            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println(
                    "Invalid input! Please enter a number."
                );
            }
        }
    }

    static String readLine(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    static void printHeader() {
        System.out.println(
            "\n========================================"
        );

        System.out.println(
            "       SMART HOSTEL MANAGEMENT SYSTEM"
        );

        System.out.println(
            "========================================"
        );
    }
}