package model;

import java.util.ArrayList;
import java.io.BufferedReader;
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


}
