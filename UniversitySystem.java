import java.util.ArrayList;
import java.util.Scanner;

// ==========================================
// Main Class (File name must be UniversitySystem.java)
// ==========================================
public class UniversitySystem {  
    
    // Store all objects (Students and Employees) in a single list for Polymorphism
    private static ArrayList<Person> records = new ArrayList<>();   
    private static Scanner userIn = new Scanner(System.in); 

    public static void main(String[] args) {
        // Initialize system with sample data
        setupSampleData(); 
        
        boolean isActive = true; 

        // Main Menu Loop
        do {
            System.out.println("\n**** University Management System ****");
            System.out.println("1. Add Student");
            System.out.println("2. Add Full-time Employee");
            System.out.println("3. Add Part-time Employee");
            System.out.println("4. Display All Records");
            System.out.println("5. Exit");

            int menuOption = inputInt("Enter your choice: ");

            switch (menuOption) {
                case 1:
                    addNewStudent();
                    break;
                case 2:
                    createFullTime();
                    break;
                case 3:
                    createPartTime();
                    break;
                case 4:
                    showAllData();
                    break;
                case 5:
                    isActive = false;
                    System.out.println("Exiting system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1-5.");
            }
        } while (isActive);
        
        userIn.close();
    }

    // --- Methods for Adding Data & Validation ---

    private static void addNewStudent() {
        System.out.println("\n--- Add Student ---");
        String name = inputString("Enter Name: "); 
        int id = inputInt("Enter ID: ");
        String course = inputString("Enter Course: ");
        
        double grade;
        // Validate that grade is between 0 and 100
        while (true) {
            grade = inputDouble("Enter Grade (0-100): ");
            if (grade >= 0 && grade <= 100) {
                break;
            }
            System.out.println("Error: Grade must be between 0 and 100.");
        }
        records.add(new Student(name, id, course, grade));
        System.out.println("Student added successfully.");
    }

    private static void createFullTime() { 
        System.out.println("\n--- Add Full-time Employee ---");
        String name = inputString("Enter Name: ");
        int id = inputInt("Enter ID: ");
        
        double salary;
        // Validate that salary is positive
        while (true) {
            salary = inputDouble("Enter Monthly Salary: ");
            if (salary > 0) {
                break;
            }
            System.out.println("Error: Salary must be positive.");
        }
        records.add(new FullTimeEmployee(name, id, salary));
        System.out.println("Full-time Employee added.");
    }

    private static void createPartTime() { 
        System.out.println("\n--- Add Part-time Employee ---");
        String name = inputString("Enter Name: ");
        int id = inputInt("Enter ID: ");
        
        double rate;
        // Validate positive hourly rate
        while (true) {
            rate = inputDouble("Enter Hourly Rate: ");
            if (rate > 0) break;
            System.out.println("Error: Rate must be positive.");
        }
        
        int hours;
        // Validate positive hours worked
        while (true) {
            hours = inputInt("Enter Hours Worked: ");
            if (hours > 0) break;
            System.out.println("Error: Hours must be positive.");
        }
        records.add(new PartTimeEmployee(name, id, rate, hours));
        System.out.println("Part-time Employee added.");
    }

    private static void showAllData() {
        System.out.println("\n--- All Records ---");
        if (records.isEmpty()) {
            System.out.println("No records found.");
        } else {
            // Polymorphism: Calling the overridden displayDetails() for each object
            for (Person p : records) {
                p.displayDetails();
            }
        }
    }

    private static void setupSampleData() {
        // Add 4 sample records as required
        records.add(new Student("Ahmed Ali", 2021001, "CS101", 95.5));
        records.add(new Student("Sara Noor", 2021002, "CS230", 45.0)); 
        records.add(new FullTimeEmployee("Dr. Omar", 101, 15000));
        records.add(new PartTimeEmployee("Eng. Khalid", 102, 100.0, 20));
    }

    // --- Helper Methods for Input ---

    private static String inputString(String msg) {
        System.out.print(msg);
        String txt = userIn.nextLine().trim();
        while (txt.isEmpty()) {
            txt = userIn.nextLine().trim();
        }
        return txt;
    }

    private static int inputInt(String msg) {
        while (true) {
            System.out.print(msg);
            try {
                return Integer.parseInt(userIn.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private static double inputDouble(String msg) {
        while (true) {
            System.out.print(msg);
            try {
                return Double.parseDouble(userIn.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
}

// ==========================================
// Class Definitions (Non-public for single file)
// ==========================================

// 1. Parent Class: Person
abstract class Person {
    protected String name;
    protected int id;

    public Person(String name, int id) {
        this.name = name;
        this.id = id;
    }

    // Abstract method to be implemented by subclasses
    public abstract void displayDetails();
}

// 2. Subclass: Student
class Student extends Person {
    private String course;
    private double grade;

    public Student(String name, int id, String course, double grade) {
        super(name, id);
        this.course = course;
        this.grade = grade;
    }

    // Check if student passed (Grade >= 50)
    public boolean isPass() {
        return grade >= 50;
    }

    @Override
    public void displayDetails() {
        System.out.println("----------------------------------------");
        System.out.println("Type: Student");
        System.out.println("Name: " + name);
        System.out.println("ID: " + id);
        System.out.println("Course: " + course);
        System.out.printf("Grade: %.2f\n", grade);

        if (isPass()) {
            System.out.println("Status: PASS");
        } else {
            System.out.println("Status: FAIL");
        }
        System.out.println("----------------------------------------");
    }
}

// 3. Subclass: Employee (Abstract)
abstract class Employee extends Person {
    public Employee(String name, int id) {
        super(name, id);
    }
    
    // Abstract method for salary calculation
    public abstract double calculateSalary();
}

// 4. Subclass: FullTimeEmployee
class FullTimeEmployee extends Employee {
    private double monthlySalary;

    public FullTimeEmployee(String name, int id, double monthlySalary) {
        super(name, id);
        this.monthlySalary = monthlySalary;
    }

    @Override
    public double calculateSalary() {
        return monthlySalary;
    }

    @Override
    public void displayDetails() {
        System.out.println("----------------------------------------");
        System.out.println("Type: Full-Time Employee");
        System.out.printf("Name: %-15s | ID: %-10d%n", name, id);
        System.out.printf("Monthly Salary: %.2f SAR%n", calculateSalary());
        System.out.println("----------------------------------------");
    }
}

// 5. Subclass: PartTimeEmployee
class PartTimeEmployee extends Employee {
    private double hourlyRate;
    private int hoursWorked;

    public PartTimeEmployee(String name, int id, double hourlyRate, int hoursWorked) {
        super(name, id);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }

    @Override
    public double calculateSalary() {
        return hourlyRate * hoursWorked;
    }

    @Override
    public void displayDetails() {
        System.out.println("----------------------------------------");
        System.out.println("Type: Part-Time Employee");
        System.out.printf("Name: %-15s | ID: %-10d%n", name, id);
        System.out.printf("Rate: %.2f | Hours: %d%n", hourlyRate, hoursWorked);
        System.out.printf("Total Salary: %.2f SAR%n", calculateSalary());
        System.out.println("----------------------------------------");
    }
}
