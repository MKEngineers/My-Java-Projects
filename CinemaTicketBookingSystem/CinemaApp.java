import java.util.Scanner;

public class CinemaApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MovieDAO movieDAO = new MovieDAO();
        BookingDAO bookingDAO = new BookingDAO();

        while (true) {
            System.out.println("\n==== Cinema Ticket Booking System ====");
            System.out.println("1. Add Movie");
            System.out.println("2. View Movies");
            System.out.println("3. Book Ticket");
            System.out.println("4. View Bookings");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> movieDAO.addMovie();
                case 2 -> movieDAO.viewMovies();
                case 3 -> bookingDAO.bookTicket();
                case 4 -> bookingDAO.viewBookings();
                case 5 -> {
                    System.out.println("Thank you for using the system!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}