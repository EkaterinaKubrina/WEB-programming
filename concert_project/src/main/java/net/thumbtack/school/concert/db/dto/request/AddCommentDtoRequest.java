package net.thumbtack.school.concert.db.dto.request;


public class AddCommentDtoRequest {
    private String comment;
    private String token;
    private int id;

    public AddCommentDtoRequest(String comment, String token, int id)  {
        this.comment = comment;
        this.token = token;
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public String getToken() {
        return token;
    }

    public int getId() {
        return id;
    }
}
