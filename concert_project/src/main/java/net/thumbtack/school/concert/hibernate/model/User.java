package net.thumbtack.school.concert.hibernate.model;


import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String login;
    @Column
    private String password;
    @Version
    private int version;

    @OneToOne (fetch = FetchType.EAGER, mappedBy="user")
    private MySession session;

    @Fetch(FetchMode.SELECT)
    @ManyToMany(mappedBy = "usersAgreed", fetch = FetchType.EAGER)
    private List<Comment> commentsAgree;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade =  CascadeType.MERGE ,  mappedBy="idUser")
    private List<Rating> ratings;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade =  CascadeType.MERGE ,  mappedBy="idAuthorProposal")
    private List<Comment> comments;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade =  CascadeType.MERGE ,  mappedBy="userAuthor")
    private List<Song> songs;


    public User(int id, String firstName, String lastName, String login, String password, int version, MySession session, List<Comment> commentsAgree, List<Rating> ratings, List<Comment> comments, List<Song> songs) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.version = version;
        this.session = session;
        this.commentsAgree = commentsAgree;
        this.ratings = ratings;
        this.comments = comments;
        this.songs = songs;
    }

    public User(int id, String firstName, String lastName, String login, String password, int version) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.version = version;
    }

    public User(String firstName, String lastName, String login, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public MySession getSession() {
        return session;
    }

    public void setSession(MySession session) {
        this.session = session;
    }

    public List<Comment> getCommentsAgree() {
        return commentsAgree;
    }

    public void setCommentsAgree(List<Comment> commentsAgree) {
        this.commentsAgree = commentsAgree;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
}
