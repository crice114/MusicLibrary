package view;

import model.LibraryModel;
import model.Song;
import model.Album;
import model.Playlist;
import model.Rating;
import java.util.List;
import java.util.Scanner;

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
public class LibraryView {
    private final LibraryModel model;
    private final Scanner scanner;

    // Constructor
    public LibraryView(LibraryModel model) {
        this.model = model;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Starts the text-based UI loop.
     */
    public void start() {
        while (true) {
            System.out.println("\n🎵 Welcome to Your Music Library 🎵");
            System.out.println("1. Search for a song");
            System.out.println("2. Search for an album");
            System.out.println("3. View all songs in your library");
            System.out.println("4. View all albums in your library");
            System.out.println("5. Create a new playlist");
            System.out.println("6. View all playlists");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            int choice = getIntInput();

            switch (choice) {
                case 1 -> searchSong();
                case 2 -> searchAlbum();
                case 3 -> displayLibrarySongs();
                case 4 -> displayLibraryAlbums();
                case 5 -> createPlaylist();
                case 6 -> displayPlaylists();
                case 7 -> {
                    System.out.println("\n👋 Thank you for using the Music Library!");
                    return;
                }
                default -> System.out.println("\n❌ Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Searches for a song by title.
     */
    private void searchSong() {
        System.out.print("\n🔍 Enter song title: ");
        String title = scanner.nextLine();
        List<String> results = model.getSongByTitle(title);

        displaySearchResults(results, "song");
    }

    /**
     * Searches for an album by title.
     */
    private void searchAlbum() {
        System.out.print("\n🔍 Enter album title: ");
        String title = scanner.nextLine();
        List<String> results = model.getAlbumByTitle(title);

        displaySearchResults(results, "album");
    }

    /**
     * Displays all songs in the user's library.
     */
    private void displayLibrarySongs() {
        List<Song> songs = model.getSongs();

        if (songs.isEmpty()) {
            System.out.println("\n🎵 Your library has no songs.");
            return;
        }

        System.out.println("\n🎼 Your Songs:");
        for (Song song : songs) {
            System.out.println("   🎶 " + song);
        }
    }

    /**
     * Displays all albums in the user's library.
     */
    private void displayLibraryAlbums() {
        List<Album> albums = model.getAlbums();

        if (albums.isEmpty()) {
            System.out.println("\n📀 Your library has no albums.");
            return;
        }

        System.out.println("\n📚 Your Albums:");
        for (Album album : albums) {
            System.out.println("   📀 " + album.getTitle() + " by " + album.getArtist());
        }
    }

    /**
     * Creates a new playlist and allows the user to add songs.
     */
    private void createPlaylist() {
        System.out.print("\n🎼 Enter playlist name: ");
        String name = scanner.nextLine();
        Playlist playlist = new Playlist(name);

        while (true) {
            System.out.print("   ➕ Enter song title to add (or 'done' to finish): ");
            String title = scanner.nextLine();

            if (title.equalsIgnoreCase("done")) break;

            List<Song> matches = model.getSongs().stream()
                    .filter(song -> song.getTitle().equalsIgnoreCase(title))
                    .toList();

            if (matches.isEmpty()) {
                System.out.println("   ❌ Song not found in your library.");
            } else {
                playlist.addSong(matches.get(0));  // ✅ Corrected method
                System.out.println("   ✅ Added " + matches.get(0).getTitle() + " to " + name);
            }
        }
        model.getPlaylists().add(playlist);  // ✅ Add playlist to LibraryModel
        System.out.println("🎵 Playlist '" + name + "' created successfully!");
    }

    /**
     * Displays all playlists in the user's library.
     */
    private void displayPlaylists() {
        List<Playlist> playlists = model.getPlaylists();

        if (playlists.isEmpty()) {
            System.out.println("\n📜 You have no playlists.");
            return;
        }

        System.out.println("\n🎶 Your Playlists:");
        for (Playlist playlist : playlists) {
            System.out.println("   📜 " + playlist.getName());
        }
    }

    /**
     * Utility method to handle integer input safely.
     * @return an integer input from the user
     */
    private int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("❌ Invalid input. Enter a number: ");
            }
        }
    }

    /**
     * Utility method to display search results.
     * @param results the list of search results
     * @param itemType the type of item being searched (song, album, etc.)
     */
    private void displaySearchResults(List<String> results, String itemType) {
        if (results.isEmpty() || (results.size() == 1 && results.get(0).contains("not available"))) {
            System.out.println("❌ No " + itemType + " found.");
        } else {
            System.out.println("\n🔎 Search Results:");
            for (String result : results) {
                System.out.println("   ✅ " + result);
            }
        }
    }

    /**
     * Main method to run the text-based UI.
     */
    public static void main(String[] args) {
        LibraryModel model = new LibraryModel();
        LibraryView view = new LibraryView(model);
        view.start();
    }
}







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
