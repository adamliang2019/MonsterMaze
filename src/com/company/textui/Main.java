package com.company.textui;
import com.company.model.Game;

public class Main {

    public static void main(String[] args) {
        Game newGame = new Game();
        GameUI ui = new GameUI();
        ui.printMap(newGame.getMap());
    }

}
