interface Rentable {
        void calculateRent(int day);
        boolean isAvailable();
}
abstract class Vehicle implements Rentable {
    String id;
    String model;
    double dailyRate;
    boolean status; // true means available, false means rented

    Vehicle(String id, String model, double dailyRate) {
        this.id = id;
        this.model = model;
        this.dailyRate = dailyRate;
        this.status = true; // Available by default
    }

    public boolean isAvailable() {
        return status;
    }
}

class Car extends Vehicle {
    Car(String id, String model, double dailyRate) { 
        super(id, model, dailyRate); 
    }

    public void calculateRent(int days) {
        double cost = dailyRate * days;
        // 3. Made sure this uses 'name' to match your main class variable
        System.out.printf("%s rented Car %s (%s) for %d days = %.2f\n", oopsProject.name, model, id, days, cost);
    }
}
class Bike extends Vehicle {
    Bike(String id, String model, double dailyRate) { 
        super(id, model, dailyRate); 
    }

    public void calculateRent(int days) {
        double cost = dailyRate * days;
        if (days >= 7) {
            cost = cost * 0.90; // 10% discount
        }
        // 3. Fixed 'currentCustomer' to 'name' here too
        System.out.printf("%s rented Bike %s (%s) for %d days = %.2f\n", oopsProject.name, model, id, days, cost);
    }
}

public class oopsProject {
    static String name;

    public static void main(String[] args) {
       name = "Alex";
       Vehicle v1 = new Car("C1", "Ertiga",550.0);
       v1.calculateRent(5);
    }
    
}