package com.denesgarda.ProjectBloodMoon.game.data;

import com.denesgarda.ProjectBloodMoon.Main;
import com.denesgarda.ProjectBloodMoon.game.Game;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Strings {
    public static boolean println(String string) throws IOException {
        /*Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("\nYou've been idle for 30 seconds. Applying safe mode settings...");
            }
        };
        timer.schedule(timerTask, 30000);**/
        while(true) {
            System.out.println(string);
            String choiceString = Main.consoleInput.readLine();
            //timer.cancel();
            if (choiceString.equalsIgnoreCase("/exit")) {
                Game.exit();
            } else if (choiceString.equalsIgnoreCase("/quit")) {
                return true;
            } else if (choiceString.equalsIgnoreCase("/stats")) {
                Game.stats.printStats();
            } else if (choiceString.equalsIgnoreCase("/inventory")) {
                Game.stats.printInventory();
            } else {
                break;
            }
        }
        return false;
    }
    public static int dialogue(String message, String @NotNull [] choices) throws IOException {
        /*Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("\nYou've been idle for 30 seconds. Applying safe mode settings...");
            }
        };
        timer.schedule(timerTask, 30000);*/
        while(true) {
            System.out.println(message);
            for (int i = 0; i < choices.length; i++) {
                System.out.println((i + 1) + ") " + choices[i]);
            }
            String choiceString = Main.consoleInput.readLine();
            //timer.cancel();
            if(choiceString.equalsIgnoreCase("/exit")) {
                Game.exit();
            }
            else if(choiceString.equalsIgnoreCase("/quit")) {
                return 0;
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

    public static void youDied() throws IOException {
        System.out.println("You died!\n(Press [ENTER] to go back to checkpoint)");
        String choiceString = Main.consoleInput.readLine();
        if(choiceString.equalsIgnoreCase("/exit")) {
            Game.exit();
        }
        else if(choiceString.equalsIgnoreCase("/quit")) {
            System.out.println("Cannot quit at this moment!");
        }
        System.out.println("Loading...");
    }
}