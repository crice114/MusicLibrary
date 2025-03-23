

	/*
    private UserManager userManager;

    @BeforeEach
    void setup() {
        userManager = new UserManager();
        new File("users.txt").delete();
        new File("bob_library.txt").delete();
        new File("dylan_library.txt").delete();
    }

    @Test
    void testMultipleUsersLibraryPersistence() {
        assertTrue(userManager.registerUser("bob", "singer123"));
        assertTrue(userManager.registerUser("dylan", "songwriter123"));

        userManager.saveUserLibrary("bob", List.of("Highway 61 Revisited", "Masters of War", "Lay Lady Lay"));
        userManager.saveUserLibrary("dylan", List.of("Tweedle Dee and Tweedle Dum", "Chimes of Freedom", "Like a Rolling Stone"));

        List<String> bobLib = userManager.loadUserLibrary("bob");
        List<String> dylanLib = userManager.loadUserLibrary("dylan");

        assertEquals(List.of("Highway 61 Revisited", "Masters of War", "Lay Lady Lay"), bobLib);
        assertEquals(List.of("Tweedle Dee and Tweedle Dum", "Chimes of Freedom", "Like a Rolling Stone"), dylanLib);
    }
}
*/
	
	package model;

	import org.junit.jupiter.api.*;

	import java.io.File;
	import java.util.List;

	import static org.junit.jupiter.api.Assertions.*;

	public class MultiUserTest {

	    private UserManager userManager;

	    @BeforeEach
	    void setup() {
	        userManager = new UserManager();
	        new File("users.txt").delete();
	        new File("bob_library.txt").delete();
	        new File("dylan_library.txt").delete();
	    }

	    @Test
	    void testMultipleUsersLibraryPersistence() {
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
