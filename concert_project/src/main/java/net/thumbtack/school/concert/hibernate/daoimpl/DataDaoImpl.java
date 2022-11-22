package net.thumbtack.school.concert.hibernate.daoimpl;

import net.thumbtack.school.concert.error.MyException;
import net.thumbtack.school.concert.hibernate.dao.DataDao;
import net.thumbtack.school.concert.hibernate.model.*;
import net.thumbtack.school.concert.db.model.Community;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.*;

public class DataDaoImpl  extends BaseDaoImpl implements DataDao {

    @Override
    public void clearServer() {
        try (Session session = getSession()) {
            session.beginTransaction();
            @SuppressWarnings("unchecked")
            Query<User> deleteUsers = session.createQuery("DELETE FROM User");
            deleteUsers.executeUpdate();

            @SuppressWarnings("unchecked")
            Query<Song> deleteSongs = session.createQuery("DELETE FROM Song");
            deleteSongs.executeUpdate();

            @SuppressWarnings("unchecked")
            Query<Composer> deleteComposers = session.createQuery("DELETE FROM Composer");
            deleteComposers.executeUpdate();

            @SuppressWarnings("unchecked")
            Query<Artist> deleteArtists = session.createQuery("DELETE FROM Artist");
            deleteArtists.executeUpdate();

            @SuppressWarnings("unchecked")
            Query<WordAuthor> deleteWordAuthors = session.createQuery("DELETE FROM WordAuthor");
            deleteWordAuthors.executeUpdate();

            session.getTransaction().commit();
        }
    }

    @Override
    public boolean emptyData() {
        try (Session session = getSession()) {
            String hql = "SELECT count(*) as count FROM User ";
            Query<Long> query = session.createQuery(hql, Long.class);


            String hql1 = "SELECT count(*) as count FROM Song ";
            Query<Long> query1 = session.createQuery(hql1, Long.class);


            String hql2 = "SELECT count(*) as count FROM Composer ";
            Query<Long> query2 = session.createQuery(hql2, Long.class);


            String hql3 = "SELECT count(*) as count FROM Artist ";
            Query<Long> query3 = session.createQuery(hql3, Long.class);


            String hql4 = "SELECT count(*) as count FROM WordAuthor ";
            Query<Long> query4 = session.createQuery(hql4, Long.class);
            if(query.getSingleResult()>0 || query1.getSingleResult()>0 || query2.getSingleResult()>0 || query3.getSingleResult()>0 || query4.getSingleResult()>0 ){
                return false;
            }
        }
        return true;
    }

    @Override
    public List<String> getPilotConcertProgram() throws MyException {
        SongDaoImpl daoSong = new SongDaoImpl();
        UserDaoImpl daoUser = new UserDaoImpl();

        List<String> answer = new ArrayList<>();
        List<Song> sortList = daoSong.getAllSongs();
        Map<Integer, Song> songs = new TreeMap<>();
        for (Song song : sortList) {
            songs.put(daoSong.getSumRating(song.getId()), song);
        }
        int amountOfDuration = 0;
        int numberOfSong = 0;
        Community community = new Community();
        for (Song song : songs.values()) {
            String author;
            StringBuilder comments = new StringBuilder();
            if (song.getUserAuthor() == null) {
                author = community.toString();
            } else {
                author = song.getUserAuthor().toString();
            }
            for (String s : daoSong.getComments(song.getId())) {
                comments.append("\"").append(s).append("\"").append("; ");
            }
            if (amountOfDuration + song.getDuration() + 10 <= 3600) {
                amountOfDuration += song.getDuration() + 10;
                numberOfSong++;
                answer.add(numberOfSong + "st song:\n" + "1. " + song.toString() + "\n" +
                        "2. " + author + "\n" +
                        "3. average rating - " + daoSong.averageRatingOfSongs(song.getId()) + "\n" +
                        "4. " + comments);
            } else if (amountOfDuration + song.getDuration() <= 3600) {
                amountOfDuration += song.getDuration();
                numberOfSong++;
                answer.add(numberOfSong + "st song:\n" + "1. " + song.toString() + "\n" +
                        "2. " + daoUser.getByLogin(song.getUserAuthor().getLogin()) + "\n" +
                        "3. average rating - " + daoSong.averageRatingOfSongs(song.getId()) + "\n" +
                        "4. " + song.getComments().toString() + "\n");
            }
        }
        return answer;


    }

}
