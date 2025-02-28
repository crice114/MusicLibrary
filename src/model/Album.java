/* Album.java
 * This Java file include the Album class.
 * An Album object is made up of a title, artist,
 * genre, year and a list of songs. */

package model;

import java.util.ArrayList;

public class Album {
	// INSTANCE VARIABLES
	private String title;
	private String artist;
	private String genre;
	private int yearReleased;
	private ArrayList<Song> songs;
	
	// CONSTRUCTOR
	public Album(String title, String artist, String genre, int yearReleased) {
		this.title = title;
		this.artist = artist;
		this.genre = genre;
		this.yearReleased = yearReleased;
		this.songs = new ArrayList<Song>();
	}

	// GETTERS
	public String getTitle() {
		return title;
	}

	public String getArtist() {
		return artist;
	}

	public String getGenre() {
		return genre;
	}

	public int getYearReleased() {
		return yearReleased;
	}

	public ArrayList<Song> getSongs() {
		return songs;
	}
	
	// ADD SONG
	public void addSong(Song song) {
		songs.add(song);
	}
	
	// add a toString() override
	@Override
	public String toString() {
		// Songs through an array to print properly and in order
		ArrayList<String> songsList = new ArrayList<String>();
		int count = 1;
		for (Song s : songs) {
			songsList.add(count + ". " + s.getTitle());
			count++;
		}
		
		// Newline per song
		String songsStr = String.join("\n", songsList);
		
		return "\"" + title + "\"\n" + "Artist: " + artist + "\nGenre: " + genre +
				"\nYear: " + String.valueOf(yearReleased) + "\n" + songsStr;
	}

}
