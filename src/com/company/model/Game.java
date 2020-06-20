package com.company.model;

public class Game {
    Map gameMap;
    Turns turns;

    private int monsters;
    private int pointsNeeded;
    private int powerLevel;
    private boolean gameWon;

    public Game(){
        monsters = 3;
        pointsNeeded = 3;
        powerLevel = 0;
        gameWon = false;
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
        int monstersLeft = turns.monstersLeft();
        pointsNeeded -= monsters - monstersLeft;
        monsters = monstersLeft;
        if(pointsNeeded <= 0){
            gameWon = true;
        }
        return true;
    }

    public boolean validTurn(int direction){
        return turns.validMove(gameMap, direction);
    }

    public void cheat(){
        pointsNeeded = 1;
    }

    public int getMonsters() {
        return monsters;
    }

    public int getPointsRemaining() {
        return pointsNeeded;
    }

    public int getPowerLevel() {
        return powerLevel;
    }
}
