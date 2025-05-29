import java.util.*;

class Room {
    int roomNumber;
    String type;
    double price;
    boolean isBooked;

    public Room(int roomNumber, String type, double price) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.price = price;
        this.isBooked = false;
    }
}

class Booking {
    String customerName;
    Room room;
    String checkInDate;
    String checkOutDate;

    public Booking(String customerName, Room room, String checkInDate, String checkOutDate) {
        this.customerName = customerName;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public void printDetails() {
        System.out.println("\n--- Booking Details ---");
        System.out.println("Name: " + customerName);
        System.out.println("Room: " + room.type + " (#" + room.roomNumber + ")");
        System.out.println("Price: $" + room.price);
        System.out.println("Check-in: " + checkInDate);
        System.out.println("Check-out: " + checkOutDate);
    }
}

public class HotelReservationSystem {

    static List<Room> rooms = new ArrayList<>();
    static List<Booking> bookings = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeRooms();

        while (true) {
            System.out.println("\n=== Hotel Reservation System ===");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Make a Reservation");
            System.out.println("3. View Bookings");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> viewAvailableRooms();
                case 2 -> makeReservation();
                case 3 -> viewBookings();
                case 4 -> {
                    System.out.println("Thank you for using the system. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    static void initializeRooms() {
        rooms.add(new Room(101, "Single", 100.0));
        rooms.add(new Room(102, "Double", 150.0));
        rooms.add(new Room(103, "Suite", 250.0));
        rooms.add(new Room(104, "Single", 100.0));
        rooms.add(new Room(105, "Double", 150.0));
    }

    static void viewAvailableRooms() {
        System.out.println("\n--- Available Rooms ---");
        for (Room room : rooms) {
            if (!room.isBooked) {
                System.out.printf("Room #%d (%s) - $%.2f%n", room.roomNumber, room.type, room.price);
            }
        }
    }

    static void makeReservation() {
        viewAvailableRooms();
        System.out.print("\nEnter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter desired room number: ");
        int roomNum = scanner.nextInt();
        scanner.nextLine();

        Room selectedRoom = null;
        for (Room room : rooms) {
            if (room.roomNumber == roomNum && !room.isBooked) {
                selectedRoom = room;
                break;
            }
        }

        if (selectedRoom == null) {
            System.out.println("Room is not available.");
            return;
        }

        System.out.print("Enter check-in date (e.g., 2025-06-01): ");
        String checkIn = scanner.nextLine();
        System.out.print("Enter check-out date (e.g., 2025-06-05): ");
        String checkOut = scanner.nextLine();

        // Simulate payment processing
        System.out.print("Enter card number (dummy): ");
        String card = scanner.nextLine();
        System.out.println("Processing payment...");
        System.out.println("Payment successful!");

        selectedRoom.isBooked = true;
        Booking booking = new Booking(name, selectedRoom, checkIn, checkOut);
        bookings.add(booking);
        booking.printDetails();
    }

    static void viewBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No bookings yet.");
        } else {
            for (Booking booking : bookings) {
                booking.printDetails();
            }
        }
    }
}
