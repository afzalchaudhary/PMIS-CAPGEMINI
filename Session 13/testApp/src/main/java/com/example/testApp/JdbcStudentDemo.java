package com.example.testApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcStudentDemo {

    private static final String URL = "jdbc:mysql://localhost:3306/java_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "afzal";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static void runDemo() {

        System.out.println(" STARTING JDBC APPROACH DEMO");
       

        try (Connection conn = getConnection()) {
            System.out.println("[JDBC] Successfully connected to MySQL database: " + conn.getCatalog());

            // 1. Ensure table and columns exist
            ensureSchema(conn);

            // 2. Clean up previous demo data
            cleanDemoData(conn);

            // 3. INSERT (Create)
            long aliceId = insertStudent(conn, "Alice Johnson", "alice.jdbc@example.com", "Computer Science");
            long bobId = insertStudent(conn, "Bob Smith", "bob.jdbc@example.com", "Data Science");

            // 4. SELECT (Read)
            System.out.println("\n[JDBC] Fetching all students after insertion:");
            fetchAllStudents(conn);

            // 5. UPDATE
            System.out.println("\n[JDBC] Updating Alice's course to 'Artificial Intelligence'...");
            updateStudentCourse(conn, aliceId, "Artificial Intelligence");

            // 6. SELECT by ID
            System.out.println("[JDBC] Fetching Alice after update:");
            fetchStudentById(conn, aliceId);

            // 7. DELETE
            System.out.println("\n[JDBC] Deleting Bob (ID: " + bobId + ")...");
            deleteStudent(conn, bobId);

            // 8. SELECT all remaining
            System.out.println("[JDBC] Final student list:");
            fetchAllStudents(conn);

            System.out.println("\n>>> [JDBC] Demo completed successfully!\n");

        } catch (SQLException e) {
            System.err.println("[JDBC Error] Database operation failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void ensureSchema(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS students (" +
                     "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                     "name VARCHAR(100) NOT NULL, " +
                     "email VARCHAR(100) NOT NULL, " +
                     "course VARCHAR(100)" +
                     ")";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("[JDBC] Ensured table 'students' exists.");
        }

        // Ensure missing columns exist in case table was created previously with older schema
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("ALTER TABLE students ADD COLUMN IF NOT EXISTS email VARCHAR(100)");
            stmt.executeUpdate("ALTER TABLE students ADD COLUMN IF NOT EXISTS course VARCHAR(100)");
        } catch (SQLException ignored) {
            // Supported on modern MySQL; if older MySQL ignores IF NOT EXISTS, schema is already updated
        }
    }

    private static void cleanDemoData(Connection conn) throws SQLException {
        String sql = "DELETE FROM students WHERE email LIKE '%@example.com'";
        try (Statement stmt = conn.createStatement()) {
            int deleted = stmt.executeUpdate(sql);
            if (deleted > 0) {
                System.out.println("[JDBC] Cleaned up " + deleted + " old demo student row(s).");
            }
        }
    }

    private static long insertStudent(Connection conn, String name, String email, String course) throws SQLException {
        String sql = "INSERT INTO students (name, email, course) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, course);
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    long generatedId = rs.getLong(1);
                    System.out.println("[JDBC] Inserted: " + name + " (Generated ID: " + generatedId + ")");
                    return generatedId;
                }
            }
        }
        return -1;
    }

    private static void fetchAllStudents(Connection conn) throws SQLException {
        String sql = "SELECT id, name, email, course FROM students";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.printf("  -> Student [ID=%d, Name='%s', Email='%s', Course='%s']%n",
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("course"));
            }
        }
    }

    private static void fetchStudentById(Connection conn, long id) throws SQLException {
        String sql = "SELECT id, name, email, course FROM students WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    System.out.printf("  -> Found: [ID=%d, Name='%s', Email='%s', Course='%s']%n",
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("course"));
                } else {
                    System.out.println("  -> No student found with ID: " + id);
                }
            }
        }
    }

    private static void updateStudentCourse(Connection conn, long id, String newCourse) throws SQLException {
        String sql = "UPDATE students SET course = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newCourse);
            pstmt.setLong(2, id);
            int rows = pstmt.executeUpdate();
            System.out.println("[JDBC] Rows updated: " + rows);
        }
    }

    private static void deleteStudent(Connection conn, long id) throws SQLException {
        String sql = "DELETE FROM students WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            int rows = pstmt.executeUpdate();
            System.out.println("[JDBC] Rows deleted: " + rows);
        }
    }

    public static void main(String[] args) {
        try {
            runDemo();
        } finally {
            com.mysql.cj.jdbc.AbandonedConnectionCleanupThread.checkedShutdown();
        }
    }
}