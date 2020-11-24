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
    private int print;
    private int down;
    private int side;
    private int[][] tempGrid;
    private int[][] tempPlace;
    
    
    
    public Tetris() {
        game = new int[16][10];
        tempGrid = new int[16][10];
        currentShape = new Shape(new int[4][2]);
        allShapes = new int[][][]{{{1, 1}, {1, 0}, {0, 0}, {-1, 0}},
                                  {{1, 0}, {0, 0}, {0, -1}, {-1, -1}},
                                  {{1, -1}, {1, 0}, {0, 0}, {-1, 0}},
                                  {{1, 0}, {0, 0}, {0, 1}, {-1, 1}},
                                  {{1, 0}, {0, 0}, {-1, 0}, {-2, 0}},
                                  {{0, 0}, {0, 1}, {1, 1}, {1, 0}},
                                  {{0, -1}, {0, 0}, {1, 0}, {0, 1}}};
        rand = new Random();
        print = 0;
        
        down = 0;
        side = 4;
        tempPlace = new int[4][2];
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
        print = rnd + 1;
        Shape next = new Shape(allShapes[rnd]);
        currentShape = next;
        down = 0;
        side = 4;
        updatePlace();
        
        game = tempGrid.clone();
    }

    /**
     * places tetris piece on the fakeboard.
     */
    public void putPiece() {
        for (int i = 0; i < 4; i++) {
            tempGrid[tempPlace[i][0]][tempPlace[i][1]] = print;
        }
    }
    
    /**
     * removes a piece from the fakeboard. Replaces values with zero, where the
     * piece is removed.
     */
    public void removePiece() {
        for (int i = 0; i < 4; i++) {
            tempGrid[tempPlace[i][0]][tempPlace[i][1]] = 0;
        }
    }
    
    /**
     * When moving or rotating the piece you have to update it on the gameboard
     * also, this does that to the fakeboard. Then you have to check for
     * collision.
     */
    public void updatePlace() {
        
        for (int i = 0; i < 4; i++) {
            int y = currentShape.getShape()[i][0] + currentShape.minY() + down;
            int x = currentShape.getShape()[i][1] + currentShape.minX() + side;
            tempPlace[i][0] = y;
            tempPlace[i][1] = x;
        }
        putPiece();
        
    }
    
    /**
     * Checks if after some move pieces are gone missing. If yes there would be
     * less pieces than before.
     * @return false if there is a collision, true if there isn't
     */
    public boolean validMove() {
        int tempPieces = 0;
        int gamePieces = 0;
        
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 10; j++) {
                if (game[i][j] != 0) {
                    gamePieces++;
                }
                if (tempGrid[i][j] != 0) {
                    tempPieces++;
                }
            }
        }
        return tempPieces >= gamePieces;  
    }
    
    
    /**
     * Rotates the piece (shape) 90 degrees to the right.
     */
    public void rotateRight() {
        int[][] backup = currentShape.getShape();
        int[][] backupPlace = tempPlace.clone();
        removePiece();
        int[][] temp = new int[4][2];
        for (int i = 0; i < 4; i++) {
            temp[i][0] = currentShape.getShape()[i][1];
            temp[i][1] = -currentShape.getShape()[i][0];
        }
        currentShape.setShape(temp.clone());
        updatePlace();
        if (validMove()) {
            game = tempGrid.clone();
        } else {
            currentShape.setShape(backup);
            tempGrid = game.clone();
            tempPlace = backupPlace.clone();
        }
    }
    
    /**
     * Rotates the piece (shape) 90 degrees to the left.
     */
    public void rotateLeft() {
        int[][] backup = currentShape.getShape();
        int[][] backupPlace = tempPlace.clone();
        removePiece();
        int[][] temp = new int[4][2];
        for (int i = 0; i < 4; i++) {
            temp[i][0] = -currentShape.getShape()[i][1];
            temp[i][1] = currentShape.getShape()[i][0];
        }
                
        currentShape.setShape(temp.clone());
        
        updatePlace();
        
        if (validMove()) {
            game = tempGrid.clone();
        } else {
            currentShape.setShape(backup);
            tempGrid = game.clone();
            tempPlace = backupPlace;
        }
    }
    
    /**
     * Moves the piece (shape) one to the right on the gameboard.
     */
    public void moveRight() {
        
        int max = 0;
        for (int i = 0; i < 4; i++) {
            if (max < tempPlace[i][1]) {
                max = tempPlace[i][1];
            }
        }
        
        if (max < 9) {
            removePiece();
            side++;
        
            updatePlace();
            if (validMove()) {
                game = tempGrid.clone();
            } else {
                tempGrid = game.clone();
            }
        }
    }
    
    /**
     * Moves the piece (shape) one to the left on the gameboard.
     */
    public void moveLeft() {
        
        int min = 10;
        for (int i = 0; i < 4; i++) {
            if (min > tempPlace[i][1]) {
                min = tempPlace[i][1];
            }
        }
                
        if (min > 0) {
            removePiece();
            side--;
            updatePlace();
        
            if (validMove()) {
                game = tempGrid.clone();
            } else {
                tempGrid = game.clone();
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
        
            if (validMove()) {
                game = tempGrid.clone();
            } else {
                tempGrid = game.clone();
            }
        }
    }


    
    
    /**
     * Checks the max value of y in the board (cause it can't be more than 15),
     * so the piece does not go over board.
     * @return max y where this shape is on the board.
     */
    public int maxPlaceY() {
        int max = 0;
        for (int i = 0; i < 4; i++) {
            if (max < tempPlace[i][0]) {
                max = tempPlace[i][0];
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
