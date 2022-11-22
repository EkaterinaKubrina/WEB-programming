package net.thumbtack.school.concert.hibernate.model;


import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "songs")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String songName;
    @Column
    private int duration;
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "songs_composers", joinColumns = @JoinColumn( name = "idSong"), inverseJoinColumns = {
            @JoinColumn(name = "idComposer") })
    private List<Composer> composers;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "songs_wordAuthors", joinColumns = @JoinColumn( name = "idSong"), inverseJoinColumns = {
            @JoinColumn(name = "idWordAuthor") })
    private List<WordAuthor> wordAuthors;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "songs_artists", joinColumns = @JoinColumn( name = "idSong"), inverseJoinColumns = {
            @JoinColumn(name = "idArtist") })
    private List<Artist> artist;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade =  CascadeType.MERGE ,  mappedBy="idSong")
    private List<Comment> comments;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade =  CascadeType.MERGE ,  mappedBy="idSong")
    private List<Rating> ratings;

    @ManyToOne(cascade =  CascadeType.MERGE)
    @JoinColumn(name="idAuthorProposal")
    private User userAuthor;

    @Version
    private int version;



    public Song(int id, String songName, int duration, List<Composer> composers, List<WordAuthor> wordAuthors, List<Artist> artist, List<Comment> comments, List<Rating> ratings, User userAuthor, int version) {
        this.id = id;
        this.songName = songName;
        this.duration = duration;
        this.composers = composers;
        this.wordAuthors = wordAuthors;
        this.artist = artist;
        this.comments = comments;
        this.ratings = ratings;
        this.userAuthor = userAuthor;
        this.version = version;
    }

    public Song(String songName, List<String> composers, List<String> wordAuthors, String artist, int duration) {
        this.songName = songName;
        this.duration = duration;
        this.composers = new ArrayList<>();
        for(String s: composers){
            this.composers.add(new Composer(s));
        }
        this.wordAuthors = new ArrayList<>();
        for(String s: wordAuthors){
            this.wordAuthors.add(new WordAuthor(s));
        }
        this.artist = new ArrayList<>();
        this.artist.add(new Artist(artist));

    }

    public Song() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<String> getComposers() {
        List<String> ans = new ArrayList<>();
        for (Composer a : composers) {
            ans.add(a.getName());
        }
        return ans;
    }

    public void setComposers(List<Composer> composers) {
        this.composers = composers;
    }

    public List<String> getWordAuthors() {
        List<String> ans = new ArrayList<>();
        for (WordAuthor a : wordAuthors) {
            ans.add(a.getName());
        }
        return ans;
    }

    public void setWordAuthors(List<WordAuthor> wordAuthors) {
        this.wordAuthors = wordAuthors;
    }

    public List<String> getArtist() {
        List<String> ans = new ArrayList<>();
        for (Artist a: artist){
            ans.add(a.getName());
        }
        return ans;
    }

    public void setArtist(List<Artist> artist) {
        this.artist = artist;
    }

    public List<String> getComments() {
        List<String> ans = new ArrayList<>();
        for (Comment c: comments){
            ans.add(c.getText());
        }
        return ans;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }


    public User getUserAuthor() {
        return userAuthor;
    }

    public void setUserAuthor(User userAuthor) {
        this.userAuthor = userAuthor;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Song)) return false;
        Song song = (Song) o;
        return getId() == song.getId() &&
                getDuration() == song.getDuration() &&
                getVersion() == song.getVersion() &&
                Objects.equals(getSongName(), song.getSongName());
    }

}
