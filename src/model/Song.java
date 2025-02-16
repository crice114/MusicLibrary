package model;

public class Song {
	// INSTANCE VARIABLES
	private String title;
	private String artist;
	private int rating;
	private boolean favorite;
	
	
	// CONSTRUCTOR
	public Song(String title, String artist) {
		this.title = title;
		this.artist = artist;
		this.rating = 0;
		this.favorite = false;
	}
	

	// SETTERS AND GETTERS
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public boolean isFavorite() {
		return favorite;
	}

	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}	
	
}
