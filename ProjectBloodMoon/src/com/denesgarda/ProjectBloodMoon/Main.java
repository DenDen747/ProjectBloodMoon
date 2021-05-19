package com.denesgarda.ProjectBloodMoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

import static com.denesgarda.ProjectBloodMoon.game.Game.game;

public class Main {
    public static java.sql.Connection conn = null;
    public static Logger logger = Logger.getLogger(Main.class.getName());
    public static BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {
        logger.info("Project: Blood Moon, by Denes G. and Henry K., beta0.1");
        System.out.println("Connecting to server...");
        try {
            conn = DriverManager.getConnection("jdbc:mysql://98.164.253.104:3306/pbm?user=connector&password=dpass");
            java.sql.Connection finalConn = conn;
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("Closing connection...");
                try {
                    finalConn.close();
                    System.out.println("Connection closed.");
                } catch (SQLException ex) {
                    System.out.println("Failed to close the connection.");
                }
            }));
            System.out.println("Connection established.");
            game();
        }
        catch(Exception e) {
            System.out.println("Failed to connect to server. Check your connection and try relaunching the game.");
        }
    }
}
