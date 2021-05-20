package com.denesgarda.ProjectBloodMoon.game.data;

import com.denesgarda.ProjectBloodMoon.Main;
import com.denesgarda.ProjectBloodMoon.game.Game;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class Strings {
    public static void println(String string) throws IOException {
        while(true) {
            System.out.println(string);
            String choiceString = Main.consoleInput.readLine();
            if (choiceString.equalsIgnoreCase("/exit")) {
                Game.saveAndExit();
            } else if (choiceString.equalsIgnoreCase("/quit")) {
                System.out.println("\nCannot quit at this moment! Please do it when you are given a choice dialogue.\n");
            } else if (choiceString.equalsIgnoreCase("/save")) {
                Game.save();
            } else if (choiceString.equalsIgnoreCase("/stats")) {
                Game.stats.printStats();
            } else if (choiceString.equalsIgnoreCase("/inventory")) {
                Game.stats.printInventory();
            } else {
                break;
            }
        }
    }
    public static int dialogue(String message, String @NotNull [] choices) throws IOException {
        while(true) {
            System.out.println(message);
            for (int i = 0; i < choices.length; i++) {
                System.out.println((i + 1) + ") " + choices[i]);
            }
            String choiceString = Main.consoleInput.readLine();
            if(choiceString.equalsIgnoreCase("/exit")) {
                Game.saveAndExit();
            }
            else if(choiceString.equalsIgnoreCase("/quit")) {
                return 0;
            }
            else if(choiceString.equalsIgnoreCase("/save")) {
                Game.save();
            }
            else if(choiceString.equalsIgnoreCase("/stats")) {
                Game.stats.printStats();
            }
            else if(choiceString.equalsIgnoreCase("/inventory")) {
                Game.stats.printInventory();
            }
            else {
                try {
                    int choice = Integer.parseInt(choiceString);
                    if (choice > 0 && choice <= choices.length) {
                        return choice;
                    } else {
                        Game.invalid();
                    }
                } catch (Exception e) {
                    Game.invalid();
                }
            }
        }
    }

    public static String[] stringToArray(String string) {
        try {
            return string.split(", ");
        }
        catch(Exception e) {
            return new String[]{};
        }
    }
}