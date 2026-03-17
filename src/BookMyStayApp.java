import java.util.*;

class Booking {
    String bookingId;
    List<String> allocatedRooms;
    String status; // "CONFIRMED" or "CANCELLED"

    public Booking(String bookingId, List<String> allocatedRooms) {
        this.bookingId = bookingId;
        this.allocatedRooms = new ArrayList<>(allocatedRooms);
        this.status = "CONFIRMED";
    }
}

class InventoryService {
    private static Map<String, Integer> roomInventory = new HashMap<>();

    public static void addRoomType(String roomId, int count) {
        roomInventory.put(roomId, count);
    }

    public static void increment(String roomId) {
        roomInventory.put(roomId, roomInventory.getOrDefault(roomId, 0) + 1);
        System.out.println("Inventory updated for room " + roomId + ": " + roomInventory.get(roomId));
    }
}

class BookingService {
    private static Map<String, Booking> bookings = new HashMap<>();

    public static void addBooking(Booking booking) {
        bookings.put(booking.bookingId, booking);
    }

    public static Booking getBooking(String bookingId) {
        return bookings.get(bookingId);
    }

    public static void updateBooking(Booking booking) {
        bookings.put(booking.bookingId, booking);
        System.out.println("Booking " + booking.bookingId + " status updated to " + booking.status);
    }
}

class CancellationService {
    private Stack<String> rollbackStack = new Stack<>();

    public void cancelBooking(String bookingId) throws Exception {
        Booking booking = BookingService.getBooking(bookingId);

        if (booking == null || "CANCELLED".equals(booking.status)) {
            throw new Exception("Invalid or already cancelled booking: " + bookingId);
        }

        // Step 1: Push allocated rooms to rollback stack
        for (String roomId : booking.allocatedRooms) {
            rollbackStack.push(roomId);
        }

        // Step 2: Restore inventory
        while (!rollbackStack.isEmpty()) {
            String roomId = rollbackStack.pop();
            InventoryService.increment(roomId);
        }

        // Step 3: Update booking status
        booking.status = "CANCELLED";
        BookingService.updateBooking(booking);

        System.out.println("Cancellation successful for booking " + bookingId);
    }
}

// Demo
public class UC10BookingCancellationDemo {
    public static void main(String[] args) throws Exception {
        InventoryService.addRoomType("R101", 2);
        InventoryService.addRoomType("R102", 1);

        Booking b1 = new Booking("B001", Arrays.asList("R101", "R102"));
        BookingService.addBooking(b1);

        CancellationService cancellationService = new CancellationService();
        cancellationService.cancelBooking("B001");
    }
}