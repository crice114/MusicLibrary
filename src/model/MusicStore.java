/* MusicStore.java
 * This Java file include the MusicStore class.
 * It loads the text files, searches for the albums.txt file
 * and iterates through every album entry, making sure each
 * album is loaded in the store properly. */

package model;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

// Public class with constructor - songs, albums and artists only.
public class MusicStore {
	// INSTANCE VARIABLES
    private ArrayList<Song> songs;
    private ArrayList<Album> albums;
    private ArrayList<Artist> artists;

    // CONSTRUCTOR
    public MusicStore() {
        this.songs = new ArrayList<>();
        this.albums = new ArrayList<>();
        this.artists = new ArrayList<>();
    }

    // GETTERS
    public ArrayList<Song> getSongs() {
        return songs;
    }

    public ArrayList<Album> getAlbums() {
        return albums;
    }
    
    public ArrayList<Artist> getArtists() {
        return artists;
    }

    // ADD content
    public void addSong(Song song) {
        songs.add(song);
    }

    public void addAlbum(Album album) {
        albums.add(album);
    }
    
    public void addArtist(Artist artist) {
    	artists.add(artist);
    }
    
    // Add the getSongByTittleOrArtist and getAlbumByTitleOrArtist to MusicStore, as according to the spec this is where to search for them
    public ArrayList<Song> getSongsByTitleOrArtist(String title) {
    	ArrayList<Song> results = new ArrayList<>();
    	for (Song s : songs) {
    		if (s.getTitle().equalsIgnoreCase(title) || s.getArtist().equalsIgnoreCase(title)) {
    			results.add(s);
    			}
    		}
    	return results;
    }
    
    public ArrayList<Song> getSongsByGenre(String genre) {
    	ArrayList<Song> results = new ArrayList<>();
    	for (Song s : songs) {
    		String albumTitle = s.getAlbum();
    		for (Album a : albums) {
    			// Search for title AND genre
    			if (a.getTitle().equalsIgnoreCase(albumTitle) && a.getGenre().equalsIgnoreCase(genre)) {
    				results.add(s);
    			}
    		}
    	}
    	return results;
    }
   


   public ArrayList<Album> getAlbumsByTitleOrArtist(String title) {
       ArrayList<Album> results = new ArrayList<>();
       for (Album a : albums) {
           if (a.getTitle().equalsIgnoreCase(title) || a.getArtist().equalsIgnoreCase(title)) {
               results.add(a);
           }
       }
       return results;
   }
    
    
    // Loads albums and songs from the `albums` folder.
    public void loadAlbums() throws IOException {
        String albumsTxtPath = "./src/albums/albums.txt";
        BufferedReader br = new BufferedReader(new FileReader(albumsTxtPath));

        // Read through every line in albums.txt
        String line;
        while ((line = br.readLine()) != null) {
        	// Separate song title and artist name
            String[] titleAlbum = line.split(",");
            String albumTxt = titleAlbum[0] + "_" + titleAlbum[1] + ".txt";
            File albumFile = new File("./src/albums/" + albumTxt);

            // Search for album in albums.txt
            if (albumFile.exists()) {
                BufferedReader br2 = new BufferedReader(new FileReader(albumFile));
                String[] albumInfo = br2.readLine().split(",");

                // Create Album object after splitting header
                Album album = new Album(albumInfo[0], albumInfo[1], albumInfo[2], Integer.parseInt(albumInfo[3]));
                albums.add(album);

                // Find or create new Artist object
                boolean artistFound = false;
                Artist artist = null;
                
                // if artist already in store... 
                for (Artist a: artists) {
                	if(a.getName().equals(albumInfo[1])) {
                		artist = a;
                		artistFound = true;
                		break;
                	}
                }
                
                // if new artist...
                if(!artistFound) {
                	artist = new Artist(albumInfo[1]);
                	artists.add(artist);
                }
                
                artist.addAlbum(album);	// either way add artist

                // Read song titles
                String songTitle;
                while ((songTitle = br2.readLine()) != null) {
                    Song song = new Song(songTitle, album.getArtist(), album.getTitle(), Rating.ZERO, false, 0);    //// added a 0 in parameter for count
                    // Add song to songs and to that specific album
                    songs.add(song);
                    album.addSong(song);
                }
                br2.close();
            }
        }
        br.close();
    }
}
