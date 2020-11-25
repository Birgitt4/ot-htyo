/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohjelmistotekniikka.domain;

import java.util.Random;

/**
 * Takes care of the features in the game. Tetris.
 * 
 */
public class Tetris {
    private int[][] game;
    private Shape currentShape;
    private int[][][] allShapes;
    private Random rand;
    private int down;
    private int side;
    private int[][] place;
    
    
    
    public Tetris() {
        game = new int[16][10];
        currentShape = new Shape(new int[4][2]);
        allShapes = new int[][][]{{{1, 1}, {1, 0}, {0, 0}, {-1, 0}},
                                  {{1, 0}, {0, 0}, {0, -1}, {-1, -1}},
                                  {{1, -1}, {1, 0}, {0, 0}, {-1, 0}},
                                  {{1, 0}, {0, 0}, {0, 1}, {-1, 1}},
                                  {{1, 0}, {0, 0}, {-1, 0}, {-2, 0}},
                                  {{0, 0}, {0, 1}, {1, 1}, {1, 0}},
                                  {{0, -1}, {0, 0}, {1, 0}, {0, 1}}};
        rand = new Random();
        
        down = 0;
        side = 4;
        place = new int[4][2];
    }
    
    public void setGame(int[][] game) {
        this.game = game;
    }
    public int[][] getGame() {
        return game;
    }
    public Shape getCurrentShape() {
        return currentShape;
    }

    
    /**
     * Creates a new piece for tetris and puts it on the top of the gameboard.
     */
    public void createShape() {
        int rnd = rand.nextInt(7);
        Shape next = new Shape(allShapes[rnd]);
        currentShape = next;
        down = 0;
        side = 4;
        updatePlace();
        
    }

    /**
     * places tetris piece on the fakeboard.
     */
    public void putPiece() {
        for (int i = 0; i < 4; i++) {
            game[place[i][0]][place[i][1]] += 1;
        }
    }
    
    /**
     * removes a piece from the fakeboard. Replaces values with zero, where the
     * piece is removed.
     */
    public void removePiece() {
        for (int i = 0; i < 4; i++) {
            game[place[i][0]][place[i][1]] -= 1;
        }
    }
    
    /**
     * When moving or rotating the piece you have to update it on the gameboard
     * also, this does that to the fakeboard. Then you have to check for
     * collision.
     */
    public void updatePlace() {
        place = new int[4][2];
        for (int i = 0; i < 4; i++) {
            int y = currentShape.getShape()[i][0] + currentShape.minY() + down;
            int x = currentShape.getShape()[i][1] + currentShape.minX() + side;
            place[i][0] = y;
            place[i][1] = x;
        }
        currentShape.setPlaceOnBoard(place);
        putPiece();
    }
    
    /**
     * Checks if after some move there are values over one in the grid.
     * If there is there has to be more than one piece -> collision.
     * @return false if there is a collision, true if there isn't
     */
    public boolean validMove() {

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 10; j++) {
                if (game[i][j] > 1) {
                    return false;
                }
            }
        }
        return true;  
    }
    
    
    /**
     * Rotates the piece (shape) 90 degrees to the right.
     */
    public void rotateRight() {
        removePiece();
        int[][] temp = new int[4][2];
        for (int i = 0; i < 4; i++) {
            temp[i][0] = currentShape.getShape()[i][1];
            temp[i][1] = -currentShape.getShape()[i][0];
        }
        currentShape.setShape(temp);
        updatePlace();
        if (!validMove()) {
            rotateLeft();
        }
    }
    
    /**
     * Rotates the piece (shape) 90 degrees to the left.
     */
    public void rotateLeft() {
        removePiece();
        int[][] temp = new int[4][2];
        for (int i = 0; i < 4; i++) {
            temp[i][0] = -currentShape.getShape()[i][1];
            temp[i][1] = currentShape.getShape()[i][0];
        }
                
        currentShape.setShape(temp);
        updatePlace();
        
        if (!validMove()) {
            rotateRight();
        }
    }
    
    /**
     * Moves the piece (shape) one to the right on the gameboard.
     */
    public void moveRight() {
        int max = 0;
        for (int i = 0; i < 4; i++) {
            if (max < place[i][1]) {
                max = place[i][1];
            }
        }
        
        if (max < 9) {
            removePiece();
            side++;
        
            updatePlace();
            if (!validMove()) {
                moveLeft();
            }
        }
    }
    
    /**
     * Moves the piece (shape) one to the left on the gameboard.
     */
    public void moveLeft() {
        int min = 10;
        for (int i = 0; i < 4; i++) {
            if (min > place[i][1]) {
                min = place[i][1];
            }
        }
        if (min > 0) {
            removePiece();
            side--;
            updatePlace();
        
            if (!validMove()) {
                moveRight();
            }
        }
    }
    
    /**
     * Moves the piece (shape) one to the down on the gameboard.
     */
    public void moveDown() {
        if (maxPlaceY() < 15) {
            removePiece();
            down++;
        
            updatePlace();
        
            System.out.println(validMove());
            if (!validMove()) {
                moveUp();
            }
        }
    }

    private void moveUp() {
        removePiece();
        down--;
        updatePlace();
    }

    
    
    /**
     * Checks the max value of y in the board (cause it can't be more than 15),
     * so the piece does not go over board.
     * @return max y where this shape is on the board.
     */
    public int maxPlaceY() {
        int max = 0;
        for (int i = 0; i < 4; i++) {
            if (max < place[i][0]) {
                max = place[i][0];
            }
        }
        return max;
    }
    
    /**
     * used for testing.
     * @return gameboard as a string.
     */
    public String toString() {
        String board = "";
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 10; j++) {
                board += game[i][j] + ",";
            }
        }
        return board;
    }
}
