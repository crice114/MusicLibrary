package model;

import java.util.ArrayList;

public class Album {
	private String title;
	private String artist;
	private String genre;
	private int yearReleased;
	private ArrayList<Song> songs;
	
	// Album objects are meant to be immutable, so they are assigned in the constructor rather than with setters
	public Album(String title, String artist, String genre, int yearReleased) {
		this.title = title;
		this.artist = artist;
		this.genre = genre;
		this.yearReleased = yearReleased;
		this.songs = new ArrayList<Song>();
	}

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
	
	public void addSong(Song song) {
		songs.add(song);
	}
	
	// add a toString() override
	@Override
	public String toString() {
	    //return "Song: " + title + " by " + artist + " Genre + "from" + year;
		// Songs through an array to print properly
		
		ArrayList<String> songsList = new ArrayList<String>();
		int count = 1;
		for (Song s : songs) {
			songsList.add(count + ". " + s.getTitle());
			count++;
		}
		
		String songsStr = String.join("\n", songsList);
		
		return "\"" + title + "\"\n" + "Artist: " + artist + "\nGenre: " + genre +
				"\nYear: " + String.valueOf(yearReleased) + "\n" + songsStr;
	}

}
