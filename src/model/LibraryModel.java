package model;

import java.util.ArrayList;

public class LibraryModel {
	// INSTANCE VARIABLES
	private MusicStore musicStore;
	
	private ArrayList<Song> songs;
	private ArrayList<Artist> artists;
	private ArrayList<Album> albums;
	private ArrayList<Playlist> playlists;
	
	// CONSTRUCTOR
	public LibraryModel() {
		this.albums = new ArrayList<Album>();
		this.playlists = new ArrayList<Playlist>();
		this.artists = new ArrayList<Artist>();
		// initialize songs CR 2/17
		this.songs = new ArrayList<Song>();
	}
	
	
	// METHODS
	
	// SEARCH AND RETURN (can have more than one. that's why array)
	// Also prints all results
	
	// return song given title
	public ArrayList<String> getSongByTitle(String title) {
		ArrayList<String> songsList = new ArrayList<String>();
		for (Song s : songs) {
			if (s.getTitle().equals(title)) {
				songsList.add(s.toString());
			}
		}
		
		if (!songsList.isEmpty()) {
			return songsList;
		}
		else {
			songsList.add("Song is not available.");
			return songsList;
		}
	}
	
	// return song given artist
	public ArrayList<String> getSongByArtist(String artist) {
		ArrayList<String> songsList = new ArrayList<String>();
		for (Song s : songs) {
			if (s.getArtist().equals(artist)) {
				songsList.add(s.toString());
			}
		}
		
		if (!songsList.isEmpty()) {
			return songsList;
		}
		else {
			songsList.add("Song is not available.");
			return songsList;
		}
	}
	
	// return album given title
	public ArrayList<String> getAlbumByTitle(String title) {
		ArrayList<String> albumsList = new ArrayList<String>();
		for (Album a : albums) {
			if (a.getTitle().equals(title)) {
				albumsList.add(a.toString());
			}
		}
		
		if (!albumsList.isEmpty()) {
			return albumsList;
		}
		else {
			albumsList.add("Album is not available.");
			return albumsList;
		}
	}
	
	// return album given artist
	public ArrayList<String> getAlbumByArtist(String artist) {
		ArrayList<String> albumsList = new ArrayList<String>();
		for (Album a : albums) {
			if (a.getArtist().equals(artist)) {
				albumsList.add(a.toString());
			}
		}
		
		if (!albumsList.isEmpty()) {
			return albumsList;
		}
		else {
			albumsList.add("Album is not available.");
			return albumsList;
		}
	}	
	
	// GET ENTIRE LISTS
	// SETTERS AND GETTERS
	
	public MusicStore getMusicStore() {
		return musicStore;
	}
	
	public void setMusicStore(MusicStore musicStore) {
		this.musicStore = musicStore;
	}
	
	
	
	public ArrayList<Album> getAlbums() {
		return new ArrayList<>(albums);
	}
	
	/*public void setAlbums(ArrayList<Album> albums) {
		this.albums = albums;
	}*/
	
	
	
	
	public ArrayList<Playlist> getPlaylists() {
		return new ArrayList<>(playlists);
	}
	
	/*public void setPlaylists(ArrayList<Playlist> playlists) {
		this.playlists = playlists;
	}*/
	
	
	public ArrayList<Song> getSongs() {
		return new ArrayList<>(songs);
	}
	
	/*public void setSongs(ArrayList<Song> songs) {
		this.songs = songs;
	}*/
	
	
	public ArrayList<Artist> getArtists() {
		return new ArrayList<>(artists);
	}
	
	/*public void setArtists(ArrayList<Playlist> artists) {
		this.artists = artists;
	}*/
	
}

