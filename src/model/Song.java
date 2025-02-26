
package model;

/**
 * Represents an immutable Song in the Music Library.
 * Uses the `Rating` enum to ensure valid ratings.
 */
public final class Song {
    private final String title;
    private final String artist;
    private final String album;
    private final Rating rating;  // ✅ Now using an enum (immutable)
    private final boolean favorite;

    public Song(String title, String artist, String album, Rating rating, boolean favorite) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.rating = rating;
        this.favorite = favorite;
    }

    // GETTERS
    public String getTitle() {
    	return title;
    }
    
    public String getArtist() {
    	return artist;
    }
    
    public String getAlbum() { 
    	return album; 
    }
    
    public Rating getRating() {
    	return rating; 
    }
    
    public boolean isFavorite() {
    	return favorite;
    }

    // Instead of modifying, return a new Song instance with updated values. should maintain encapsulation
    public Song changeRating(Rating newRating) {
        return new Song(this.title, this.artist, this.album, newRating, this.favorite);
    }
    
    //same idea for encapsulation here
    public Song markFavorite(boolean isFavorite) {
        return new Song(this.title, this.artist, this.album, this.rating, isFavorite);
    }

    @Override
    public String toString() {
        return title + " - " + artist + " | " + album;
    }
}




















/*package model;

enum Rating {
	ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5);
	
	private int stars;
	
	Rating(int stars) {
		this.stars = stars;
	}
	
	public int getStars() {
		return stars;
	}
}

public class Song {
	// INSTANCE VARIABLES -- set to final, to make song class immutable CR 2/17
	private final String title;
	private final String artist;
	private final String album;
	private Rating rating;  // Added Rating class, so type is now Rating
	private boolean favorite;
	
	
	// CONSTRUCTOR -- add rating and favorite to the constructor to accomplish immutability. Rating is now its own class
	public Song(String title, String artist, String album, Rating rating, boolean favorite) {
		this.title = title;
		this.artist = artist;
		this.album = album;
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
	
	public String getAlbum() {
		return album;
	}
	
	//rating type is now Rating
	public Rating getRating() {
		return rating;
	}
	
	public void setRating(Rating rating) {
		this.rating = rating;
	}

	public boolean isFavorite() {
		return favorite;
	}

	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}
	
	// add a toString() override
	@Override
	public String toString() {
	    //return "Song: " + title + " by " + artist + " | Rating: " + rating + " | Favorite: " + (favorite ? "Yes" : "No");
		return title + " by " + artist + " from the album " + album;
	}

	
}*/
