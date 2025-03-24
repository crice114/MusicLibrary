/* The view was coded using generative AI. */

/**
 * LibraryView.java
 *
 * This class represents the **View** component in the MVC architecture.
 * It provides a **text-based user interface** that allows users to interact
 * with their music library by searching, adding songs, and managing playlists.
 *
 * Responsibilities:
 * - Displays a **menu** for user interaction.
 * - Accepts **user input** and validates it.
 * - Calls methods from **LibraryModel** to retrieve and modify data.
 * - Displays **formatted results** to the user.
 */

package view;

import model.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;


public class LibraryView {
    // VARIABLES
    private final LibraryModel model;
    private final MusicStore musicStore;
    private final Scanner scanner;
    private User currentUser; // Track the logged-in user

    // CONSTRUCTOR
    public LibraryView(LibraryModel model, MusicStore musicStore) {
        this.model = model;
        this.musicStore = musicStore;
        this.scanner = new Scanner(System.in);
    }
   
    private boolean login() throws FileNotFoundException, IOException {
        System.out.println("\n🔐 Please log in to access your music library.");

        System.out.print("👤 Username: ");
        String username = scanner.nextLine().trim();

        System.out.print("🔑 Password: ");
        String password = scanner.nextLine().trim();

        UserManager userManager = new UserManager();

        if (userManager.loginUser(username, password)) {
            String hashedPassword = userManager.getHashedPassword(username);
            this.currentUser = new User(username, hashedPassword);
            model.addUser(currentUser); // Add to in-memory user list

            // Clear any previous session data
            model.clearLibrary();

            // Load this user's saved library
            List<String> savedSongs = userManager.loadUserLibrary(username);
            
            for (String songStr : savedSongs) {
            	Song loaded = Song.fromFileString(songStr);
            	String genre = "";
            	int year = 0;
            	if (loaded != null) {
            		for (Album a : musicStore.getAlbums()) {
            			if (loaded.getAlbum().equals(a.getTitle())) {
            				genre = a.getGenre();
            				year = a.getYearReleased();
            				break;
            			}
            		}
            	    model.addSong(loaded, genre, year);
            	}
            }
            
       	 // 🔽 Load user's saved playlists
            List<Playlist> loadedPlaylists = userManager.loadUserPlaylists(username);
            model.addPlaylists(loadedPlaylists);

            System.out.println("\n✅ Login successful! Welcome, " + username + "!");
            return true;
        } else {
            System.out.println("\n❌ Login failed. Please enter a valid username and password.");
            return false;
        }
    }

    private void createUser() throws FileNotFoundException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        System.out.println("\n🔑 Create a new user account:");

        String username = null;
        UserManager userManager = new UserManager();
		while (username == null) {
            System.out.print("👤 Enter username (4-20 characters, no spaces): ");
            username = scanner.nextLine().trim();

            // Username validation: must be between 4 and 20 characters and no spaces
            if (username.isEmpty()) {
                System.out.println("❌ Username cannot be empty.");
                username = null;
            } else if (username.length() < 4 || username.length() > 20) {
                System.out.println("❌ Username must be between 4 and 20 characters.");
                username = null;
            } else if (userManager.userExists(username)) {
                System.out.println("\n❌ Username already taken. Please choose another.");
                username = null;
            } else if (username.contains(" ")) {
                System.out.println("❌ Username cannot contain spaces.");
                username = null;
            }
        }

        String password = null;
        while (password == null) {
            System.out.print("🔑 Enter password (min 8 characters): ");
            password = scanner.nextLine().trim();

            // Password validation: must be at least 8 characters long
            if (password.isEmpty()) {
                System.out.println("❌ Password cannot be empty.");
                password = null;
            } else if (password.length() < 8) {
                System.out.println("❌ Password must be at least 8 characters.");
                password = null;
            } else if (!password.matches(".*[A-Za-z].*") || !password.matches(".*\\d.*")) {
                System.out.println("❌ Password must contain both letters and numbers.");
                password = null;
            }
        }

        // Register user using UserManager (handles salting + hashing + file write)
        boolean success = userManager.registerUser(username, password);

        if (success) {
            // Store User in model memory with hashed password
            String hashedPassword = userManager.getHashedPassword(username);
            User newUser = new User(username, hashedPassword);
            model.addUser(newUser);

            System.out.println("\n✅ User created successfully! You can now log in.");
        } else {
            System.out.println("\n❌ Failed to create user. Try again.");
        }
    }



    private void logout() throws IOException {
        if (currentUser != null) {
            UserManager userManager = new UserManager();
            List<String> songStrings = model.getSongs().stream()
                    .map(Song::toFileString) // Assumes Song.toString() gives "Title - Artist | Album"
                    .toList();
            /*
            List<String> songStrings = model.getSongs().stream()
                .map(Song::toString) // Assumes Song.toString() gives "Title - Artist | Album"
                .toList();
                */
            userManager.saveUserLibrary(currentUser.getUsername(), songStrings);
            userManager.saveUserPlaylists(currentUser.getUsername(), model.getUserPlaylists());

        }
        currentUser = null;
        model.clearLibrary(); // Clear in-memory data
        System.out.println("\n🔒 You have been logged out.");
    }

    /////////////////////////////////////////////////////////////////////////
    // MENU OPTION
    public void start() throws FileNotFoundException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        boolean loggedIn = false;

        // Prompt user to log in first or create a new account
        while (!loggedIn) {
            System.out.println("\n1. Log In");
            System.out.println("2. Create New Account");
            System.out.print("Enter your choice: ");
            int choice = getIntInput();

            switch (choice) {
                case 1:
                    loggedIn = login();
                    break;
                case 2:
                    createUser();
                    break;
                default:
                    System.out.println("\n❌ Invalid choice. Please try again.");
            }
        }

        // This loop continues while the user is logged in
        while (loggedIn) {
            System.out.println("\n🎵 Welcome to Your Music Library 🎵");
            System.out.println("1. Search for a song in library");
            System.out.println("2. Search for an album in library");
            System.out.println("3. Search by artist in library");
            System.out.println("4. Search by genre in library");
            System.out.println("5. Add a song from store to library");
            System.out.println("6. Play song from library");
            System.out.println("7. Add an album from store to library");
            System.out.println("8. Search for a song in our store");
            System.out.println("9. Search for an album in our store");
            System.out.println("10. Search by artist from our store");
            System.out.println("11. Search by genre from our store");
            System.out.println("12. Search for a playlist");
            System.out.println("13. Create a new playlist");
            System.out.println("14. Add song to playlist");
            System.out.println("15. Remove song from playlist");
            System.out.println("16. Rate a song");
            System.out.println("17. Mark song as favorite");
            System.out.println("18. View all songs");
            System.out.println("19. View all artists");
            System.out.println("20. View all albums");
            System.out.println("21. View all playlists");
            System.out.println("22. View favorite songs");
            System.out.println("23. Remove a song from library");
            System.out.println("24. Remove an album from library");
            System.out.println("25. Sort songs");
            System.out.println("26. Shuffle songs");
            System.out.println("27. Shuffle a Playlist");
            System.out.println("28. Log out");  // New log-out option
            System.out.println("29. Exit");

            System.out.print("Enter your choice: ");

            // USER INPUT
            int choice = getIntInput();

            // CALL METHODS
            switch (choice) {
                case 1 -> searchSongLibrary();
                case 2 -> searchAlbumLibrary();
                case 3 -> searchByArtistLibrary();
                case 4 -> searchByGenreLibrary();
                case 5 -> addSong();
                case 6 -> playSong();
                case 7 -> addAlbum();
                case 8 -> searchSong();
                case 9 -> searchAlbum();
                case 10 -> searchByArtist();
                case 11 -> searchByGenre();
                case 12 -> searchPlaylist();
                case 13 -> createPlaylist();
                case 14 -> addSongToPlaylist();
                case 15 -> removeSongFromPlaylist();
                case 16 -> rateSong();
                case 17 -> markSongAsFavorite();
                case 18 -> listSongs();
                case 19 -> listArtists();
                case 20 -> listAlbums();
                case 21 -> listPlaylists();
                case 22 -> listFavoriteSongs();
                case 23 -> removeSong();
                case 24 -> removeAlbum();
                case 25 -> listSongsBySorting();
                case 26 -> shuffleLibrary();
                case 27 -> shufflePlaylist();
                case 28 -> {
                    logout();  // Log the user out
                    loggedIn = false; // Set loggedIn to false to return to the login screen
                    break;  // Exit to login screen
                }
                
                case 29 -> {
                    if (currentUser != null) {
                        UserManager userManager = new UserManager();
                        List<String> songStrings = model.getSongs().stream()
                                .map(Song::toFileString)
                                .toList();
                        /*
                        List<String> songStrings = model.getSongs().stream()
                            .map(Song::toString)
                            .toList();
                            */
                        userManager.saveUserLibrary(currentUser.getUsername(), songStrings);
                        userManager.saveUserPlaylists(currentUser.getUsername(), model.getUserPlaylists());

                    }
                    System.out.println("\n👋 Thank you for using the Music Library!");
                    return;
                }
                default -> System.out.println("\n❌ Invalid choice. Please try again.");
            }

            // After logging out, loop back to the login screen
            if (!loggedIn) {
                System.out.println("\nPlease log in again.");
                start(); // Restart the login process
            }
        }
    }
    
    private void searchSongLibrary() {
        System.out.print("\n🔍 Enter song title or artist: ");
        String title = scanner.nextLine();
        List<Song> results = model.getSongsByTitleOrArtist(title);
        displaySearchResults(results, "song");

        // If results exist, ask if the user wants album details
        if (!results.isEmpty()) {
            System.out.print("\n📀 Would you like to see album details? (y/n): ");
            String response = scanner.nextLine().trim().toLowerCase();
            
            if (response.equals("y")) {
                for (Song song : results) {
                    String albumTitle = song.getAlbum();
                    
                    // Retrieve albums by title (returns a list)
                    List<Album> albums = model.getAlbumsByTitleOrArtist(albumTitle);
                    
                    if (albums.isEmpty()) {
                        System.out.println("\n❌ No album information found for: " + albumTitle);
                    } else {
                        for (Album album : albums) {
                        	boolean inLibrary = model.albumInLibrary(album); // Check if in user library
                        	System.out.println(album.toString());
                            System.out.println("   📚 In Your Library: " + (inLibrary ? "✅ Yes" : "❌ No"));
                        }
                    }
                }
            }
        }
    }

    // #4 SEARCH ALBUM
    private void searchAlbumLibrary() {
        System.out.print("\n🔍 Enter album title or artist: ");
        String title = scanner.nextLine();
        List<Album> results = model.getAlbumsByTitleOrArtist(title);
        displaySearchResults(results, "album");
    }

    // #5 SEARCH BY ARTIST
    private void searchByArtistLibrary() {
        System.out.print("\n🔍 Enter artist name: ");
        String artistName = scanner.nextLine();

        List<Song> songResults = model.getSongsByTitleOrArtist(artistName);
        List<Album> albumResults = model.getAlbumsByTitleOrArtist(artistName);

        if (albumResults.isEmpty() && songResults.isEmpty()) {
            System.out.println("❌ No results found for artist: " + artistName);
        } else {
            System.out.println("\n🎤 Artist: " + artistName);
            if (!albumResults.isEmpty()) {
                System.out.println("\n📀 Albums:");
                for (Album album : albumResults) {
                    System.out.println("   📀 " + album.getTitle());
                }
            }
            if (!songResults.isEmpty()) {
                System.out.println("\n🎵 Songs:");
                for (Song song : songResults) {
                    System.out.println("   🎶 " + song.getTitle() + " (from " + song.getAlbum() + ")");
                }
            }
        }
    }
    
 // #7 SEARCH BY GENRE
    private void searchByGenreLibrary() {
        System.out.print("\n🔍 Enter genre: ");
        String genre = scanner.nextLine().trim(); // Get the genre input

        List<Song> songResults = model.getSongsByGenre(genre);

        if (songResults.isEmpty()) {
            System.out.println("❌ No results found for genre: " + genre);
        } else {
            System.out.println("\n🎤 Genre: " + genre);
            // Output the song results
            System.out.println("\n🎵 Songs:");
            for (Song song : songResults) {
                System.out.println("   🎶 " + song.getTitle() + " (from " + song.getAlbum() + ")");
            }
        }
    }

    // #1 ADD SONG
    private void addSong() {
        System.out.print("\n🎵 Enter song title to add: ");
        String title = scanner.nextLine();
        
        String result = model.addSong(title, musicStore, null);
        System.out.println(result);
        
        int numSongs = musicStore.getSongsByTitleOrArtist(title).size();

        // If multiple songs are found, ask the user for the artist
        if (numSongs > 1) {
            System.out.print("Please specify the artist: ");
            String artistName = scanner.nextLine().trim();

            // Call the model's addSong method again with the artist name
            result = model.addSong(title, musicStore, artistName);
            System.out.println(result);
        }
    }
    
    // #2 PLAY SONG
    private void playSong() {
        System.out.print("\n🎵 Enter song title to play: ");
        String title = scanner.nextLine();
        
        String result = model.playSong(title, null);
        System.out.println(result);
        
        int numSongs = musicStore.getSongsByTitleOrArtist(title).size();

        // If multiple songs are found, ask the user for the artist
        if (numSongs > 1) {
            System.out.print("Please specify the artist: ");
            String artistName = scanner.nextLine().trim();

            // Call the model's addSong method again with the artist name
            result = model.playSong(title, artistName);
            System.out.println(result);
        }
    }

    // #2 ADD ALBUM
    private void addAlbum() {
        System.out.print("\n📀 Enter album title to add: ");
        String title = scanner.nextLine();
        
        String result = model.addAlbum(title, musicStore, null);
        System.out.println(result);
        
        int numAlbums = musicStore.getAlbumsByTitleOrArtist(title).size();

        // If multiple songs are found, ask the user for the artist
        if (numAlbums > 1) {
            System.out.print("Please specify the artist: ");
            String artistName = scanner.nextLine().trim();

            // Call the model's addSong method again with the artist name
            result = model.addAlbum(title, musicStore, artistName);
            System.out.println(result);
        }
    }

    // #3 SEARCH SONG + REQUEST ALBUM INFO
    private void searchSong() {
        System.out.print("\n🔍 Enter song title or artist: ");
        String title = scanner.nextLine();
        List<Song> results = musicStore.getSongsByTitleOrArtist(title);
        displaySearchResults(results, "song");

        // If results exist, ask if the user wants album details
        if (!results.isEmpty()) {
            System.out.print("\n📀 Would you like to see album details? (y/n): ");
            String response = scanner.nextLine().trim().toLowerCase();
            
            if (response.equals("y")) {
                for (Song song : results) {
                    String albumTitle = song.getAlbum();
                    
                    // Retrieve albums by title (returns a list)
                    List<Album> albums = musicStore.getAlbumsByTitleOrArtist(albumTitle);
                    
                    if (albums.isEmpty()) {
                        System.out.println("\n❌ No album information found for: " + albumTitle);
                    } else {
                        for (Album album : albums) {
                        	boolean inLibrary = model.albumInLibrary(album); // Check if in user library
                        	System.out.println(album.toString());
                            System.out.println("   📚 In Your Library: " + (inLibrary ? "✅ Yes" : "❌ No"));
                        }
                    }
                }
            }
        }
    }

    // #4 SEARCH ALBUM
    private void searchAlbum() {
        System.out.print("\n🔍 Enter album title or artist: ");
        String title = scanner.nextLine();
        List<Album> results = musicStore.getAlbumsByTitleOrArtist(title);
        displaySearchResults(results, "album");
    }

    // #5 SEARCH BY ARTIST
    private void searchByArtist() {
        System.out.print("\n🔍 Enter artist name: ");
        String artistName = scanner.nextLine();

        List<Song> songResults = musicStore.getSongsByTitleOrArtist(artistName);
        List<Album> albumResults = musicStore.getAlbumsByTitleOrArtist(artistName);

        if (albumResults.isEmpty() && songResults.isEmpty()) {
            System.out.println("❌ No results found for artist: " + artistName);
        } else {
            System.out.println("\n🎤 Artist: " + artistName);
            if (!albumResults.isEmpty()) {
                System.out.println("\n📀 Albums:");
                for (Album album : albumResults) {
                    System.out.println("   📀 " + album.getTitle());
                }
            }
            if (!songResults.isEmpty()) {
                System.out.println("\n🎵 Songs:");
                for (Song song : songResults) {
                    System.out.println("   🎶 " + song.getTitle() + " (from " + song.getAlbum() + ")");
                }
            }
        }
    }
    
 // #7 SEARCH BY GENRE
    private void searchByGenre() {
        System.out.print("\n🔍 Enter genre: ");
        String genre = scanner.nextLine().trim(); // Get the genre input

        List<Song> songResults = musicStore.getSongsByGenre(genre);

        if (songResults.isEmpty()) {
            System.out.println("❌ No results found for genre: " + genre);
        } else {
            System.out.println("\n🎤 Genre: " + genre);
            // Output the song results
            System.out.println("\n🎵 Songs:");
            for (Song song : songResults) {
                System.out.println("   🎶 " + song.getTitle() + " (from " + song.getAlbum() + ")");
            }
        }
    }
    
    
    // #6 SEARCH PLAYLIST
    private void searchPlaylist() {
    	// Ensure all auto-generated playlists are up to date
        model.getAutomaticPlaylists();
        
        System.out.print("\n📜 Enter playlist name: ");
        String playlistName = scanner.nextLine().trim();  // Trim whitespace just in case
        
        if (playlistName.isEmpty()) {
            System.out.println("❌ Playlist name cannot be empty.");
            return;
        }

        // 🔹 Now, search for the playlist AFTER ensuring it's created
        Playlist playlist = model.getPlaylistByName(playlistName);

        if (playlist == null) {
            System.out.println("❌ Playlist not found.");
        } else {
            System.out.println("\n📜 Playlist: " + playlist.getName());
            List<Song> songs = playlist.getSongs();
            if (songs.isEmpty()) {
                System.out.println("   ❌ No songs in this playlist.");
            } else {
                for (Song song : songs) {
                    System.out.println("   🎵 " + song.getTitle() + " - " + song.getArtist());
                }
            }
        }
    }

    // #7 CREATE PLAYLIST
    private void createPlaylist() {
        System.out.print("\n📜 Enter playlist name: ");
        String name = scanner.nextLine().trim();  // Trim input to avoid leading/trailing spaces
        
        if (name.isEmpty()) {
            System.out.println("❌ Playlist name cannot be empty.");
            return;  // Early exit if name is empty
        }

        String result = model.createPlaylist(name);  // Call the model to create the playlist
        System.out.println(result);
    }

 // #8 ADD SONG TO PLAYLIST
    private void addSongToPlaylist() {
    	// Ensure all auto-generated playlists are up to date
        model.getAutomaticPlaylists();
        
        System.out.print("\n📜 Enter playlist name: ");
        String playlistName = scanner.nextLine().trim();  // Trim spaces

        if (playlistName.isEmpty()) {
            System.out.println("❌ Playlist name cannot be empty.");
            return;
        }

        System.out.print("\n🎵 Enter song title to add: ");
        String songTitle = scanner.nextLine().trim();  // Trim spaces

        if (songTitle.isEmpty()) {
            System.out.println("❌ Song title cannot be empty.");
            return;
        }

        String result = model.addToPlaylist(playlistName, songTitle);
        System.out.println(result);
    }

    // #9 REMOVE SONG FROM PLLAYLIST
 // #9 REMOVE SONG FROM PLAYLIST
    private void removeSongFromPlaylist() {
    	// Ensure all auto-generated playlists are up to date
        model.getAutomaticPlaylists();
        
        System.out.print("\n📜 Enter playlist name: ");
        String playlistName = scanner.nextLine().trim();  // Trim spaces

        if (playlistName.isEmpty()) {
            System.out.println("❌ Playlist name cannot be empty.");
            return;
        }

        System.out.print("\n🎵 Enter song title to remove: ");
        String songTitle = scanner.nextLine().trim();  // Trim spaces

        if (songTitle.isEmpty()) {
            System.out.println("❌ Song title cannot be empty.");
            return;
        }

        String result = model.removeFromPlaylist(playlistName, songTitle);
        System.out.println(result);
    }

    // #10 RATE SONG
    private void rateSong() {
        System.out.print("\n⭐ Enter song title to rate: ");
        String title = scanner.nextLine();

        System.out.print("\n🌟 Enter rating (0-5): ");
        int ratingInput = getIntInput();

        if (ratingInput < 0 || ratingInput > 5) {
            System.out.println("❌ Invalid rating. Please enter a number between 0 and 5.");
            return;
        }

        String result = model.rateSong(title, ratingInput);
        System.out.println(result);
    }

    // #11 MARK SONG AS FAVORITE
    private void markSongAsFavorite() {
        System.out.print("\n💖 Enter song title to mark as favorite: ");
        String title = scanner.nextLine();

        String result = model.setToFavorite(title);
        System.out.println(result);
    }

    // #12 LIST SONGS
    private void listSongs() {
        List<String> songs = model.getSongTitles();
        displayListResults(songs, "songs");
    }

    // #13 LIST ARTISTS
    private void listArtists() {
        List<String> artists = model.getArtistNames();
        displayListResults(artists, "artists");
        
    }

    // #14 LIST ALBUMS
    private void listAlbums() {
        List<String> albums = model.getAlbumTitles();
        displayListResults(albums, "albums");
    }

    // #15 LIST PLAYLIST
    private void listPlaylists() {
        // Make sure the automatic playlists (Top Rated, Favorite, Genre-based) are generated
        model.getAutomaticPlaylists();
        
        List<String> playlists = model.getPlaylistNames();
        
        // Check if there are any playlists
        if (playlists.isEmpty()) {
            System.out.println("❌ No playlists found.");
        } else {
            displayListResults(playlists, "playlists");
        }
    }

    // #16 LIST FAVORITE SONGS
    private void listFavoriteSongs() {
        List<Song> favorites = model.getFavoriteSongs();
        if (favorites.isEmpty()) {
            System.out.println("❌ No favorite songs.");
        } else {
            System.out.println("\n💖 Favorite Songs:");
            for (Song s : favorites) {
                System.out.println("   🎵 " + s.getTitle() + " - " + s.getArtist());
            }
        }
    }

    // GET USER INPUT
    private int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("❌ Invalid input. Enter a number: ");
            }
        }
    }

    // DISPLAY RESULTS
    private void displaySearchResults(List<?> results, String type) {
        if (results.isEmpty()) {
            System.out.println("❌ No " + type + " found.");
        } else {
            System.out.println("\n🔎 Search Results:");
            results.forEach(System.out::println);
        }
    }

    // DISPLAY LISTS
    private void displayListResults(List<String> list, String type) {
        if (list.isEmpty()) {
            System.out.println("❌ No " + type + " found.");
        } else {
            System.out.println("\n📜 List of " + type + ":");
            list.forEach(item -> System.out.println("   " + item));
        }
    }

    
    private void shuffleLibrary() {
        System.out.println("\n🔀 Shuffling your music library...");
        Iterator<Song> shuffledSongs = model.shuffleLibrary();
        while (shuffledSongs.hasNext()) {
            System.out.println("🎵 " + shuffledSongs.next().getTitle());
        }
    }

 // Remove a song from the library
    private void removeSong() {
        System.out.print("\n🗑️ Enter the song title to remove: ");
        String title = scanner.nextLine();

        System.out.print("🎤 Enter the artist name: ");
        String artist = scanner.nextLine();

        String result = model.removeSong(title, artist);
        System.out.println(result);
    }

    // Remove an album from the library
    private void removeAlbum() {
        System.out.print("\n🗑️ Enter the album title to remove: ");
        String title = scanner.nextLine();

        System.out.print("🎤 Enter the artist name: ");
        String artist = scanner.nextLine();

        String result = model.removeAlbum(title, artist);
        System.out.println(result);
    }
    
    private void listSongsBySorting() {
        System.out.print("\n🗂️ Enter sorting method (title, artist, rating): ");
        String method = scanner.nextLine().trim().toLowerCase();  // Normalize input to lowercase
        
        // Check if the input is valid
        if (!method.equals("title") && !method.equals("artist") && !method.equals("rating")) {
            System.out.println("\n❌ Invalid sorting method! Please choose 'title', 'artist', or 'rating'.");
            return;  // Exit the method early if the input is invalid
        }

        List<String> songs = model.getSortedSongs(method);
        displayListResults(songs, "sorted songs by " + method + " 🗂️");
    }
    
    //// shuffle a playlist
    private void shufflePlaylist() {
        System.out.print("\n🔀 Enter playlist name to shuffle: ");
        String playlistName = scanner.nextLine();

        Iterator<Song> shuffledSongs = model.getShuffledPlaylist(playlistName);
        if (shuffledSongs == null) {
            System.out.println("❌ Playlist not found.");
            return;
        }

        System.out.println("\n🎶 Shuffled Playlist: ");
        while (shuffledSongs.hasNext()) {
            System.out.println("🎵 " + shuffledSongs.next().getTitle());
        }
    }



    // MAIN
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        LibraryModel model = new LibraryModel();
        MusicStore musicStore = new MusicStore();
        musicStore.loadAlbums();

        LibraryView view = new LibraryView(model, musicStore);
        view.start();
    }
}
