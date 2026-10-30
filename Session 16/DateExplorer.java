package Day_16;

// Create a Java program called DateExplorer that asks the user to enter a date in the format yyyy-MM-dd. The program should then display a menu that allows the user to explore different details about that date.

// For example, the user enters 2026-10-26 and gets the following menu:

// Requirements
// 1. Ask the user to input a date using Scanner.
// 2. Use LocalDate.parse() to convert the input into a LocalDate object.
// 3. Use a switch statement to handle the menu options.
// 4. Use minusMonths() to look back by a number of months specified by the user.
// 5. Use getMonth(), getYear(), and getDayOfWeek() to display date information.
// 6. Use an if-else statement to check whether the entered year is a leap year.
// 7. Use a loop to keep displaying the menu until the user chooses Exit.
// 8. Create a DateExplorer class with methods such as displayMonth(), displayYear(), and lookBackMonths().

import java.time.LocalDate;
import java.time.Month;
import java.time.DayOfWeek;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class DateExplorer {

    private LocalDate date;
    private Scanner scanner;

    public DateExplorer(LocalDate date, Scanner scanner) {
        this.date = date;
        this.scanner = scanner;
    }

    public void displayMonth() {
        Month month = date.getMonth();
        System.out.println("Month: " + month);
    }

    public void displayYear() {
        int year = date.getYear();
        System.out.println("Year: " + year);
    }

    public void displayDayOfWeek() {
        DayOfWeek day = date.getDayOfWeek();
        System.out.println("Day of the week: " + day);
    }

    public void checkLeapYear() {
        int year = date.getYear();

        if (date.isLeapYear()) {
            System.out.println(year + " is a leap year.");
        } else {
            System.out.println(year + " is not a leap year.");
        }
    }

    public void lookBackMonths() {
        System.out.print("Enter the number of months to look back: ");
        int months = scanner.nextInt();

        if (months < 0) {
            System.out.println("Please enter a non-negative number.");
            return;
        }

        LocalDate previousDate = date.minusMonths(months);

        System.out.println("Original date: " + date);
        System.out.println("Date after looking back " + months
                + " months: " + previousDate);
    }

    public void displayMenu() {
        int choice;

        do {
            System.out.println("\n===== Date Explorer =====");
            System.out.println("1. Display Month");
            System.out.println("2. Display Year");
            System.out.println("3. Display Day of the Week");
            System.out.println("4. Check Leap Year");
            System.out.println("5. Look Back by Months");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    displayMonth();
                    break;

                case 2:
                    displayYear();
                    break;

                case 3:
                    displayDayOfWeek();
                    break;

                case 4:
                    checkLeapYear();
                    break;

                case 5:
                    lookBackMonths();
                    break;

                case 6:
                    System.out.println("Exiting Date Explorer. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 6);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter a date (yyyy-MM-dd): ");
            String input = scanner.nextLine();

            LocalDate date = LocalDate.parse(input);

            DateExplorer explorer = new DateExplorer(date, scanner);

            System.out.println("Date entered: " + date);
            explorer.displayMenu();

        } catch (DateTimeParseException e) {
            System.out.println("Invalid date. Please use yyyy-MM-dd.");
        } finally {
            scanner.close();
        }
    }
}
