

package model;

//import model.UserManager;
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

        userManager.saveUserLibrary("bob", List.of("Highway 61 Revisited", "Masters of War", "Lay Lady Lay"));
        userManager.saveUserLibrary("dylan", List.of("Tweedle Dee and Tweedle Dum", "Chimes of Freedom", "Like a Rolling Stone"));

        List<String> bobLib = userManager.loadUserLibrary("bob");
        List<String> dylanLib = userManager.loadUserLibrary("dylan");

        assertEquals(List.of("Highway 61 Revisited", "Masters of War", "Lay Lady Lay"), bobLib);
        assertEquals(List.of("Tweedle Dee and Tweedle Dum", "Chimes of Freedom", "Like a Rolling Stone"), dylanLib);
    }
}
