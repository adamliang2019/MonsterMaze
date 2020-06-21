package com.company.textui;

import static com.company.model.Direction.*;
import java.util.Scanner;

/***
 * UserInput Class contains functions required for taking user input
 * contains a Scanner object
 * has method for taking option
 */


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
            return LEFT.integer;
        }else if(option.equals("W")){
            return UP.integer;
        }else if(option.equals("D")){
            return RIGHT.integer;
        }else if(option.equals("S")){
            return DOWN.integer;
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
