// 94% coverage

package model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LibraryModelTest {
	// Determined variables to avoid duplicated code
    LibraryModel library;
	MusicStore musicStore;
	
	Song song1 = new Song("These Chains", "Toto", "The Seventh One", Rating.ZERO, false);
	Song song2 = new Song("Daydreamer", null, null, null, false);
	
    Album album1 = new Album("The Seventh One", "Toto", "Rock", 1988);
    Album album2 = new Album("19", "Adele", null, 0);
    Album album3 = new Album("A Rush of Blood to the Head", "Coldplay", null, 0);
	
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
		library.addSong("Daydreamer", musicStore, null);
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
		library.addSong("Daydreamer", musicStore, null);
		assertEquals(1, library.getSongs().size());
		library.addSong("Daydreamer", musicStore, null);
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
		library.addAlbum("19", musicStore, null);
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
		library.addAlbum("19", musicStore, null);
		assertEquals(1, library.getAlbums().size());
		library.addAlbum("19", musicStore, null);
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
		library.addToPlaylist("Pop", "Daydreamer");
		library.addToPlaylist("Yacht Rock", "Daydreamer");
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
		library.addAlbum("19", musicStore, null);
		assertEquals(1, library.getAlbums().size());
	}
	
	@Test
	void testGetPlaylists() {
		library.createPlaylist("70s Rock");
		assertEquals(1, library.getPlaylists().size());
	}
	
	@Test
	void testGetSongs() {
		library.addSong("Daydreamer", musicStore, null);
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
		library.addSong("Daydreamer", musicStore, "Adele");
		assertEquals("Daydreamer", library.getSongTitles().get(0));
		library.addAlbum("21", musicStore, null);
		assertEquals(13, library.getSongTitles().size());
	}
	
	@Test
	void testGetArtistNames_SongAndAlbum() {
		library.addSong("Daydreamer", musicStore, "Adele");
		assertEquals("Adele", library.getArtistNames().get(0));
		library.addAlbum("Sons", musicStore, null);
		assertEquals("The Heavy", library.getArtistNames().get(1));
	}
	
	@Test
	void testGetAlbumTitles_TitleAndAlbum() {
		library.addAlbum("19", musicStore, null);
		assertEquals("19", library.getAlbumTitles().get(0));
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
	
}
