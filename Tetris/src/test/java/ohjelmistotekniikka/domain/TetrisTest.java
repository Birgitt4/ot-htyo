/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohjelmistotekniikka.domain;

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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    @Test
    public void createShapeChangesStartingShape() {
        game.createShape();
        assertNotEquals(shape.toString(), game.getCurrentShape().toString());
        
    }
    
    @Test
    public void removePieceRemoves() {
        int[][] temp = game2.getCurrentShape().getPlaceOnBoard();
        game2.removePiece(temp);
        assertEquals(game.toString(), game2.toString());
    }
    
    @Test
    public void MovingRightMovesEverythingOneToTheRight() {
        int[][] temp = new int[16][10];
        for (int i=0; i<16; i++) {
            for (int j=1; j<10; j++) {
                temp[i][j] = game2.getGame()[i][j-1];
            }
        }
        game2.move('r');
        game.setGame(temp);
        assertEquals(game.toString(), game2.toString());
    }
    @Test
    public void MovingLeftMovesEverythingOneToTheLeft() {
        int[][] temp = new int[16][10];
        for (int i=0; i<16; i++) {
            for (int j=0; j<9; j++) {
                temp[i][j] = game2.getGame()[i][j+1];
            }
        }
        game2.move('l');
        game.setGame(temp);
        assertEquals(game.toString(), game2.toString());
    }
    @Test
    public void MovingDownMovesEverythingOneToTheDown() {
        int[][] temp = new int[16][10];
        for (int i=1; i<16; i++) {
            for (int j=0; j<10; j++) {
                temp[i][j] = game2.getGame()[i-1][j];
            }
        }
        game2.move('d');
        game.setGame(temp);
        assertEquals(game.toString(), game2.toString());
    }
}
