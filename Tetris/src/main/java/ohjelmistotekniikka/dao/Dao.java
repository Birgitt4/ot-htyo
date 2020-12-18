
package ohjelmistotekniikka.dao;

import java.sql.*;
import java.util.ArrayList;
/**
 * Interface for SQL querys.
 * 
 */
public interface Dao {
    /**
     * adds given points with player's name to memory.
     * @param name player
     * @param points gameresult
     * @throws SQLException 
     */
    void addPoints(String name, int points) throws SQLException;
    /**
     * Self explaining.
     * @return Returns top tree player from memory
     * @throws SQLException 
     */
    ArrayList<String[]> getTopTree() throws SQLException;
    /**
     * Search all scores for the given player.
     * @param name player
     * @return ArrayList of integers = every gameresult for the player
     * @throws SQLException 
     */
    ArrayList<Integer> getPointsFor(String name) throws SQLException;
}
