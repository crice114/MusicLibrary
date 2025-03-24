// 96% coverage

package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class PasswordManagerTest {
    @Test
    void testHashAndVerifyPassword() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String password = "password321";
        String stored = PasswordManager.hashPassword(password); // salt:hash
        String[] parts = stored.split(":");
        assertEquals(2, parts.length, "Hash should contain salt and hash separated by ':'");

        String salt = parts[0];
        String hash = parts[1];

        assertNotNull(stored, "hashed password should not be null");
        assertTrue(PasswordManager.verifyPassword(password, salt, hash), "correct password should verify");
        assertFalse(PasswordManager.verifyPassword("wrongPassword", salt, hash), "incorrect password should fail verification");
        
        assertFalse(PasswordManager.verifyPassword(null, null, null), "incorrect password should fail verification");
    }

    @Test
    void testDifferentHashesForSamePassword() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String password = "samePassword";
        String hash1 = PasswordManager.hashPassword(password);
        String hash2 = PasswordManager.hashPassword(password);

        assertNotEquals(hash1, hash2, "hashes should be different due to different salts");
    }

    @Test
    void testMalformedStoredHash() {
        String password = "wrong";
        String badSalt = "bad_salt";
        String badHash = "bad_hash";

        assertFalse(PasswordManager.verifyPassword(password, badSalt, badHash), "Malformed salt or hash should return false");
    }
    
    @Test
    void testGetHash() throws Exception {
        String password = "password321";
    	byte[] salt = new byte[] {8,16};

    	// Reflection to test private class
    	Method method = PasswordManager.class.getDeclaredMethod("getHash", String.class, byte[].class);
    	method.setAccessible(true);
    	
    	assertNotNull(method.invoke(method, password, salt));
    }
    
}
