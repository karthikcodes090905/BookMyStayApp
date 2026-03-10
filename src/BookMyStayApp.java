/**
 * UC3: Centralized Room Inventory Management
 * Book My Stay App v3.1
 *
 * Demonstrates centralized inventory using HashMap for room availability.
 * Provides controlled access to room counts and ensures consistent system state.
 *
 * Author: YourName
 * Version: 3.1
 */

import java.util.HashMap;
import java.util.Map;

// Abstract Room class (reused from UC2)
abstract class Room {
    protected String roomType;
    protected int beds;
    protected double price;

    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    public void displayDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Price per night: $" + price);
    }
}

// Concrete room classes
class SingleRoom extends Room {
    public SingleRoom() { super("Single Room", 1, 50.0); }
}

class DoubleRoom extends Room {
    public DoubleRoom() { super("Double Room", 2, 90.0); }
}

class SuiteRoom extends Room {
    public SuiteRoom() { super("Suite Room", 3, 150.0); }
}

// Centralized inventory manager
class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    // Register room type with availability
    public void addRoomType(String roomType, int count) {
        inventory.put(roomType, count);
    }

    // Get current availability
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability after booking
    public void updateAvailability(String roomType, int change) {
        inventory.put(roomType, getAvailability(roomType) + change);
    }

    // Display all inventory
    public void displayInventory() {
        System.out.println("--- Current Room Inventory ---");
        for (String type : inventory.keySet()) {
            System.out.println(type + " : " + inventory.get(type) + " rooms available");
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Welcome to Book My Stay App v3.1\n");

        // Initialize rooms
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Display room details
        single.displayDetails();
        System.out.println();
        doubleRoom.displayDetails();
        System.out.println();
        suite.displayDetails();
        System.out.println();

        // Initialize centralized inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single Room", 5);
        inventory.addRoomType("Double Room", 3);
        inventory.addRoomType("Suite Room", 2);

        // Display inventory
        inventory.displayInventory();

        // Example of booking a room
        System.out.println("\nBooking 1 Single Room...");
        inventory.updateAvailability("Single Room", -1);
        inventory.displayInventory();

        System.out.println("\nApplication execution completed.");
    }
}