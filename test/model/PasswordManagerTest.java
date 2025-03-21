package model;




import  org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PasswordManagerTest {

    @Test
    void testHashAndVerifyPassword() {
        String password = "superSecret123!";
        String hash = PasswordManager.hashPassword(password);

        assertNotNull(hash, "Hashed password should not be null");
        assertTrue(PasswordManager.verifyPassword(password, hash), "correct password should verify");
        assertFalse(PasswordManager.verifyPassword("wrongPassword", hash), "incorrect password should fail verification");
    }

    @Test
    void testDifferentHashesForSamePassword() {
        String password = "samePassword";
        String hash1 = PasswordManager.hashPassword(password);
        String hash2 = PasswordManager.hashPassword(password);

        assertNotEquals(hash1, hash2, "hashes should be different due to different salts");
    }

    @Test
    void testMalformedStoredHash() {
        String password = "whatever";
        String badStoredHash = "not_a_valid_hash_format";

        assertFalse(PasswordManager.verifyPassword(password, badStoredHash), "wrong hash should return false");
    }
}