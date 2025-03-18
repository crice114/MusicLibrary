package model;

public class Password {
	public static String hashPassword(String password) {
		String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
		return hashed;
   }
	
	public static boolean verifyPassword(String inputPassword, String storedHash) {
		return BCrypt.checkpw(inputPassword, storedHash);
   }
}
