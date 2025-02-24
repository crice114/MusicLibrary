package model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileReader;

import org.junit.jupiter.api.Test;

class MusicStoreTest {
	 String albumsTxtPath = "./src/albums/albums.txt";
     BufferedReader br = new BufferedReader(new FileReader(albumsTxtPath));
	
	@Test
	void testLoadAlbums() {
		fail("Not yet implemented");
	}

}
