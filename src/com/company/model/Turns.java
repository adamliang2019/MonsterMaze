package com.company.model;

import java.util.Random;
import java.util.ArrayList;
import java.util.Stack;

public class Turns {
    private int[] heroRC = {1,1};
    private ArrayList<Monster> monsterRCList;

    private final int EMPTY = 1;
    private final int HERO = 2;
    private final int MONSTER = 3;
    private final int POWERUP = 4;
    private final int GRAVE = 5;

    public Turns(){
        monsterRCList = new ArrayList<>();
        Monster monster1RC = new Monster(16,11);
        Monster monster2RC = new Monster(16,1);
        Monster monster3RC = new Monster(1,11);
        monsterRCList.add(monster1RC);
        monsterRCList.add(monster2RC);
        monsterRCList.add(monster3RC);
    }

    protected int heroTurn(Map gameMap, int direction, int powerLevel){
        int powerUsed = 0;
        if(direction == 0){
            if(gameMap.getValue(heroRC[0],heroRC[1]-1)==0){
                return 0;
            }
            gameMap.setValue(EMPTY,heroRC[0],heroRC[1]);
            heroRC[1]--;
            powerUsed -= getPower(gameMap);
            powerUsed += fightMonsters();
            if(powerLevel<powerUsed){
                gameMap.setValue(GRAVE,heroRC[0],heroRC[1]);
            }else{
                gameMap.setValue(HERO,heroRC[0],heroRC[1]);
            }
        }else if(direction == 1){
            if(gameMap.getValue(heroRC[0]-1,heroRC[1])==0){
                return 0;
            }
            gameMap.setValue(EMPTY,heroRC[0],heroRC[1]);
            heroRC[0]--;
            powerUsed -= getPower(gameMap);
            powerUsed += fightMonsters();
            if(powerLevel<powerUsed){
                gameMap.setValue(GRAVE,heroRC[0],heroRC[1]);
            }else{
                gameMap.setValue(HERO,heroRC[0],heroRC[1]);
            }
        }else if(direction == 2){
            if(gameMap.getValue(heroRC[0],heroRC[1]+1)==0){
                return 0;
            }
            gameMap.setValue(EMPTY,heroRC[0],heroRC[1]);
            heroRC[1]++;
            powerUsed -= getPower(gameMap);
            powerUsed += fightMonsters();
            if(powerLevel<powerUsed){
                gameMap.setValue(GRAVE,heroRC[0],heroRC[1]);
            }else{
                gameMap.setValue(HERO,heroRC[0],heroRC[1]);
            }
        }else if(direction == 3){
            if(gameMap.getValue(heroRC[0]+1,heroRC[1])==0){
                return 0;
            }
            gameMap.setValue(EMPTY,heroRC[0],heroRC[1]);
            heroRC[0]++;
            powerUsed -= getPower(gameMap);
            powerUsed += fightMonsters();
            if(powerLevel<powerUsed){
                gameMap.setValue(GRAVE,heroRC[0],heroRC[1]);
            }else{
                gameMap.setValue(HERO,heroRC[0],heroRC[1]);
            }
        }
        return powerUsed;
    }

    protected int monsterTurn(Map gameMap, int powerLevel){
        Random rand = new Random();
        int totalPowerUsed = 0;
        Stack<Monster> monsterStack = new Stack<Monster>();
        monsterStack.addAll(monsterRCList);
        while(!monsterStack.isEmpty()){
            Monster monster = monsterStack.pop();
            gameMap.setValue(EMPTY,monster.getRow(),monster.getCol());
            int[] newPosition = monster.randomValidMove(gameMap,rand);
            monster.move(newPosition[0],newPosition[1]);
            int powerUsed = fightMonsters();
            totalPowerUsed += powerUsed;
            if(powerUsed==0){
                gameMap.setValue(MONSTER,monster.getRow(),monster.getCol());
            }else {
                if (powerUsed > powerLevel) {
                    gameMap.setValue(GRAVE,monster.getRow(),monster.getCol());
                }
            }
        }

        return totalPowerUsed;
    }

    private int fightMonsters(){
        int powerUsed = 0;
        ArrayList<Monster> toRemove = new ArrayList<>();
        for(Monster monster : monsterRCList) {
            if (monster.getRow() == heroRC[0] && monster.getCol() == heroRC[1]) {
                powerUsed++;
                toRemove.add(monster);
            }
        }
        for(Monster monster : toRemove){
            monsterRCList.remove(monster);
        }
        return powerUsed;
    }

    private int getPower(Map gameMap){
        if(gameMap.getValue(heroRC[0], heroRC[1]) == 3){
            Random rand = new Random();
            boolean validLocation = false;
            int row = 0;
            int col = 0;
            while(!validLocation){
                // borders are all walls
                row = rand.nextInt(gameMap.numRows()-2)+1;
                col = rand.nextInt(gameMap.numCols()-2)+1;
                if(gameMap.getValue(row,col)==1){
                    validLocation = true;
                }
            }
            gameMap.setValue(POWERUP,row,col);
            return 1;
        }
        return 0;
    }

    protected boolean validMove(Map gameMap, int direction){
        if(direction == 0) {
            if (gameMap.getValue(heroRC[0], heroRC[1] - 1) == 0) {
                return false;
            }
        }else if(direction == 1){
            if(gameMap.getValue(heroRC[0]-1,heroRC[1])==0){
                return false;
            }
        }else if(direction == 2){
            if(gameMap.getValue(heroRC[0],heroRC[1]+1)==0){
                return false;
            }
        }else{
            if(gameMap.getValue(heroRC[0]+1,heroRC[1])==0){
                return false;
            }
        }
        return true;
    }

    protected int monstersLeft(){
        return monsterRCList.size();
    }
}
