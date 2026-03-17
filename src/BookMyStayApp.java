import java.util.*;

// Reservation class
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// Booking History (stores confirmed bookings)
class BookingHistory {
    private List<Reservation> history = new ArrayList<>();

    // Add confirmed booking
    public void addReservation(Reservation r) {
        history.add(r);
    }

    // Get all bookings
    public List<Reservation> getAllReservations() {
        return history;
    }
}

// Reporting Service
class BookingReportService {

    // Display all bookings
    public void showAllBookings(List<Reservation> reservations) {
        for (Reservation r : reservations) {
            System.out.println(r.getReservationId() + " | " +
                    r.getGuestName() + " | " +
                    r.getRoomType());
        }
    }

    // Generate summary
    public void generateSummary(List<Reservation> reservations) {
        System.out.println("Total Bookings: " + reservations.size());
    }
}

// Main class
public class BookMyStayApp {
    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // Simulating confirmed bookings
        history.addReservation(new Reservation("R101", "Karthik", "Deluxe"));
        history.addReservation(new Reservation("R102", "Arun", "Suite"));
        history.addReservation(new Reservation("R103", "Priya", "Standard"));

        // Admin views all bookings
        System.out.println("Booking History:");
        reportService.showAllBookings(history.getAllReservations());

        // Generate report
        reportService.generateSummary(history.getAllReservations());
    }
}