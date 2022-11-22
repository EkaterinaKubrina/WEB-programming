package net.thumbtack.school.concert.db.dto.request;


public class UserDtoRequestInternal {
    private String firstName;
    private String lastName;
    private String login;
    private String password;

    public UserDtoRequestInternal(String firstName, String lastName, String login, String password) {
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
}
