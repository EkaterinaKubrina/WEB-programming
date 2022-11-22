package net.thumbtack.school.concert.hibernate.model;

import javax.persistence.*;

@Entity
@Table(name = "userAgree")
public class UserAgree {
    @Id
    private int idUser;

    @Column
    private int idComment;

    public UserAgree(int idUser, int idComment) {
        this.idUser = idUser;
        this.idComment = idComment;
    }

    public UserAgree() {
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdComment() {
        return idComment;
    }

    public void setIdComment(int idComment) {
        this.idComment = idComment;
    }
}
