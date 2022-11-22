package net.thumbtack.school.concert.hibernate.dao;

import net.thumbtack.school.concert.error.MyException;
import net.thumbtack.school.concert.hibernate.model.Song;
import net.thumbtack.school.concert.hibernate.model.User;

import java.util.List;
import java.util.Set;

public interface SongDao {
    int addSong(Song song, User user) throws MyException;
    List<Song> getAllSongs();
    Set<Song> getSongsByComposers(List<String> composers);
    Set<Song> getAllSongsByEachComposers(List<String> composers);
    Set<Song> getSongsByWordAuthors(List<String> wordAuthors);
    Set<Song> getAllSongsByEachWordAuthors(List<String> wordAuthors);
    Set<Song> getSongsByArtist(String artist);
    void rateSong(int id, User user, int rate) throws MyException;
    void deleteRatingSong(int id, User user) throws MyException;
    void setRatingSong(int id, User user, int rate) throws MyException;
    void cancelSong(int id, User user) throws MyException;
    double averageRatingOfSongs(int id) throws MyException;
    void addComment(String comment, User user, int id) throws MyException;
    void deleteComment(String comment, User user, int id) throws MyException;
    void setComment(String commentBefore, String commentAfter, User user, int id) throws MyException;
    void addUserAgreeWithComment(String comment, User user, int id) throws MyException;
    void deleteUserAgreeWithComment(String comment, User user, int id) throws MyException;
    List<String> getComments(int id) throws MyException;
    List<User> getUsersAgreeWithComment(int id, String comment) throws MyException;

}
