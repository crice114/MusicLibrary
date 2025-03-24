// 92% coverage

package model;

//import model.UserManager;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserManagerTest {

    private UserManager userManager;

    @BeforeEach
    void setup() {
        userManager = new UserManager();
        new File("data/users.txt").delete();
        new File("data/bobdylan_library.txt").delete();
    }

    @Test
    void testRegisterAndLoginUser() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
    	assertTrue(userManager.registerUser("bob", "singer123"));
        assertTrue(userManager.loginUser("bob", "singer123"));
        assertFalse(userManager.loginUser("bob", "wrongPassword"));
    }

    @Test
    void testLibrarySaveAndLoad() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        userManager.registerUser("bob", "singer123");
        List<String> library = List.of("Highway 61 Revisited", "Masters of War", "Lay Lady Lay");

        userManager.saveUserLibrary("bob", library);
        List<String> loadedLibrary = userManager.loadUserLibrary("bob");

        Playlist p1 = new Playlist("P1");
        List<Playlist> playlist = List.of(p1);
        
        userManager.saveUserPlaylists("bob", playlist);
        userManager.loadUserPlaylists("bob");
        
        assertEquals(library, loadedLibrary);
    }
    
    @Test
    void testUserExistsAndHashedPassword() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        userManager.registerUser("bob", "singer123");
        userManager.userExists("bob");
        userManager.userExists("dylan");
        userManager.getHashedPassword("bob");
        userManager.getHashedPassword("dylan");
    }
    
    @Test
    void testMultipleUsersLibraryPersistence() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
    	userManager = new UserManager();
        new File("users.txt").delete();
        new File("bob_library.txt").delete();
        new File("dylan_library.txt").delete();
    	
    	assertTrue(userManager.registerUser("bob", "singer123"));
        assertTrue(userManager.registerUser("dylan", "songwriter123"));

        Song bob1 = new Song("Highway 61 Revisited", "Bob Dylan", "Highway 61 Revisited", Rating.FOUR, true, 5);
        Song bob2 = new Song("Masters of War", "Bob Dylan", "The Freewheelin' Bob Dylan", Rating.THREE, false, 3);
        Song bob3 = new Song("Lay Lady Lay", "Bob Dylan", "Nashville Skyline", Rating.FIVE, true, 7);

        Song dylan1 = new Song("Tweedle Dee and Tweedle Dum", "Bob Dylan", "Love and Theft", Rating.TWO, false, 1);
        Song dylan2 = new Song("Chimes of Freedom", "Bob Dylan", "Another Side of Bob Dylan", Rating.THREE, false, 2);
        Song dylan3 = new Song("Like a Rolling Stone", "Bob Dylan", "Highway 61 Revisited", Rating.FIVE, true, 10);

        List<String> bobSongs = List.of(bob1.toFileString(), bob2.toFileString(), bob3.toFileString());
        List<String> dylanSongs = List.of(dylan1.toFileString(), dylan2.toFileString(), dylan3.toFileString());

        userManager.saveUserLibrary("bob", bobSongs);
        userManager.saveUserLibrary("dylan", dylanSongs);

        List<String> loadedBob = userManager.loadUserLibrary("bob");
        List<String> loadedDylan = userManager.loadUserLibrary("dylan");

        assertEquals(bobSongs, loadedBob);
        assertEquals(dylanSongs, loadedDylan);
    }
}


