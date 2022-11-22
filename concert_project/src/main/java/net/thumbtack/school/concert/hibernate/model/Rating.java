package net.thumbtack.school.concert.hibernate.model;

import javax.persistence.*;

@Entity
@Table(name ="ratings")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private int rating;
    @Version
    private int version;

    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name="idSong")
    private Song idSong;

    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name="idUser")
    private User idUser;

    public Rating(int id, int rating, Song songR, User userR) {
        this.id = id;
        this.rating = rating;
        this.idSong = songR;
        this.idUser = userR;
    }

    public Rating( int rating, Song idSong, User idUser) {
        this.rating = rating;
        this.idSong = idSong;
        this.idUser = idUser;
    }

    public Rating() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Song getSongR() {
        return idSong;
    }

    public void setSongR(Song songR) {
        this.idSong = songR;
    }

    public User getUserR() {
        return idUser;
    }

    public void setUserR(User userR) {
        this.idUser = userR;
    }
}
