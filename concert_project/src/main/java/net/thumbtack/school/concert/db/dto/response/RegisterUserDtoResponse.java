package net.thumbtack.school.concert.db.dto.response;

public class RegisterUserDtoResponse {
    private String token;

    public RegisterUserDtoResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
