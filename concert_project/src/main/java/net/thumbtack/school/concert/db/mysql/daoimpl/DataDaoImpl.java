package net.thumbtack.school.concert.db.mysql.daoimpl;

import net.thumbtack.school.concert.db.dao.DataDao;
import net.thumbtack.school.concert.db.mydb.data.DataBase;
import net.thumbtack.school.concert.error.ErrorCode;
import net.thumbtack.school.concert.error.MyException;
import net.thumbtack.school.concert.db.model.*;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DataDaoImpl extends DaoImplBase implements DataDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataDaoImpl.class);


    public DataDaoImpl() {
    }

    @Override
    public void clearServer() {
        LOGGER.debug("Clear Database");
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).deleteAll();
                getSongMapper(sqlSession).deleteAll();
            } catch (RuntimeException ex) {
                LOGGER.info("Can't clear database");
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

    @Override
    public boolean emptyData() {
        return DataBase.emptyData();
    }

    @Override
    public List<String> getPilotConcertProgram() throws MyException {
        LOGGER.debug("DAO get pilot program");
        try (SqlSession sqlSession = getSession()) {
            try {
                List<String> answer = new ArrayList<>();
                List<Song> sortList = getDataMapper(sqlSession).getAllSongs();
                for(Song song : sortList){
                    song.setSumRating(getSongMapper(sqlSession).getSumRating(song));
                }
                sortList.sort(Comparator.comparing(Song::getSumRating));
                int amountOfDuration = 0;
                int numberOfSong = 0;
                Community community = new Community();
                for(Song song : sortList){
                    String author;
                    StringBuilder comments = new StringBuilder();
                    if(getDataMapper(sqlSession).getUserProposal(song) == null){
                        author = community.toString();
                    }
                    else {
                        author = getDataMapper(sqlSession).getUserProposal(song).toString();
                    }
                    for(String s:getDataMapper(sqlSession).getComments(song)){
                        comments.append("\"").append(s).append("\"").append("; ");
                    }
                    if(amountOfDuration+song.getDuration()+10<=3600){
                        amountOfDuration += song.getDuration()+10;
                        numberOfSong ++;
                        answer.add(numberOfSong + "st song:\n"+ "1. " + song.toString() + "\n" +
                                "2. " + author + "\n" +
                                "3. average rating - " + (double) song.getSumRating()/ getUserMapper(sqlSession).getTheCountOfUserRatedTheSong(song) + "\n" +
                                "4. " + comments);
                    }
                    else if(amountOfDuration+song.getDuration()<=3600){
                        amountOfDuration += song.getDuration();
                        numberOfSong ++;
                        answer.add(numberOfSong + "st song:\n"+ "1. " + song.toString() + "\n" +
                                "2. " + getUserMapper(sqlSession).getByLogin((String) song.getIdAuthorOfSuggestion()) + "\n" +
                                "3. average rating - " + (double) song.getSumRating()/ getUserMapper(sqlSession).getTheCountOfUserRatedTheSong(song) + "\n" +
                                "4. " + song.getCommentsToString() + "\n");
                    }
                }
                return answer;
            } catch (RuntimeException e) {
                LOGGER.debug("Can't get pilot program", e);
                sqlSession.rollback();
                throw new MyException(ErrorCode.DATABASE_ERROR);
            }
        }
    }

    @Override
    public User getUserByToken(String token) throws MyException {
        LOGGER.debug("DAO get User by Token {}", token);
        User user;
        try (SqlSession sqlSession = getSession()) {
            try {
                user = getUserMapper(sqlSession).getByToken(token);
            } catch (RuntimeException e) {
                LOGGER.debug("Can't get User by Token {}", token, e);
                sqlSession.rollback();
                throw new MyException(ErrorCode.DATABASE_ERROR);
            }
        }
        return user;
    }


}
