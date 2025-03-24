

package model;

import java.io.*;
import java.util.*;

public class UserManager {
    private static final String USER_FILE = "users.txt";

    /**
     * Registers a new user by hashing their password and saving it to users.txt
     */
    public boolean registerUser(String username, String password) {
        if (userExists(username)) {
            return false;
        }

        try (FileWriter writer = new FileWriter(USER_FILE, true)) {
            // Hash password using PasswordManager (returns "salt:hash")
            String hashedPassword = PasswordManager.hashPassword(password);
            writer.write(username + ":" + hashedPassword + "\n");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Verifies login credentials against stored salted hash
     */
    public boolean loginUser(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(username + ":")) {
                    String[] parts = line.split(":");
                    if (parts.length == 3) {
                        String salt = parts[1];
                        String storedHash = parts[2];
                        return PasswordManager.verifyPassword(password, salt, storedHash);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Loads user's saved library from disk
     */
    public List<String> loadUserLibrary(String username) {
        List<String> library = new ArrayList<>();
        File file = new File(username + "_library.txt");

        if (!file.exists()) {
            return library;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String song;
            while ((song = reader.readLine()) != null) {
                library.add(song);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return library;
    }

    /**
     * Saves user's current library to disk
     */
    public void saveUserLibrary(String username, List<String> library) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(username + "_library.txt"))) {
            for (String song : library) {
                writer.write(song);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if the user exists in users.txt
     */
    public boolean userExists(String username) {
    	
    	File file = new File(USER_FILE);
        if (!file.exists()) return false; // prevent unnecessary stack trace
        
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.split(":")[0].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Gets the raw hashed password (salt:hash) for a given user
     */
    public String getHashedPassword(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(username + ":")) {
                    String[] parts = line.split(":", 3);
                    if (parts.length == 3) {
                        return parts[1] + ":" + parts[2];
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public void saveUserPlaylists(String username, List<Playlist> playlists) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(username + "_playlists.txt"))) {
            for (Playlist p : playlists) {
                writer.write(p.getName());
                for (Song s : p.getSongs()) {
                    writer.write(" || " + s.toFileString());
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public List<Playlist> loadUserPlaylists(String username) {
        List<Playlist> playlists = new ArrayList<>();
        File file = new File(username + "_playlists.txt");

        if (!file.exists()) return playlists;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(" \\|\\| ");
                if (tokens.length >= 1) {
                    Playlist playlist = new Playlist(tokens[0]);
                    for (int i = 1; i < tokens.length; i++) {
                        Song s = Song.fromFileString(tokens[i]);
                        if (s != null) {
                            playlist.addSong(s);
                        }
                    }
                    playlists.add(playlist);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return playlists;
    }


}


