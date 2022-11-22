package net.thumbtack.school.concert.db.dto.request;

public class LoginDtoRequest {
    String login;
    String password;

    public LoginDtoRequest(String login, String password)  {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
