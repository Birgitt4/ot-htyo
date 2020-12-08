/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohjelmistotekniikka.dao;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author birgi
 */
public class TetrisDao implements Dao {
    
    private Connection db;
    
    public TetrisDao(String database) throws SQLException {
        this.db = DriverManager.getConnection("jdbc:sqlite:" + database);
        initializeDatabase();
    }
    
    private void initializeDatabase() throws SQLException {
        Statement s = db.createStatement();
        s.execute("CREATE TABLE IF NOT EXISTS Points (id INTEGER PRIMARY KEY, name TEXT NOT NULL, points INTEGER)"); 
    }
    
    @Override
    public void addPoints(String name, int points) throws SQLException {
        PreparedStatement p = db.prepareStatement("INSERT INTO Points (name,points) VALUES (?,?)");
        p.setString(1, name);
        p.setInt(2, points);
        
        p.executeUpdate();
    }

    @Override
    public ArrayList<String[]> getTopTree() throws SQLException {
        ArrayList<String[]> results = new ArrayList<>();
        Statement s = db.createStatement();
        ResultSet r = s.executeQuery("SELECT name, points FROM Points ORDER BY points DESC LIMIT 3");
        while (r.next()) {
            String[] row = new String[]{r.getString("name"), Integer.toString(r.getInt("points"))};
            results.add(row);
        }
        
        return results;
    }

    @Override
    public ArrayList<Integer> getPointsFor(String name) throws SQLException {
        ArrayList<Integer> results = new ArrayList<>();
        PreparedStatement p = db.prepareStatement("SELECT points FROM Points WHERE name = ? ORDER BY points");
        p.setString(1, name);
        ResultSet r = p.executeQuery();
        while (r.next()) {
            results.add(r.getInt("points"));
        }
        return results;
    }

}
