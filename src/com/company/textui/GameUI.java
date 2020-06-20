package com.company.textui;

import com.company.model.Game;

public class GameUI {
    public GameUI(){

    }

    protected void printStats(Game currentGame){
        System.out.println("Number of monsters to be killed: " + currentGame.getPointsRemaining());
        System.out.println("Current power level: " + currentGame.getPowerLevel());
        System.out.println("Number of monsters alive: " + currentGame.getMonsters());
    }

    protected void askInput(){
        System.out.println("Enter your move [WASD?]:");
    }

    protected void printMap(int[][] map){
        for(int row = 0; row < map.length; row++){
            StringBuilder column = new StringBuilder();
            for(int col = 0; col < map[0].length; col++){
                switch (map[row][col]){
                    case -1:
                        column.append(".");
                        break;
                    case 0:
                        column.append("#");
                        break;
                    case 1:
                        column.append(" ");
                        break;
                    case 2:
                        column.append("@");
                        break;
                    case 3:
                        column.append("!");
                        break;
                    case 4:
                        column.append("$");
                        break;
                    case 5:
                        column.append("X");
                        break;
                    default:
                        column.append("E");
                        break;
                }
            }
            System.out.println(column);
        }
    }
}
