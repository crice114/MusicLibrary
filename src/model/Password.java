package model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Password {
	public static String hashPassword(String password) {
       BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
       return passwordEncoder.encode(password);
   }
	
	public static boolean verifyPassword(String inputPassword, String storedHash) {
       BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
       return encoder.matches(inputPassword, storedHash);
   }
}
