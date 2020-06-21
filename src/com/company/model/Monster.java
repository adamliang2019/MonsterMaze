package com.company.model;

import java.util.ArrayList;
import java.util.Random;

import static com.company.model.Cell.*;
import static com.company.model.Direction.*;

/***
 * Monster class models an in game monster
 * Data:
 * int row of monster
 * int column of monster
 * Direction lastMove (to avoid backtracking unless only option)
 * Cell what was under the monster originally
 * Methods:
 * Monster   initializes row, col values (lastMove is null covering is EMPTY
 * int[] randomValidMove  returns new valid row,col location for monster (avoids backtracking)
 * void move   sets monster location to new location
 * getters and setters for row, col, covering
 */

class Monster {
    private int row;
    private int col;
    private Direction lastMove;
    private Cell covering;

    protected Monster(int row, int col){
        this.row = row;
        this.col = col;
        lastMove = null;
        covering = EMPTY;
    }

    protected int[] randomValidMove(Map gameMap, Random random){
        ArrayList<int[]> newPositions = new ArrayList<>();
        int[] backtrackMove = null;
        if(gameMap.getValue(row+1, col) != WALL){     //down
            int[] newPosition = {row+1, col, DOWN.integer};
            if(lastMove != UP) {
                newPositions.add(newPosition);
            }else{
                backtrackMove = newPosition;
            }
        }
        if(gameMap.getValue(row-1, col) != WALL){     //up
            int[] newPosition = {row-1, col, UP.integer};
            if(lastMove != DOWN) {
                newPositions.add(newPosition);
            }else{
                backtrackMove = newPosition;
            }
        }
        if(gameMap.getValue(row, col+1) != WALL){     //right
            int[] newPosition = {row, col+1, RIGHT.integer};
            if(lastMove != LEFT) {
                newPositions.add(newPosition);
            }else{
                backtrackMove = newPosition;
            }
        }
        if(gameMap.getValue(row, col-1) != WALL){     //left
            int[] newPosition = {row, col-1, LEFT.integer};
            if(lastMove != RIGHT) {
                newPositions.add(newPosition);
            }else{
                backtrackMove = newPosition;
            }
        }
        int[] newPosition;
        if(newPositions.size() == 0 && backtrackMove != null){
            newPosition = backtrackMove;
        }else {
            newPosition = newPositions.get(random.nextInt(newPositions.size()));
        }
        lastMove = Direction.valueOfInteger(newPosition[2]);
        return newPosition;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void move(int row, int col){
        this.row = row;
        this.col = col;
    }

    public Cell getCovering(){
        return covering;
    }

    public void setCovering(Cell cell){
        covering = cell;
    }
}
