// 94% coverage

package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

class SongTest {
	// Determined variables to avoid duplicated code
    Song song1 = new Song("Sultans of Swing", "Dire Straits", "Dire Straits", Rating.TWO, false, 0);
    Song song2 = new Song("Sultans of Rock", "Dire Straits", "Dire Straits", Rating.THREE, false, 0);
    Song song3 = new Song("Sultans of Pop", "Dire Bridges", "Dire Straits", Rating.TWO, false, 0);

    @Test
    void testImmutability() {
        // Ensure initial values are correct
        assertEquals("Sultans of Swing", song1.getTitle());
        assertEquals("Dire Straits", song1.getArtist());
        assertEquals("Dire Straits", song1.getAlbum());
        assertEquals(Rating.TWO, song1.getRating());
        assertFalse(song1.isFavorite());

        // Create new Song with new rating
        Song song2 = song1.setRating(Rating.FIVE);
        Song song3 = song2.setFavorite(true);
    }
    
    @Test
    void testCount() {
    	song1.getCount();
    	song1.incrementCount();
    }
    
    // Test toString
    @Test
	void testToString() {
		String str = "Sultans of Swing - Dire Straits | Dire Straits";
		assertEquals(str, song1.toString());
	}
    
    @Test
	void testToFileString() {
		String str = "Sultans of Swing - Dire Straits | Dire Straits";
		assertNotEquals(str, song1.toFileString());
	}
    
    @Test
    void testFromFileString() {
    	Song.fromFileString(song1.getTitle() + " - " + song1.getArtist() + " | " +
    	song1.getAlbum() + " | " + 2 + " | " + true + " | " + 2);
    }
    
    @Test
    void testFirstComparators() {
    	List<Song> songs = Arrays.asList(song1, song2, song3);
    	Collections.sort(songs, Song.titleFirstComparator());
    	Collections.sort(songs, Song.artistFirstComparator());
    	Collections.sort(songs, Song.ratingFirstComparator());

    }
}
