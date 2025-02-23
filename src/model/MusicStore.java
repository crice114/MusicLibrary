package model;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MusicStore {
    private ArrayList<Song> songs;
    private ArrayList<Album> albums;
    private ArrayList<Playlist> playlists;

    public MusicStore() {
        this.songs = new ArrayList<>();
        this.albums = new ArrayList<>();
        this.playlists = new ArrayList<>();
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public ArrayList<Album> getAlbums() {
        return albums;
    }

    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    public void addAlbum(Album album) {
        albums.add(album);
    }

    public void addPlaylist(Playlist playlist) {
        playlists.add(playlist);
    }

    /**
     * Loads albums and songs from the `albums` folder. put into method loadAlbums, to separate from main.
     */
    public void loadAlbums() throws IOException {
        String albumsTxtPath = "./src/albums/albums.txt";
        BufferedReader br = new BufferedReader(new FileReader(albumsTxtPath));

        String line;
        while ((line = br.readLine()) != null) {
            String[] titleAlbum = line.split(",");
            String albumTxt = titleAlbum[0] + "_" + titleAlbum[1] + ".txt";
            File albumFile = new File("./src/albums/" + albumTxt);

            if (albumFile.exists()) {
                BufferedReader br2 = new BufferedReader(new FileReader(albumFile));
                String[] albumInfo = br2.readLine().split(",");

                // Create Album object
                Album album = new Album(albumInfo[0], albumInfo[1], albumInfo[2], Integer.parseInt(albumInfo[3]));
                albums.add(album);

                System.out.println("Loaded Album: " + album.getTitle());

                // Read song titles
                String songTitle;
                while ((songTitle = br2.readLine()) != null) {
                    Song song = new Song(songTitle, album.getArtist(), album.getTitle(), Rating.THREE, false); //default rating to 3
                    songs.add(song);
                    album.addSong(song);
                    System.out.println("  ➜ Added song: " + songTitle);
                }
                br2.close();
            }
        }
        br.close();
    }

    
    public static void main(String[] args) throws IOException {
        MusicStore store = new MusicStore();
        store.loadAlbums();

        System.out.println("\n🎵 All Loaded Songs:");
        for (Song song : store.getSongs()) {
            System.out.println(song);
        }
    }
}




/*
package model;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;  // imported
import java.io.FileNotFoundException;  // imported
import java.io.FileReader;  // imported
import java.io.IOException;  // imported
import java.lang.String;

public class MusicStore {
	
	private ArrayList<Song> songs;
	private ArrayList<Album> albums;
	private ArrayList<Playlist> playlists;
	
	public MusicStore() {
		this.songs = new ArrayList<>();
        this.albums = new ArrayList<>();
        this.playlists = new ArrayList<>();
	}

	public ArrayList<Song> getSongs() {
		return songs;
	}

	public ArrayList<Album> getAlbums() {
		return albums;
	}

	public ArrayList<Playlist> getPlaylists() {
		return playlists;
	}

	public void addSong(Song song) {
		songs.add(song);
	}
	
	public void addAlbum(Album album) {
		albums.add(album);
	}
	
	public void addPlaylist(Playlist playlist) {
        playlists.add(playlist);
    }

	// MAIN to read files
	public static void main(String[] args) throws IOException {
		String albumsTxtPath = "./src/albums/albums.txt";
		BufferedReader br = new BufferedReader(new FileReader(albumsTxtPath));
		
		String line = "";
		
		while((line = br.readLine()) != null) {
			String[] titleAlbum = line.split(",");
			String albumTxt = titleAlbum[0] + "_" + titleAlbum[1];
			System.out.println(albumTxt);
			
			File album = new File("./src/albums/"+albumTxt+".txt");
			
			if(album.exists()) {
				String line2 = "";
				BufferedReader br2 = new BufferedReader(new FileReader(album));
				
				line2 = br2.readLine();
				
				String[] albumInfo = line2.split(",");
				System.out.println("TITLE: " + albumInfo[0]);
				System.out.println("ARTIST: " + albumInfo[1]);
				System.out.println("GENRE: " + albumInfo[2]);
				System.out.println("YEAR: " + albumInfo[3]);
				System.out.println("Songs:");
				
				while((line2 = br2.readLine()) != null) {
					System.out.println(line2);
				}
				
				System.out.print('\n');
				
				//br2.close();
			}
			//br.close();
		}
		
	}

	

}
*/