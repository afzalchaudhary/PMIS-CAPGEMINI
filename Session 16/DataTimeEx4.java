package Day_16;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Set;

public class DataTimeEx4 {
    public static void main(String[] args) { 
        ZonedDateTime now = ZonedDateTime.now();
        //System.out.println(now);
        
        Set<String> availableZones = ZoneId.getAvailableZoneIds();
        availableZones.forEach(System.out::println);
    }
}
