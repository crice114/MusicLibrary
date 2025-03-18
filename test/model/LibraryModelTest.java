// 94% coverage

package model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LibraryModelTest {
	// Determined variables to avoid duplicated code
    LibraryModel library;
	MusicStore musicStore;
	
	//adding a 0 at the end of the song parameters to account for counts
	Song song1 = new Song("These Chains", "Toto", "The Seventh One", Rating.ZERO, false,0);
	Song song2 = new Song("Daydreamer", null, null, null, false, 0);
	
	//add songs to test the sorting methods
    Song song3 = new Song("Jesus", "Amos Lee", "Mission Bell", Rating.THREE, false, 0);
    Song song4 = new Song("Banjo", "Leonard Cohen", "Old Ideas", Rating.FIVE, false, 0);
    Song song5 = new Song("Tapestry", "Carol King", "Tapestry", Rating.FOUR, false, 0);

	
    Album album1 = new Album("The Seventh One", "Toto", "Rock", 1988);
    Album album2 = new Album("19", "Adele", null, 0);
    Album album3 = new Album("A Rush of Blood to the Head", "Coldplay", null, 0);

    Song songSame1 = new Song("Lullaby", "OneRepublic", "Waking Up", Rating.ZERO, false, 0);
    Song songSame2 = new Song("Lullaby", "Leonard Cohen", "Old Ideas", Rating.ZERO, false, 0);
    
	
    // Before Each allows the code below to run before every test
    @BeforeEach
    void setUp() throws IOException {
    	// Load albums - avoid duplicated code
    	musicStore = new MusicStore();
    	library = new LibraryModel();
    	musicStore.loadAlbums();
    	library.loadAlbums();
    	
    }
    
    // Get song and album
	@Test
	void testGetSongByTitleOrArtist_NotInStore() throws IOException {
		ArrayList<Song> resTitle = library.getSongByTitleOrArtist(song1.getTitle());
		ArrayList<Song> resArtist = library.getSongByTitleOrArtist(song1.getArtist());
		
		assertEquals(0, resTitle.size());
		assertEquals(0, resArtist.size());
	}
	
	@Test
	void testGetSongByTitleOrArtist_InStore_OneSong() throws IOException {
		library.addSong(song2.getTitle(), musicStore, song2.getArtist());
		ArrayList<Song> resTitle = library.getSongByTitleOrArtist(song2.getTitle());
		ArrayList<Song> resArtist = library.getSongByTitleOrArtist(song2.getArtist());
		
		assertEquals(1, resTitle.size());
		assertNotEquals(1, resArtist.size());
	}
	
	@Test
	void testGetAlbumByTitleOrArtist() throws IOException {
		library.addAlbum(album2.getTitle(), musicStore, album2.getArtist());
		ArrayList<Album> resTitle = library.getAlbumByTitleOrArtist(album2.getTitle());
		ArrayList<Album> resArtist = library.getAlbumByTitleOrArtist(album2.getArtist());
		
		assertEquals(1, resTitle.size());
		assertEquals(1, resArtist.size());
	}
	
	// Songs in and not in store
	@Test
	void testAddSong_InStore() throws IOException {
		library.addSong(song2.getTitle(), musicStore, null);
		assertEquals(1, library.getSongs().size());
	}
	
	@Test
	void testAddSong_NotInStore() throws IOException {
		library.addSong("Beat It", musicStore, null);
		assertEquals(0, library.getSongs().size());
	}
	
	// Songs already in library
	@Test
	void testAddSong_Duplicate() throws IOException {
		library.addSong(song2.getTitle(), musicStore, null);
		assertEquals(1, library.getSongs().size());
		library.addSong(song2.getTitle(), musicStore, null);
		assertEquals(1, library.getSongs().size());
	}
	
	// Songs with same title
	@Test
	void testAddSong_SameTitle() throws IOException {
		library.addSong("Lullaby", musicStore, null);
		assertEquals(0, library.getSongs().size());
		library.addSong("Lullaby", musicStore, "OneRepublic");
		assertEquals(1, library.getSongs().size());
	}
	
	// Albums in and not in store
	@Test
	void testAddAlbum_InStore() throws IOException {
		library.addAlbum(album2.getTitle(), musicStore, null);
		assertEquals(1, library.getAlbums().size());
	}
	
	@Test
	void testAddAlbum_NotInStore() throws IOException {
		library.addAlbum("Thriller", musicStore, null);
		assertEquals(0, library.getAlbums().size());
	}
	
	// Album duplicate
	@Test
	void testAddAlbum_Duplicate() throws IOException {
		library.addAlbum(album2.getTitle(), musicStore, null);
		assertEquals(1, library.getAlbums().size());
		library.addAlbum(album2.getTitle(), musicStore, null);
		assertEquals(1, library.getAlbums().size());
	}
	
	// Create Playlist
	@Test
	void testCreatePlaylist() throws IOException {
		library.createPlaylist("Yacht Rock");
		assertEquals(1, library.getPlaylistNames().size());
		library.createPlaylist("Yacht Rock");
		assertEquals(1, library.getPlaylistNames().size());
	}
	
	// Add and remove from playlist
	@Test
	void testAddToPlaylist() throws IOException {
		library.createPlaylist("Yacht Rock");
		library.addToPlaylist("Pop", song2.getTitle());
		library.addToPlaylist("Yacht Rock", song2.getTitle());
	}
	
	@Test
	void testRemoveFromPlaylist() throws IOException {
		library.addSong(song2.getTitle(), musicStore, null);
		library.createPlaylist("Pop");
		library.addToPlaylist("Pop", song2.getTitle());
		assertEquals(1, library.getPlaylistByName("Pop").getSongs().size());
		library.removeFromPlaylist("Pop", "Daydreamer");
		assertEquals(0, library.getPlaylistByName("Pop").getSongs().size());
	}
	
	@Test
	void testRemoveFromPlaylist_NotExist() throws IOException {
		library.createPlaylist("Pop");
		library.addToPlaylist("Pop", song2.getTitle());
		library.removeFromPlaylist("Rock", song2.getTitle());
	}
	
	// Get Playlist in and not in library
	@Test
	void testGetPlaylistByName_InLibrary() {
		library.createPlaylist("Yacht Rock");
		assertNotEquals(null, library.getPlaylistByName("Yacht Rock"));
	}
	
	@Test
	void testGetPlaylistByName_NotInLibrary() {
		library.createPlaylist("Yacht Rock");
		assertEquals(null, library.getPlaylistByName("Salsoul"));
	}
	
	// Rate and rate favorite
	@Test
	void testRateSong_NotFavorite() {
		library.addSong(song2.getTitle(), musicStore, null);
		library.rateSong(song2.getTitle(), 2);
		assertEquals(Rating.TWO, library.getSongByTitleOrArtist(song2.getTitle()).get(0).getRating());
	}
	
	@Test
	void testRateSong_Favorite() {
		library.addSong(song2.getTitle(), musicStore, null);
		library.rateSong(song2.getTitle(), 5);
		assertEquals(Rating.FIVE, library.getSongByTitleOrArtist(song2.getTitle()).get(0).getRating());
		assertEquals(true, library.getSongByTitleOrArtist(song2.getTitle()).get(0).isFavorite());
	}
	
	@Test
	void testRateSong_NotInLibrary() {
		library.addSong("Money for Nothing", musicStore, null);
		library.rateSong("Money for Nothing", 5);
	}
	
	// Getters
	@Test
	void testGetAlbums() {
		library.addAlbum(album2.getTitle(), musicStore, null);
		assertEquals(1, library.getAlbums().size());
	}
	
	@Test
	void testGetPlaylists() {
		library.createPlaylist("70s Rock");
		assertEquals(1, library.getPlaylists().size());
	}
	
	@Test
	void testGetSongs() {
		library.addSong(song2.getTitle(), musicStore, null);
		assertEquals(1, library.getSongs().size());
	}
	
	@Test
	void testGetArtists() {
		library.addSong(song2.getTitle(), musicStore, song2.getArtist());
		assertEquals(1, library.getArtistNames().size());
		library.addAlbum(album3.getTitle(), musicStore, null);
		assertEquals(2, library.getArtistNames().size());
		library.getArtists();
	}
	
	@Test
	void testGetSongTitles_TitleAndAlbum() {
		library.addSong(song2.getTitle(), musicStore, song2.getArtist());
		assertEquals(song2.getTitle(), library.getSongTitles().get(0));
		library.addAlbum("21", musicStore, null);
		assertEquals(13, library.getSongTitles().size());
	}
	
	@Test
	void testGetArtistNames_SongAndAlbum() {
		library.addSong(song2.getTitle(), musicStore, song2.getArtist());
		assertEquals("Adele", library.getArtistNames().get(0));
		library.addAlbum("Sons", musicStore, null);
		assertEquals("The Heavy", library.getArtistNames().get(1));
	}
	
	@Test
	void testGetAlbumTitles_TitleAndAlbum() {
		library.addAlbum(album2.getTitle(), musicStore, null);
		assertEquals(album2.getTitle(), library.getAlbumTitles().get(0));
		library.addSong("Fire", musicStore, null);
		assertEquals("Sons", library.getAlbumTitles().get(1));
	}
	
	@Test
	void testGetPlaylistNames() {
		library.createPlaylist("Indie Rock");
		assertEquals("Indie Rock", library.getPlaylistNames().get(0));
	}
	
	@Test
	void testGetFavoriteSongs() {
		library.addSong(song2.getTitle(), musicStore, null);
		library.rateSong(song2.getTitle(), 5);
		assertNotEquals(song2.getTitle(), library.getFavoriteSongs().get(0));
	}
	
	
	
	
	//test the sorting methods
	@Test
	void testSortingByTitle() {
	    library.addSong(song3.getTitle(), musicStore, song3.getArtist());
	    library.addSong(song4.getTitle(), musicStore, song4.getArtist());
	    library.addSong(song5.getTitle(), musicStore, song5.getArtist());

	    List<Song> sortedSongs = library.getSortedSongsByTitle();

	    assertEquals(3, sortedSongs.size()); // Only 3 songs added
	    assertEquals("Banjo", sortedSongs.get(0).getTitle());
	    assertEquals("Jesus", sortedSongs.get(1).getTitle());
	    assertEquals("Tapestry", sortedSongs.get(2).getTitle());
	}

	// Test sorting by artist
	@Test
	void testSortingByArtist() {
	    library.addSong(song3.getTitle(), musicStore, song3.getArtist());
	    library.addSong(song4.getTitle(), musicStore, song4.getArtist());
	    library.addSong(song5.getTitle(), musicStore, song5.getArtist());

	    List<Song> sortedSongs = library.getSortedSongsByArtist();

	    assertEquals(3, sortedSongs.size()); // Only 3 songs added
	    assertEquals("Amos Lee", sortedSongs.get(0).getArtist());
	    assertEquals("Carol King", sortedSongs.get(1).getArtist());
	    assertEquals("Leonard Cohen", sortedSongs.get(2).getArtist());
	}

	// Test sorting by rating
	@Test
	void testSortingByRating() {
	    library.addSong(song3.getTitle(), musicStore, song3.getArtist());
	    library.addSong(song4.getTitle(), musicStore, song4.getArtist());
	    library.addSong(song5.getTitle(), musicStore, song5.getArtist());
	    
	    
	    library.rateSong(song3.getTitle(), 3);
	    library.rateSong(song4.getTitle(), 5);
	    library.rateSong(song5.getTitle(), 4);

	    List<Song> sortedSongs = library.getSortedSongsByRating();

	    assertEquals(3, sortedSongs.size()); 
	    assertEquals(3, sortedSongs.get(0).getRating().getStars()); 
	    assertEquals(4, sortedSongs.get(1).getRating().getStars()); 
	    assertEquals(5, sortedSongs.get(2).getRating().getStars()); 
	}
	
	@Test
	void testRemoveSong() {
	    library.addSong(song3.getTitle(), musicStore, song3.getArtist());
	    assertEquals(1, library.getSongs().size()); 
	    library.removeSong(song3.getTitle(), song3.getArtist());
	    assertEquals(0, library.getSongs().size()); 
	    assertEquals("Song was not found", library.removeSong(null, null));
	    
	}

	@Test
	void testRemoveAlbum() {
	    library.addAlbum(album2.getTitle(), musicStore, album2.getArtist());
	    assertEquals(1, library.getAlbums().size()); 
	    library.removeAlbum(album2.getTitle(), album2.getArtist());
	    assertEquals(0, library.getAlbums().size()); 
	    assertEquals("Album was not found", library.removeAlbum(null, null));
	}

	@Test
	void testPlaySong() {
		assertEquals("Song not in store!", library.playSong(song1.getTitle(), musicStore, song1.getArtist()));
		library.addSong(song2.getTitle(), musicStore, song2.getArtist());
		assertEquals(0, song2.getCount());
		library.playSong(song2.getTitle(), musicStore, song2.getArtist());
		library.playSong(songSame1.getTitle(), musicStore, songSame1.getArtist());
	}
	
	@Test
	void testGetFrequentSongs() {
		library.addSong(song2.getTitle(), musicStore, song2.getArtist());
		library.playSong(song2.getTitle(), musicStore, song2.getArtist());
		assertEquals(1, library.getFrequentSongs().size());
	}
	
	@Test
	void testGetRecentlyPlayedSongs() {
		library.addSong(song2.getTitle(), musicStore, song2.getArtist());
		library.playSong(song2.getTitle(), musicStore, song2.getArtist());
		assertEquals(1, library.getRecentlyPlayedSongs().size());
	}
	
	@Test
	void testAlbumInLibrary() {
		library.addAlbum(album1.getTitle(), musicStore, null);
		assertFalse(library.albumInLibrary(album1));
	}
	
	@Test
	void testShuffleLibrary() {
		Iterator<Song> shuffle0 = library.shuffleLibrary();
		library.addSong(song2.getTitle(), musicStore, song2.getArtist());
		Iterator<Song> shuffle1 = library.shuffleLibrary();
		assertNotEquals(shuffle0, shuffle1);
	}
	
	
}
