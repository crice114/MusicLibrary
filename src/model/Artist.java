package model;

import java.util.ArrayList;

public class Artist {
	private final String name;
	private ArrayList<Song> songs;
	private ArrayList<Album> albums;
	
	public Artist(String name) {
		this.name = name;
		this.songs = new ArrayList<Song>();
		this.albums = new ArrayList<Album>();
	}
	
	public String getName() {
		return name;
	}
	
	public ArrayList<Song> getSongs() {
		return songs;
	}
	
	public ArrayList<Album> getAlbums() {
		return albums;
	}
}
