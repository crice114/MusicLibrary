
package model;
public final class User {
	// INSTANCE VARIABLES
	private final String username;
	private final String password;
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;		
	}
	
	public String getUsername() {
		return username;
	}
	
	//add a getPassword method
	public String getPassword() {
		return password;
	}
}
