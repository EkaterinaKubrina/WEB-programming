package net.thumbtack.school.concert.db.dto.request;

public class AverageDtoRequest {
    private int id;
    private String token;

    public AverageDtoRequest(int id, String token) {
        this.token = token;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

}
