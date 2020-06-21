package com.company.model;

/***
 * Class Point models information of an immutable point (used in MapGenerator)
 */

class Point {
    private int row;
    private int col;

    public Point(int row, int col){
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

}
