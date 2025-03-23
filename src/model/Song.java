/* Song.java
 * This Java file include the immutable Song class.
 * A Song object is made up of a title, artist,
 * album, rating and favorite. Ratings are inside an enum,
 * and changing rating/favorite returns a new Song,
 * maintaining immutability. */

package model;

// Final class - values cannot change unless newly defined
public final class Song {
	// INSTANCE VARIABLES
    private final String title;
    private final String artist;
    private final String album;
    private final Rating rating;  // enum (immutable)
    private final boolean favorite;
    
    private final int count;

    // CONSTRUCTOR
    public Song(String title, String artist, String album, Rating rating, boolean favorite, int count) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.rating = rating;
        this.favorite = favorite;
        
        this.count = count;
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
    
    public int getCount() {
    	return count;
    }

    // Return a new Song instance with updated values (instead of changing previous one).
    public Song setRating(Rating newRating) {
        return new Song(this.title, this.artist, this.album, newRating, this.favorite, this.count);
    }
    
    // Similar concept. Both ensure encapsulation.
    public Song setFavorite(boolean isFavorite) {
        return new Song(this.title, this.artist, this.album, this.rating, isFavorite, this.count);
    }
    
    // Similar concept. ensure encapsulation.
    public Song incrementCount() {
        return new Song(this.title, this.artist, this.album, this.rating, this.favorite, this.count+1);
    }

    // Override toString returning title, artist and album
    @Override
    public String toString() {
        return title + " - " + artist + " | " + album;
    }
    
    //added 3/22
    public String toFileString() {
    	return title + " - " + artist + " | " + album + " | " + rating.getStars() + " | " + isFavorite() + " | " + getCount();

    }
    //added 3/22
    public static Song fromFileString(String line) {
        try {
            // Format: Title - Artist | Album | Rating | Favorite | Count
            String[] parts = line.split(" - | \\| ");
            if (parts.length != 6) return null;

            String title = parts[0].trim();
            String artist = parts[1].trim();
            String album = parts[2].trim();
            int rating = Integer.parseInt(parts[3].trim());

            // Ensure rating is within bounds
            if (rating < 0 || rating >= Rating.values().length) return null;

            boolean favorite = Boolean.parseBoolean(parts[4].trim());
            int count = Integer.parseInt(parts[5].trim());

            return new Song(title, artist, album, Rating.values()[rating], favorite, count);
        } catch (Exception e) {
            // Handle any parsing error gracefully
            return null;
        }
    }


}
