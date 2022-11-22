package net.thumbtack.school.concert.hibernate.daoimpl;

import net.thumbtack.school.concert.hibernate.model.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    public static SessionFactory buildSessionFactory() {
        try {
            Configuration conf = new Configuration();
            conf.addAnnotatedClass(Artist.class);
            conf.addAnnotatedClass(Comment.class);
            conf.addAnnotatedClass(Composer.class);
            conf.addAnnotatedClass(MySession.class);
            conf.addAnnotatedClass(WordAuthor.class);
            conf.addAnnotatedClass(Song.class);
            conf.addAnnotatedClass(User.class);
            conf.addAnnotatedClass(Rating.class);
            conf.addAnnotatedClass(UserAgree.class);
            return conf.configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.out.print(ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
