/* PasswordManager.java
 * This Java file include the PasswordManager class.
 * It uses salts to hash user passwords. It also
 *  uses SecretKeyFactory and PBEKeySpec as libraries.
 *  Finally, it verifies the password. */


package model;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordManager {
    private static final int SALT_LENGTH = 16;
    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 256;

    // Hashes password and returns salt:hash as a single string
    public static String hashPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt = generateSalt();
        String saltStr = Base64.getEncoder().encodeToString(salt);
        String hashStr = Base64.getEncoder().encodeToString(getHash(password, salt));
        
        return saltStr + ":" + hashStr;
    }

    public static boolean verifyPassword(String password, String saltStr, String storedHash) {
        try {
            byte[] salt = Base64.getDecoder().decode(saltStr);
            byte[] hash = getHash(password, salt);
            String computedHash = Base64.getEncoder().encodeToString(hash);
            return computedHash.equals(storedHash);
        }
        
        catch (IllegalArgumentException e) {
            // This catches Base64 decoding errors
            return false;
        }
        
        catch (Exception e) {
            // Catches any unexpected errors in getHash or elsewhere
            return false;
        }
    }
    
    // Generates random salt
    private static byte[] generateSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        new SecureRandom().nextBytes(salt);
        
        return salt;
    }

    // Derives a hash using PBKDF2
    private static byte[] getHash(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        return skf.generateSecret(spec).getEncoded();
    }
}

