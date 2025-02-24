// 90+% coverage

package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RatingTest {
	private final int NUM_RATINGS = 6;
	
	@Test
	void testLength() {
		assertEquals(NUM_RATINGS, Rating.values().length);
	}

}
