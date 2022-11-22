package net.thumbtack.school.concert.db.dto.response;

public class AverageDtoResponse {
    private double averageRatingOfSong;

    public AverageDtoResponse(double averageRatingOfSong) {
        this.averageRatingOfSong = averageRatingOfSong;
    }

    public double getAverageRatingOfSong() {
        return averageRatingOfSong;
    }
}
