package net.thumbtack.school.concert.db.dto.request;

public class TokenDtoRequest {
    private String token;

    public TokenDtoRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
