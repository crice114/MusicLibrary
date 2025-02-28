/* Song.java
 * This Java file include the Album class.
 * An Artist object is made up of a name, songs and
 * albums. */

package model;

import java.util.ArrayList;

public class Artist {
	// INSTANCE VARIABLES
	private final String name;
	private ArrayList<Song> songs;
	private ArrayList<Album> albums;
	
	// CONSTRUCTOR
	public Artist(String name) {
		this.name = name;
		this.songs = new ArrayList<Song>();
		this.albums = new ArrayList<Album>();
	}
	
	// GETTERS
	public String getName() {
		return name;
	}
	
	public ArrayList<Song> getSongs() {
		return songs;
	}
	
	public ArrayList<Album> getAlbums() {
		return albums;
	}
	
	// ADD ARTIST
	public void addAlbum(Album album) {
		albums.add(album);
	}
}
