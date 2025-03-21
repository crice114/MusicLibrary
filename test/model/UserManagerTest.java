

package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserManagerTest {
    private UserManager userManager;
    
    @BeforeEach
    void setUp() {
        //delete users.txt and any test user library files before each test
    	//not sure if we need this if we just change the user and password to test. the test cases pass regardless tho
        File userFile = new File("users.txt");
        if (userFile.exists()) {
            userFile.delete();
        }

        //also delete any leftover test library files
        //again not sure we need to do this if we change the username and password for the tests. this doesnt seem to work like i thought it would
        File libraryFile = new File("testUser_library.txt");
        if (libraryFile.exists()) {
            libraryFile.delete();
        }

        userManager = new UserManager();
    }

    @Test
    void testRegisterAndLoginUser() {
    	
    	//change these values for new test to avoid the User already exists message
        String username = "testUser";
        String password = "testPassword";

        assertTrue(userManager.registerUser(username, password), "the user should be successfully registered");
        assertFalse(userManager.registerUser(username, password), "shouldnt be able to register duplicate users");

        assertTrue(userManager.loginUser(username, password), "proper login should be successful");
        assertFalse(userManager.loginUser(username, "wrongPassword"), "wrong password should fail to log in");
        assertFalse(userManager.loginUser("wrongUser", password), "wrong username should fail to log in");
    }

    @Test
    void testSaveAndLoadUserLibrary() {
        String username = "testUser";
        List<String> library = Arrays.asList("Bruises of a Peach", "November Rain", "Master of Puppets");

        userManager.saveUserLibrary(username, library);
        List<String> loadedLibrary = userManager.loadUserLibrary(username);

        assertEquals(library, loadedLibrary, "loaded and saved libraries should be the same");
    }
}



