package net.thumbtack.school.concert.hibernate.model;



import javax.persistence.*;
import java.util.List;

@Entity
@Table(name ="comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String text;


    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "userAgree", joinColumns = @JoinColumn( name = "idComment"), inverseJoinColumns = {
            @JoinColumn(name = "idUser") })
    private List<User> usersAgreed;

    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name="idSong")
    private Song idSong;

    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name="idAuthorProposal")
    private User idAuthorProposal;


    public Comment() {
    }

    public Comment(int id, String text, List<User> usersAgreed, Song songC, User userC) {
        this.id = id;
        this.text = text;
        this.usersAgreed = usersAgreed;
        this.idSong = songC;
        this.idAuthorProposal = userC;
    }

    public Comment(String text, Song songC, User userC) {
        this.id = id;
        this.text = text;
        this.idSong = songC;
        this.idAuthorProposal = userC;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<User> getUsersAgreed() {
        return usersAgreed;
    }

    public void setUsersAgreed(List<User> usersAgreed) {
        this.usersAgreed = usersAgreed;
    }

    public Song getSongC() {
        return idSong;
    }

    public void setSongC(Song songC) {
        this.idSong = songC;
    }

    public User getUserC() {
        return idAuthorProposal;
    }

    public void setUserC(User userC) {
        this.idAuthorProposal = userC;
    }
}
