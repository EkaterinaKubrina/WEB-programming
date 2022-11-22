package net.thumbtack.school.concert.hibernate.model;

import javax.persistence.*;

@Entity
@Table(name ="sessions")
public class MySession {
    @Id
    private int idUser;
    @OneToOne ( fetch = FetchType.EAGER)
    @JoinColumn(name="idUser")
    private User user;
    @Column
    private String token;

    public MySession(int idUser, User user, String token) {
        this.idUser = idUser;
        this.user = user;
        this.token = token;
    }

    public MySession() {
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
