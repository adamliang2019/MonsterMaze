package com.company.textui;

import java.util.Scanner;

public class UserInput {
    Scanner scanner;
    protected UserInput(){
        scanner = new Scanner(System.in);
    }

    protected int getInput(GameUI ui){
        ui.askInput();
        String option = scanner.nextLine();
        option = option.strip().toUpperCase();
        if(option.equals("A")){
            return 0;
        }else if(option.equals("W")){
            return 1;
        }else if(option.equals("D")){
            return 2;
        }else if(option.equals("S")){
            return 3;
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
