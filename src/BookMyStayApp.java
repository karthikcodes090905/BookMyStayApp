/**
 * UC2: Basic Room Types & Static Availability
 * Book My Stay App v2.1
 *
 * Demonstrates object-oriented modeling with abstract classes, inheritance, and polymorphism.
 * Initializes rooms and prints their details along with static availability.
 *
 * @author YourName
 * @version 2.1
 */

abstract class Room {
    protected String roomType;
    protected int beds;
    protected double price;

    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    // Method to display room details
    public void displayDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Price per night: $" + price);
    }
}

// Concrete room classes
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 50.0);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 90.0);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 150.0);
    }
}

public class BookMyStayApp {

    // Static availability variables
    static int singleRoomAvailable = 5;
    static int doubleRoomAvailable = 3;
    static int suiteRoomAvailable = 2;

    public static void main(String[] args) {

        System.out.println("Welcome to Book My Stay App v2.1\n");

        // Initialize rooms
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Display room details and availability
        System.out.println("--- Room Details ---");
        single.displayDetails();
        System.out.println("Availability: " + singleRoomAvailable + "\n");

        doubleRoom.displayDetails();
        System.out.println("Availability: " + doubleRoomAvailable + "\n");

        suite.displayDetails();
        System.out.println("Availability: " + suiteRoomAvailable + "\n");

        System.out.println("Application execution completed.");
    }
}