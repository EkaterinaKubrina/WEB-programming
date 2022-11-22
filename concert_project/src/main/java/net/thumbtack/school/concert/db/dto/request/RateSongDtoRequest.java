package net.thumbtack.school.concert.db.dto.request;


public class RateSongDtoRequest {
    private int id;
    private String token;
    private int rate;

    public RateSongDtoRequest(int id, String token, int rate) {
        this.id = id;
        this.token = token;
        this.rate = rate;
    }

    public int getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public int getRate() {
        return rate;
    }
}
