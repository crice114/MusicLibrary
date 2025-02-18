package model;

public class Song {
	// INSTANCE VARIABLES -- set to final, to make song class immutable CR 2/17
	private final String title;
	private final String artist;
	private final Rating rating;  //Added Rating class, so type is now Rating
	private final boolean favorite;
	
	
	// CONSTRUCTOR -- add rating and favorite to the constructor to accomplish immutability. Rating is now its own class
	public Song(String title, String artist, Rating rating, boolean favorite) {
		this.title = title;
		this.artist = artist;
		this.rating = rating;
		this.favorite = favorite;
	}
	
	//  GETTERS  -- remove setters, not needed as they are declared in the constructor
	public String getTitle() {
		return title;
	}
	
	
	public String getArtist() {
		return artist;
	}
	
	//rating type is now Rating
	public Rating getRating() {
		return rating;
	}
	

	public boolean isFavorite() {
		return favorite;
	}
	
	
	// add a toString() override
	@Override
	public String toString() {
	    return "Song: " + title + " by " + artist + " | Rating: " + rating + " | Favorite: " + (favorite ? "Yes" : "No");
	}

	
}
