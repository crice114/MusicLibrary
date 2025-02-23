package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class LibraryModelTest {

    @Test
    void testLibraryInitialization() {
        LibraryModel library = new LibraryModel();
        assertNotNull(library.getAlbums());
        assertNotNull(library.getSongs());
        assertNotNull(library.getArtists());
        assertNotNull(library.getPlaylists());
    }

    @Test
    void testAddingSongsToLibrary() {
        LibraryModel library = new LibraryModel();
        Song song = new Song("Wonderwall", "Oasis", "Morning Glory", Rating.FOUR, false);

        library.getSongs().add(song);
        assertEquals(1, library.getSongs().size());
    }
}
