
package ohjelmistotekniikka.domain;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author birgi
 */
public class TetrisTest {
    
    Tetris game;
    Tetris game2;
    
    @Before
    public void setUp() {
        game = new Tetris();
        game2 = new Tetris();
        game2.createShape();
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
    public void ifSomethingfitsOnTopItShows() {
        int[][] grid = new int[16][10];
        for (int i = 1; i < 16; i++) {
            for (int j = 0; j < 10; j++) {
                grid[i][j] = 1;
            }
        }
        game.setGame(grid);
        game.createShape();
        boolean shows = false;
        if (game.getGame()[0][4] == 1 || game.getGame()[0][5] == 1) {
            shows = true;
        }
        assertTrue(shows);
    }
    @Test
    public void topRowFullSecondRowEmptyPieceDoNotShow() {
        int[][] grid = new int[16][10];
        for (int j = 0; j < 10; j++) {
            grid[0][j] = 1;
        }
        game.setGame(grid);
        game.createShape();
        boolean shows = true;
        if (game.getGame()[1][4] == 0 && game.getGame()[1][5] == 0) {
            shows = false;
        }
        assertFalse(shows);
    }
    @Test
    public void clearRow() {
        int[][] grid = new int[16][10];
        int[][] grid2 = new int[16][10];
        int[][] place = new int[][]{{14, 2}, {14, 3}, {15, 2}, {15, 3}};
        game.getCurrentShape().setPlaceOnBoard(place);
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
        assertEquals(toString(game.getGame()), toString(game2.getGame()));
    }
    @Test
    public void validMoveReturnsFalseWhenNotValidGrid() {
        int[][] grid = new int[16][10];
        grid[14][2] = -1;
        game.setGame(grid);
        boolean works = true;
        if (game.validMove()) {
            works = false;
        }
        grid[14][2] = 2;
        if (game.validMove()) {
            works = false;
        }
        assertTrue(works);
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
        assertEquals(toString(game.getGame()), toString(game2.getGame()));
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
        assertEquals(toString(game.getGame()), toString(game2.getGame()));
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
        assertEquals(toString(game.getGame()), toString(game2.getGame()));
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
            System.out.println(e.getMessage());
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
            System.out.println(e.getMessage());
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
            System.out.println(e.getMessage());
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
            System.out.println(e.getMessage());
            works = false;
        }
        assertTrue(works);
    }
    @Test
    public void rotationDoesNotCauseExceptionOnBottom() {
        boolean works = true;
        try {
            for (int i = 0; i < 20; i++) {
                game2.moveDown();
                game2.moveDown();
                game2.rotateLeft();
                game2.moveDown();
                game2.rotateRight();
            }
        } catch (Exception e) {
            System.out.println("error");
            System.out.println(e.getMessage());
            works = false;
        }
        assertTrue(works);
    }
    @Test
    public void piecesDontOverLapMovingRight() {
        Shape o = new Shape(5);
        Shape i = new Shape(4);
        game.setCurrentShape(o);
        game.updatePlace(o.getShape());
        game.moveRight();
        game.moveRight();
        game.setCurrentShape(i);
        game.setDown(0);
        game.setSide(4);
        game.updatePlace(i.getShape());
        game.moveRight();
        game.moveRight();
        game.moveRight();
        int[][] grid = new int[16][10];
        grid[0][5] = 1;
        grid[0][6] = 1;
        grid[0][7] = 1;
        grid[1][5] = 1;
        grid[1][6] = 1;
        grid[1][7] = 1;
        grid[2][5] = 1;
        grid[3][5] = 1;
        assertEquals(toString(grid), toString(game.getGame()));
    }
    @Test
    public void piecesDontOverLapMovingLeft() {
        Shape o = new Shape(5);
        Shape i = new Shape(4);
        game.setCurrentShape(o);
        game.updatePlace(o.getShape());
        game.moveLeft();
        game.moveLeft();
        game.setCurrentShape(i);
        game.setDown(0);
        game.setSide(4);
        game.updatePlace(i.getShape());
        game.moveLeft();
        game.moveLeft();
        game.moveLeft();
        int[][] grid = new int[16][10];
        grid[0][2] = 1;
        grid[0][3] = 1;
        grid[0][4] = 1;
        grid[1][2] = 1;
        grid[1][3] = 1;
        grid[1][4] = 1;
        grid[2][4] = 1;
        grid[3][4] = 1;
        assertEquals(toString(grid), toString(game.getGame()));
    }
    @Test
    public void piecesDontOverLapOnRotation() {
        Shape o = new Shape(5);
        Shape i = new Shape(4);
        game.setCurrentShape(o);
        game.updatePlace(o.getShape());
        game.moveRight();
        game.moveRight();
        game.setCurrentShape(i);
        game.setDown(0);
        game.setSide(4);
        game.updatePlace(i.getShape());
        game.rotateRight();
        int[][] grid = new int[16][10];
        grid[0][4] = 1;
        grid[0][6] = 1;
        grid[0][7] = 1;
        grid[1][4] = 1;
        grid[1][6] = 1;
        grid[1][7] = 1;
        grid[2][4] = 1;
        grid[3][4] = 1;
        assertEquals(toString(grid), toString(game.getGame()));
        game.rotateLeft();
        assertEquals(toString(grid), toString(game.getGame()));
    }
    
    private String toString(int[][] grid) {
        String board = "";
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 10; j++) {
                board += grid[i][j] + ",";
            }
            board += "\n";
        }
        return board;
    }
}
