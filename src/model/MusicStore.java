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
		File folder = new File("./src/albums");
		File[] listOfAlbums = folder.listFiles();
		
		for(int i = 0; i < listOfAlbums.length; i++) {
			File albumTxt = listOfAlbums[i];
			String fileName = albumTxt.getName();
			BufferedReader br = new BufferedReader(new FileReader(albumTxt));
			
			String line = "";
			while(((line = br.readLine()) != null) && (fileName != "albums.txt")) {
				System.out.println(line);
			}
			System.out.println("ALBUM DONE\n");
			
			//close the BufferedReader after use
			br.close();
		}
		
	}

	

}
