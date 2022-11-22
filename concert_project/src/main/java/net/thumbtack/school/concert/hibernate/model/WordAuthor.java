package net.thumbtack.school.concert.hibernate.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name ="wordAuthors")
public class WordAuthor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;

    @Fetch(FetchMode.SELECT)
    @ManyToMany(mappedBy = "wordAuthors", fetch = FetchType.EAGER)
    private List<Song> songs;

    public WordAuthor(int id, String name, List<Song> songs) {
        this.id = id;
        this.name = name;
        this.songs = songs;
    }

    public WordAuthor( String name) {
        this.name = name;
    }

    public WordAuthor() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
}
