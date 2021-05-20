package com.denesgarda.ProjectBloodMoon.game;

import com.denesgarda.ProjectBloodMoon.Main;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Random;

public class Game {
    public static String username;

    public static void game() throws IOException, SQLException {
        loginSignup:
        while(true) {
            System.out.println("""

                    Login / Signup
                    1) Login
                    2) Signup
                    3) Quit
                    4) Forgot password""");
            String loginSignupInput = Main.consoleInput.readLine();
            if(loginSignupInput.equalsIgnoreCase("1")) {
                username = login();
                if(username != null) {
                    mainMenuLoop:
                    while(true) {
                        System.out.println("""

                    Welcome to Project: Blood Moon
                    ==============================
                    1) Play
                    2) Quit
                    3) How to play (Important)
                    4) Log out
                    ps. Type "/exit" at any time to save and exit.""");
                        String mainMenuInput = Main.consoleInput.readLine();
                        if (mainMenuInput.equalsIgnoreCase("1")) {
                            boolean continueToGame = false;
                            infoLoop:
                            while(true) {
                                System.out.println("Fetching game info...");
                                String progress = getProgress(username);
                                if (progress.equalsIgnoreCase("0")) {
                                    while (true) {
                                        System.out.println("""
                                                                                        
                                                Choose an option to start:
                                                1) Start new game""");
                                        String startOption = Main.consoleInput.readLine();
                                        if (startOption.equalsIgnoreCase("1")) {
                                            System.out.println("Starting game...");
                                            continueToGame = true;
                                            break;
                                        }
                                        else if (startOption.equalsIgnoreCase("/exit")) {
                                            exit();
                                        }
                                        else if (startOption.equalsIgnoreCase("/quit")) {
                                            break infoLoop;
                                        }
                                        else if(startOption.equalsIgnoreCase("/save")) {
                                            System.out.println("Cannot save progress because you are not in a game.");
                                        }
                                        else if(startOption.equalsIgnoreCase("/stats")) {
                                            System.out.println("Cannot view stats because you are not in a game.");
                                        }
                                        else if(startOption.equalsIgnoreCase("/inventory")) {
                                            System.out.println("Cannot view inventory because you are not in a game.");
                                        }
                                        else invalid();
                                    }
                                }
                                else {
                                    while(true) {
                                        System.out.println("""
                                                                                        
                                                Choose an option to start:
                                                1) Continue game
                                                2) Reset progress""");
                                        String startOption = Main.consoleInput.readLine();
                                        if (startOption.equalsIgnoreCase("1")) {
                                            System.out.println("Loading game...");
                                            continueToGame = true;
                                            break;
                                        }
                                        else if (startOption.equalsIgnoreCase("2")) {
                                            System.out.print("You will lose all of your progress if you continue. Enter your password to continue.\nPassword: ");
                                            String cont = Main.consoleInput.readLine();
                                            if (cont.equalsIgnoreCase(getPassword(username))) {
                                                System.out.println("Resetting progress...");
                                                resetProgress(username);
                                                break;
                                            }
                                            else {
                                                System.out.println("Incorrect password.");
                                            }
                                        }
                                        else if (startOption.equalsIgnoreCase("/exit")) {
                                            exit();
                                        }
                                        else if (startOption.equalsIgnoreCase("/quit")) {
                                            break infoLoop;
                                        }
                                        else if(startOption.equalsIgnoreCase("/save")) {
                                            System.out.println("Cannot save progress because you are not in a game.");
                                        }
                                        else if(startOption.equalsIgnoreCase("/stats")) {
                                            System.out.println("Cannot view stats because you are not in a game.");
                                        }
                                        else if(startOption.equalsIgnoreCase("/inventory")) {
                                            System.out.println("Cannot view inventory because you are not in a game.");
                                        }
                                        else invalid();
                                    }
                                }
                                if(continueToGame) {
                                    break;
                                }
                            }
                            if(continueToGame) {
                                System.out.println("THE GAME IS NOT YET AVAILABLE");
                                exit();
                            }
                        }
                        else if(mainMenuInput.equalsIgnoreCase("2")) {
                            exit();
                        }
                        else if(mainMenuInput.equals("3")) {
                            System.out.println("""

                        How to play
                        ===========
                        The game will tell you how to progress the farther you get.
                        ===========
                        There are also keywords, which you can run any time in the game, other than login/signup input. These are the keywords:
                        "/exit" - Save and exit the game
                        "/quit" - Quits to main menu
                        "/save" - Saves progress
                        "/stats" - View your character's stats
                        "/inventory" - Check your inventory
                        
                        (Press [ENTER] to continue)""");
                            Main.consoleInput.readLine();
                        }
                        else if(mainMenuInput.equalsIgnoreCase("4")) {
                            System.out.println("Logging out...");
                            username = "";
                            break;
                        }
                        else if(mainMenuInput.equalsIgnoreCase("/exit")) {
                            exit();
                        }
                        else if(mainMenuInput.equalsIgnoreCase("/quit")) {}
                        else if(mainMenuInput.equalsIgnoreCase("/save")) {
                            System.out.println("Cannot save progress because you are not in a game.");
                        }
                        else if(mainMenuInput.equalsIgnoreCase("/stats")) {
                            System.out.println("Cannot view stats because you are not in a game.");
                        }
                        else if(mainMenuInput.equalsIgnoreCase("/inventory")) {
                            System.out.println("Cannot view inventory because you are not in a game.");
                        }
                        else invalid();
                    }
                }
            }
            else if(loginSignupInput.equalsIgnoreCase("2")) {
                signup();
            }
            else if(loginSignupInput.equalsIgnoreCase("3")) {
                exit();
            }
            else if(loginSignupInput.equalsIgnoreCase("4")) {
                System.out.print("Password reset\nEmail: ");
                String email = Main.consoleInput.readLine();
                if(checkIfEmailExists(email)) {
                    System.out.println("Sending code...");
                    int code = emailCode(email);
                    System.out.print("Enter verification code: ");
                    String enteredString = Main.consoleInput.readLine();
                    try {
                        int entered = Integer.parseInt(enteredString);
                        if(entered == code) {
                            System.out.print("New password: ");
                            String newPassword = Main.consoleInput.readLine();
                            String query = "UPDATE pbm.accounts SET password = \"" + newPassword + "\" WHERE email = \"" + email + "\"";
                            PreparedStatement stmt = Main.conn.prepareStatement(query);
                            stmt.executeUpdate();
                            System.out.println("Password reset! Please log in.");
                        }
                        else {
                            System.out.println("Code is incorrect! Please try again.");
                        }
                    }
                    catch(Exception e) {
                        System.out.println("That is not a valid number! Please try again.");
                    }
                }
                else {
                    System.out.println("That email is not found in our system.");
                }
            }
            else if(loginSignupInput.equalsIgnoreCase("/exit")) {
                exit();
            }
            else if(loginSignupInput.equalsIgnoreCase("/quit")) {
                System.out.println("Cannot quit to main menu.");
            }
            else if(loginSignupInput.equalsIgnoreCase("/save")) {
                System.out.println("Cannot save progress because you are not in a game.");
            }
            else if(loginSignupInput.equalsIgnoreCase("/stats")) {
                System.out.println("Cannot view stats because you are not in a game.");
            }
            else if(loginSignupInput.equalsIgnoreCase("/inventory")) {
                System.out.println("Cannot view inventory because you are not in a game.");
            }
            else invalid();
        }
    }

    public static void invalid() {
        System.out.println("That is not a valid input! Please try again.");
    }

    public static void exit() {
        System.out.println("Thank you for playing! Bye...");
        System.exit(0);
    }
    public static void saveAndExit() {
        //save
        System.out.println("Thank you for playing! Bye...");
        System.exit(0);
    }

    public static String login() throws IOException, SQLException {
        System.out.print("Username: ");
        String username = Main.consoleInput.readLine();
        System.out.print("Password: ");
        String password = Main.consoleInput.readLine();

        Statement stmt = Main.conn.createStatement();
        String query = "SELECT password FROM pbm.accounts WHERE username = \"" + username + "\"";
        ResultSet rs = stmt.executeQuery(query);
        if (rs.next()) {
            if(rs.getString("password").equals(password)) {
                System.out.println("Logging in...");
                return username;
            }
            else {
                System.out.println("Wrong password! Please try again.");
                return null;
            }
        }
        else {
            System.out.println("Username not found! Please try again.");
            return null;
        }
    }
    public static void signup() throws IOException, SQLException {
        System.out.print("Email: ");
        String email = Main.consoleInput.readLine();
        System.out.print("Username: ");
        String username = Main.consoleInput.readLine();
        System.out.print("Password: ");
        String password = Main.consoleInput.readLine();

        Statement stmt = Main.conn.createStatement();
        String query = "SELECT * FROM pbm.accounts WHERE email = \"" + email + "\"";
        ResultSet rs = stmt.executeQuery(query);
        if(rs.next()) {
            System.out.println("Email is taken! Please try again.");
        }
        else {
            Statement stmt2 = Main.conn.createStatement();
            String query2 = "SELECT * FROM pbm.accounts WHERE username = \"" + username + "\"";
            ResultSet rs2 = stmt2.executeQuery(query2);
            if(rs2.next()) {
                System.out.println("Username is taken! Please try again.");
            }
            else {
                while(true) {
                    System.out.println("Pick your character's gender\n1) Male\n2) Female");
                    String gender = Main.consoleInput.readLine();
                    System.out.println("""
                            Pick your character's race. (This cannot be changed later in the game!!!)
                            1) Human
                            2) Elf
                            3) Fairy
                            4) Beastmen""");
                    String race = Main.consoleInput.readLine();
                    if((gender.equalsIgnoreCase("1") || gender.equalsIgnoreCase("2")) && (race.equalsIgnoreCase("1") || race.equalsIgnoreCase("2") || race.equalsIgnoreCase("3") || race.equalsIgnoreCase("4"))) {
                        String query3 = "INSERT INTO pbm.accounts (username, password, email, gender, race, progress) VALUES (?, ?, ?, ?, ?, ?)";
                        PreparedStatement stmt3 = Main.conn.prepareStatement(query3);
                        stmt3.setString(1, username);
                        stmt3.setString(2, password);
                        stmt3.setString(3, email);
                        stmt3.setString(6, "0");
                        if(gender.equalsIgnoreCase("1")) {
                            stmt3.setString(4, "male");
                        }
                        else if(gender.equalsIgnoreCase("2")) {
                            stmt3.setString(4, "female");
                        }
                        if(race.equalsIgnoreCase("1")) {
                            stmt3.setString(5, "Human");
                        }
                        else if(race.equalsIgnoreCase("2")) {
                            stmt3.setString(5, "Elf");
                        }
                        else if(race.equalsIgnoreCase("3")) {
                            stmt3.setString(5, "Fairy");
                        }
                        else if(race.equalsIgnoreCase("4")) {
                            stmt3.setString(5, "Beastmen");
                        }
                        try {
                            stmt3.executeUpdate();
                            System.out.println("Account created! Please log in.");
                            break;
                        }
                        catch(Exception e) {
                            e.printStackTrace();
                            System.out.println("Username cannot be longer than 12 characters! Please try again.");
                            break;
                        }
                    }
                    else {
                        System.out.println("Either the gender or the race is invalid! Please try again.");
                    }
                }
            }
        }
    }

    public static String getProgress(String username) throws SQLException {
        Statement stmt = Main.conn.createStatement();
        String query = "SELECT progress FROM pbm.accounts WHERE username = \"" + username + "\"";
        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        return rs.getString("progress");
    }

    public static String getPassword(String username) throws SQLException {
        Statement stmt = Main.conn.createStatement();
        String query = "SELECT password FROM pbm.accounts WHERE username = \"" + username + "\"";
        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        return rs.getString("password");
    }

    public static void resetProgress(String username) throws SQLException {
        String query = "UPDATE pbm.accounts SET progress = \"0\" WHERE username = \"" + username + "\"";
        PreparedStatement stmt = Main.conn.prepareStatement(query);
        stmt.executeUpdate();
    }

    public static boolean checkIfEmailExists(String email) throws SQLException {
        Statement stmt = Main.conn.createStatement();
        String query = "SELECT * FROM pbm.accounts WHERE email = \"" + email + "\"";
        return stmt.executeQuery(query).next();
    }

    public static int emailCode(String email) {
        Random random = new Random();
        int number = Integer.parseInt(String.format("%06d", random.nextInt(999999)));

        String from = "projectbloodmoon.services";
        String pass = "dpassgmail";
        String[] to = { email }; // list of recipient email addresses
        String subject = "Project: Blood Moon verification code";
        String body = "Your verification code is: " + number;

        sendFromGMail(from, pass, to, subject, body);

        return number;
    }

    public static void sendFromGMail(String from, String pass, String[] to, String subject, String body) {
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for( int i = 0; i < to.length; i++ ) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
    }
}
