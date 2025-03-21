package model;


import java.io.*;
import java.util.ArrayList;
import java.util.List;



public class UserManager {
    private static final String USER_FILE = "users.txt";

    //register a new user
    public boolean registerUser(String username, String password) {
        if (userExists(username)) {
            System.out.println("User already exists.");
            return false;
        }

       // password hash before storing
        String hashedPassword = PasswordManager.hashPassword(password);

        try (FileWriter writer = new FileWriter(USER_FILE, true)) {
            writer.write(username + ":" + hashedPassword + "\n");
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    //check if user exists
    private boolean userExists(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.split(":")[0].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
        }
        return false;
    }

    //user login
    public boolean loginUser(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2 && parts[0].equals(username)) {
                    return PasswordManager.verifyPassword(password, parts[1]);
                }
            }
        } catch (IOException e) {
        }
        return false;
    }

    //save user's library
    public void saveUserLibrary(String username, List<String> library) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(username + "_library.txt"))) {
            for (String song : library) {
                writer.write(song);
                writer.newLine();
            }
        } catch (IOException e) {
        }
    }

    //load user's library
    public List<String> loadUserLibrary(String username) {
        List<String> library = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(username + "_library.txt"))) {
            String song;
            while ((song = reader.readLine()) != null) {
                library.add(song);
            }
        } catch (IOException e) {
        }
        return library;
    }
}



