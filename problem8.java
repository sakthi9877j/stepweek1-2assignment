
public class Problem8 {

    static class ParkingSpot {
        String plate;
        long entryTime;
        boolean occupied;

        ParkingSpot() {
            occupied = false;
        }
    }

    static final int SIZE = 500;
    static ParkingSpot[] lot = new ParkingSpot[SIZE];

    static {
        for (int i = 0; i < SIZE; i++)
            lot[i] = new ParkingSpot();
    }

    static int hash(String plate) {
        return Math.abs(plate.hashCode()) % SIZE;
    }

    public static void parkVehicle(String plate) {

        int index = hash(plate);
        int probes = 0;

        while (lot[index].occupied) {
            index = (index + 1) % SIZE;
            probes++;
        }

        lot[index].plate = plate;
        lot[index].entryTime = System.currentTimeMillis();
        lot[index].occupied = true;

        System.out.println("Assigned spot #" + index + " (" + probes + " probes)");
    }

    public static void exitVehicle(String plate) {

        for (int i = 0; i < SIZE; i++) {

            if (lot[i].occupied && lot[i].plate.equals(plate)) {

                long duration =
                        (System.currentTimeMillis() - lot[i].entryTime) / 60000;

                lot[i].occupied = false;

                System.out.println("Spot #" + i +
                        " freed, Duration: " + duration + " minutes");

                return;
            }
        }

        System.out.println("Vehicle not found");
    }

    public static void getStatistics() {

        int occupied = 0;

        for (ParkingSpot p : lot)
            if (p.occupied)
                occupied++;

        double occupancy = (occupied * 100.0) / SIZE;

        System.out.println("Occupancy: " + occupancy + "%");
    }

    public static void main(String[] args) {

        parkVehicle("ABC1234");
        parkVehicle("ABC1235");
        parkVehicle("XYZ9999");

        exitVehicle("ABC1234");

        getStatistics();
    }
}
