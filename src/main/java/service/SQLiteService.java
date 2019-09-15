package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteService {

    private final String URL = "jdbc:sqlite:D:\\JAVA\\PROJECTS\\JacksonSQLite\\src\\main\\resources\\test.db";

    public void createNewTable() {

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS colours( " +
                 "id integer PRIMARY KEY, " +
                 "name text NOT NULL, " +
                 "red integer, " +
                 "green integer, " +
                 "blue integer, " +
                 "alpha integer); ";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);

            System.out.println("Table \"colours\" created or already exists");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
