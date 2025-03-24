// 100% coverage

package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UserTest {
	User user1 = new User("jscm1607", "123456");
	
	@Test
	void testGetUsername() {
		user1.getUsername();
		user1.getPassword();
	}

}
