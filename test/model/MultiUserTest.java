package model;

import org.junit.jupiter.api.*;
import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class MultiUserTest {

    private final UserManager userManager = new UserManager();
    private final String user1 = "bob";
    private final String pass1 = "singer123";

    private final String user2 = "dylan";
    private final String pass2 = "songwriter123";

    private final List<String> library1 = Arrays.asList("Highway 61 Revisited", "Masters of War");
    private final List<String> library2 = Arrays.asList("Tweedle Dee and Tweedle Dum", "Chimes of Freedom", "Like a Rolling Stone");

    @BeforeEach
    void setUp() {
        // Clean up test files
        new File("users.txt").delete();
        new File(user1 + "_library.txt").delete();
        new File(user2 + "_library.txt").delete();
    }

    @AfterEach
    void tearDown() {
        // Clean up after tests
        new File("users.txt").delete();
        new File(user1 + "_library.txt").delete();
        new File(user2 + "_library.txt").delete();
    }

    @Test
    void testMultipleUsersLibraryPersistence() {
        // Register and login user1
        assertTrue(userManager.registerUser(user1, pass1));
        assertTrue(userManager.loginUser(user1, pass1));

        // Register and login user2
        assertTrue(userManager.registerUser(user2, pass2));
        assertTrue(userManager.loginUser(user2, pass2));

        // Save libraries
        userManager.saveUserLibrary(user1, library1);
        userManager.saveUserLibrary(user2, library2);

        // Load and verify user1's library
        List<String> loadedLibrary1 = userManager.loadUserLibrary(user1);
        assertEquals(library1, loadedLibrary1, "User1's library should be correctly loaded.");

        // Load and verify user2's library
        List<String> loadedLibrary2 = userManager.loadUserLibrary(user2);
        assertEquals(library2, loadedLibrary2, "User2's library should be correctly loaded.");
    }
}
