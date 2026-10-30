package Day_16;

import java.time.LocalDate;

class DateTimeCalculations {
    public static void main(String[] args) {
        // 1. Get current date
        LocalDate now = LocalDate.now();
        System.out.println("Today: " + now);

        // 2. Subtract 1 day
        LocalDate yesterday = now.minusDays(1);
        System.out.println("Yesterday was: " + yesterday);

        // 3. Subtract 100 months
        LocalDate pastDate = now.minusMonths(100);
        System.out.println("100 months before was: " + pastDate);
    }
}


