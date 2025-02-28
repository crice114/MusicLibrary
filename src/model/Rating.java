/* Rating.java
 * This Java file include the Rating enum.
 * A simple enum dictating the possible song
 * ratings from 0 to 5. */

package model;

public enum Rating {
	// ENUM VALUES
    ZERO(0), ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5);

	// INSTANCE VARIABLE
    private final int stars;

    // CONSTRUCTOR
    Rating(int stars) {
        this.stars = stars;
    }

    // GETTER
    public int getStars() {
        return stars;
    }

    // toString
    @Override
    public String toString() {
        return "Rating: " + String.valueOf(stars) + "/5";
    }
}
