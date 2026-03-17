import java.io.*;
import java.util.*;

// Booking class
class Booking implements Serializable {
    String bookingId;
    String roomId;
    String status;

    public Booking(String bookingId, String roomId, String status) {
        this.bookingId = bookingId;
        this.roomId = roomId;
        this.status = status;
    }
}

// Inventory service with persistence
class InventoryService implements Serializable {
    private Map<String, Integer> roomInventory = new HashMap<>();

    public void addRoom(String roomId, int count) {
        roomInventory.put(roomId, count);
    }

    public void increment(String roomId) {
        roomInventory.put(roomId, roomInventory.getOrDefault(roomId, 0) + 1);
    }

    public boolean allocate(String roomId) {
        int count = roomInventory.getOrDefault(roomId, 0);
        if (count > 0) {
            roomInventory.put(roomId, count - 1);
            return true;
        }
        return false;
    }

    public Map<String, Integer> getInventory() {
        return roomInventory;
    }
}

// Persistence service
class PersistenceService {
    private static final String FILE_NAME = "system_state.dat";

    public static void saveState(InventoryService inventory, List<Booking> bookings) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(inventory);
            oos.writeObject(bookings);
            System.out.println("System state saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object[] loadState() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            InventoryService inventory = (InventoryService) ois.readObject();
            List<Booking> bookings = (List<Booking>) ois.readObject();
            System.out.println("System state restored successfully.");
            return new Object[]{inventory, bookings};
        } catch (FileNotFoundException e) {
            System.out.println("No previous state found. Starting fresh.");
            return null;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}

// Demo
public class UC12PersistenceRecoveryDemo {
    public static void main(String[] args) {
        Object[] restoredState = PersistenceService.loadState();
        InventoryService inventory;
        List<Booking> bookings;

        if (restoredState != null) {
            inventory = (InventoryService) restoredState[0];
            bookings = (List<Booking>) restoredState[1];
        } else {
            inventory = new InventoryService();
            bookings = new ArrayList<>();
            inventory.addRoom("R401", 2);
            inventory.addRoom("R402", 1);
        }

        // Simulate new bookings
        Booking b1 = new Booking("B201", "R401", "CONFIRMED");
        if (inventory.allocate(b1.roomId)) bookings.add(b1);

        Booking b2 = new Booking("B202", "R402", "CONFIRMED");
        if (inventory.allocate(b2.roomId)) bookings.add(b2);

        // Save system state before shutdown
        PersistenceService.saveState(inventory, bookings);

        // Display current state
        System.out.println("Current Inventory: " + inventory.getInventory());
        System.out.println("Bookings:");
        bookings.forEach(b -> System.out.println(b.bookingId + " - " + b.roomId + " - " + b.status));
    }
}