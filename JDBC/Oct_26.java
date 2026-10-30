
package JDBC;

import java.sql.*;
import java.util.Scanner;

public class Oct_26 {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/Management";
        String username = "root";
        String password = "afzal";

        try (
                Connection conn = DriverManager.getConnection(
                        url, username, password
                );
                Scanner sc = new Scanner(System.in)
        ) {
            System.out.println("Connection Established Successfully!");

            while (true) {
                System.out.println("\n===== EMPLOYEE MANAGEMENT =====");
                System.out.println("1. Insert Employee");
                System.out.println("2. Display Employees");
                System.out.println("3. Update Employee");
                System.out.println("4. Delete Employee");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");

                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                        insertEmployee(conn, sc);
                        break;

                    case 2:
                        displayEmployees(conn);
                        break;

                    case 3:
                        updateEmployee(conn, sc);
                        break;

                    case 4:
                        deleteEmployee(conn, sc);
                        break;

                    case 5:
                        System.out.println("Program Closed.");
                        return;

                    default:
                        System.out.println("Invalid choice!");
                }
            }

        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    // CREATE: Insert an employee
    static void insertEmployee(Connection conn, Scanner sc)
            throws SQLException {

        String query = "INSERT INTO employees "
                + "(name, email, phone_No, salary) "
                + "VALUES (?, ?, ?, ?)";

        System.out.print("Enter name: ");
        String name = sc.nextLine();

        System.out.print("Enter email: ");
        String email = sc.nextLine();

        System.out.print("Enter phone number: ");
        long phone = sc.nextLong();

        System.out.print("Enter salary: ");
        double salary = sc.nextDouble();
        sc.nextLine();

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setLong(3, phone);
            ps.setDouble(4, salary);

            int rows = ps.executeUpdate();
            System.out.println(rows + " employee inserted!");
        }
    }

    // READ: Display all employees
    static void displayEmployees(Connection conn)
            throws SQLException {

        String query = "SELECT * FROM employees";

        try (
                Statement stm = conn.createStatement();
                ResultSet rst = stm.executeQuery(query)
        ) {
            while (rst.next()) {
                System.out.println("\n========================");
                System.out.println("ID: " + rst.getInt("id"));
                System.out.println("Name: " + rst.getString("name"));
                System.out.println("Email: " + rst.getString("email"));
                System.out.println("Phone: " + rst.getLong("phone_No"));
                System.out.println("Salary: " + rst.getDouble("salary"));
                System.out.println("========================");
            }
        }
    }

    // UPDATE: Modify employee details using ID
    static void updateEmployee(Connection conn, Scanner sc)
            throws SQLException {

        String query = "UPDATE employees SET salary = ? WHERE id = ?";

        System.out.print("Enter employee ID: ");
        int id = sc.nextInt();

        System.out.print("Enter new salary: ");
        double salary = sc.nextDouble();
        sc.nextLine();

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setDouble(1, salary);
            ps.setInt(2, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Employee updated successfully!");
            } else {
                System.out.println("Employee ID not found.");
            }
        }
    }

    // DELETE: Remove employee using ID
    static void deleteEmployee(Connection conn, Scanner sc)
            throws SQLException {

        String query = "DELETE FROM employees WHERE id = ?";

        System.out.print("Enter employee ID to delete: ");
        int id = sc.nextInt();
        sc.nextLine();

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Employee deleted successfully!");
            } else {
                System.out.println("Employee ID not found.");
            }
        }
    }
}
