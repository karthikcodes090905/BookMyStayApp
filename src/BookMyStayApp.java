/**
 * UC6: Reservation Confirmation & Room Allocation
 * Book My Stay App v6.0
 *
 * Confirms booking requests by assigning rooms while preventing double-booking.
 * Demonstrates safe inventory updates and unique room ID tracking.
 *
 * Author: YourName
 * Version: 6.0
 */

import java.util.*;

class Reservation {
    private String guestName;
    private String requestedRoomType;
    private String assignedRoomID;

    public Reservation(String guestName, String requestedRoomType) {
        this.guestName = guestName;
        this.requestedRoomType = requestedRoomType;
        this.assignedRoomID = null; // Assigned during allocation
    }

    public String getGuestName() { return guestName; }
    public String getRequestedRoomType() { return requestedRoomType; }

    public void setAssignedRoomID(String roomID) { this.assignedRoomID = roomID; }
    public String getAssignedRoomID() { return assignedRoomID; }

    public void displayReservation() {
        System.out.println("Guest: " + guestName
                + " | Room Type: " + requestedRoomType
                + " | Assigned Room ID: " + assignedRoomID);
    }
}

// Centralized Inventory
class Inventory {
    private Map<String, Integer> roomAvailability;

    public Inventory() {
        roomAvailability = new HashMap<>();
        roomAvailability.put("Single", 2);
        roomAvailability.put("Double", 2);
        roomAvailability.put("Suite", 1);
    }

    public boolean isAvailable(String roomType) {
        return roomAvailability.getOrDefault(roomType, 0) > 0;
    }

    public void decrement(String roomType) {
        roomAvailability.put(roomType, roomAvailability.get(roomType) - 1);
    }

    public void displayInventory() {
        System.out.println("\n--- Current Inventory ---");
        for (String type : roomAvailability.keySet()) {
            System.out.println(type + ": " + roomAvailability.get(type) + " rooms available");
        }
    }
}

// Booking Service: Confirms reservations safely
class BookingService {
    private Queue<Reservation> requestQueue;
    private Inventory inventory;
    private Map<String, Set<String>> allocatedRooms; // Tracks assigned room IDs
    private int roomCounter; // For unique IDs

    public BookingService(Queue<Reservation> requestQueue, Inventory inventory) {
        this.requestQueue = requestQueue;
        this.inventory = inventory;
        this.allocatedRooms = new HashMap<>();
        this.roomCounter = 100; // Start room IDs from 100
    }

    public void processBookings() {
        while (!requestQueue.isEmpty()) {
            Reservation res = requestQueue.poll();
            String type = res.getRequestedRoomType();

            if (inventory.isAvailable(type)) {
                // Generate unique room ID
                String roomID = type.substring(0,1).toUpperCase() + roomCounter++;

                // Record allocation
                allocatedRooms.putIfAbsent(type, new HashSet<>());
                allocatedRooms.get(type).add(roomID);

                // Assign room and update inventory
                res.setAssignedRoomID(roomID);
                inventory.decrement(type);

                System.out.println("Reservation confirmed for " + res.getGuestName());
            } else {
                System.out.println("No available rooms for " + res.getGuestName() + " (" + type + ")");
            }
        }
    }

    public void displayAllocatedRooms() {
        System.out.println("\n--- Allocated Rooms ---");
        for (String type : allocatedRooms.keySet()) {
            System.out.println(type + ": " + allocatedRooms.get(type));
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {
        System.out.println("Welcome to Book My Stay App v6.0\n");

        // Step 1: Initialize inventory
        Inventory inventory = new Inventory();

        // Step 2: Create booking request queue (simulating UC5 requests)
        Queue<Reservation> bookingRequests = new LinkedList<>();
        bookingRequests.add(new Reservation("Alice", "Single"));
        bookingRequests.add(new Reservation("Bob", "Double"));
        bookingRequests.add(new Reservation("Charlie", "Suite"));
        bookingRequests.add(new Reservation("Diana", "Single"));

        // Step 3: Process bookings
        BookingService bookingService = new BookingService(bookingRequests, inventory);
        bookingService.processBookings();

        // Step 4: Display allocated rooms
        bookingService.displayAllocatedRooms();

        // Step 5: Show updated inventory
        inventory.displayInventory();
    }
}