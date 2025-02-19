package model;

import java.util.ArrayList;

public class Playlist {
	private String name;
	private ArrayList<Song> songs;
	
	public Playlist(String name) {
		this.setName(name);
		this.songs = new ArrayList<>();
	}
	
	
	public ArrayList<Song> getSongs() {
		return songs;
	}
	
	public void addSong(Song song) {
		songs.add(song);
	}
	
	public void removeSong(Song song) {
		songs.remove(song);
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

}
