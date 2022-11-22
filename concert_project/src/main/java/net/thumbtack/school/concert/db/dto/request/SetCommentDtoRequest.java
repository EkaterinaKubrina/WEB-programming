package net.thumbtack.school.concert.db.dto.request;


public class SetCommentDtoRequest {
    private String commentBefore;
    private String commentAfter;
    private String token;
    private int id;

    public SetCommentDtoRequest(String commentBefore, String commentAfter, String token, int id){
        this.commentBefore = commentBefore;
        this.commentAfter = commentAfter;
        this.token = token;
        this.id = id;
    }

    public String getCommentBefore() {
        return commentBefore;
    }

    public String getCommentAfter() {
        return commentAfter;
    }

    public String getToken() {
        return token;
    }

    public int getId() {
        return id;
    }
}
