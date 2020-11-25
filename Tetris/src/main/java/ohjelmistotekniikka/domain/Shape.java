/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohjelmistotekniikka.domain;

import static java.lang.Math.abs;

/**
 * Shape implements the pieces in Tetris.
 * 
 */

// Muotoja seitsemän erilaista kierron (vasen) voi toteuttaa funktiolla (x,y)->(-y,x)
// oikea kierto on (x,y)->(y,-x)
// Tallennetaan jokainen muoto siis koordinaatteina
// kolmiulotteinen taulukko, jossa kaikki muodot
// int[7][4][2] 7 muotoa, kaikilla 4 koordinaatin pistettä, 2 = x,y
// tuo ylempi ehkä talteen tetris luokkaan ja tähän parametrina randomi noista
public class Shape {
    
    private int[][] shape;
    private int[][] placeOnBoard;

    
    public Shape(int[][] shape) {
        this.shape = shape.clone();
        placeOnBoard = new int[4][2];

    }
    
    public int[][] getShape() {
        return shape;
    }
    public void setShape(int[][] shape) {
        this.shape = shape;
    }
    public int[][] getPlaceOnBoard() {
        return placeOnBoard;
    }
    public void setPlaceOnBoard(int[][] place) {
        this.placeOnBoard = place;
    }
    
//    /**
//     * When moving or rotating the piece you have to update it on the gameboard
//     * also, this does that.
//     */
//    public void updatePlace() {
//        
//        for (int i = 0; i < 4; i++) {
//            int y = shape[i][0] + minY() + down;
//            int x = shape[i][1] + minX() + side;
//            placeOnBoard[i][0] = y;
//            placeOnBoard[i][1] = x;
//        }
//    }
//    
//    /**
//     * Rotates the piece (shape) 90 degrees to the right.
//     */
//    public void rotateRight() {
//        int[][] temp = new int[4][2];
//        for (int i = 0; i < 4; i++) {
//            temp[i][0] = shape[i][1];
//            temp[i][1] = -shape[i][0];
//        }
//        shape = temp.clone();
//        updatePlace();
//    }
//    
//    /**
//     * Rotates the piece (shape) 90 degrees to the left.
//     */
//    public void rotateLeft() {
//        int[][] temp = new int[4][2];
//        for (int i = 0; i < 4; i++) {
//            temp[i][0] = -shape[i][1];
//            temp[i][1] = shape[i][0];
//        }
//        shape = temp.clone();
//        
//        updatePlace();
//        
//    }
//    
//    /**
//     * Moves the piece (shape) one to the right on the gameboard.
//     */
//    public void moveRight() {
//        int max = 0;
//        for (int i = 0; i < 4; i++) {
//            if (max < placeOnBoard[i][1]) {
//                max = placeOnBoard[i][1];
//            }
//        }
//        if (max < 9) {
//            side++;
//        }
//        updatePlace();
//    }
//    
//    /**
//     * Moves the piece (shape) one to the left on the gameboard.
//     */
//    public void moveLeft() {
//        int min = 10;
//        for (int i = 0; i < 4; i++) {
//            if (min > placeOnBoard[i][1]) {
//                min = placeOnBoard[i][1];
//            }
//        }
//        if (min > 0) {
//            side--;
//        }
//        updatePlace();
//    }
//    
//    /**
//     * Moves the piece (shape) one to the down on the gameboard.
//     */
//    public void moveDown() {
//        if (maxPlaceY() < 15) {
//            down++;
//        }
//        updatePlace();
//    }
    
    /**
     * Shapes are described with four points in (y,x) coordinate system.
     * Method checks values of y.
     * @return lowest y value.
     */
    public int minY() {
        int min = 16;
        for (int i = 0; i < 4; i++) {
            if (min > shape[i][0]) {
                min = shape[i][0];
            }
        }
        return abs(min);
    }
    
    /**
     * Shapes are described with four points in (y,x) coordinate system.
     * Method checks values of x.
     * @return lowest x value.
     */
    public int minX() {
        int min = 10;
        for (int i = 0; i < 4; i++) {
            if (min > shape[i][1]) {
                min = shape[i][1];
            }
        }
        return abs(min);
    }
    
//    /**
//     * Checks the max value of y in the board (cause it can't be more than 15),
//     * so the piece does not go over board.
//     * @return max y where this shape is on the board.
//     */
//    public int maxPlaceY() {
//        int max = 0;
//        for (int i = 0; i < 4; i++) {
//            if (max < placeOnBoard[i][0]) {
//                max = placeOnBoard[i][0];
//            }
//        }
//        return max;
//    }
    
    /**
     * Used for testing.
     * @return coordinates as a string y,x,y,x,y,x,y,x,
     */
    public String toString() {
        String piece = "";
        for (int i = 0; i < 4; i++) {
            piece += shape[i][0] + "," + shape[i][1] + ",";
        }
        return piece;
    }
}
