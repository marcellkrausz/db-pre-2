package com.codecool.database;


import java.sql.*;

public class RadioCharts {
    private final String URL;
    private final String USER;
    private final String PASSWORD;
    private final Connection CONN;

    public RadioCharts(String URL, String USER, String PASSWORD) {
        this.URL = URL;
        this.USER = USER;
        this.PASSWORD = PASSWORD;
        this.CONN = getConnection();
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Database not reachable.");
        }
        return connection;
    }

    public String getMostPlayedSong() {
        try (this.CONN) {
            String query = "SELECT song, SUM(times_aired) as most_played FROM music_broadcast " +
                    "GROUP BY artist " +
                    "ORDER BY most_played DESC LIMIT 1";
            PreparedStatement ps = CONN.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    public String getMostActiveArtist() {
        try (this.CONN) {
            String query = "SELECT artist, COUNT(DISTINCT song) as songs FROM music_broadcast " +
                    "GROUP BY artist " +
                    "ORDER BY songs DESC LIMIT 1";
            PreparedStatement ps = CONN.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }
}
