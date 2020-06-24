package com.company.model;

/***
 * Class Game models an instance of a maze game
 * Data:
 * Map gameMap
 * Turns turns
 * int number of monsters alive
 * int number of points needed to win
 * int power level of hero
 * boolean if the game is won
 *
 * Constructors instantiates all fields (uses MapGenerator to create new map for Map class
 * Methods:
 * Cell[][] getMap() returns display map of Map class
 * void fullyExplore reveals all cells of map
 * boolean advanceTurn  calls advances turn of hero and monsters, returns false if hero dies, also updates local fields ex. points,powerlevel,...
 * getters for monsters, pointsNeeded, powerLevel, and gameWon
 * cheat function sets points Needed to 1
 * can also check if a direction is valid
 *
 */

public class Game {
    private Map gameMap;
    private Turns turns;
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
        gameMap = new Map(mapGenerator.generateMaze(20,15));
        turns = new Turns(gameMap);
    }

    public Cell[][] getMap(){
        return gameMap.getDisplayMap();
    }

    public void fullyExplore(){
        gameMap.fullyExplore();
    }

    public boolean advanceTurn(int directionInt){
        Direction direction = Direction.valueOfInteger(directionInt);
        int powerUsed = turns.heroTurn(gameMap, direction, powerLevel);
        powerLevel -= powerUsed;
        if(powerLevel < 0){
            powerLevel = 0;
            return false;
        }
        powerUsed = turns.monsterTurn(gameMap, powerLevel);
        powerLevel -= powerUsed;
        if(powerLevel < 0){
            powerLevel = 0;
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

    public boolean validTurn(int directionInt){
        Direction direction = Direction.valueOfInteger(directionInt);
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

    public boolean gameWon(){
        return gameWon;
    }
}
