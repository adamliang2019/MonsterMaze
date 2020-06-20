package com.company.textui;
import com.company.model.Game;

public class Main {

    public static void main(String[] args) {
        Game game = new Game();
        GameUI ui = new GameUI();
        UserInput inputHelper = new UserInput();
        boolean gameActive = true;
        while(gameActive) {
            ui.printMap(game.getMap());
            ui.printStats(game);
            int input = inputHelper.getInput(ui);
            if (input < 4 && game.validTurn(input)) {
                gameActive = game.advanceTurn(input);
            }
        }
        ui.printMap(game.getMap());
    }


}
