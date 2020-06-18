package com.company.model;

import java.util.ArrayList;

public class Turns {
    int[] heroRC = {1,1};
    ArrayList<int[]> monsterRCList;
    final int HERO = 2;
    final int EMPTY = 1;
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
        int powerUsed = 0;
        return powerUsed;
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
