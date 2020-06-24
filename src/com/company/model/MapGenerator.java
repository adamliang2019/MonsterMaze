package com.company.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;

import static com.company.model.Cell.*;

/***
 * MapGenerator class encapsulates all functionality needed to generate a random maze with loops, but no 2X2 blocks
 * generates maze with depth first search algorithm
 */

class MapGenerator {
    private Cell[][] maze;
    private Stack<Point> stack = new Stack<>();
    private int numRows = 0;
    private int numCols = 0;

    protected MapGenerator(){
        maze = null;
    }

    protected Cell[][] getMaze(){
        if(maze==null){
            return null;
        }
        Cell[][] copy = maze.clone();
        return copy;
    }

    protected Cell[][] generateMaze(int row, int col){
        numRows = row;
        numCols = col;
        boolean validMaze = false;
        while(!validMaze) {
            maze = wallMatrix(row, col);
            stack.push(new Point(1, 1));
            while (!stack.empty()) {
                Point current = stack.pop();
                if (validPoint(current)) {
                    maze[current.getRow()][current.getCol()] = EMPTY;
                    ArrayList<Point> neighbors = getNeighbours(current);
                    randomlyAddNodesToStack(neighbors);
                }
            }
            validMaze = isValidMaze();
        }
        maze[1][1] = HERO;
        maze[1][numCols-2] = MONSTER;
        maze[numRows-2][numCols-2] = MONSTER;
        maze[numRows-2][1] = MONSTER;

        Random rand = new Random();
        boolean validLocation = false;
        int powerRow = 0;
        int powerCol = 0;
        while(!validLocation){
            // borders are all walls
            powerRow = rand.nextInt(numRows-2) + 1;
            powerCol = rand.nextInt(numCols-2) + 1;
            if(maze[powerRow][powerCol] == EMPTY){
                validLocation = true;
            }
        }
        maze[powerRow][powerCol] = POWERUP;

        Cell[][] copy = maze.clone();
        return copy;
    }

    private boolean validPoint(Point point){
        if(maze[point.getRow()][point.getCol()] != WALL){
            return false;
        }
        int row = point.getRow();
        int col = point.getCol();
        //check for all 4 potential 2X2 openings created by opening up point
        if(inBounds(row-1,col) && maze[row-1][col] == EMPTY){
            if((inBounds(row-1,col-1) && maze[row-1][col-1] == EMPTY) && (inBounds(row,col-1) && maze[row][col-1] == EMPTY)){
                return false;
            }
            if((inBounds(row-1,col+1) && maze[row-1][col+1] == EMPTY) && (inBounds(row,col+1) && maze[row][col+1] == EMPTY)){
                return false;
            }
        }
        if(inBounds(row+1,col) && maze[row+1][col] == EMPTY){
            if((inBounds(row+1,col-1) && maze[row+1][col-1] == EMPTY) && (inBounds(row,col-1) && maze[row][col-1] == EMPTY)){
                return false;
            }
            if((inBounds(row+1,col+1) && maze[row+1][col+1] == EMPTY) && (inBounds(row,col+1) && maze[row][col+1] == EMPTY)){
                return false;
            }
        }
        return true;
    }

    private ArrayList<Point> getNeighbours(Point current){
        ArrayList<Point> neighbours = new ArrayList<Point>();
        for (int row = current.getRow()-1; row <= current.getRow()+1; row++) {
            for (int col = current.getCol()-1; col <= current.getCol()+1; col++) {
                if (inBounds(row,col) && (row == current.getRow() ^ col == current.getCol())) {
                    neighbours.add(new Point(row,col));
                }
            }
        }
        return neighbours;
    }

    private void randomlyAddNodesToStack(ArrayList<Point> points){
        Collections.shuffle(points);
        for(Point point : points){
            stack.push(point);
        }
    }

    private boolean inBounds(int row, int col){
        return row > 0 && col > 0 && row < numRows-1 && col < numCols-1;
    }

    private boolean isValidMaze(){
        //check for back to back walls along edge (forms a 2X2)
        for(int row = 1; row < numRows-1; row++){
            if(maze[row][1] == WALL && maze[row+1][1] == WALL){
                return false;
            }
            if(maze[row][numCols-2] == WALL && maze[row+1][numCols-2] == WALL){
                return false;
            }
        }
        for(int col = 1; col < numCols-1; col++){
            if(maze[1][col] == WALL && maze[1][col+1] == WALL){
                return false;
            }
            if(maze[numRows-2][col] == WALL && maze[numRows-2][col+1] == WALL){
                return false;
            }
        }
        //check 3 monster corners
        return maze[1][numCols-2] == EMPTY && maze[numRows-2][numCols-2] == EMPTY && maze[numRows-2][1] == EMPTY;
    }

    private Cell[][] wallMatrix(int row, int col){
        Cell[][] wallMatrix = new Cell[row][col];
        for(int r = 0; r < row; r++){
            for(int c = 0; c < col; c++){
                wallMatrix[r][c] = WALL;
            }
        }
        return wallMatrix;
    }
}
