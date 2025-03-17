// 100% coverage

package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;

class PlaylistTest {
	// Determined variables to avoid duplicated code
	Playlist playlist1 = new Playlist("Yacht Rock");
    Song song1 = new Song("Reminiscing", "Little River Band", "Sleeper Catcher", Rating.ZERO, false, 0);
    Song song2 = new Song("These Chains", "Toto", "The Seventh One", Rating.ZERO, false, 0);
    Song song3 = new Song("Rooster", "Alice in Chains", "Dirt", Rating.ZERO, false, 0);
    Song song4 = new Song("September Gurls", "Big Star", "Radio City", Rating.ZERO, false, 0);
	
    // Getters and Setters and Add
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
		assertEquals(2, playlist1.getSongs().size());
		playlist1.removeSong(song1);
		assertEquals(1, playlist1.getSongs().size());
	}
	
	@Test
	void testPlaylistShuffe() {
		Playlist playlist2 = new Playlist("Songs");
		playlist2.addSong(song1);
        playlist2.addSong(song2);
        playlist2.addSong(song3); ///adding more songs will ensure the order is shuffled and not the same(since only 3 songs now, the shuffled order may still end up being the same
        playlist2.addSong(song4);
        //original order
        List<Song> originalOrder = new ArrayList<>(playlist2.getSongs());

        //shuffle
        Iterator<Song> shuffledIterator = playlist2.shufflePlaylist();
        List<Song> shuffledOrder = new ArrayList<>();
        while (shuffledIterator.hasNext()) {
            shuffledOrder.add(shuffledIterator.next());
        }

        // Verify that all original songs exist in the shuffled list
        assertTrue(shuffledOrder.containsAll(originalOrder));
        assertTrue(originalOrder.containsAll(shuffledOrder));
        assertNotEquals(originalOrder, shuffledOrder);
	}
	
	

 
}