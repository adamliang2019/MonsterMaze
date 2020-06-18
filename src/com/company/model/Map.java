package com.company.model;

import java.util.Arrays;

/***
 * Map Class
 * contains: int[][] game map, int[][]tiles that have been explored
 * constructor: takes in a generated map and sets the game map to be that map plus a border of walls
 * Methods:
 * getDisplayMap() returns map with -1 where it is unexplored
 * getter and setters for r,c coordinates
 */
public class Map {
    private int[][] map;        // {0:wall,1:open,2:hero,3:monster,4:powerup,5:grave}
    private int[][] explored;   // {0:not explored, 1:explored
    int numRows;
    int numCols;

    public Map(int[][] generated){
        numRows = generated.length;
        numCols = generated[0].length;
        map = generated;
        explored = new int[numRows][numCols];
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

    protected void setValue(int value, int row, int col){
        map[row][col] = value;
        for(int r = row; r <= row+1; r++){
            for(int c = col; c <= col+1; c++) {
                if(inBounds(r,c)) {
                    explored[r][c] = 1;
                }
            }
        }
    }

    protected int getValue(int row, int col){
        return map[row][col];
    }

    protected int[][] getDisplayMap(){
        int[][] copy = new int[map.length][map[0].length];
        for(int r=0;r<map.length;r++){
            for(int c=0; c<map[0].length; c++){
                if(map[r][c] == 2 || map[r][c] == 3){
                    copy[r][c] = map[r][c];
                }else {
                    if (explored[r][c] == 1) {
                        copy[r][c] = map[r][c];
                    } else {
                        copy[r][c] = -1;
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
