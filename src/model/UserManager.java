/* UserManager.java
 * This Java file include the UserManager class.
 * It incorporates the program functionality and 
 * connects with the View and MusicStore to retrieve
 * and return information. */


package model;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;

public class UserManager {
    private static final String USER_FILE = "data/users.txt";

    // Exceptions!!! thrown when creating method
    /**
     * Registers a new user by hashing their password and saving it to users.txt
     * @throws InvalidKeySpecException 
     * @throws NoSuchAlgorithmException 
     */
    public boolean registerUser(String username, String password) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        if (userExists(username)) {
            return false;
        }

        try (FileWriter writer = new FileWriter(USER_FILE, true)) {
            // Hash password using PasswordManager (returns "salt:hash")
            String hashedPassword = PasswordManager.hashPassword(password);
            writer.write(username + ":" + hashedPassword + "\n");
            return true;
        }
    }

    /**
     * Verifies login credentials against stored salted hash
     * @throws IOException 
     * @throws FileNotFoundException 
     */
    public boolean loginUser(String username, String password) throws FileNotFoundException, IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            //username:hash
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
        }
		return false;
    }

    /**
     * Loads user's saved library from disk
     * @throws IOException 
     */
    public List<String> loadUserLibrary(String username) throws IOException {
        List<String> library = new ArrayList<>();
        File file = new File("data/" + username + "_library.txt");

        if (!file.exists()) {
            return library;
        }

        // library txt stores songs
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String song;
            while ((song = reader.readLine()) != null) {
                library.add(song);
            }
        }
        return library;
    }

    /**
     * Saves user's current library to disk
     * @throws IOException 
     */
    // save it inside the same file
    public void saveUserLibrary(String username, List<String> library) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/" + username + "_library.txt"))) {
            for (String song : library) {
                writer.write(song);
                writer.newLine();
            }
        }
    }

    /**
     * Checks if the user exists in users.txt
     * @throws IOException 
     * @throws FileNotFoundException 
     */
    public boolean userExists(String username) throws FileNotFoundException, IOException {
    	
    	File file = new File(USER_FILE);
        if (!file.exists()) return false; // prevent unnecessary stack trace
        
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.split(":")[0].equals(username)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Gets the raw hashed password (salt:hash) for a given user
     * @throws IOException 
     */
    // hash method
    public String getHashedPassword(String username) throws IOException {
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
        }
        return null;
    }
    
    // writes for playlists
    public void saveUserPlaylists(String username, List<Playlist> playlists) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/" + username + "_playlists.txt"))) {
            for (Playlist p : playlists) {
                writer.write(p.getName());
                for (Song s : p.getSongs()) {
                	// playlists in format name || songs
                    writer.write(" || " + s.toFileString());
                }
                writer.newLine();
            }
        }
    }
    
    // writes for playlists
    public List<Playlist> loadUserPlaylists(String username) throws FileNotFoundException, IOException {
        List<Playlist> playlists = new ArrayList<>();
        File file = new File("data/" + username + "_playlists.txt");

        if (!file.exists()) return playlists;

        // store playlist in name || songs format
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
        }

        return playlists;
    }


}


