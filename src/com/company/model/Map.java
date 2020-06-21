package com.company.model;

import java.util.Arrays;

import static com.company.model.Cell.*;

/***
 * Map Class
 * contains: Cell[][] game map, int[][] tiles that have been explored, numRows, numCols
 * constructor: takes in a generated map and creates explored
 * Methods:
 * getDisplayMap() returns map with -1 where it is unexplored
 * fullyExplore()  sets explored to be all true
 * inBounds     returns if row, col coordinates are in map
 * getter and setters for row,col coordinates and map values
 */
public class Map {
    private Cell[][] map;        // {0:wall,1:open,2:hero,3:monster,4:powerup,5:grave}
    private int[][] explored;   // {0:not explored, 1:explored
    private int numRows;
    private int numCols;

    public Map(Cell[][] generated){
        numRows = generated.length;
        numCols = generated[0].length;
        map = generated;
        explored = new int[numRows][numCols];
        //should be able to see border of maze and starting location of hero
        for(int row=0; row<numRows; row++){
            explored[row][0] = 1;
            explored[row][numCols-1] = 1;
        }
        for(int col=1; col<numCols-1; col++){
            explored[0][col] = 1;
            explored[numRows-1][col] = 1;
        }
        explored[1][1] = 1;
        explored[1][2] = 1;
        explored[2][1] = 1;
        explored[2][2] = 1;
    }

    protected void setValue(Cell value, int row, int col){
        map[row][col] = value;
        if(value == HERO) {
            for (int r = row-1; r <= row + 1; r++) {
                for (int c = col-1; c <= col + 1; c++) {
                    if (inBounds(r, c)) {
                        explored[r][c] = 1;
                    }
                }
            }
        }
    }

    protected Cell getValue(int row, int col){
        return map[row][col];
    }

    protected int numRows(){
        return numRows;
    }

    protected int numCols(){
        return numCols;
    }

    protected Cell[][] getDisplayMap(){
        Cell[][] copy = new Cell[map.length][map[0].length];
        for(int r=0;r<map.length;r++){
            for(int c=0; c<map[0].length; c++){
                if(map[r][c] == HERO || map[r][c] == MONSTER || map[r][c] == POWERUP){
                    copy[r][c] = map[r][c];
                }else {
                    if (explored[r][c] == 1) {
                        copy[r][c] = map[r][c];
                    } else {
                        copy[r][c] = OBSCURED;
                    }
                }
            }
        }
        return copy;
    }

    protected void fullyExplore(){
        for(int row = 0; row < map.length; row++){
            Arrays.fill(explored[row],1);
        }
    }

    private boolean inBounds(int row, int col){
        return row>0 && col>0 && row<numRows-1 && col<numCols-1;
    }
}
