package com.company.model;

import java.util.Random;
import java.util.ArrayList;
import java.util.Stack;

import static com.company.model.Cell.*;

/***
 * Turns class models the data and functions required to advance a turn
 * Data:
 * int[] the row and column location of the hero in the maze
 * ArrayList<Monster> list of monsters that are still alive
 * Methods:
 * Turns(Map map) fills monster list with 3 monsters
 * int heroTurn   advances the hero one move does all associated actions for the move (fight and collect), returns power used on move
 * int monsterTurn   advances all living monsters one move does all associated actions for the move, returns power used on moves
 * int fightMonsters   if sufficient power, kill monsters on same tile as hero, return power used (private)
 * int getPower   collects power on hero tile and respawn the power if taken, returns power collected (private)
 * boolean validMove   returns if the hero can move in a certain direction, if there is a wall return false else true
 */

class Turns {
    private int[] heroRC = {1,1};
    private ArrayList<Monster> monsterList;

    protected Turns(Map map){
        monsterList = new ArrayList<>();
        Monster monster1RC = new Monster(map.numRows()-2, map.numCols()-2);
        Monster monster2RC = new Monster(map.numRows()-2, 1);
        Monster monster3RC = new Monster(1, map.numCols()-2);
        monsterList.add(monster1RC);
        monsterList.add(monster2RC);
        monsterList.add(monster3RC);
    }

    protected int heroTurn(Map gameMap, Direction direction, int powerLevel){
        int powerUsed = 0;
        if(direction == Direction.LEFT){
            if(gameMap.getValue(heroRC[0], heroRC[1]-1) == WALL){
                return 0;
            }
            gameMap.setValue(EMPTY, heroRC[0], heroRC[1]);
            heroRC[1]--;
        }else if(direction == Direction.UP){
            if(gameMap.getValue(heroRC[0]-1, heroRC[1]) == WALL){
                return 0;
            }
            gameMap.setValue(EMPTY, heroRC[0], heroRC[1]);
            heroRC[0]--;
        }else if(direction == Direction.RIGHT){
            if(gameMap.getValue(heroRC[0], heroRC[1]+1) == WALL){
                return 0;
            }
            gameMap.setValue(EMPTY, heroRC[0], heroRC[1]);
            heroRC[1]++;
        }else if(direction == Direction.DOWN){
            if(gameMap.getValue(heroRC[0]+1, heroRC[1]) == WALL){
                return 0;
            }
            gameMap.setValue(EMPTY, heroRC[0], heroRC[1]);
            heroRC[0]++;
        }
        powerUsed += fightMonsters(powerLevel);
        if(powerLevel<powerUsed){
            gameMap.setValue(GRAVE, heroRC[0], heroRC[1]);
        }else{
            powerUsed -= getPower(gameMap);
            gameMap.setValue(HERO, heroRC[0], heroRC[1]);
        }
        return powerUsed;
    }

    protected int monsterTurn(Map gameMap, int powerLevel){
        Random rand = new Random();
        int totalPowerUsed = 0;
        Stack<Monster> monsterStack = new Stack<>();
        monsterStack.addAll(monsterList);
        while(!monsterStack.isEmpty()){
            Monster monster = monsterStack.pop();
            if(monster.getCovering() != MONSTER) {      //only first monster in the cell will know what it is covering
                gameMap.setValue(monster.getCovering(), monster.getRow(), monster.getCol());
            }
            int[] newPosition = monster.randomValidMove(gameMap, rand);
            monster.move(newPosition[0], newPosition[1]);
            monster.setCovering(gameMap.getValue(newPosition[0], newPosition[1]));
            int powerUsed = fightMonsters(powerLevel);
            totalPowerUsed += powerUsed;
            if(powerUsed == 0){
                gameMap.setValue(MONSTER, monster.getRow(), monster.getCol());
            }else {
                if (powerUsed > powerLevel) {
                    gameMap.setValue(GRAVE, monster.getRow(), monster.getCol());
                    break;
                }
            }
        }

        return totalPowerUsed;
    }

    private int fightMonsters(int powerlevel){
        int powerUsed = 0;
        ArrayList<Monster> toRemove = new ArrayList<>();
        for(Monster monster : monsterList) {
            if (monster.getRow() == heroRC[0] && monster.getCol() == heroRC[1]) {
                powerUsed++;
                if(powerUsed <= powerlevel) {
                    toRemove.add(monster);
                }
            }
        }
        for(Monster monster : toRemove){
            monsterList.remove(monster);
        }
        return powerUsed;
    }

    private int getPower(Map gameMap){
        if(gameMap.getValue(heroRC[0], heroRC[1]) == POWERUP){
            Random rand = new Random();
            boolean validLocation = false;
            int row = 0;
            int col = 0;
            while(!validLocation){
                // borders are all walls
                row = rand.nextInt(gameMap.numRows()-2)+1;
                col = rand.nextInt(gameMap.numCols()-2)+1;
                if(gameMap.getValue(row,col) == EMPTY){
                    validLocation = true;
                }
            }
            gameMap.setValue(POWERUP,row,col);
            return 1;
        }
        return 0;
    }

    protected boolean validMove(Map gameMap, Direction direction){
        if(direction == Direction.LEFT) {
            if (gameMap.getValue(heroRC[0], heroRC[1] - 1) == WALL) {
                return false;
            }
        }else if(direction == Direction.UP){
            if(gameMap.getValue(heroRC[0]-1,heroRC[1]) == WALL){
                return false;
            }
        }else if(direction == Direction.RIGHT){
            if(gameMap.getValue(heroRC[0],heroRC[1]+1) == WALL){
                return false;
            }
        }else{
            if(gameMap.getValue(heroRC[0]+1,heroRC[1]) == WALL){
                return false;
            }
        }
        return true;
    }

    protected int monstersLeft(){
        return monsterList.size();
    }
}
