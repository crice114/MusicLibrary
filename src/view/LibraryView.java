/* The view was coded using generative AI.
 * Any use of self-created code will be mentioned.*/
/*
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */


package view;

import model.LibraryModel;
import model.MusicStore;
import model.Song;
import model.Album;
import model.Playlist;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class LibraryView {
    private final LibraryModel model;
    private final MusicStore musicStore;
    private final Scanner scanner;

    public LibraryView(LibraryModel model, MusicStore musicStore) {
        this.model = model;
        this.musicStore = musicStore;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("\n🎵 Welcome to Your Music Library 🎵");
            System.out.println("1. Add a song to library");
            System.out.println("2. Add an album to library");
            System.out.println("3. Search for a song");
            System.out.println("4. Search for an album");
            System.out.println("5. Search by artist");
            System.out.println("6. Create a new playlist");
            System.out.println("7. Add song to playlist");
            System.out.println("8. Remove song from playlist");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");

            int choice = getIntInput();

            switch (choice) {
                case 1 -> addSong();
                case 2 -> addAlbum();
                case 3 -> searchSong();
                case 4 -> searchAlbum();
                case 5 -> searchByArtist();
                case 6 -> createPlaylist();
                case 7 -> addSongToPlaylist();
                case 8 -> removeSongFromPlaylist();
                case 9 -> {
                    System.out.println("\n👋 Thank you for using the Music Library!");
                    return;
                }
                default -> System.out.println("\n❌ Invalid choice. Please try again.");
            }
        }
    }

    private void addSong() {
        System.out.print("\n🎵 Enter song title to add: ");
        String title = scanner.nextLine();
        model.addSong(title, musicStore);
    }

    private void addAlbum() {
        System.out.print("\n📀 Enter album title to add: ");
        String title = scanner.nextLine();
        model.addAlbum(title, musicStore);
    }

    private void searchSong() {
        System.out.print("\n🔍 Enter song title or artist: ");
        String title = scanner.nextLine();
        List<Song> results = model.getSongByTitleOrArtist(title);
        displaySearchResults(results);
    }

    private void searchAlbum() {
        System.out.print("\n🔍 Enter album title or artist: ");
        String title = scanner.nextLine();
        List<Album> results = model.getAlbumByTitleOrArtist(title);
        displaySearchResults(results);
    }

    private void searchByArtist() {
        System.out.print("\n🔍 Enter artist name: ");
        String artistName = scanner.nextLine();
        
        List<Song> songResults = model.getSongByTitleOrArtist(artistName);
        List<Album> albumResults = model.getAlbumByTitleOrArtist(artistName);
        
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

    private void createPlaylist() {
        System.out.print("\n📜 Enter playlist name: ");
        String name = scanner.nextLine();
        model.createPlaylist(name);
    }

    private void addSongToPlaylist() {
        System.out.print("\n📜 Enter playlist name: ");
        String playlistName = scanner.nextLine();

        System.out.print("\n🎵 Enter song title to add: ");
        String songTitle = scanner.nextLine();

        model.addToPlaylist(playlistName, songTitle);
    }

    private void removeSongFromPlaylist() {
        System.out.print("\n📜 Enter playlist name: ");
        String playlistName = scanner.nextLine();

        System.out.print("\n🎵 Enter song title to remove: ");
        String songTitle = scanner.nextLine();

        model.removeFromPlaylist(playlistName, songTitle);
    }

    private int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("❌ Invalid input. Enter a number: ");
            }
        }
    }

    private void displaySearchResults(List<?> results) {
        if (results.isEmpty()) {
            System.out.println("❌ No results found.");
        } else {
            results.forEach(System.out::println);
        }
    }

    public static void main(String[] args) throws IOException {
        LibraryModel model = new LibraryModel();
        MusicStore musicStore = new MusicStore();
        LibraryView view = new LibraryView(model, musicStore);
        musicStore.loadAlbums();
        view.start();
    }
}






/*
package view;

import model.LibraryModel;
import model.Song;
import model.Album;
import model.Playlist;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class LibraryView {
    private final LibraryModel model;
    private final Scanner scanner;

    public LibraryView(LibraryModel model) {
        this.model = model;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("\n🎵 Welcome to Your Music Library 🎵");
            System.out.println("1. Add a song to library");
            System.out.println("2. Add an album to library");
            System.out.println("3. Search for a song");
            System.out.println("4. Search for an album");
            System.out.println("5. Search by artist");  // ✅ Added option to search by artist
            System.out.println("6. Create a new playlist");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            int choice = getIntInput();

            switch (choice) {
                case 1 -> addSong();
                case 2 -> addAlbum();
                case 3 -> searchSong();
                case 4 -> searchAlbum();
                case 5 -> searchByArtist();  // ✅ New method to handle artist search
                case 6 -> createPlaylist();
                case 7 -> {
                    System.out.println("\n👋 Thank you for using the Music Library!");
                    return;
                }
                default -> System.out.println("\n❌ Invalid choice. Please try again.");
            }
        }
    }

    private void addSong() {
        System.out.print("\n🎵 Enter song title to add: ");
        String title = scanner.nextLine();
        model.addSong(title);
    }

    private void addAlbum() {
        System.out.print("\n📀 Enter album title to add: ");
        String title = scanner.nextLine();
        model.addAlbum(title);
    }

    private void searchSong() {
        System.out.print("\n🔍 Enter song title or artist: ");
        String title = scanner.nextLine();
        List<Song> results = model.getSongByTitleOrArtist(title);
        displaySearchResults(results);
    }

    private void searchAlbum() {
        System.out.print("\n🔍 Enter album title or artist: ");
        String title = scanner.nextLine();
        List<Album> results = model.getAlbumByTitleOrArtist(title);
        displaySearchResults(results);
    }

    // ✅ NEW: Search by artist method
    private void searchByArtist() {
        System.out.print("\n🎤 Enter artist name: ");
        String artist = scanner.nextLine();

        // Use existing LibraryModel methods to retrieve songs and albums by artist
        List<Song> songs = model.getSongByTitleOrArtist(artist);
        List<Album> albums = model.getAlbumByTitleOrArtist(artist);

        // If no results found, inform the user
        if (songs.isEmpty() && albums.isEmpty()) {
            System.out.println("❌ No songs or albums found for artist: " + artist);
            return;
        }

        // Display albums by the artist
        if (!albums.isEmpty()) {
            System.out.println("\n📀 Albums by " + artist + ":");
            for (Album a : albums) {
                System.out.println("   📀 " + a.getTitle());
            }
        }

        // Display songs by the artist
        if (!songs.isEmpty()) {
            System.out.println("\n🎶 Songs by " + artist + ":");
            for (Song s : songs) {
                System.out.println("   🎵 " + s.getTitle() + " (Album: " + s.getAlbum() + ")");
            }
        }
    }

    private void createPlaylist() {
        System.out.print("\n📜 Enter playlist name: ");
        String name = scanner.nextLine();
        model.createPlaylist(name);
    }

    private int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("❌ Invalid input. Enter a number: ");
            }
        }
    }

    private void displaySearchResults(List<?> results) {
        if (results.isEmpty()) {
            System.out.println("❌ No results found.");
        } else {
            results.forEach(System.out::println);
        }
    }

    public static void main(String[] args) throws IOException {
        LibraryModel model = new LibraryModel();
        LibraryView view = new LibraryView(model);
        model.loadAlbums();
        view.start();
    }
}
*/









/*
package view;

import model.LibraryModel;
import model.Song;
import model.Album;
import model.Playlist;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class LibraryView {
    private final LibraryModel model;
    private final Scanner scanner;

    public LibraryView(LibraryModel model) {
        this.model = model;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("\n🎵 Welcome to Your Music Library 🎵");
            System.out.println("1. Add a song to library");
            System.out.println("2. Add an album to library");
            System.out.println("3. Search for a song");
            System.out.println("4. Search for an album");
            System.out.println("5. Create a new playlist");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = getIntInput();

            switch (choice) {
                case 1 -> addSong();
                case 2 -> addAlbum();
                case 3 -> searchSong();
                case 4 -> searchAlbum();
                case 5 -> createPlaylist();
                case 6 -> {
                    System.out.println("\n👋 Thank you for using the Music Library!");
                    return;
                }
                default -> System.out.println("\n❌ Invalid choice. Please try again.");
            }
        }
    }

    private void addSong() {
        System.out.print("\n🎵 Enter song title to add: ");
        String title = scanner.nextLine();
        model.addSong(title);
    }

    private void addAlbum() {
        System.out.print("\n📀 Enter album title to add: ");
        String title = scanner.nextLine();
        model.addAlbum(title);
    }

    private void searchSong() {
        System.out.print("\n🔍 Enter song title or artist: ");
        String title = scanner.nextLine();
        List<Song> results = model.getSongByTitleOrArtist(title);
        displaySearchResults(results);
    }

    private void searchAlbum() {
        System.out.print("\n🔍 Enter album title or artist: ");
        String title = scanner.nextLine();
        List<Album> results = model.getAlbumByTitleOrArtist(title);
        displaySearchResults(results);
    }

    private void createPlaylist() {
        System.out.print("\n📜 Enter playlist name: ");
        String name = scanner.nextLine();
        model.createPlaylist(name);
    }

    private int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("❌ Invalid input. Enter a number: ");
            }
        }
    }

    private void displaySearchResults(List<?> results) {
        if (results.isEmpty()) {
            System.out.println("❌ No results found.");
        } else {
            results.forEach(System.out::println);
        }
    }

    public static void main(String[] args) throws IOException {
        LibraryModel model = new LibraryModel();
        LibraryView view = new LibraryView(model);
        model.loadAlbums();
        view.start();
    }
}
*/
















/*
package view;

import model.LibraryModel;
import model.MusicStore;
import model.Song;
import model.Album;
import model.Playlist;
import model.Rating;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
*/
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
//
//public class LibraryView {
//    private final LibraryModel model;
//    private final Scanner scanner;
//
//    // Constructor
//    public LibraryView(LibraryModel model) {
//        this.model = model;
//        this.scanner = new Scanner(System.in);
//    }
//
//    /**
//     * Starts the text-based UI loop.
//     */
//    public void start() {
//        while (true) {
//            System.out.println("\n🎵 Welcome to Your Music Library 🎵");
//            System.out.println("1. Add a song to library");
//            System.out.println("2. Add an album to library");
//            System.out.println("3. Search for a song");
//            System.out.println("4. Search for an album");
//            System.out.println("5. View all songs in your library");
//            System.out.println("6. View all albums in your library");
//            System.out.println("7. Create a new playlist");
//            System.out.println("8. View all playlists");
//            System.out.println("9. Exit");
//            System.out.print("Enter your choice: ");
//
//            int choice = getIntInput();
//
//            switch (choice) {
//            	case 1 -> addSong();
//            	case 2 -> addAlbum();
//                case 3 -> searchSong();
//                case 4 -> searchAlbum();
//                case 5 -> displayLibrarySongs();
//                case 6 -> displayLibraryAlbums();
//                case 7 -> createPlaylist();
//                case 8 -> displayPlaylists();
//                case 9 -> {
//                    System.out.println("\n👋 Thank you for using the Music Library!");
//                    return;
//                }
//                default -> System.out.println("\n❌ Invalid choice. Please try again.");
//            }
//        }
//    }
//
//	private void addSong() {
//		System.out.print("\n🔍 Enter song title: ");
//		String title = scanner.nextLine();
//		List<String> results = model.getSongByTitleOrArtist(title);
//	}
//	
//	private Object addAlbum() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//     * Searches for a song by title.
//     */
//    private void searchSong() {
//        System.out.print("\n🔍 Enter song title or artist: ");
//        String title = scanner.nextLine();
//        List<String> results = model.getSongByTitleOrArtist(title);
//        //results.addAll(model.getSongByArtist(title));
//
//        displaySearchResults(results, "song");
//    }
//
//    /**
//     * Searches for an album by title.
//     */
//    private void searchAlbum() {
//        System.out.print("\n🔍 Enter album title or artist: ");
//        String title = scanner.nextLine();
//        List<String> results = model.getAlbumByTitleOrArtist(title);
//        //results.addAll(model.getAlbumByTitleOrArtist(title));
//
//        displaySearchResults(results, "album");
//    }
//
//    /**
//     * Displays all songs in the user's library.
//     */
//    private void displayLibrarySongs() {
//        List<Song> songs = model.getSongs();
//
//        if (songs.isEmpty()) {
//            System.out.println("\n🎵 Your library has no songs.");
//            return;
//        }
//
//        System.out.println("\n🎼 Your Songs:");
//        for (Song song : songs) {
//            System.out.println("   🎶 " + song);
//        }
//    }
//
//    /**
//     * Displays all albums in the user's library.
//     */
//    private void displayLibraryAlbums() {
//        List<Album> albums = model.getAlbums();
//
//        if (albums.isEmpty()) {
//            System.out.println("\n📀 Your library has no albums.");
//            return;
//        }
//
//        System.out.println("\n📚 Your Albums:");
//        for (Album album : albums) {
//            System.out.println("   📀 " + album.getTitle() + " by " + album.getArtist());
//        }
//    }
//
//    // NOT KEEP (IT IS MANIPULATING DATA - MOVE TO MODEL)
//    /**
//     * Creates a new playlist and allows the user to add songs.
//     */
//    private void createPlaylist() {
//        System.out.print("\n🎼 Enter playlist name: ");
//        String name = scanner.nextLine();
//        Playlist playlist = new Playlist(name);
//
//        while (true) {
//            System.out.print("   ➕ Enter song title to add (or 'done' to finish): ");
//            String title = scanner.nextLine();
//
//            if (title.equalsIgnoreCase("done")) break;
//
//            List<Song> matches = model.getSongs().stream()
//                    .filter(song -> song.getTitle().equalsIgnoreCase(title))
//                    .toList();
//
//            if (matches.isEmpty()) {
//                System.out.println("   ❌ Song not found in your library.");
//            } else {
//                playlist.addSong(matches.get(0));  // ✅ Corrected method
//                System.out.println("   ✅ Added " + matches.get(0).getTitle() + " to " + name);
//            }
//        }
//        model.getPlaylists().add(playlist);  // ✅ Add playlist to LibraryModel
//        System.out.println("🎵 Playlist '" + name + "' created successfully!");
//    }
//
//    // KEEP
//    /**
//     * Displays all playlists in the user's library.
//     */
//    private void displayPlaylists() {
//        List<Playlist> playlists = model.getPlaylists();
//
//        if (playlists.isEmpty()) {
//            System.out.println("\n📜 You have no playlists.");
//            return;
//        }
//
//        System.out.println("\n🎶 Your Playlists:");
//        for (Playlist playlist : playlists) {
//            System.out.println("   📜 " + playlist.getName());
//        }
//    }
//
//    // KEEP
//    /**
//     * Utility method to handle integer input safely.
//     * @return an integer input from the user
//     */
//    private int getIntInput() {
//        while (true) {
//            try {
//                return Integer.parseInt(scanner.nextLine().trim());
//            } catch (NumberFormatException e) {
//                System.out.print("❌ Invalid input. Enter a number: ");
//            }
//        }
//    }
//
//    // KEEP
//    /**
//     * Utility method to display search results.
//     * @param results the list of search results
//     * @param itemType the type of item being searched (song, album, etc.)
//     */
//    private void displaySearchResults(List<String> results, String itemType) {
//        if (results.isEmpty() || (results.size() == 1 && results.get(0).contains("not available"))) {
//            System.out.println("❌ No " + itemType + " found.");
//        } else {
//            System.out.println("\n🔎 Search Results:");
//            for (String result : results) {
//                System.out.println("\n   ✅ " + result);
//            }
//        }
//    }
//
//    /**
//     * Main method to run the text-based UI.
//     * @throws IOException 
//     */
//    public static void main(String[] args) throws IOException {
//        LibraryModel model = new LibraryModel();
//        LibraryView view = new LibraryView(model);
//        
//        model.loadAlbums();
//        
//        view.start();
//    }
//}
//
//
//
//
//


//package view;
//
//import model.LibraryModel;
//import model.Song;
//import model.Album;
//import model.Playlist;
//import model.Rating;
//import java.util.List;
//import java.util.Scanner;
//
///**
// * LibraryView.java
// *
// * This class represents the **View** component in the MVC architecture.
// * It provides a **text-based user interface** that allows users to interact
// * with their music library by searching, adding songs, and managing playlists.
// *
// * Responsibilities:
// * - Displays a **menu** for user interaction.
// * - Accepts **user input** and validates it.
// * - Calls methods from **LibraryModel** to retrieve and modify data.
// * - Displays **formatted results** to the user.
// */
//public class LibraryView {
//    private final LibraryModel model;
//    private final Scanner scanner;
//
//    // Constructor
//    public LibraryView(LibraryModel model) {
//        this.model = model;
//        this.scanner = new Scanner(System.in);
//    }
//
//    /**
//     * Starts the text-based UI loop.
//     */
//    public void start() {
//        while (true) {
//            System.out.println("\n🎵 Welcome to Your Music Library 🎵");
//            System.out.println("1. Search for a song");
//            System.out.println("2. Search for an album");
//            System.out.println("3. View all songs in your library");
//            System.out.println("4. View all albums in your library");
//            System.out.println("5. Create a new playlist");
//            System.out.println("6. View all playlists");
//            System.out.println("7. Exit");
//            System.out.print("Enter your choice: ");
//
//            int choice = getIntInput();
//
//            switch (choice) {
//                case 1 -> searchSong();
//                case 2 -> searchAlbum();
//                case 3 -> displayLibrarySongs();
//                case 4 -> displayLibraryAlbums();
//                case 5 -> createPlaylist();
//                case 6 -> displayPlaylists();
//                case 7 -> {
//                    System.out.println("\n👋 Thank you for using the Music Library!");
//                    return;
//                }
//                default -> System.out.println("\n❌ Invalid choice. Please try again.");
//            }
//        }
//    }
//
//    /**
//     * Searches for a song by title.
//     */
//    private void searchSong() {
//        System.out.print("\n🔍 Enter song title: ");
//        String title = scanner.nextLine();
//        List<String> results = model.getSongByTitle(title);
//
//        displaySearchResults(results, "song");
//    }
//
//    /**
//     * Searches for an album by title.
//     */
//    private void searchAlbum() {
//        System.out.print("\n🔍 Enter album title: ");
//        String title = scanner.nextLine();
//        List<String> results = model.getAlbumByTitle(title);
//
//        displaySearchResults(results, "album");
//    }
//
//    /**
//     * Displays all songs in the user's library.
//     */
//    private void displayLibrarySongs() {
//        List<Song> songs = model.getSongs();
//
//        if (songs.isEmpty()) {
//            System.out.println("\n🎵 Your library has no songs.");
//            return;
//        }
//
//        System.out.println("\n🎼 Your Songs:");
//        for (Song song : songs) {
//            System.out.println("   🎶 " + song);
//        }
//    }
//
//    /**
//     * Displays all albums in the user's library.
//     */
//    private void displayLibraryAlbums() {
//        List<Album> albums = model.getAlbums();
//
//        if (albums.isEmpty()) {
//            System.out.println("\n📀 Your library has no albums.");
//            return;
//        }
//
//        System.out.println("\n📚 Your Albums:");
//        for (Album album : albums) {
//            System.out.println("   📀 " + album.getTitle() + " by " + album.getArtist());
//        }
//    }
//
//    /**
//     * Creates a new playlist and allows the user to add songs.
//     */
//    private void createPlaylist() {
//        System.out.print("\n🎼 Enter playlist name: ");
//        String name = scanner.nextLine();
//        Playlist playlist = new Playlist(name, List.of());
//
//        while (true) {
//            System.out.print("   ➕ Enter song title to add (or 'done' to finish): ");
//            String title = scanner.nextLine();
//
//            if (title.equalsIgnoreCase("done")) break;
//
//            List<Song> matches = model.getSongs().stream()
//                    .filter(song -> song.getTitle().equalsIgnoreCase(title))
//                    .toList();
//
//            if (matches.isEmpty()) {
//                System.out.println("   ❌ Song not found in your library.");
//            } else {
//                playlist = playlist.withAddedSong(matches.get(0));
//                System.out.println("   ✅ Added " + matches.get(0).getTitle() + " to " + name);
//            }
//        }
//        System.out.println("🎵 Playlist '" + name + "' created successfully!");
//    }
//
//    /**
//     * Displays all playlists in the user's library.
//     */
//    private void displayPlaylists() {
//        List<Playlist> playlists = model.getPlaylists();
//
//        if (playlists.isEmpty()) {
//            System.out.println("\n📜 You have no playlists.");
//            return;
//        }
//
//        System.out.println("\n🎶 Your Playlists:");
//        for (Playlist playlist : playlists) {
//            System.out.println("   📜 " + playlist.getName());
//        }
//    }
//
//    /**
//     * Utility method to handle integer input safely.
//     * @return an integer input from the user
//     */
//    private int getIntInput() {
//        while (true) {
//            try {
//                return Integer.parseInt(scanner.nextLine().trim());
//            } catch (NumberFormatException e) {
//                System.out.print("❌ Invalid input. Enter a number: ");
//            }
//        }
//    }
//
//    /**
//     * Utility method to display search results.
//     * @param results the list of search results
//     * @param itemType the type of item being searched (song, album, etc.)
//     */
//    private void displaySearchResults(List<String> results, String itemType) {
//        if (results.isEmpty() || (results.size() == 1 && results.get(0).contains("not available"))) {
//            System.out.println("❌ No " + itemType + " found.");
//        } else {
//            System.out.println("\n🔎 Search Results:");
//            for (String result : results) {
//                System.out.println("   ✅ " + result);
//            }
//        }
//    }
//
//    /**
//     * Main method to run the text-based UI.
//     */
//    public static void main(String[] args) {
//        LibraryModel model = new LibraryModel();
//        LibraryView view = new LibraryView(model);
//        view.start();
//    }
//}
//
//
//
//






/*package view;

import model.LibraryModel;

public class LibraryView {
	private LibraryModel model;

	public LibraryView(LibraryModel model) {
		this.model = model;
	}	
}
*/


/**
 * LibraryView.java
 *
 * This class represents the **View** component in the MVC architecture for the Music Library application.
 * It provides a **text-based user interface** that allows users to interact with their music library.
 *
 * Responsibilities:
 * - Displays a **menu** for the user to interact with.
 * - Accepts **user input** for searching, adding, and managing songs, albums, and playlists.
 * - Communicates with the **LibraryModel** (the backend) to retrieve and manipulate data.
 * - Ensures the **separation of concerns** by not storing any music data itself—only displaying information.
 *
 * This class relies on:
 * - `LibraryModel`: Provides access to the user's music library and music store.
 * - `Scanner`: Handles user input.
 *
 * Note: This View does **not** handle GUI elements, as the project specifies a **text-based UI**.
 *
 
 */

//
//
//package view;
//
//import model.LibraryModel;
//import java.util.Scanner;
//
//public class LibraryView {
//    private LibraryModel model;
//    private Scanner scanner;
//
//    public LibraryView(LibraryModel model) {
//        this.model = model;
//        this.scanner = new Scanner(System.in);
//    }
//
//    public void start() {
//        while (true) {
//            System.out.println("\n--- Music Library Menu ---");
//            System.out.println("1. Search for a song");
//            System.out.println("2. Search for an album");
//            System.out.println("3. Exit");
//            System.out.print("Enter choice: ");
//            
//            int choice = scanner.nextInt();
//            scanner.nextLine(); // Consume newline
//            
//            switch (choice) {
//                case 1:
//                    searchSong();
//                    break;
//                case 2:
//                    searchAlbum();
//                    break;
//                case 3:
//                    System.out.println("Exiting...");
//                    return;
//                default:
//                    System.out.println("Invalid choice, try again.");
//            }
//        }
//    }
//
//    private void searchSong() {
//        System.out.print("Enter song title: ");
//        String title = scanner.nextLine();
//        System.out.println(model.getSongByTitle(title));
//    }
//
//    private void searchAlbum() {
//        System.out.print("Enter album title: ");
//        String title = scanner.nextLine();
//        System.out.println(model.getAlbumByTitle(title));
//    }
//
//    public static void main(String[] args) {
//        LibraryModel model = new LibraryModel();
//        LibraryView view = new LibraryView(model);
//        view.start();
//    }
//}
