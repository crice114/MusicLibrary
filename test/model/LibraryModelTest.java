package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class LibraryModelTest {
    LibraryModel library = new LibraryModel();
	
    @Test
    void testLibraryInitialization() {
        assertNotNull(library.getAlbums());
        assertNotNull(library.getSongs());
        assertNotNull(library.getArtists());
        assertNotNull(library.getPlaylists());
    }

    @Test
    void testAddingSongsToLibrary() {
        Song song = new Song("Wonderwall", "Oasis", "Morning Glory", Rating.FOUR, false);

        library.getSongs().add(song);
        assertEquals(1, library.getSongs().size());
    }
}
