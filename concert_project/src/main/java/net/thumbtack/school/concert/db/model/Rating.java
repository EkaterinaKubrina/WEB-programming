package net.thumbtack.school.concert.db.model;

public class Rating {
    private User ratingAuthor;
    private int rating;

    public Rating(User ratingAuthor, int rating) {
        this.ratingAuthor = ratingAuthor;
        this.rating = rating;
    }

    public User getRatingAuthor() {
        return ratingAuthor;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
