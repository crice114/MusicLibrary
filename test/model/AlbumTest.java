// 100% coverage

package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AlbumTest {
	// Determined variables to avoid duplicated code
    Album album1 = new Album("Ten Percent", "Double Exposure", "Disco", 1976);
    Song song1 = new Song("Ten Percent", "Double Exposure", "Ten Percent", Rating.THREE, false);
    Song song2 = new Song("Gonna Give My Love Away", "Double Exposure", "Ten Percent", Rating.THREE, false);
    Song song3 = new Song("Everyman", "Double Exposure", "Ten Percent", Rating.THREE, false);
    Song song4 = new Song("Baby I Need Your Loving", "Double Exposure", "Ten Percent", Rating.THREE, false);
    Song song5 = new Song("Just Can't Say Hello", "Double Exposure", "Ten Percent", Rating.THREE, false);
    Song song6 = new Song("My Love Is Free", "Double Exposure", "Ten Percent", Rating.THREE, false);
    Song song7 = new Song("Pick Me", "Double Exposure", "Ten Percent", Rating.THREE, false);

	@Test
    void testImmutability() {
        // Ensure initial values are correct
        assertEquals("Ten Percent", album1.getTitle());
        assertEquals("Double Exposure", album1.getArtist());
        assertEquals("Disco", album1.getGenre());
        assertEquals(1976, album1.getYearReleased());
    }
	
	@Test
	void testGetSongs() {
		// No added songs yet
		assertEquals(0, album1.getSongs().size());
	}
	
	@Test
	void testAddSong() {
		// Added songs
		album1.addSong(song1);
		album1.addSong(song2);
		album1.addSong(song3);
		album1.addSong(song4);
		album1.addSong(song5);
		album1.addSong(song6);
		album1.addSong(song7);
		assertEquals(7, album1.getSongs().size());
	}
	
	@Test
	void testToString() {
		// toString with one song
		album1.addSong(song1);
		String str = "\"Ten Percent\"\nArtist: Double Exposure\n"
				+ "Genre: Disco\nYear: 1976\n1. Ten Percent";
		assertEquals(str,album1.toString());		
	}

}
