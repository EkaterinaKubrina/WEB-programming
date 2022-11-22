package net.thumbtack.school.concert.db.dto.request;


public class GetUsersAgreeDtoRequest {
    private int id;
    private String comment;
    private String token;

    public GetUsersAgreeDtoRequest(int id, String comment, String token) {
        this.id = id;
        this.comment = comment;
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public String getToken() {
        return token;
    }

}
