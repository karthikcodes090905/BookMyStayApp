/**
 * UC4: Room Search & Availability Check
 * Book My Stay App v4.0
 *
 * Demonstrates read-only access to centralized room inventory.
 * Displays available room types and their details without modifying inventory.
 *
 * Author: YourName
 * Version: 4.0
 */

import java.util.HashMap;
import java.util.Map;

// Abstract Room class (reused from previous UCs)
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

    public String getRoomType() {
        return roomType;
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

// Centralized inventory (read-only access for search)
class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void addRoomType(String roomType, int count) {
        inventory.put(roomType, count);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public Map<String, Integer> getAllAvailability() {
        // Return a copy to prevent modification
        return new HashMap<>(inventory);
    }
}

// Search service to display available rooms
class RoomSearchService {
    private RoomInventory inventory;

    public RoomSearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void displayAvailableRooms(Room[] rooms) {
        System.out.println("--- Available Rooms ---");
        for (Room room : rooms) {
            int available = inventory.getAvailability(room.getRoomType());
            if (available > 0) { // show only available rooms
                room.displayDetails();
                System.out.println("Availability: " + available + "\n");
            }
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Welcome to Book My Stay App v4.0\n");

        // Initialize rooms
        Room[] rooms = { new SingleRoom(), new DoubleRoom(), new SuiteRoom() };

        // Initialize centralized inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single Room", 5);
        inventory.addRoomType("Double Room", 3);
        inventory.addRoomType("Suite Room", 0); // simulate fully booked suite

        // Search service (read-only)
        RoomSearchService searchService = new RoomSearchService(inventory);

        // Display only rooms with availability > 0
        searchService.displayAvailableRooms(rooms);

        System.out.println("Room search completed. Inventory not modified.");
    }
}