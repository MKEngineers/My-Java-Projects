import java.sql.*;
import java.util.Scanner;

public class MovieDAO {
    private final Scanner scanner = new Scanner(System.in);

    // ✅ Method to add a new movie
    public void addMovie() {
        System.out.print("Enter movie title: ");
        String title = scanner.nextLine().trim();

        // 🔒 Validate title
        if (title.isEmpty()) {
            System.out.println(" Movie title cannot be empty. Movie not added.");
            return;
        }

        int seats = 0;
        boolean validInput = false;

        // 🎫 Validate number of seats
        while (!validInput) {
            System.out.print("Enter available seats: ");
            try {
                seats = Integer.parseInt(scanner.nextLine().trim());
                if (seats > 0) {
                    validInput = true;
                } else {
                    System.out.println(" Seats must be a positive number.");
                }
            } catch (NumberFormatException e) {
                System.out.println(" Invalid input. Please enter a valid integer.");
            }
        }

        String sql = "INSERT INTO movies (title, available_seats) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            if (conn == null) 
            {
                System.out.println(" Could not connect to database. Movie not added.");
                return;
            }

            ps.setString(1, title);
            ps.setInt(2, seats);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println(" Movie added successfully!");
            } else {
                System.out.println(" Movie not added. Please try again.");
            }

        } catch (SQLException e) {
            System.out.println(" Database error: " + e.getMessage());
        }
    }

    // ✅ Method to view all movies
    public void viewMovies() {
        String sql = "SELECT * FROM movies";

        try (Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("Movie ID | Title       | Available Seats");
            System.out.println("----------------------------------------");

            while (rs.next()) {
                String title = rs.getString("title");
                if (title == null || title.trim().isEmpty()) {
                    continue; // Skip movies with blank or null titles
                }

                System.out.printf("%8d | %-10s | %15d\n",
                        rs.getInt("movie_id"),
                        title,
                        rs.getInt("available_seats"));
            }

        } catch (SQLException e) {
            System.out.println(" Error retrieving movies: " + e.getMessage());
        }
    }
}
