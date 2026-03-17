import java.util.*;
import java.util.concurrent.*;

class InventoryService {
    private static Map<String, Integer> roomInventory = new HashMap<>();

    public static void addRoomType(String roomId, int count) {
        roomInventory.put(roomId, count);
    }

    // synchronized to ensure thread safety
    public static synchronized boolean allocateRoom(String roomId) {
        int count = roomInventory.getOrDefault(roomId, 0);
        if (count > 0) {
            roomInventory.put(roomId, count - 1);
            System.out.println(Thread.currentThread().getName() + " allocated room " + roomId);
            return true;
        } else {
            System.out.println(Thread.currentThread().getName() + " failed to allocate room " + roomId);
            return false;
        }
    }

    public static synchronized void releaseRoom(String roomId) {
        roomInventory.put(roomId, roomInventory.getOrDefault(roomId, 0) + 1);
        System.out.println(Thread.currentThread().getName() + " released room " + roomId);
    }
}

class BookingRequest {
    String guestName;
    String roomId;

    public BookingRequest(String guestName, String roomId) {
        this.guestName = guestName;
        this.roomId = roomId;
    }
}

class BookingProcessor implements Runnable {
    private BlockingQueue<BookingRequest> queue;

    public BookingProcessor(BlockingQueue<BookingRequest> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (!queue.isEmpty()) {
            try {
                BookingRequest request = queue.poll(1, TimeUnit.SECONDS);
                if (request != null) {
                    // Critical section: allocate room
                    boolean success = InventoryService.allocateRoom(request.roomId);
                    if (!success) {
                        System.out.println(request.guestName + " could not book room " + request.roomId);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class UC11ConcurrentBookingDemo {
    public static void main(String[] args) throws InterruptedException {
        // Setup inventory
        InventoryService.addRoomType("R201", 2);
        InventoryService.addRoomType("R202", 1);

        // Create booking requests
        BlockingQueue<BookingRequest> bookingQueue = new LinkedBlockingQueue<>();
        bookingQueue.add(new BookingRequest("Guest1", "R201"));
        bookingQueue.add(new BookingRequest("Guest2", "R201"));
        bookingQueue.add(new BookingRequest("Guest3", "R201")); // should fail due to limited rooms
        bookingQueue.add(new BookingRequest("Guest4", "R202"));
        bookingQueue.add(new BookingRequest("Guest5", "R202")); // should fail

        // Start multiple threads to process bookings concurrently
        Thread t1 = new Thread(new BookingProcessor(bookingQueue), "Thread-1");
        Thread t2 = new Thread(new BookingProcessor(bookingQueue), "Thread-2");
        Thread t3 = new Thread(new BookingProcessor(bookingQueue), "Thread-3");

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("All booking requests processed.");
    }
}