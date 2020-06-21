package com.company.textui;

import com.company.model.Cell;
import com.company.model.Game;

/***
 * GameUI class contains all functionality for the text display of the game
 * can print the game stats, ask for input, error statement, the map, instructions, and game conclusions
 */

class GameUI {
    public GameUI(){
        //empty constructor
    }

    protected void printStats(Game currentGame){
        System.out.println("Number of monsters to be killed: " + currentGame.getPointsRemaining());
        System.out.println("Current power level: " + currentGame.getPowerLevel());
        System.out.println("Number of monsters alive: " + currentGame.getMonsters());
    }

    protected void askInput(){
        System.out.println("Enter your move [WASD?]:");
    }

    protected void invalidDirection(){
        System.out.println("Invalid move: you cannot move through walls!");
    }

    protected void invalidOption(){
        System.out.println("Invalid option, press '?' for help");
    }

    protected void cheat(){
        System.out.println("Cheat Code Entered, One Kill Required");
    }

    protected void printInstructions(){
        System.out.println("DIRECTIONS:\n   Kill 3 Monsters! (Killing a monster uses up one power level)\n   Collect Power to increase power level!");
        System.out.println("LEGEND:\n" +
                "   #: Wall\n" +
                "   @: You (the hero)\n" +
                "   !: Monster\n" +
                "   $: Power\n" +
                "   .: Unexplored space");
        System.out.println("MOVES:\n   Use W (up), A (left), S (down) and D (right) to move.\n  (You must press enter after each move).\n");
        System.out.println("ADDITIONAL:\n" +
                "   ?: Help\n" +
                "   m: Uncover Entire Map" +
                "   c: Cheat Code");
    }

    protected void gameWon(){
        System.out.println("Congratulations!\nYou Win Nothing!");
    }

    protected void gameLost(){
        System.out.println("Game Over\nI'm sorry, you have been eaten!");
    }

    protected void printMap(Cell[][] map){
        for(int row = 0; row < map.length; row++){
            StringBuilder column = new StringBuilder();
            for(int col = 0; col < map[0].length; col++){
                switch (map[row][col]){
                    case OBSCURED:
                        column.append(".");
                        break;
                    case WALL:
                        column.append("#");
                        break;
                    case EMPTY:
                        column.append(" ");
                        break;
                    case HERO:
                        column.append("@");
                        break;
                    case MONSTER:
                        column.append("!");
                        break;
                    case POWERUP:
                        column.append("$");
                        break;
                    case GRAVE:
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
