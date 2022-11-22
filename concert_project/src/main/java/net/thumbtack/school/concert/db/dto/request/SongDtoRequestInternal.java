package net.thumbtack.school.concert.db.dto.request;

import java.util.ArrayList;
import java.util.List;

public class SongDtoRequestInternal {
    private String songName;
    private List<String> composers;
    private List<String> wordAuthors;
    private String artist;
    private int duration;
    private List<String> comments;

    public SongDtoRequestInternal(String songName, List<String> composers, List<String> wordAuthors, String artist, int duration) {
        this.songName = songName;
        this.composers = composers;
        this.wordAuthors = wordAuthors;
        this.artist = artist;
        this.duration = duration;
        this.comments = new ArrayList<>();
    }

    public String getSongName() {
        return songName;
    }

    public List<String> getComposers() {
        return composers;
    }

    public List<String> getWordAuthors() {
        return wordAuthors;
    }

    public String getArtist() {
        return artist;
    }

    public int getDuration() {
        return duration;
    }



}
