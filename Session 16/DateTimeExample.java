package Day_16;

import java.time.LocalDate;
import java.time.Month;

public class DateTimeExample {
    public static void main(String[] args) {
        // Fetches the current date
        LocalDate now = LocalDate.now();
        System.out.println("Todays date is: " + now);
        
        // Extracts individual date components
        int dayOfMonth = now.getDayOfMonth();
        Month month = now.getMonth();
        int year = now.getYear();
        
        System.out.println("Day: " + dayOfMonth);
        System.out.println("Month: " + month);
        System.out.println("Year: " + year);
    }
}


