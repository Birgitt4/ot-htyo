
package ohjelmistotekniikka.dao;

import java.sql.*;
import java.util.ArrayList;

/**
 * Class for managing database.
 * 
 */
public class TetrisDao implements Dao {
    
    private Connection db;
    private String database;
    
    /**
     * constructor.
     * @param database path and name of database used
     * @throws SQLException 
     */
    public TetrisDao(String database) throws SQLException {
        this.database = database;
        initializeDatabase();
    }
    
    private void initializeDatabase() throws SQLException {
        if (db == null || db.isClosed()) {
            db = DriverManager.getConnection("jdbc:sqlite:" + database);
        }
        Statement s = db.createStatement();
        s.execute("CREATE TABLE IF NOT EXISTS Points (id INTEGER PRIMARY KEY, name TEXT NOT NULL, points INTEGER)");
        db.close();
    }
    
    @Override
    public void addPoints(String name, int points) throws SQLException {
        if (db == null || db.isClosed()) {
            db = DriverManager.getConnection("jdbc:sqlite:" + database);
        }
        PreparedStatement p = db.prepareStatement("INSERT INTO Points (name,points) VALUES (?,?)");
        p.setString(1, name);
        p.setInt(2, points);
        
        p.executeUpdate();
        db.close();
    }

    @Override
    public ArrayList<String[]> getTopTree() throws SQLException {
        if (db == null || db.isClosed()) {
            db = DriverManager.getConnection("jdbc:sqlite:" + database);
        }
        ArrayList<String[]> results = new ArrayList<>();
        Statement s = db.createStatement();
        ResultSet r = s.executeQuery("SELECT name, points FROM Points ORDER BY points DESC LIMIT 3");
        while (r.next()) {
            String[] row = new String[]{r.getString("name"), Integer.toString(r.getInt("points"))};
            results.add(row);
        }
        db.close();
        return results;
    }

    @Override
    public ArrayList<Integer> getPointsFor(String name) throws SQLException {
        if (db == null || db.isClosed()) {
            db = DriverManager.getConnection("jdbc:sqlite:" + database);
        }
        ArrayList<Integer> results = new ArrayList<>();
        PreparedStatement p = db.prepareStatement("SELECT points FROM Points WHERE name = ? ORDER BY points");
        p.setString(1, name);
        ResultSet r = p.executeQuery();
        while (r.next()) {
            results.add(r.getInt("points"));
        }
        db.close();
        return results;
    }

}