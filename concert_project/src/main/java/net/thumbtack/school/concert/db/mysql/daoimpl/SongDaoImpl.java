package net.thumbtack.school.concert.db.mysql.daoimpl;

import net.thumbtack.school.concert.db.dao.SongDao;
import net.thumbtack.school.concert.error.ErrorCode;
import net.thumbtack.school.concert.error.MyException;
import net.thumbtack.school.concert.db.model.Song;
import net.thumbtack.school.concert.db.model.User;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;


public class SongDaoImpl extends DaoImplBase implements SongDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(SongDaoImpl.class);


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

    @Override
    public int addSong(Song song, User user) throws MyException {
        LOGGER.debug("DAO insert Song {} {}", song, user);
        try (SqlSession sqlSession = getSession()) {
            try {
                if(getSongMapper(sqlSession).getSong(song)!=null){
                    throw new MyException(ErrorCode.SONG_ALREADY_ADDED);
                }
                getSongMapper(sqlSession).insertSong(song, user);
                getSongMapper(sqlSession).insertArtists(song.getArtist());
                getSongMapper(sqlSession).insert_songsArtists(song);
                for(String composer : song.getComposers()){
                    getSongMapper(sqlSession).insertComposers(composer);
                    getSongMapper(sqlSession).insert_songsComposers(song);
                }
                for(String wordAuthor : song.getWordAuthors()){
                    getSongMapper(sqlSession).insertWordAuthors(wordAuthor);
                    getSongMapper(sqlSession).insert_songsWordAuthors(song);
                }
                getSongMapper(sqlSession).insertRate(song, user, 5);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert Song {} {}", song, user, ex);
                sqlSession.rollback();
                throw new MyException(ErrorCode.DATABASE_ERROR);
            }
            sqlSession.commit();
            return song.getId();
        }
    }


    @Override
    public List<Song> getAllSongs() throws MyException {
        LOGGER.debug("DAO select Songs");
        try (SqlSession sqlSession = getSession()) {
            try {
                return getSongMapper(sqlSession).getAllSongs();
            } catch (RuntimeException ex) {
                LOGGER.info("Can't select Song", ex);
                sqlSession.rollback();
                throw new MyException(ErrorCode.DATABASE_ERROR);
            }
        }
    }



    @Override
    public Set<Song> getSongsByComposers(List<String> composers) throws MyException {
        LOGGER.debug("DAO select Songs by Composers");
        try (SqlSession sqlSession = getSession()) {
            try {
                Set<Song> set = getSongMapper(sqlSession).getSongsByComposers(composers.get(0));
                for (String str : composers) {
                    set.retainAll(getSongMapper(sqlSession).getSongsByComposers(str));
                }
                return set;
            } catch (RuntimeException ex) {
                LOGGER.info("Can't select Song", ex);
                sqlSession.rollback();
                throw new MyException(ErrorCode.DATABASE_ERROR);
            }
        }
    }

    @Override
    public Set<Song> getAllSongsByEachComposers(List<String> composers) throws MyException {
        LOGGER.debug("DAO select Songs by each Composers");
        try (SqlSession sqlSession = getSession()) {
            try {
                Set<Song> set = getSongMapper(sqlSession).getSongsByComposers(composers.get(0));
                for (String str : composers) {
                    set.addAll(getSongMapper(sqlSession).getSongsByComposers(str));
                }
                return set;
            } catch (RuntimeException ex) {
                LOGGER.info("Can't select Song", ex);
                sqlSession.rollback();
                throw new MyException(ErrorCode.DATABASE_ERROR);
            }
        }
    }

    @Override
    public Set<Song> getSongsByWordAuthors(List<String> wordAuthors) throws MyException {
        LOGGER.debug("DAO select Songs by Word Authors");
        try (SqlSession sqlSession = getSession()) {
            try {
                Set<Song> set = getSongMapper(sqlSession).getSongsByWordAuthors(wordAuthors.get(0));
                for (String str : wordAuthors) {
                    set.retainAll(getSongMapper(sqlSession).getSongsByWordAuthors(str));
                }
                return set;
            } catch (RuntimeException ex) {
                LOGGER.info("Can't select Song", ex);
                sqlSession.rollback();
                throw new MyException(ErrorCode.DATABASE_ERROR);
            }
        }
    }

    @Override
    public Set<Song> getAllSongsByEachWordAuthors(List<String> wordAuthors) throws MyException {
        LOGGER.debug("DAO select Songs by each Word Authors");
        try (SqlSession sqlSession = getSession()) {
            try {
                Set<Song> set = getSongMapper(sqlSession).getSongsByWordAuthors(wordAuthors.get(0));
                for (String str : wordAuthors) {
                    set.addAll(getSongMapper(sqlSession).getSongsByWordAuthors(str));
                }
                return set;
            } catch (RuntimeException ex) {
                LOGGER.info("Can't select Song", ex);
                sqlSession.rollback();
                throw new MyException(ErrorCode.DATABASE_ERROR);
            }
        }
    }

    @Override
    public Set<Song> getSongsByArtist(String artist) throws MyException {
        LOGGER.debug("DAO select Songs  by artists");
        try (SqlSession sqlSession = getSession()) {
            try {
                return getSongMapper(sqlSession).getSongsByArtist(artist);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't select Songs", ex);
                sqlSession.rollback();
                throw new MyException(ErrorCode.DATABASE_ERROR);
            }
        }
    }

    public void rateSong(int id, User user, int rate) throws MyException {
        LOGGER.debug("DAO insert ratings {} {} {}", id, user, rate);
        try (SqlSession sqlSession = getSession()) {
            try {
                Song song1 = getSongMapper(sqlSession).getSongById(id);
                if (song1 == null) {
                    throw new MyException(ErrorCode.SONG_NOT_FOUND);
                }
                if(getSongMapper(sqlSession).getRate(song1, user)!=null){
                    throw new MyException(ErrorCode.RATING_ALREADY_ADDED);
                }
                getSongMapper(sqlSession).insertRate(song1, user, rate);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert ratings {} {} {}", id, user, rate, ex);
                sqlSession.rollback();
                throw new MyException(ErrorCode.DATABASE_ERROR);
            }
            sqlSession.commit();
        }
    }

    @Override
    public void deleteRatingSong(int id, User user) throws MyException {
        LOGGER.debug("DAO delete ratings {} {}", id, user);
        try (SqlSession sqlSession = getSession()) {
            try {
                Song song1 = getSongMapper(sqlSession).getSongById(id);
                if (song1 == null) {
                    throw new MyException(ErrorCode.SONG_NOT_FOUND);
                }
                if((int)song1.getIdAuthorOfSuggestion()==user.getId()){
                    throw new MyException(ErrorCode.ACTION_NOT_ALLOWED);
                }
                getSongMapper(sqlSession).deleteRate(song1, user);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete ratings  {} {}", id, user, ex);
                sqlSession.rollback();
                throw new MyException(ErrorCode.DATABASE_ERROR);
            }
            sqlSession.commit();
        }
    }

    @Override
    public void setRatingSong(int id, User user, int rate) throws MyException {
        LOGGER.debug("DAO set rating {} {}", id, user);
        try (SqlSession sqlSession = getSession()) {
            try {
                Song song1 = getSongMapper(sqlSession).getSongById(id);
                if (song1 == null) {
                    throw new MyException(ErrorCode.SONG_NOT_FOUND);
                }
                if((int)song1.getIdAuthorOfSuggestion()==user.getId()){
                    throw new MyException(ErrorCode.ACTION_NOT_ALLOWED);
                }
                getSongMapper(sqlSession).deleteRate(song1, user);
                getSongMapper(sqlSession).insertRate(song1, user, rate);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't set rating {} {}", id, user, ex);
                sqlSession.rollback();
                throw new MyException(ErrorCode.DATABASE_ERROR);
            }
            sqlSession.commit();
        }
    }

    @Override
    public void cancelSong(int id, User user) throws MyException {
        LOGGER.debug("DAO delete Song {} {}", id, user);
        try (SqlSession sqlSession = getSession()) {
            try {
                Song song1 = getSongMapper(sqlSession).getSongById(id);
                if (song1 == null) {
                    throw new MyException(ErrorCode.SONG_NOT_FOUND);
                }
                if((int)song1.getIdAuthorOfSuggestion()!=user.getId()){
                    throw new MyException(ErrorCode.ACTION_NOT_ALLOWED);
                }
                getSongMapper(sqlSession).cancelSong(id);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete Song {} {}", id, user, ex);
                sqlSession.rollback();
                throw new MyException(ErrorCode.DATABASE_ERROR);
            }
            sqlSession.commit();
        }
    }

    @Override
    public double averageRatingOfSongs(int id) throws MyException {
        LOGGER.debug("DAO select average Rating Of Songs");
        try (SqlSession sqlSession = getSession()) {
            try {
                Song song1 = getSongMapper(sqlSession).getSongById(id);
                if (song1 == null) {
                    throw new MyException(ErrorCode.SONG_NOT_FOUND);
                }
                return (double) getSongMapper(sqlSession).getSumRating(song1) / getUserMapper(sqlSession).getTheCountOfUserRatedTheSong(song1);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't select average Rating Of Songs", ex);
                sqlSession.rollback();
                throw new MyException(ErrorCode.DATABASE_ERROR);
            }
        }
    }

    @Override
    public void addComment(String comment, User user, int id) throws MyException {
        LOGGER.debug("DAO insert Comment {} {} {}", comment, id, user);
        try (SqlSession sqlSession = getSession()) {
            try {
                Song song1 = getSongMapper(sqlSession).getSongById(id);
                if (song1 == null) {
                    throw new MyException(ErrorCode.SONG_NOT_FOUND);
                }

                Object idComment = getSongMapper(sqlSession).getCommentId(comment, song1);
                if(idComment != null){
                    throw new MyException(ErrorCode.COMMENT_ALREADY_ADDED);
                }
                getSongMapper(sqlSession).insertComment(comment, song1, user);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert Comment {} {} {}", comment, id, user, ex);
                sqlSession.rollback();
                throw new MyException(ErrorCode.DATABASE_ERROR);
            }
            sqlSession.commit();
        }
    }

    @Override
    public void deleteComment(String comment, User user, int id) throws MyException {
        LOGGER.debug("DAO delete Comment {} {} {}", comment, id, user);
        try (SqlSession sqlSession = getSession()) {
            try {
                Song song1 = getSongMapper(sqlSession).getSongById(id);
                if (song1 == null) {
                    throw new MyException(ErrorCode.SONG_NOT_FOUND);
                }
                Object idC = getSongMapper(sqlSession).getCommentId(comment, song1);
                if(idC == null){
                    throw new MyException(ErrorCode.COMMENT_NOT_FOUND);
                }
                if(getSongMapper(sqlSession).getUsersAgreeWithComment((int)idC).size()==0){
                getSongMapper(sqlSession).deleteComment((int)idC, user);}
                else{
                    getSongMapper(sqlSession).updateAuthorComment((int) idC, user);
                }
            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete Comment {} {} {}", comment, id, user, ex);
                sqlSession.rollback();
                throw new MyException(ErrorCode.DATABASE_ERROR);
            }
            sqlSession.commit();
        }
    }

    @Override
    public void setComment(String commentBefore, String commentAfter, User user, int id) throws MyException {
        LOGGER.debug("DAO set Comment {} {} {} {}", commentBefore, commentAfter, id, user);
        try (SqlSession sqlSession = getSession()) {
            try {
                Song song1 = getSongMapper(sqlSession).getSongById(id);
                if (song1 == null) {
                    throw new MyException(ErrorCode.SONG_NOT_FOUND);
                }
                Object idC = getSongMapper(sqlSession).getCommentId(commentBefore, song1);
                if(idC == null){
                    throw new MyException(ErrorCode.COMMENT_NOT_FOUND);
                }
                if(getSongMapper(sqlSession).getUsersAgreeWithComment((int)idC).size()==0) {
                    getSongMapper(sqlSession).updateComment((int) idC, commentAfter, user);
                }
                else{
                    getSongMapper(sqlSession).updateAuthorComment((int) idC, user);
                    getSongMapper(sqlSession).insertComment(commentAfter, song1, user);
                }
            } catch (RuntimeException ex) {
                LOGGER.info("Can't set Comment {} {} {} {}", commentBefore, commentAfter, id, user, ex);
                sqlSession.rollback();
                throw new MyException(ErrorCode.DATABASE_ERROR);
            }
            sqlSession.commit();
        }
    }

    @Override
    public void addUserAgreeWithComment(String comment, User user, int id) throws MyException {
        LOGGER.debug("DAO insert user agree with comment {} {} {} ", comment, id, user);
        try (SqlSession sqlSession = getSession()) {
            try {
                Song song1 = getSongMapper(sqlSession).getSongById(id);
                if (song1 == null) {
                    throw new MyException(ErrorCode.SONG_NOT_FOUND);
                }
                Object idC = getSongMapper(sqlSession).getCommentId(comment, song1);
                if(idC == null){
                    throw new MyException(ErrorCode.COMMENT_NOT_FOUND);
                }
                getSongMapper(sqlSession).insertUserAgreeWithComments((int) idC, user);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert user agree with comment {} {} {} ", comment, id, user, ex);
                sqlSession.rollback();
                throw new MyException(ErrorCode.DATABASE_ERROR);
            }
            sqlSession.commit();
        }
    }

    @Override
    public void deleteUserAgreeWithComment(String comment, User user, int id) throws MyException {
        LOGGER.debug("DAO delete user agree with comment {} {} {} ", comment, id, user);
        try (SqlSession sqlSession = getSession()) {
            try {
                Song song1 = getSongMapper(sqlSession).getSongById(id);
                if (song1 == null) {
                    throw new MyException(ErrorCode.SONG_NOT_FOUND);
                }
                Object idC = getSongMapper(sqlSession).getCommentId(comment, song1);
                if(idC == null){
                    throw new MyException(ErrorCode.COMMENT_NOT_FOUND);
                }
                getSongMapper(sqlSession).deleteUserAgreeWithComments((int) idC, user);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete user agree with comment {} {} {} ", comment, id, user, ex);
                sqlSession.rollback();
                throw new MyException(ErrorCode.DATABASE_ERROR);
            }
            sqlSession.commit();
        }
    }

    @Override
    public List<String> getComments(int id) throws MyException {
        LOGGER.debug("DAO select Comments");
        try (SqlSession sqlSession = getSession()) {
            try {
                Song song1 = getSongMapper(sqlSession).getSongById(id);
                if (song1 == null) {
                    throw new MyException(ErrorCode.SONG_NOT_FOUND);
                }
                return getSongMapper(sqlSession).getComments(song1);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't select Comments", ex);
                sqlSession.rollback();
                throw new MyException(ErrorCode.DATABASE_ERROR);
            }
        }
    }

    @Override
    public List<User> getUsersAgreeWithComment(int id, String comment) throws MyException {
        LOGGER.debug("DAO select users agree with comment {} {} ", comment, id);
        try (SqlSession sqlSession = getSession()) {
            try {
                Song song1 = getSongMapper(sqlSession).getSongById(id);
                if (song1 == null) {
                    throw new MyException(ErrorCode.SONG_NOT_FOUND);
                }
                Object idC = getSongMapper(sqlSession).getCommentId(comment, song1);
                if(idC == null){
                    throw new MyException(ErrorCode.COMMENT_NOT_FOUND);
                }
                return  getSongMapper(sqlSession).getUsersAgreeWithComment((int) idC);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't select users agree with comment {} {} {} ", comment, id, ex);
                sqlSession.rollback();
                throw new MyException(ErrorCode.DATABASE_ERROR);
            }
        }
    }



}
