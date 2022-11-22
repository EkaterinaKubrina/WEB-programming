package net.thumbtack.school.concert.db.model;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;


import java.util.List;
import java.util.Objects;

public class Song {
    private int id;
    private String songName;
    private List<String> composers;
    private List<String> wordAuthors;
    private String artist;
    private int duration;
    private MultiValuedMap<AuthorProposal, String> comments;
    private MultiValuedMap<String, User> usersAgreeWithComment;
    private int sumRating;
    private Object idAuthorOfSuggestion;

    public Song(String songName, List<String> composers, List<String> wordAuthors, String artist, int duration) {
        this.songName = songName;
        this.composers = composers;
        this.wordAuthors = wordAuthors;
        this.artist = artist;
        this.duration = duration;
        this.comments = new ArrayListValuedHashMap<>();
        this.usersAgreeWithComment = new ArrayListValuedHashMap<>();
    }

    public Song(int id, String songName, int duration,  String artist) {
        this.id = id;
        this.songName = songName;
        this.duration = duration;
        this.artist = artist;
    }

    public Song(int id, String songName, int duration, int sumRating, String artist) {
        this.id = id;
        this.songName = songName;
        this.duration = duration;
        this.sumRating = sumRating;
        this.artist = artist;
    }

    public Song(int id, String artist, int idAuthorOfSuggestion) {
        this.id = id;
        this.artist = artist;
        this.idAuthorOfSuggestion = idAuthorOfSuggestion;
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

    public MultiValuedMap<AuthorProposal, String> getComments() {
        return comments;
    }

    public String getCommentsToString() {
        StringBuilder answer = new StringBuilder();
        for(String s: comments.values()){
            answer.append("\"").append(s).append("\"").append("; ");
        }
        return answer.toString();
    }

    public MultiValuedMap<String, User> getUsersAgreeWithComment() {
        return usersAgreeWithComment;
    }

    public void addComment(AuthorProposal authorProposal, String s){
        comments.put(authorProposal, s);
    }

    public void deleteComment(AuthorProposal authorProposal, String s){
        comments.removeMapping(authorProposal, s);
    }

    public void deleteComments(AuthorProposal authorProposal){
        comments.remove(authorProposal);
    }

    public void addUserAgree(User user, String s){
        usersAgreeWithComment.put(s, user);
    }

    public void deleteUserAgree(String s, User user){
        usersAgreeWithComment.removeMapping(s, user);
    }

    public int getSumRating() {
        return sumRating;
    }

    public void setSumRating(int sumRating) {
        this.sumRating += sumRating;
    }

    public void deleteSumRating() {
        this.sumRating = 0;
    }

    public Object getIdAuthorOfSuggestion() {
        return idAuthorOfSuggestion;
    }

    public void setIdAuthorOfSuggestion(Object idAuthorOfSuggestion) {
        this.idAuthorOfSuggestion = idAuthorOfSuggestion;
    }

    @Override
    public String toString() {
        return "The song is title - " + songName +
                ", composer (s) - " + composers +
                ", author (s) of words- " + wordAuthors +
                ", artist - " + artist + '.';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Song)) return false;
        Song song = (Song) o;
        return getDuration() == song.getDuration() &&
                Objects.equals(getSongName(), song.getSongName()) &&
                Objects.equals(getComposers(), song.getComposers()) &&
                Objects.equals(getWordAuthors(), song.getWordAuthors()) &&
                Objects.equals(getArtist(), song.getArtist());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSongName(), getComposers(), getWordAuthors(), getArtist(), getDuration());
    }
}
