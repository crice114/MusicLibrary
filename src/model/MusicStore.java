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
