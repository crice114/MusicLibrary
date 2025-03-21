

package model;

//import model.UserManager;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserManagerTest {

    private UserManager userManager;

    @BeforeEach
    void setup() {
        userManager = new UserManager();
        new File("users.txt").delete();
        new File("bobdylan_library.txt").delete();
    }

    @Test
    void testRegisterAndLoginUser() {
        assertTrue(userManager.registerUser("bob", "singer123"));
        assertTrue(userManager.loginUser("bob", "singer123"));
        assertFalse(userManager.loginUser("bob", "wrongPassword"));
    }

    @Test
    void testLibrarySaveAndLoad() {
        userManager.registerUser("bob", "singer123");
        List<String> library = List.of("Highway 61 Revisited", "Masters of War", "Lay Lady Lay");

        userManager.saveUserLibrary("bob", library);
        List<String> loadedLibrary = userManager.loadUserLibrary("bob");

        assertEquals(library, loadedLibrary);
    }
}


