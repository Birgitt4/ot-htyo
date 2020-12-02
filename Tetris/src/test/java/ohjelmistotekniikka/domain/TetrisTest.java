/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohjelmistotekniikka.domain;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author birgi
 */
public class TetrisTest {
    
    Tetris game;
    Tetris game2;
    Shape shape;
    
    public TetrisTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        game = new Tetris();
        game2 = new Tetris();
        game2.createShape();
        shape = new Shape(new int[4][2]);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void createShapeChangesStartingShape() {
        game.createShape();
        assertNotEquals(shape.toString(), game.getCurrentShape().toString());
        
    }
    
    @Test
    public void createShapeDoesNotCreateIfNoFit() {
        int[][] grid = new int[16][10];
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 10; j++) {
                if (i == 0) {
                    grid[i][j] = 0;
                } else {
                    grid[i][j] = 1;
                }
            }
        }
        game.setGame(grid);
        game.createShape();
        Shape current = game.getCurrentShape();
        assertEquals(current, null);
    }
    @Test
    public void movingRightMovesPieceOneToTheRight() {
        int[][] temp = new int[16][10];
        for (int i=0; i<16; i++) {
            for (int j=1; j<10; j++) {
                temp[i][j] = game2.getGame()[i][j-1];
            }
        }
        game2.moveRight();
        game.setGame(temp);
        assertEquals(game.toString(), game2.toString());
    }
    @Test
    public void movingLeftMovesPieceOneToTheLeft() {
        int[][] temp = new int[16][10];
        for (int i=0; i<16; i++) {
            for (int j=0; j<9; j++) {
                temp[i][j] = game2.getGame()[i][j+1];
            }
        }
        game2.moveLeft();
        game.setGame(temp);
        assertEquals(game.toString(), game2.toString());
    }
    @Test
    public void movingDownMovesPieceOneToTheDown() {
        int[][] temp = new int[16][10];
        for (int i=1; i<16; i++) {
            for (int j=0; j<10; j++) {
                temp[i][j] = game2.getGame()[i-1][j];
            }
        }
        game2.moveDown();
        game.setGame(temp);
        assertEquals(game.toString(), game2.toString());
    }
    
    @Test
    public void movingOverLimitsDoesNotCauseException() {
        boolean works = true;
        try {
            for (int i = 0; i < 12; i++) {
                game2.moveRight();
            }
            for (int i = 0; i < 12; i++) {
                game2.moveLeft();
            }
            for (int i = 0; i < 18; i++) {
                game2.moveDown();
            }
        }
        catch (Exception e) {
            works = false;
        }
        assertTrue(works);
    }
    
    @Test
    public void rotateLeftDoNotCauseExceptionOnTheRight() {
        boolean works = true;
        try {
            for (int i = 0; i < 10; i++) {
                game2.moveRight();
                game2.moveRight();
                game2.rotateLeft();
                game2.moveRight();
                game2.rotateLeft();
            }
        }
        catch (Exception e) {
            works = false;
        }
        assertTrue(works);
    }
    @Test
    public void rotateLeftDoNotCauseExceptionOnTheLeft() {
        boolean works = true;
        try {
            for (int i = 0; i < 10; i++) {
                game2.moveLeft();
                game2.moveLeft();
                game2.rotateLeft();
                game2.moveLeft();
                game2.rotateLeft();
            }
        }
        catch (Exception e) {
            works = false;
        }
        assertTrue(works);
    }
    @Test
    public void rotateRightDoNotCauseExceptionOnTheRight() {
        boolean works = true;
        try {
            for (int i = 0; i < 10; i++) {
                game2.moveRight();
                game2.moveRight();
                game2.rotateRight();
                game2.moveRight();
                game2.rotateRight();
            }
        }
        catch (Exception e) {
            works = false;
        }
        assertTrue(works);
    }
    @Test
    public void rotateRightDoNotCauseExceptionOnTheLeft() {
        boolean works = true;
        try {
            for (int i = 0; i < 10; i++) {
                game2.moveLeft();
                game2.moveLeft();
                game2.rotateRight();
                game2.moveLeft();
                game2.rotateRight();
            }
        }
        catch (Exception e) {
            works = false;
        }
        assertTrue(works);
    }
    @Test
    public void clearRow() {
        int[][] grid = new int[16][10];
        int[][] grid2 = new int[16][10];
        int[][] place = new int[][]{{14, 2}, {14, 3}, {15, 2}, {15, 3}};
        game.setPlace(place);
        for (int j = 0; j < 10; j++) {
            if (j % 2 == 0) {
                grid[14][j] = 1;
                grid2[15][j] = 1;
            }
        }
        for (int j = 0; j < 10; j++) {
            grid[15][j] = 1;
        }
        game.setGame(grid);
        game2.setGame(grid2);
        ArrayList<Integer> fullrows = game.lineScan();
        game.emptyFullRows(fullrows);
        assertEquals(game.toString(), game2.toString());
    }
    
}
