package com.company.model;

import java.util.ArrayList;
import java.util.Random;

public class Monster {
    private int row;
    private int col;
    private int lastMove;      //0:left,1:up,2:right,3:down
    private int covering;

    protected Monster(int row, int col){
        this.row = row;
        this.col = col;
        lastMove = -1;
        covering = 0;
    }

    protected int[] randomValidMove(Map gameMap, Random random){
        ArrayList<int[]> newPositions = new ArrayList<>();
        int[] backtrackMove = null;
        if(gameMap.getValue(row+1,col)!=0){     //down
            int[] newPosition = {row+1,col,3};
            if(lastMove != 1) {
                newPositions.add(newPosition);
            }else{
                backtrackMove = newPosition;
            }
        }
        if(gameMap.getValue(row-1,col)!=0){     //up
            int[] newPosition = {row-1,col,1};
            if(lastMove != 3) {
                newPositions.add(newPosition);
            }else{
                backtrackMove = newPosition;
            }
        }
        if(gameMap.getValue(row,col+1)!=0){     //right
            int[] newPosition = {row,col+1,2};
            if(lastMove != 0) {
                newPositions.add(newPosition);
            }else{
                backtrackMove = newPosition;
            }
        }
        if(gameMap.getValue(row,col-1)!=0){     //left
            int[] newPosition = {row,col-1,0};
            if(lastMove != 2) {
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
        lastMove = newPosition[2];
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

    public int getCovering(){
        return covering;
    }

    public void setCovering(int cell){
        covering = cell;
    }
}
