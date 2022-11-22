package net.thumbtack.school.concert.hibernate.daoimpl;

import net.thumbtack.school.concert.error.ErrorCode;
import net.thumbtack.school.concert.error.MyException;
import net.thumbtack.school.concert.hibernate.dao.UserDao;
import net.thumbtack.school.concert.hibernate.model.MySession;
import net.thumbtack.school.concert.hibernate.model.User;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;


import javax.persistence.NoResultException;
import java.util.List;

public class UserDaoImpl extends BaseDaoImpl implements UserDao {


    @Override
    public void registerUser(User user) throws MyException {
        try (Session session = getSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (ConstraintViolationException e) {
            throw new MyException(ErrorCode.LOGIN_ALREADY_EXIST);
        }
    }

    @Override
    public void logout(String token) throws MyException {
        try (Session session = getSession()) {
            String hql = "FROM MySession WHERE token = :paramName";
            Query<MySession> query = session.createQuery(hql, MySession.class);
            query.setParameter("paramName", token);
                session.beginTransaction();
                session.delete(query.getSingleResult());
                session.getTransaction().commit();
        } catch (NoResultException e) {
            throw new MyException(ErrorCode.USER_NOT_FOUND);
        }
    }

    @Override
    public String login(User user, String token) throws MyException {
        try (Session session = getSession()) {
            String hql = "FROM User WHERE login = :paramName";
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("paramName", user.getLogin());
                session.beginTransaction();
                session.save(new MySession(user.getId(), user, token));
                session.getTransaction().commit();
                return token;
        }  catch (NoResultException e) {
            throw new MyException(ErrorCode.USER_NOT_FOUND);
        }
    }

    @Override
    public void delete(String token) throws MyException {
        try (Session session = getSession()) {
            String hql = "FROM MySession WHERE token = :paramName";
            Query<MySession> query = session.createQuery(hql, MySession.class);
            query.setParameter("paramName", token);
            session.beginTransaction();
            session.delete(query.getSingleResult().getUser());
            session.getTransaction().commit();
        } catch (NoResultException e) {
            throw new MyException(ErrorCode.USER_NOT_FOUND);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        try (Session session = getSession()) {
            return session.createQuery("FROM User").list();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllOnlineUsers() {
        try (Session session = getSession()) {
            return session.createQuery("SELECT u FROM User u join u.session s WHERE s.idUser = u.id").list();
        }
    }

    @Override
    public User getByLogin(String login) throws MyException {
        try (Session session = getSession()) {
            String hql1 = "FROM User WHERE login = :paramName";
            Query<User> query1 = session.createQuery(hql1, User.class);
            query1.setParameter("paramName", login);
            return query1.getSingleResult();
        } catch (NoResultException e) {
            throw new MyException(ErrorCode.USER_NOT_FOUND);
        }
    }

}
