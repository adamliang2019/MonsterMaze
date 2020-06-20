package com.company.textui;
import com.company.model.Game;

public class Main {

    public static void main(String[] args) {
        Game game = new Game();
        GameUI ui = new GameUI();
        UserInput inputHelper = new UserInput();
        boolean gameActive = true;
        boolean updated = true;

        ui.printInstructions();
        while(gameActive) {
            if(updated) {
                ui.printMap(game.getMap());
                ui.printStats(game);
                updated = false;
            }
            int input = inputHelper.getInput(ui);
            if (input < 4) {
                if(game.validTurn(input)) {
                    gameActive = game.advanceTurn(input);
                    updated = true;
                }else{
                    ui.invalidDirection();
                }
            }else if(input == 4){
                ui.printInstructions();
            }else if(input == 5){
                game.fullyExplore();
                updated = true;
            }else if(input == 6){
                game.cheat();
                ui.cheat();
            }else{
                ui.invalidOption();
            }
        }
        game.fullyExplore();
        ui.printMap(game.getMap());
        ui.printStats(game);
        if(game.gameWon()){
            ui.gameWon();
        }else{
            ui.gameLost();
        }
    }


}
