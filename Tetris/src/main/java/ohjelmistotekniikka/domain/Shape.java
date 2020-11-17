/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohjelmistotekniikka.domain;

import static java.lang.Math.abs;

/**
 *
 * @author birgi
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
    private int down;
    private int side;
    
    public Shape(int[][] shape) {
        this.shape = shape.clone();
        placeOnBoard = new int[4][2];
        down = 0;
        side = 4;
        updatePlace(down,side);
    }
    
    public int[][] getShape() {
        return shape;
    }
    public int[][] getPlaceOnBoard() {
        return placeOnBoard;
    }
    
    public void updatePlace(int down, int side) {
        
        for (int i=0; i<4; i++) {
            int y = shape[i][0]+minY()+down;
            int x = shape[i][1]+minX()+side;
            placeOnBoard[i][0] = y;
            placeOnBoard[i][1] = x;
        }
    }
    
    public void rotateRight() {
        int[][] temp = new int[4][2];
        for (int i=0; i<4; i++) {
            temp[i][0] = shape[i][1];
            temp[i][1] = -shape[i][0];
        }
        shape = temp.clone();
        updatePlace(down,side);
    }
    public void rotateLeft() {
        int[][] temp = new int[4][2];
        for (int i=0; i<4; i++) {
            temp[i][0] = -shape[i][1];
            temp[i][1] = shape[i][0];
        }
        shape = temp.clone();
        
        updatePlace(down,side);
        
    }
    
    public void moveRight() {
        int max = 0;
        for (int i=0; i<4; i++) {
            if (max < placeOnBoard[i][1]) {
                max = placeOnBoard[i][1];
            }
        }
        if (max < 9) {
            side++;
        }
        updatePlace(down, side);
    }
    public void moveLeft() {
        int min = 10;
        for (int i=0; i<4; i++) {
            if (min > placeOnBoard[i][1]) {
                min = placeOnBoard[i][1];
            }
        }
        if (min > 0) {
            side--;
        }
        updatePlace(down, side);
    }
    public void moveDown() {
        if (maxPlaceY() < 15) {
            down++;
        }
        updatePlace(down, side);
    }
    
    public int minY() {
        int min = 16;
        for (int i=0; i<4; i++) {
            if (min > shape[i][0]) {
                min = shape[i][0];
            }
        }
        return abs(min);
    }
    public int minX() {
        int min = 10;
        for (int i=0; i<4; i++) {
            if (min > shape[i][1]) {
                min = shape[i][1];
            }
        }
        return abs(min);
    }
    public int maxPlaceY() {
        int max = 0;
        for (int i=0; i<4; i++) {
            if (max < placeOnBoard[i][0]) {
                max = placeOnBoard[i][0];
            }
        }
        return max;
    }
    
    public String toString() {
        String piece = "";
        for (int i=0; i<4; i++) {
            piece += shape[i][0] + "," + shape[i][1] + ",";
        }
        return piece;
    }
}
