package com.company.textui;

import com.company.model.Direction;

import java.util.Scanner;

public class UserInput {
    private Scanner scanner;
    protected UserInput(){
        scanner = new Scanner(System.in);
    }

    protected int getInput(GameUI ui){
        ui.askInput();
        String option = scanner.nextLine();
        option = option.strip().toUpperCase();
        if(option.equals("A")){
            return Direction.LEFT.integer;
        }else if(option.equals("W")){
            return Direction.UP.integer;
        }else if(option.equals("D")){
            return Direction.RIGHT.integer;
        }else if(option.equals("S")){
            return Direction.DOWN.integer;
        }else if(option.equals("?")){
            return 4;
        }else if(option.equals("M")){
            return 5;
        }else if(option.equals("C")){
            return 6;
        }else{
            return -1;
        }
    }
}
