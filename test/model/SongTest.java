// 100% coverage

package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class SongTest {
	// Determined variables to avoid duplicated code
    Song song1 = new Song("Sultans of Swing", "Dire Straits", "Dire Straits", Rating.ZERO, false);

    @Test
    void testImmutability() {
        // Ensure initial values are correct
        assertEquals("Sultans of Swing", song1.getTitle());
        assertEquals("Dire Straits", song1.getArtist());
        assertEquals("Dire Straits", song1.getAlbum());
        assertEquals(Rating.ZERO, song1.getRating());
        assertFalse(song1.isFavorite());

        // Create new Song with new rating
        Song song2 = song1.setRating(Rating.FIVE);
        Song song3 = song2.setFavorite(true);

        // Ensure song1 remains unchanged
        assertEquals(Rating.ZERO, song1.getRating());
        assertFalse(song1.isFavorite());

        // Ensure song2 has updated rating
        assertEquals(Rating.FIVE, song2.getRating());
        assertFalse(song2.isFavorite());

        // Ensure song3 has updated favorite
        assertEquals(Rating.FIVE, song3.getRating());
        assertTrue(song3.isFavorite());
    }
    
    // Test toString
    @Test
	void testToString() {
		String str = "Sultans of Swing - Dire Straits | Dire Straits";
		assertEquals(str, song1.toString());
	}
}
