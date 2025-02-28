// 100% coverage

package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ArtistTest {
	// Determined variables to avoid duplicated code
	Artist artist1 = new Artist("Queen");
    Album album1 = new Album("Queen II", "Queen", "Rock", 1974);
	
    // Testing Getters
	@Test
	void testGetName() {
		assertEquals("Queen", artist1.getName());
	}
	
	@Test
	void testGetSongs() {
		assertEquals(0, artist1.getSongs().size());
	}
	
	@Test
	void testGetAlbums() {
		assertEquals(0, artist1.getAlbums().size());
	}
	
	// Test Add
	@Test
	void testAddAlbums() {
		artist1.addAlbum(album1);
		assertEquals(1, artist1.getAlbums().size());
	}

}
