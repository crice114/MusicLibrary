package model;

import java.util.ArrayList;

public class LibraryModel {
	// INSTANCE VARIABLES
	private MusicStore musicStore;
	private ArrayList<Album> albums;
	private ArrayList<Playlist> playlists;
	private ArrayList<Song> songs;
	
	
	
	// CONSTRUCTOR
	public LibraryModel() {
		this.albums = new ArrayList<Album>();
		this.playlists = new ArrayList<Playlist>();
	}

	
	// METHODS
	
	// return song given title
	public Song getSongByTitle(String title) {
		for (Song s : songs) {
			if (s.getTitle().equals(title)) {
				return s;
			}
		}
		return null;
	}
	
	// return song given artist
	public Song getSongByArtist(String artist) {
		for (Song s : songs) {
			if (s.getArtist().equals(artist)) {
				return s;
			}
		}
		return null;
	}
	
	// return song given artist
	public Album getAlbumByTitle(String title) {
		for (Album a : albums) {
			if (a.getTitle().equals(title)) {
				return a;
			}
		}
		return null;
	}
	
	// return song given artist
	public Album getAlbumByArtist(String artist) {
		for (Album a : albums) {
			if (a.getArtist().equals(artist)) {
				return a;
			}
		}
		return null;
	}
	
	
	
	// SETTERS AND GETTERS
	public MusicStore getMusicStore() {
		return musicStore;
	}

	public void setMusicStore(MusicStore musicStore) {
		this.musicStore = musicStore;
	}

	public ArrayList<Album> getAlbums() {
		return albums;
	}

	public void setAlbums(ArrayList<Album> albums) {
		this.albums = albums;
	}

	public ArrayList<Playlist> getPlaylists() {
		return playlists;
	}

	public void setPlaylists(ArrayList<Playlist> playlists) {
		this.playlists = playlists;
	}
	
	
	
}
