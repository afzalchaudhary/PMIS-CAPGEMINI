package Day_16;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Scanner;

public class DataTimeEx5 {
    public static void main(String[] args) { 
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter a time zone (eg. Asia/Tokyo): ");
        String zoneInput = scanner.nextLine();
        
        try {
            ZoneId zone = ZoneId.of(zoneInput);
            ZonedDateTime currentTime = ZonedDateTime.now(zone);
            System.out.println("Time zone: " + zone);
            System.out.println("Current date and time: " + currentTime);
            
        } catch (java.time.DateTimeException e) {
            System.out.println("Invalid time zone! Please enter a valid zone ID.");
        }
        
        scanner.close();
    }
}


