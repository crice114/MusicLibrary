// 100% coverage

package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PlaylistTest {
	Playlist playlist1 = new Playlist("Yacht Rock");
    Song song1 = new Song("Reminiscing", "Little River Band", "Sleeper Catcher", Rating.THREE, false);
    Song song2 = new Song("These Chains", "Toto", "The Seventh One", Rating.THREE, false);
	
	@Test
	void testGetSong() {
		assertEquals("Yacht Rock", playlist1.getName());
	}
	
	@Test
	void testSetSong() {
		playlist1.setName("Toto Essentials");
		assertEquals("Toto Essentials", playlist1.getName());
	}
	
	@Test
	void testAddSong() {
		playlist1.addSong(song1);
		assertEquals(1, playlist1.getSongs().size());
	}

	@Test
	void testSetter() {
		playlist1.addSong(song1);
		playlist1.addSong(song2);
		playlist1.removeSong(song1);
		assertEquals(1, playlist1.getSongs().size());
	}


}