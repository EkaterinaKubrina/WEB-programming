package net.thumbtack.school.concert.db.dto.request;


public class DeleteRatingSongDtoRequest {
    private int id;
    private String token;

    public DeleteRatingSongDtoRequest(int id, String token) {
        this.id = id;
        this.token = token;
    }


    public int getId() {
        return id;
    }

    public String getToken() {
        return token;
    }
}
