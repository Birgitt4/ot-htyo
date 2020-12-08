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
public interface Dao {
    void addPoints(String name, int points) throws SQLException;
    ArrayList<String[]> getTopTree() throws SQLException;
    ArrayList<Integer> getPointsFor(String name) throws SQLException;
}
