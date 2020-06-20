package com.company.model;

import java.util.Random;
import java.util.ArrayList;

public class Turns {
    int[] heroRC = {1,1};
    ArrayList<int[]> monsterRCList;

    final int EMPTY = 1;
    final int HERO = 2;
    final int MONSTER = 3;
    final int POWERUP = 4;
    final int GRAVE = 5;

    public Turns(){
        monsterRCList = new ArrayList<>();
        int[] monster1RC = {16,11};
        int[] monster2RC = {16,1};
        int[] monster3RC = {1,11};
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
        for(int[] monster : monsterRCList){
            ArrayList<int[]> newPositions = new ArrayList<>();
            if(gameMap.getValue(monster[0]+1,monster[1])!=0){
                int[] newPosition = {monster[0]+1,monster[1]};
                newPositions.add(newPosition);
            }
            if(gameMap.getValue(monster[0]-1,monster[1])!=0){
                int[] newPosition = {monster[0]-1,monster[1]};
                newPositions.add(newPosition);
            }
            if(gameMap.getValue(monster[0],monster[1]+1)!=0){
                int[] newPosition = {monster[0],monster[1]+1};
                newPositions.add(newPosition);
            }
            if(gameMap.getValue(monster[0],monster[1]-1)!=0){
                int[] newPosition = {monster[0],monster[1]-1};
                newPositions.add(newPosition);
            }
            int[] newPosition = newPositions.get(rand.nextInt(newPositions.size()));
            gameMap.setValue(EMPTY,monster[0],monster[1]);
            monster = newPosition;
            int powerUsed = fightMonsters();
            totalPowerUsed += powerUsed;
            if(powerUsed==0){
                gameMap.setValue(MONSTER,monster[0],monster[1]);
            }else {
                if (powerUsed <= powerLevel) {
                    monsterRCList.remove(monster);
                } else {
                    gameMap.setValue(GRAVE,monster[0],monster[1]);
                }
            }

        }
        return totalPowerUsed;
    }

    private int fightMonsters(){
        int powerUsed = 0;
        ArrayList<int[]> toRemove = new ArrayList<>();
        for(int[] monster : monsterRCList) {
            if (monster[0] == heroRC[0] && monster[1] == heroRC[1]) {
                powerUsed++;
                toRemove.add(monster);
            }
        }
        for(int[] monster : toRemove){
            monsterRCList.remove(monster);
        }
        return  powerUsed;
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
}
