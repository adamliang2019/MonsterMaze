package com.company.model;

public class Game {
    Map gameMap;
    Turns turns;
    int monsters;
    int pointsRemaining;
    int powerLevel;

    public Game(){
        monsters = 3;
        pointsRemaining = 3;
        powerLevel = 0;
        MapGenerator mapGenerator = new MapGenerator();
        gameMap = new Map(mapGenerator.generateMaze(18,13));
        turns = new Turns();
    }

    public int[][] getMap(){
        return gameMap.getDisplayMap();
    }

    public void fullyExplore(){
        gameMap.fullyExplore();
    }

    public boolean advanceTurn(int direction){
        int powerUsed = turns.heroTurn(gameMap, direction, powerLevel);
        powerLevel -= powerUsed;
        if(powerLevel < 0){
            return false;
        }
        powerUsed = turns.monsterTurn(gameMap, powerLevel);
        powerLevel -= powerUsed;
        if(powerLevel < 0){
            return false;
        }
        return true;
    }

    public boolean validTurn(int direction){
        return turns.validMove(gameMap, direction);
    }
}
