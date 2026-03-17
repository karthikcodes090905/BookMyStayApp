import java.util.*;

class AddOnService {
    String name;
    double cost;

    AddOnService(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }

    public String toString() {
        return name + " (" + cost + ")";
    }
}

class AddOnServiceManager {
    private Map<String, List<AddOnService>> reservationServices;

    AddOnServiceManager() {
        reservationServices = new HashMap<>();
    }

    public void addService(String reservationId, AddOnService service) {
        reservationServices
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);
    }

    public List<AddOnService> getServices(String reservationId) {
        return reservationServices.getOrDefault(reservationId, new ArrayList<>());
    }

    public double calculateTotalCost(String reservationId) {
        double total = 0;
        List<AddOnService> services = getServices(reservationId);

        for (AddOnService s : services) {
            total += s.getCost();
        }
        return total;
    }
}

public class Main {
    public static void main(String[] args) {
        AddOnServiceManager manager = new AddOnServiceManager();

        String reservationId = "RES123";

        AddOnService breakfast = new AddOnService("Breakfast", 200);
        AddOnService spa = new AddOnService("Spa", 500);
        AddOnService pickup = new AddOnService("Airport Pickup", 300);

        manager.addService(reservationId, breakfast);
        manager.addService(reservationId, spa);
        manager.addService(reservationId, pickup);

        System.out.println("Services for " + reservationId + ":");
        for (AddOnService s : manager.getServices(reservationId)) {
            System.out.println(s);
        }

        System.out.println("Total Add-On Cost: " + manager.calculateTotalCost(reservationId));
    }
}