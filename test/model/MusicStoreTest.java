// 100% coverage

package model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class MusicStoreTest {
	// Determined variables to avoid duplicated code
	MusicStore musicStore = new MusicStore();
	
	Song song1 = new Song("These Chains", "Toto", "The Seventh One", Rating.ZERO, false, 0);
    Song song2 = new Song("Daydreamer", "Adele", "19", Rating.ZERO, false, 0);
	Album album1 = new Album("The Seventh One", "Toto", "Rock", 1988);
    Album album2 = new Album("19", "Adele", "Pop", 2008);
    Artist artist1 = new Artist("Toto");
    Artist artist2 = new Artist("Toto");
	
    // Getters and Load Albums
	@Test
	void testGetSongsByTitleOrArtist() {
		musicStore.addSong(song1);
		
		ArrayList<Song> resTitle = musicStore.getSongsByTitleOrArtist(song1.getTitle());
		ArrayList<Song> resArtist = musicStore.getSongsByTitleOrArtist(song1.getArtist());
		
		assertEquals(1, resTitle.size());
		assertEquals(1, resArtist.size());
	}
	
	@Test
	void testGetSongsByGenre() throws IOException {
		musicStore.addSong(song2);
		musicStore.addAlbum(album2);
		ArrayList<Song> resGenre = musicStore.getSongsByGenre(album2.getGenre());
	}
	
	@Test
	void testGetAlbumsByTitleOrArtist() {
		musicStore.addAlbum(album1);
		
		ArrayList<Album> resTitle = musicStore.getAlbumsByTitleOrArtist(album1.getTitle());
		ArrayList<Album> resArtist = musicStore.getAlbumsByTitleOrArtist(album1.getArtist());
		
		assertEquals(1, resTitle.size());
		assertEquals(1, resArtist.size());
	}
	
	@Test
	void testGetSongs() {
		musicStore.addSong(song1);
		assertEquals(1, musicStore.getSongs().size());
	}
	
	@Test
	void testGetArtists() {
		musicStore.addArtist(artist1);
		assertEquals(1, musicStore.getArtists().size());
	}
	
	@Test
	void testGetAlbums() {
		musicStore.addAlbum(album1);
		assertEquals(1, musicStore.getAlbums().size());
	}
	
	@Test
	void testLoadAlbums() throws IOException {
		musicStore.loadAlbums();
	}
	
}
