// 90% coverage

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
    Album album2 = new Album("19", "Adele", "Pop", 2018);
    Album album3 = new Album("A Rush of Blood to the Head", "Coldplay", null, 0);

    Song songSame1 = new Song("Lullaby", "OneRepublic", "Waking Up", Rating.ZERO, false, 0);
    Song songSame2 = new Song("Lullaby", "Leonard Cohen", "Old Ideas", Rating.ZERO, false, 0);
    
    User user1 = new User("jscm1607", "crice1");
    
	
    // Before Each allows the code below to run before every test
    @BeforeEach
    void setUp() throws IOException {
    	// Load albums - avoid duplicated code
    	musicStore = new MusicStore();
    	library = new LibraryModel();
    	musicStore.loadAlbums();
    	library.loadAlbums();
    	library.getAutomaticPlaylists();
    	
    }
    
    @Test
    void testCheckUser() {
    	library.addUser(user1);
    	library.checkUser("jscm1607");
    	library.checkUser("crice1");
    }
    
    // Get song and album
	@Test
	void testGetSongByTitleOrArtist_NotInStore() throws IOException {
		ArrayList<Song> resTitle = library.getSongsByTitleOrArtist(song1.getTitle());
		ArrayList<Song> resArtist = library.getSongsByTitleOrArtist(song1.getArtist());
		
		assertEquals(0, resTitle.size());
		assertEquals(0, resArtist.size());
	}
	
	@Test
	void testGetSongByTitleOrArtist_InStore_OneSong() throws IOException {
		library.addSong(song2.getTitle(), musicStore, song2.getArtist());
		ArrayList<Song> resTitle = library.getSongsByTitleOrArtist(song2.getTitle());
		ArrayList<Song> resArtist = library.getSongsByTitleOrArtist(song2.getArtist());
		
		assertEquals(1, resTitle.size());
		assertNotEquals(1, resArtist.size());
	}
	
	@Test
	void testGetAlbumByTitleOrArtist() throws IOException {
		library.addAlbum(album2.getTitle(), musicStore, album2.getArtist());
		ArrayList<Album> resTitle = library.getAlbumsByTitleOrArtist(album2.getTitle());
		ArrayList<Album> resArtist = library.getAlbumsByTitleOrArtist(album2.getArtist());
		
		assertEquals(1, resTitle.size());
		assertEquals(1, resArtist.size());
	}
	
	@Test
	void testGetSongsByGenre() throws IOException {
		library.addAlbum(album2.getTitle(), musicStore, album2.getArtist());
		ArrayList<Song> resGenre = library.getSongsByGenre(album2.getGenre());
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
		
		library.addAlbum("21", musicStore, "Adele");
		library.addAlbum("21", musicStore, null);
	}
	
	@Test
	void testAddAlbum_NotInStore() throws IOException {
		library.addAlbum("Thriller", musicStore, null);
		assertEquals(0, library.getAlbums().size());
	}
	
	// Album duplicate
	@Test
	void testAddAlbum_Duplicate() throws IOException {
		library.addAlbum(album2.getTitle(), musicStore, album2.getArtist());
		assertEquals(1, library.getAlbums().size());
		library.addAlbum(album2.getTitle(), musicStore, album2.getArtist());
		assertEquals(1, library.getAlbums().size());
		
		library.addAlbum(album2.getTitle(), musicStore, null);
	}
	
	@Test
	void testAddAlbum_CheckFull() throws IOException {
		library.addSong("Lullaby", musicStore, null);
		library.addSong("Lullaby", musicStore, null);
		
		library.addAlbum("Sons", musicStore, null);
		library.addAlbum("Sons", musicStore, null);
	}
	
	@Test
	void testAddPartAlbum_InStore() throws IOException {
		library.addPartAlbum(album2.getTitle(), musicStore, null);
		assertEquals(1, library.getAlbums().size());
		
		library.addPartAlbum(songSame1.getAlbum(), musicStore, null);
		library.addPartAlbum(songSame2.getAlbum(), musicStore, null);
		library.addPartAlbum(songSame1.getAlbum(), musicStore, songSame1.getArtist());
	}
	
	@Test
	void testAddPartAlbum_NotInStore() throws IOException {
		library.addPartAlbum("Thriller", musicStore, null);
		assertEquals(0, library.getAlbums().size());
	}
	
	// Album duplicate
	@Test
	void testAddPartAlbum_Duplicate() throws IOException {
		library.addPartAlbum(album2.getTitle(), musicStore, null);
		assertEquals(1, library.getAlbums().size());
		library.addPartAlbum(album2.getTitle(), musicStore, null);
		assertEquals(1, library.getAlbums().size());
	}
	
	// Create Playlist
	@Test
	void testCreatePlaylist() throws IOException {
		library.createPlaylist("Yacht Rock");
		assertEquals(5, library.getPlaylistNames().size());
		library.createPlaylist("Yacht Rock");
		assertEquals(5, library.getPlaylistNames().size());
		library.createPlaylist("Top Rated Songs");
		assertEquals(5, library.getPlaylistNames().size());
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
		library.getAutomaticPlaylists();
		library.getPlaylistByName("Top Rated Songs");
	}
	
	@Test
	void testRemoveFromPlaylist_NotExist() throws IOException {
		library.createPlaylist("Pop");
		library.addToPlaylist("Pop", song2.getTitle());
		library.removeFromPlaylist("Rock", song2.getTitle());
		library.removeFromPlaylist("Pop", null);
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
		assertEquals(Rating.TWO, library.getSongsByTitleOrArtist(song2.getTitle()).get(0).getRating());
	}
	
	@Test
	void testRateSong_Favorite() {
		library.addSong(song2.getTitle(), musicStore, null);
		library.rateSong(song2.getTitle(), 5);
		library.setToFavorite(null);
		assertEquals(Rating.FIVE, library.getSongsByTitleOrArtist(song2.getTitle()).get(0).getRating());
		assertEquals(true, library.getSongsByTitleOrArtist(song2.getTitle()).get(0).isFavorite());
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
		assertEquals(5, library.getPlaylists().size());
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
		assertFalse(library.getSongTitles().contains(song2.getTitle()));
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
		library.addSong("Fire", musicStore, "Sons");
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
	void testSorting() {
	    library.addSong(song3.getTitle(), musicStore, song3.getArtist());
	    library.addSong(song4.getTitle(), musicStore, song4.getArtist());
	    library.addSong(song5.getTitle(), musicStore, song5.getArtist());

	    List<String> sortedSongsT = library.getSortedSongs("title");
	    
	    assertEquals(3, sortedSongsT.size()); // Only 3 songs added
	    assertEquals(song4.toString(), sortedSongsT.get(0));
	    assertEquals(song3.toString(), sortedSongsT.get(1));
	    assertEquals(song5.toString(), sortedSongsT.get(2));
	    
	    List<String> sortedSongsA = library.getSortedSongs("artist");
	    
	    assertEquals(3, sortedSongsA.size()); // Only 3 songs added
	    assertEquals(song3.toString(), sortedSongsA.get(0));
	    assertEquals(song5.toString(), sortedSongsA.get(1));
	    assertEquals(song4.toString(), sortedSongsA.get(2));
	    
	    library.rateSong(song3.getTitle(), 3);
	    library.rateSong(song4.getTitle(), 5);
	    library.rateSong(song5.getTitle(), 4);

	    List<String> sortedSongsR = library.getSortedSongs("rating");

	    assertEquals(3, sortedSongsR.size()); // Only 3 songs added
	    assertEquals(song3.toString(), sortedSongsR.get(0));
	    assertEquals(song5.toString(), sortedSongsR.get(1));
	    assertEquals(song4.toString(), sortedSongsR.get(2));
	    
	    List<String> sortedSongsG = library.getSortedSongs("genre");

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
		assertEquals("Song not in store!", library.playSong(song1.getTitle(), song1.getArtist()));
		library.addSong(song2.getTitle(), musicStore, song2.getArtist());
		assertEquals(0, song2.getCount());
		library.playSong(song2.getTitle(), song2.getArtist());
		
		library.addSong(songSame1.getTitle(), musicStore, null);
		library.playSong(songSame1.getTitle(), null);
		library.addSong(songSame1.getTitle(), musicStore, songSame1.getArtist());
		library.playSong(songSame1.getTitle(), songSame1.getArtist());	
		
		library.addSong(songSame2.getTitle(), musicStore, null);
		library.playSong(songSame2.getTitle(), null);
		library.addSong(songSame2.getTitle(), musicStore, songSame2.getArtist());
		library.playSong(songSame2.getTitle(), songSame2.getArtist());
		
		library.addSong(songSame1.getTitle(), musicStore, songSame1.getArtist());
		library.addSong(songSame2.getTitle(), musicStore, songSame2.getArtist());
		
		assertEquals("Artist needed.", library.playSong(songSame1.getTitle(), null));		
	}
	
	@Test
	void testAlbumInLibrary() {
		library.addAlbum(album1.getTitle(), musicStore, null);
		assertFalse(library.albumInLibrary(album1));
	}
	
	@Test
	void testGetShuffledPlaylist() {
		library.createPlaylist("happy");
		Iterator<Song> shuffle0 = library.getShuffledPlaylist("happy");
		library.addSong(song2.getTitle(), musicStore, song2.getArtist());
		library.addToPlaylist("happy", song2.getTitle());
		Iterator<Song> shuffle1 = library.getShuffledPlaylist("happy2");
		assertNotEquals(shuffle0, shuffle1);
	}
	
	@Test
	void testShuffleLibrary() {
		Iterator<Song> shuffle0 = library.shuffleLibrary();
		library.addSong(song2.getTitle(), musicStore, song2.getArtist());
		Iterator<Song> shuffle1 = library.shuffleLibrary();
		assertNotEquals(shuffle0, shuffle1);
	}
	
	@Test
	void testGetAutomaticPlaylists() {
		library.getAutomaticPlaylists();
		assertEquals(4, library.getPlaylists().size());
		
		library.addSong(songSame1.getTitle(), musicStore, songSame1.getArtist());
		library.playSong(songSame1.getTitle(), songSame1.getArtist());
		
		library.rateSong(songSame1.getTitle(), 5);
		
		library.addAlbum(album2.getTitle(), musicStore, album2.getArtist());
		assertEquals(1, library.getAutomaticPlaylists().get(2).getSongs().size());
	}
	
	@Test
	void testClearLibrary() {
		library.clearLibrary();
	}
	
	@Test
	void testAddSongOverloaded() {
		library.addAlbum(songSame1.getTitle(), musicStore, songSame1.getArtist());
		library.addSong(songSame1, null, 0);
		library.addSong(song2, null, 0);
	}
	
	@Test
	void testAddPlaylists() {
		library.createPlaylist("Philly Disco");
		library.addPlaylists(library.getPlaylists());
	}
	
	@Test
	void testGetUserPlaylists() {
		library.getUserPlaylists();
	}
	
}
