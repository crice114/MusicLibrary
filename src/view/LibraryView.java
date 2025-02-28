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

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class LibraryView {
	// VARIABLES
    private final LibraryModel model;
    private final MusicStore musicStore;
    private final Scanner scanner;

    // CONSTRUCTOR
    public LibraryView(LibraryModel model, MusicStore musicStore) {
        this.model = model;
        this.musicStore = musicStore;
        this.scanner = new Scanner(System.in);
    }

    // MENU OPTION
    public void start() {
        while (true) {
            System.out.println("\n🎵 Welcome to Your Music Library 🎵");
            System.out.println("1. Add a song from store to library");
            System.out.println("2. Add an album from store to library");
            System.out.println("3. Search for a song in our store");
            System.out.println("4. Search for an album in our store");
            System.out.println("5. Search by artist from our store");
            System.out.println("6. Search for a playlist");
            System.out.println("7. Create a new playlist");
            System.out.println("8. Add song to playlist");
            System.out.println("9. Remove song from playlist");
            System.out.println("10. Rate a song");
            System.out.println("11. Mark song as favorite");
            System.out.println("12. View all songs");
            System.out.println("13. View all artists");
            System.out.println("14. View all albums");
            System.out.println("15. View all playlists");
            System.out.println("16. View favorite songs");
            System.out.println("17. Exit");
            System.out.print("Enter your choice: ");

            // USER INPUT
            int choice = getIntInput();

            // CALL METHODS
            switch (choice) {
                case 1 -> addSong();
                case 2 -> addAlbum();
                case 3 -> searchSong();
                case 4 -> searchAlbum();
                case 5 -> searchByArtist();
                case 6 -> searchPlaylist();
                case 7 -> createPlaylist();
                case 8 -> addSongToPlaylist();
                case 9 -> removeSongFromPlaylist();
                case 10 -> rateSong();
                case 11 -> markSongAsFavorite();
                case 12 -> listSongs();
                case 13 -> listArtists();
                case 14 -> listAlbums();
                case 15 -> listPlaylists();
                case 16 -> listFavoriteSongs();
                case 17 -> {
                    System.out.println("\n👋 Thank you for using the Music Library!");
                    return;
                }
                default -> System.out.println("\n❌ Invalid choice. Please try again.");
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

    // #3 SEARCH SONG
    private void searchSong() {
        System.out.print("\n🔍 Enter song title or artist: ");
        String title = scanner.nextLine();
        List<Song> results = musicStore.getSongsByTitleOrArtist(title);
        displaySearchResults(results, "song");
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

    // #6 SEARCH PLAYLIST
    private void searchPlaylist() {
        System.out.print("\n📜 Enter playlist name: ");
        String playlistName = scanner.nextLine();
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
        String name = scanner.nextLine();
        String result = model.createPlaylist(name);
        System.out.println(result);
    }

    // #8 ADD SONG TO PLAYLIST
    private void addSongToPlaylist() {
        System.out.print("\n📜 Enter playlist name: ");
        String playlistName = scanner.nextLine();

        System.out.print("\n🎵 Enter song title to add: ");
        String songTitle = scanner.nextLine();

        String result = model.addToPlaylist(playlistName, songTitle);
        System.out.println(result);
    }

    // #9 REMOVE SONG FROM PLLAYLIST
    private void removeSongFromPlaylist() {
        System.out.print("\n📜 Enter playlist name: ");
        String playlistName = scanner.nextLine();

        System.out.print("\n🎵 Enter song title to remove: ");
        String songTitle = scanner.nextLine();

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
        List<String> playlists = model.getPlaylistNames();
        displayListResults(playlists, "playlists");
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

    // MAIN
    public static void main(String[] args) throws IOException {
        LibraryModel model = new LibraryModel();
        MusicStore musicStore = new MusicStore();
        musicStore.loadAlbums();

        LibraryView view = new LibraryView(model, musicStore);
        view.start();
    }
}
