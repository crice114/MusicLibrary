package model;


//import model.PasswordManager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PasswordManagerTest {

    @Test
    void testHashPasswordAndVerify() {
        String password = "secret123";
        String hashed = PasswordManager.hashPassword(password);

        assertNotNull(hashed, "the hash should not be null");
        assertTrue(PasswordManager.verifyPassword(password, hashed), "password not verified correctly");
        assertFalse(PasswordManager.verifyPassword("wrongPassword", hashed), "an incorrect password should not verify");
    }

    @Test
    void testDifferentPasswordsGiveDifferentHashes() {
        String hash1 = PasswordManager.hashPassword("password1");
        String hash2 = PasswordManager.hashPassword("password2");

        assertNotEquals(hash1, hash2, "different hashes required for different hashes");
    }
}
