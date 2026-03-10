/**
 * UC5: Booking Request (First-Come-First-Served)
 * Book My Stay App v5.0
 *
 * Demonstrates handling multiple booking requests fairly using a queue.
 * Requests are queued in arrival order and prepared for allocation.
 *
 * Author: YourName
 * Version: 5.0
 */

import java.util.LinkedList;
import java.util.Queue;

// Reservation class representing a guest booking request
class Reservation {
    private String guestName;
    private String requestedRoomType;

    public Reservation(String guestName, String requestedRoomType) {
        this.guestName = guestName;
        this.requestedRoomType = requestedRoomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRequestedRoomType() {
        return requestedRoomType;
    }

    public void displayRequest() {
        System.out.println("Guest: " + guestName + " | Room Requested: " + requestedRoomType);
    }
}

// Booking Request Queue (FIFO)
class BookingRequestQueue {
    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    // Add a booking request
    public void addRequest(Reservation reservation) {
        requestQueue.add(reservation);
        System.out.println("Booking request added for: " + reservation.getGuestName());
    }

    // View all requests in order
    public void displayAllRequests() {
        System.out.println("\n--- Current Booking Requests (FIFO) ---");
        for (Reservation res : requestQueue) {
            res.displayRequest();
        }
    }

    // Poll (process) the next request in the queue
    public Reservation pollNextRequest() {
        return requestQueue.poll();
    }

    public boolean isEmpty() {
        return requestQueue.isEmpty();
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {
        System.out.println("Welcome to Book My Stay App v5.0\n");

        // Initialize booking request queue
        BookingRequestQueue requestQueue = new BookingRequestQueue();

        // Simulate booking requests from guests
        requestQueue.addRequest(new Reservation("Alice", "Single Room"));
        requestQueue.addRequest(new Reservation("Bob", "Double Room"));
        requestQueue.addRequest(new Reservation("Charlie", "Suite Room"));

        // Display all queued requests
        requestQueue.displayAllRequests();

        System.out.println("\nBooking requests collected. Inventory not updated at this stage.");
    }
}