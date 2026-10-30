package Day_16;

import java.time.LocalDate;

public class DateTimeExample2 {
    public static void main(String[] args) {
        // 1. Get the current system date
        LocalDate now = LocalDate.now();

        // 2. Create a specific custom date (December 31, 2026)
        LocalDate customDate = LocalDate.of(2026, 12, 31);
        System.out.println("Custom Date: " + customDate);

        // 3. Extract individual date components from 'now'
        int dayOfMonth = now.getDayOfMonth();
        int month = now.getMonthValue(); // Returns 1-12 as an int
        int year = now.getYear();

        // 4. Corrected Print Statements
        System.out.println("Day: " + dayOfMonth);
        System.out.println("Month: " + month);
        System.out.println("Year: " + year);
    }
}
