/* Playlist.java
 * This Java file include the Playlist class.
 * A Playlist object is made up of a name and songs.
 * Since playlists are meant to be editable,
 * this class is mutable. */

package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Playlist {
	// INSTANCE VARIABLES
	private String name;
	private ArrayList<Song> songs;
	
	// CONSTRUCTOR
	public Playlist(String name) {
		this.name = name;
		this.songs = new ArrayList<>();
	}
	
	// GETTERS
	public ArrayList<Song> getSongs() {
		return songs;
	}
	
	public String getName() {
		return name;
	}
	
	// SETTER
	public void setName(String name) {
		this.name = name;
	}
	
	// ADD-REMOVE
	public void addSong(Song song) {
		songs.add(song);
	}
	
	public void removeSong(Song song) {
		songs.remove(song);
	}
	
	 public Iterator<Song> shufflePlaylist() {
	        List<Song> shuffledSongs = new ArrayList<>(songs);
	        Collections.shuffle(shuffledSongs);
	        return shuffledSongs.iterator();
	    }

}
