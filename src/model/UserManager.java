

package model;

import java.io.*;
import java.util.*;

public class UserManager {
    private static final String USER_FILE = "users.txt";

    public boolean registerUser(String username, String password) {
        if (userExists(username)) {
            return false;
        }
        try (FileWriter writer = new FileWriter(USER_FILE, true)) {
            String hashedPassword = PasswordManager.hashPassword(password);
            writer.write(username + ":" + hashedPassword + "\n");
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean loginUser(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts[0].equals(username)) {
                    String storedHash = line.substring(username.length() + 1); // Get the hash portion
                    return PasswordManager.verifyPassword(password, storedHash);
                }
            }
        } catch (IOException e) {
        }
        return false;
    }

    public void saveUserLibrary(String username, List<String> library) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(username + "_library.txt"))) {
            for (String song : library) {
                writer.write(song);
                writer.newLine();
            }
        } catch (IOException e) {
        }
    }

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
}


