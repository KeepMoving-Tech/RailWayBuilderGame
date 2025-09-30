package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private static final String URL = "jdbc:mysql://localhost:3306/game?zeroDateTimeBehavior=CONVERT_TO_NULL";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public List<Tile> loadMap(int mapId) {

        List<Tile> tiles = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "SELECT x, y, TilesType FROM maptiles WHERE MapID = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setInt(1, mapId);  // A kívánt MapID
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        int x = rs.getInt("x");
                        int y = rs.getInt("y");
                        String type = rs.getString("TilesType");
                        String customImage = null;
                        if ("BETONRAIL".equals(type)) {
                            customImage = getRandomRailImage();
                        } else if ("GRASS".equals(type)) {
                            customImage = "grass.jpg";
                    }
                        tiles.add(new Tile(x, y, type));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tiles;
    }

    // A játékos pozíciójának lekérése a mapId alapján
    public Position loadPlayerPosition(int mapId) {
        Position playerPosition = null;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "SELECT x, y FROM maptiles WHERE TilesType = 'Player' AND MapID = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setInt(1, mapId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        int x = rs.getInt("x");
                        int y = rs.getInt("y");
                        playerPosition = new Position(x, y);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playerPosition != null ? playerPosition : new Position(0, 0);
    }

    public void updatePlayerPosition(Position position, int mapId) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String deleteQuery = "UPDATE maptiles SET TilesType = 'GRASS' WHERE TilesType = 'Player' AND MapID = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(deleteQuery)) {
                pstmt.setInt(1, mapId);
                pstmt.executeUpdate();
            }

            String insertQuery = "INSERT INTO maptiles (x, y, TilesType, MapID) VALUES (?, ?, 'Player', ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(insertQuery)) {
                pstmt.setInt(1, position.getX());
                pstmt.setInt(2, position.getY());
                pstmt.setInt(3, mapId);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Position> loadStations(int mapId) {
    List<Position> stations = new ArrayList<>();
    try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
        String query = "SELECT x, y FROM maptiles WHERE TilesType = 'Station' AND MapID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, mapId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int x = rs.getInt("x");
                    int y = rs.getInt("y");
                    stations.add(new Position(x, y));
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return stations;
}
    
    public int loadMapIdByDifficulty(String difficulty) {
        int mapId = -1;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "SELECT MapID FROM maptiles WHERE DifficultyLevel = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setString(1, difficulty);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    mapId = rs.getInt("MapID");
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return mapId;
}
    
    
    private String getRandomRailImage() {
        List<String> railImages = List.of("betonrail1.jpg", "betonrail2.jpg", "betonrail3.jpg", "betonrail4.jpg", "betonrail5.jpg", "betonrail6.jpg");
    return railImages.get((int) (Math.random() * railImages.size()));
}
}
