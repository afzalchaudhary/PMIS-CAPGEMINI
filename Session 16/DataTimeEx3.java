package Day_16;

import java.time.LocalDateTime;

public class DataTimeEx3 {
    public static void main(String[] args) { 
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
        
        LocalDateTime customDate = LocalDateTime.parse("2026-10-26T13:48");
        System.out.println(customDate);
    }
}
