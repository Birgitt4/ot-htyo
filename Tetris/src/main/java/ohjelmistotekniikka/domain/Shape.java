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

    /**
     * constructor creates Shape object.
     * @param shape coordinates for the shape
     */
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
