package net.thumbtack.school.concert.db.mysql.daoimpl;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import net.thumbtack.school.concert.db.dao.UserDao;
import net.thumbtack.school.concert.error.ErrorCode;
import net.thumbtack.school.concert.error.MyException;
import net.thumbtack.school.concert.db.model.Song;
import net.thumbtack.school.concert.db.model.User;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;



public class UserDaoImpl extends DaoImplBase implements UserDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataDaoImpl.class);

    @Override
    public void registerUser(User user) throws MyException {
        LOGGER.debug("DAO insert User {} ", user);
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).insertUser(user);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert User {} {}", user.getLogin(), ex);
                sqlSession.rollback();
                if (ex.getCause() instanceof MySQLIntegrityConstraintViolationException) {
                    throw new MyException(ErrorCode.LOGIN_ALREADY_EXIST);
                }
            }
            sqlSession.commit();
        }
    }

    @Override
    public String login(User user, String token) throws MyException {
        LOGGER.debug("DAO insert Session {} {}", user, token);
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).insert(user, token);
            } catch (RuntimeException e) {
                LOGGER.debug("Can't insert Session {} {}", user, token, e);
                sqlSession.rollback();
                throw new MyException(ErrorCode.DATABASE_ERROR);
            }
            sqlSession.commit();
        }
        return token;
    }

    @Override
    public User getByLogin(String login) throws MyException {
        LOGGER.debug("DAO get User by Login {}", login);
        User user;
        try (SqlSession sqlSession = getSession()) {
            try {
                user = getUserMapper(sqlSession).getByLogin(login);
            } catch (RuntimeException e) {
                LOGGER.debug("Can't get User by Login {}", login, e);
                sqlSession.rollback();
                throw new MyException(ErrorCode.DATABASE_ERROR);
            }
        }
        return user;
    }

    @Override
    public User getByToken(String token) throws MyException {
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
    public void logout(String token) throws MyException {
        LOGGER.debug("DAO delete session {}", token);
        try (SqlSession sqlSession = getSession()) {
            try {
                if(getByToken(token)==null){
                    throw new MyException(ErrorCode.USER_NOT_FOUND);
                }
                getUserMapper(sqlSession).logout(token);
            } catch (RuntimeException e) {
                LOGGER.debug("Can't delete session {}", token, e);
                sqlSession.rollback();
                throw new MyException(ErrorCode.DATABASE_ERROR);
            }
            sqlSession.commit();
        }
    }

    @Override
    public void delete(String token) throws MyException {
        LOGGER.debug("DAO delete User by token {}", token);
        try (SqlSession sqlSession = getSession()) {
            try {
                User user = getUserMapper(sqlSession).getByToken(token);
                if(user == null){
                    throw new MyException(ErrorCode.USER_NOT_FOUND);
                }
                for(Song song : getUserMapper(sqlSession).getAllSongsByUser(user.getId())){
                    if(getUserMapper(sqlSession).getTheCountOfUserRatedTheSong(song)<=1){
                        getSongMapper(sqlSession).cancelSong(song.getId());
                    }
                }
                getUserMapper(sqlSession).delete(user.getId());
            } catch (RuntimeException e) {
                LOGGER.debug("Can't delete User {}", token, e);
                sqlSession.rollback();
                throw new MyException(ErrorCode.DATABASE_ERROR);
            }
            sqlSession.commit();
        }
    }

    @Override
    public List<User> getAllUsers() throws MyException {
        LOGGER.debug("DAO get all Users ");
        try (SqlSession sqlSession = getSession()) {
            return getUserMapper(sqlSession).getAll();
        } catch (RuntimeException e) {
            LOGGER.debug("Can't get all Users ", e);
            throw new MyException(ErrorCode.DATABASE_ERROR);
        }
    }

    @Override
    public List<User> getAllOnlineUsers() throws MyException {
        LOGGER.debug("DAO get all online Users ");
        try (SqlSession sqlSession = getSession()) {
            return getUserMapper(sqlSession).getAllOnlineUsers();
        } catch (RuntimeException e) {
            LOGGER.debug("Can't get all online Users ", e);
            throw new MyException(ErrorCode.DATABASE_ERROR);
        }
    }

}
