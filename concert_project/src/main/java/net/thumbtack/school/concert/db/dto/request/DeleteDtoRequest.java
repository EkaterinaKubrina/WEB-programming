package net.thumbtack.school.concert.db.dto.request;

public class DeleteDtoRequest {

    private String token;

    public DeleteDtoRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
