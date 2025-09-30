package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class HighscoreManager {

    private static final String URL = "jdbc:mysql://localhost:3306/game?zeroDateTimeBehavior=CONVERT_TO_NULL"; 
    private static final String USER = "root"; // Az adatbázis felhasználó neve
    private static final String PASSWORD = "root"; // Az adatbázis jelszava

    private Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void saveHighscore(String name, double time, String difficulty) {
        String query = "INSERT INTO Highscore (Name, Time, Difficulty) VALUES (?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setDouble(2, time);
            stmt.setString(3, difficulty);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Highscore> getHighscores() {
    List<Highscore> highscores = new ArrayList<>();
    String query = "SELECT * FROM Highscore ORDER BY Time ASC";  // Rendezzük az adatokat idő szerint növekvő sorrendben
    try (Connection connection = getConnection();
         PreparedStatement stmt = connection.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            Highscore highscore = new Highscore(
                    rs.getInt("ID"),
                    rs.getString("Name"),
                    rs.getDouble("Time"),
                    rs.getString("Difficulty")
            );
            highscores.add(highscore);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return highscores;
    }
}

