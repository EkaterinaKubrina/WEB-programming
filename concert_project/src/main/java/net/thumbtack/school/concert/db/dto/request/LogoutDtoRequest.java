package net.thumbtack.school.concert.db.dto.request;

public class LogoutDtoRequest {
    private String token;

    public LogoutDtoRequest(String token){
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
