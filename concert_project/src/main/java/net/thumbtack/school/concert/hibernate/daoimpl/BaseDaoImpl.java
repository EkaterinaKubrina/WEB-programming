package net.thumbtack.school.concert.hibernate.daoimpl;

import net.thumbtack.school.concert.error.ErrorCode;
import net.thumbtack.school.concert.error.MyException;
import net.thumbtack.school.concert.hibernate.model.MySession;
import net.thumbtack.school.concert.hibernate.model.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;


public class BaseDaoImpl {

    public BaseDaoImpl() {
    }

    protected Session getSession() {
        return HibernateUtils.getSessionFactory().openSession();
    }

    public User getByToken(String token) throws MyException {
        try (Session session = getSession()) {
            String hql1 = "FROM MySession WHERE token = :paramName";
            Query<MySession> query1 = session.createQuery(hql1, MySession.class);
            query1.setParameter("paramName", token);
            return query1.getSingleResult().getUser();
        } catch (NoResultException e) {
            throw new MyException(ErrorCode.USER_NOT_FOUND);
        }
    }
}
