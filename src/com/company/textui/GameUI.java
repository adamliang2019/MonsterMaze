package com.company.textui;

public class GameUI {
    public GameUI(){

    }

    protected void printMap(int[][] map){
        for(int row = 0; row < map.length-1; row++){
            StringBuilder column = new StringBuilder();
            for(int col = 0; col < map[0].length-1; col++){
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
