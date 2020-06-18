package com.company.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class MapGenerator {
    private int[][] maze;
    private Stack<Point> stack = new Stack<>();
    int numRows = 0;
    int numCols = 0;

    public MapGenerator(){
        maze = null;
    }

    public int[][] getMaze(){
        if(maze==null){
            return null;
        }
        int[][] copy = maze.clone();
        return copy;
    }

    public int[][] generateMaze(int row, int col){
        numRows = row;
        numCols = col;
        boolean validMaze = false;
        while(!validMaze) {
            maze = new int[row][col];
            stack.push(new Point(1, 1));
            while (!stack.empty()) {
                Point current = stack.pop();
                if (validPoint(current)) {
                    maze[current.getRow()][current.getCol()] = 1;
                    ArrayList<Point> neighbors = getNeighbours(current);
                    randomlyAddNodesToStack(neighbors);
                }
            }
            validMaze = isValidMaze();
        }
        maze[1][1] = 2;
        maze[1][numCols-2] = 3;
        maze[numRows-2][numCols-2] = 3;
        maze[numRows-2][1] = 3;

        int[][] copy = maze.clone();
        return copy;
    }

    private boolean validPoint(Point point){
        if(maze[point.getRow()][point.getCol()] != 0){
            return false;
        }
        int row = point.getRow();
        int col = point.getCol();
        //check for all 4 potential 2X2 openings created by opening up point
        if(inBounds(row-1,col) && maze[row-1][col] == 1){
            if((inBounds(row-1,col-1) && maze[row-1][col-1] == 1)&&(inBounds(row,col-1) && maze[row][col-1] == 1)){
                return false;
            }
            if((inBounds(row-1,col+1) && maze[row-1][col+1] == 1)&&(inBounds(row,col+1) && maze[row][col+1] == 1)){
                return false;
            }
        }
        if(inBounds(row+1,col) && maze[row+1][col] == 1){
            if((inBounds(row+1,col-1) && maze[row+1][col-1] == 1)&&(inBounds(row,col-1) && maze[row][col-1] == 1)){
                return false;
            }
            if((inBounds(row+1,col+1) && maze[row+1][col+1] == 1)&&(inBounds(row,col+1) && maze[row][col+1] == 1)){
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
        return row>0 && col>0 && row<numRows-1 && col<numCols-1;
    }

    private boolean isValidMaze(){
        //check for back to back walls along edge (forms a 2X2)
        for(int row=1; row<numRows-1; row++){
            if(maze[row][1]==0 && maze[row+1][1]==0){
                return false;
            }
            if(maze[row][numCols-2]==0 && maze[row+1][numCols-2]==0){
                return false;
            }
        }
        for(int col=1; col<numCols-1; col++){
            if(maze[1][col]==0 && maze[1][col+1]==0){
                return false;
            }
            if(maze[numRows-2][col]==0 && maze[numRows-2][col+1]==0){
                return false;
            }
        }
        //check 3 monster corners
        return maze[1][numCols-2]==1 && maze[numRows-2][numCols-2]==1 && maze[numRows-2][1]==1;
    }
}
