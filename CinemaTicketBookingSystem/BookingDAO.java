import java.sql.*;
import java.util.Scanner;

public class BookingDAO {
    Scanner scanner = new Scanner(System.in);

    public void bookTicket() {
        System.out.print("Enter movie ID: ");
        int movieId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter your name: ");
        String customerName = scanner.nextLine();

        String checkSeats = "SELECT available_seats FROM movies WHERE movie_id = ?";
        String bookSql = "INSERT INTO bookings (movie_id, customer_name) VALUES (?, ?)";
        String updateSeats = "UPDATE movies SET available_seats = available_seats - 1 WHERE movie_id = ?";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement checkStmt = conn.prepareStatement(checkSeats)) {
                checkStmt.setInt(1, movieId);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    int seats = rs.getInt("available_seats");
                    if (seats <= 0) {
                        System.out.println("No seats available!");
                        return;
                    }
                } else {
                    System.out.println("Movie not found.");
                    return;
                }

                try (PreparedStatement bookStmt = conn.prepareStatement(bookSql);
                    PreparedStatement updateStmt = conn.prepareStatement(updateSeats)) {

                    bookStmt.setInt(1, movieId);
                    bookStmt.setString(2, customerName);
                    bookStmt.executeUpdate();

                    updateStmt.setInt(1, movieId);
                    updateStmt.executeUpdate();

                    conn.commit();
                    System.out.println("Ticket booked successfully!");
                }
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewBookings() {
        String sql = "SELECT b.booking_id, m.title, b.customer_name " +
                     "FROM bookings b " +
                     "JOIN movies m ON b.movie_id = m.movie_id";
    
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
    
            System.out.println("\n==== Booking List ====");
    
            boolean hasBookings = false;
    
            while (rs.next()) {
                hasBookings = true;
                System.out.println("ID    : " + rs.getInt("booking_id"));
                System.out.println("Movie : " + rs.getString("title"));
                System.out.println("Name  : " + rs.getString("customer_name"));
                System.out.println("------------------------");
            }
    
            if (!hasBookings) {
                System.out.println("No bookings found.");
            }
    
        } catch (SQLException e) {
            System.out.println("Error fetching bookings: " + e.getMessage());
        }
    }    
}