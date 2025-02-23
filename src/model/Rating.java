package model;

public enum Rating {
    ZERO(0), ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5);

    private final int stars;

    Rating(int stars) {
        this.stars = stars;
    }

    public int getStars() {
        return stars;
    }

    @Override
    public String toString() {
        return "Rating: " + stars + "/5";
    }
}










/*package model;


public class Rating {
    // INSTANCE VARIABLES
    private int rating;  // Rating from 0-5

    // CONSTRUCTOR
    public Rating(int rating) {
    	
    	//validate that a valid rating is entered
    	if(rating < 0 || rating > 5) {
    		throw new IllegalArgumentException("Error: must enter a valid rating out of 5");
    	}
        this.rating = rating;
    }

    // GETTERS and SETTERS
    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }


    // Override toString for a nice output
    @Override
    public String toString() {
        return "Rating: " + rating + "/5";
    }
}*/
