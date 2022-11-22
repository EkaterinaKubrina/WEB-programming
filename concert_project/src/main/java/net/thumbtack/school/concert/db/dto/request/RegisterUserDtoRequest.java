package net.thumbtack.school.concert.db.dto.request;


public class RegisterUserDtoRequest {
    private String firstName;
    private String lastName;
    private String login;
    private String password;

    public RegisterUserDtoRequest(String firstName, String lastName, String login, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
