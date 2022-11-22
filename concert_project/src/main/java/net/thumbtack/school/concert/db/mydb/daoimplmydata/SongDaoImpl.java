package net.thumbtack.school.concert.db.mydb.daoimplmydata;

import net.thumbtack.school.concert.db.dao.SongDao;
import net.thumbtack.school.concert.db.mydb.data.DataBase;
import net.thumbtack.school.concert.error.MyException;
import net.thumbtack.school.concert.db.model.Song;
import net.thumbtack.school.concert.db.model.User;

import java.util.List;
import java.util.Set;

public class SongDaoImpl implements SongDao {


    @Override
    public User getUserByToken(String token) {
        return DataBase.getDataBase().getByToken(token);
    }

    @Override
    public int addSong(Song song, User user) throws MyException {
        return DataBase.getDataBase().addSong(song, user);
    }

    @Override
    public List<Song> getAllSongs(){
        return DataBase.getDataBase().getAllSongs();
    }

    @Override
    public Set<Song> getSongsByComposers(List<String> composers){
        return DataBase.getDataBase().getSongsByComposers(composers);
    }

    @Override
    public Set<Song> getAllSongsByEachComposers(List<String> composers){
        return DataBase.getDataBase().getAllSongsByEachComposers(composers);
    }

    @Override
    public Set<Song> getSongsByWordAuthors(List<String> wordAuthors) {
        return DataBase.getDataBase().getSongsByWordAuthors(wordAuthors);
    }

    @Override
    public Set<Song> getAllSongsByEachWordAuthors(List<String> wordAuthors) {
        return DataBase.getDataBase().getAllSongsByEachWordAuthors(wordAuthors);
    }

    @Override
    public Set<Song> getSongsByArtist(String artist) {
        return DataBase.getDataBase().getSongsByArtist(artist);
    }

    public void rateSong(int id, User user, int rate) throws MyException{
        DataBase.getDataBase().rateSong(id, user, rate);
    }

    @Override
    public void deleteRatingSong(int id, User user) throws MyException {
        DataBase.getDataBase().deleteRatingSong(id, user);
    }

    @Override
    public void setRatingSong(int id, User user, int rate) throws MyException {
        DataBase.getDataBase().setRatingSong(id, user, rate);
    }

    @Override
    public void cancelSong(int id, User user) throws MyException {
        DataBase.getDataBase().cancelSong(id, user);
    }

    @Override
    public double averageRatingOfSongs(int id) throws MyException {
        return DataBase.getDataBase().averageRatingOfSongs(id);
    }

    @Override
    public void addComment(String comment, User user, int id) throws MyException {
        DataBase.getDataBase().addComment(comment, user, id);
    }

    @Override
    public void deleteComment(String comment, User user, int id) throws MyException {
        DataBase.getDataBase().deleteComment(comment, user, id);
    }

    @Override
    public void setComment(String commentBefore, String commentAfter, User user, int id) throws MyException {
        DataBase.getDataBase().setComment(commentBefore, commentAfter, user, id);
    }

    @Override
    public void addUserAgreeWithComment(String comment, User user, int id) throws MyException {
        DataBase.getDataBase().addUserAgreeWithComment(comment, user, id);
    }

    @Override
    public void deleteUserAgreeWithComment(String comment, User user, int id) throws MyException {
        DataBase.getDataBase().deleteUserAgreeWithComment(comment, user, id);
    }

    @Override
    public List<String> getComments(int id) throws MyException {
        return DataBase.getDataBase().getComments(id);
    }

    @Override
    public List<User> getUsersAgreeWithComment(int id, String comment) throws MyException {
        return DataBase.getDataBase().getUsersAgreeWithComment(id, comment);
    }


}
