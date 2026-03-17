import java.util.*;

// Custom Exception
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Validator class
class BookingValidator {

    private static final List<String> validRoomTypes =
            Arrays.asList("Standard", "Deluxe", "Suite");

    // Validate booking
    public static void validate(String roomType, int availableRooms)
            throws InvalidBookingException {

        // Validate room type
        if (!validRoomTypes.contains(roomType)) {
            throw new InvalidBookingException("Invalid room type selected!");
        }

        // Validate availability
        if (availableRooms <= 0) {
            throw new InvalidBookingException("No rooms available!");
        }
    }
}

// Booking Service
class BookingService {

    private Map<String, Integer> inventory = new HashMap<>();

    public BookingService() {
        inventory.put("Standard", 2);
        inventory.put("Deluxe", 1);
        inventory.put("Suite", 0);
    }

    public void bookRoom(String roomType) {
        try {
            int available = inventory.getOrDefault(roomType, 0);

            // Validate before booking (Fail-Fast)
            BookingValidator.validate(roomType, available);

            // Process booking
            inventory.put(roomType, available - 1);
            System.out.println("Booking successful for " + roomType);

        } catch (InvalidBookingException e) {
            // Graceful failure
            System.out.println("Booking Failed: " + e.getMessage());
        }
    }
}

// Main class
public class BookMyStayApp {
    public static void main(String[] args) {

        BookingService service = new BookingService();

        // Valid booking
        service.bookRoom("Standard");

        // Invalid room type
        service.bookRoom("Luxury");

        // No availability
        service.bookRoom("Suite");

        // System still runs
        service.bookRoom("Deluxe");
    }
}