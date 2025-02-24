// 100% coverage

package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ArtistTest {
	Artist artist1 = new Artist("Queen");
	
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

}
