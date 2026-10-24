import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    private int rollNo;
    private String name;
    private double marks;

    public Student(int rollNo, String name, double marks) {
        this.rollNo = rollNo;
        this.name = name;
        this.marks = marks;
    }

    public int getRollNo() {
        return rollNo;
    }

    public String getName() {
        return name;
    }

    public double getMarks() {
        return marks;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }

    public void displayStudent() {
        System.out.println("Roll No: " + rollNo + " | Name: " + name + " | Marks: " + marks);
    }
}

public class StudentManagement {
    private static final String FILE_NAME = "students.dat";

    public static void main(String[] args) {
        ArrayList<Student> studentList = loadDataFromFile();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=================================");
            System.out.println("  -- Student Management System --");
            System.out.println("=================================");
            System.out.println("1. Add Student");
            System.out.println("2. Display All Students");
            System.out.println("3. Search Student by Roll No");
            System.out.println("4. Update Student Details");
            System.out.println("5. Delete Student");
            System.out.println("6. Save and Exit");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Roll Number: ");
                    int rollNo = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Marks: ");
                    double marks = scanner.nextDouble();

                    studentList.add(new Student(rollNo, name, marks));
                    System.out.println("Student added successfully!");
                    break;

                case 2:
                    System.out.println("\n-- Student Records --");
                    if (studentList.isEmpty()) {
                        System.out.println("No records found!");
                    } else {
                        for (Student s : studentList) {
                            s.displayStudent();
                        }
                    }
                    break;

                case 3:
                    System.out.print("Enter Roll Number to Search: ");
                    int searchRoll = scanner.nextInt();
                    boolean found = false;
                    for (Student s : studentList) {
                        if (s.getRollNo() == searchRoll) {
                            System.out.println("Record Found:");
                            s.displayStudent();
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Student with Roll No " + searchRoll + " not found!");
                    }
                    break;

                case 4:
                    System.out.print("Enter Roll Number to Update: ");
                    int updateRoll = scanner.nextInt();
                    boolean updated = false;
                    for (Student s : studentList) {
                        if (s.getRollNo() == updateRoll) {
                            scanner.nextLine();
                            System.out.print("Enter New Name: ");
                            String newName = scanner.nextLine();
                            System.out.print("Enter New Marks: ");
                            double newMarks = scanner.nextDouble();

                            s.setName(newName);
                            s.setMarks(newMarks);
                            System.out.println("Student record updated successfully!");
                            updated = true;
                            break;
                        }
                    }
                    if (!updated) {
                        System.out.println("Student with Roll No " + updateRoll + " not found!");
                    }
                    break;

                case 5:
                    System.out.print("Enter Roll Number to Delete: ");
                    int deleteRoll = scanner.nextInt();
                    boolean removed = studentList.removeIf(s -> s.getRollNo() == deleteRoll);
                    if (removed) {
                        System.out.println("Student deleted successfully!");
                    } else {
                        System.out.println("Student with Roll No " + deleteRoll + " not found!");
                    }
                    break;

                case 6:
                    saveDataToFile(studentList);
                    System.out.println("Data saved successfully. Exiting... Goodbye!");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice! Please enter a number between 1 and 6.");
            }
        }
    }

    private static void saveDataToFile(ArrayList<Student> list) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(list);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private static ArrayList<Student> loadDataFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (ArrayList<Student>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}