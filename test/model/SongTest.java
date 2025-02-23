package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class SongTest {

    @Test
    void testImmutability() {
        Song song1 = new Song("Imagine", "John Lennon", "Imagine", Rating.THREE, false);

        // Ensure initial values are correct
        assertEquals("Imagine", song1.getTitle());
        assertEquals("John Lennon", song1.getArtist());
        assertEquals("Imagine", song1.getAlbum());
        assertEquals(Rating.THREE, song1.getRating());
        assertFalse(song1.isFavorite());

        // Create new Song instances with modifications
        Song song2 = song1.changeRating(Rating.FIVE);
        Song song3 = song2.markFavorite(true);

        // Ensure song1 remains unchanged
        assertEquals(Rating.THREE, song1.getRating());
        assertFalse(song1.isFavorite());

        // Ensure song2 has updated rating but same other attributes
        assertEquals(Rating.FIVE, song2.getRating());
        assertFalse(song2.isFavorite());

        // Ensure song3 has updated favorite status but same rating
        assertEquals(Rating.FIVE, song3.getRating());
        assertTrue(song3.isFavorite());
    }
}
