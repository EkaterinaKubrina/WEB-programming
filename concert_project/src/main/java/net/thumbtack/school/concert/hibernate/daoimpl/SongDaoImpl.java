package net.thumbtack.school.concert.hibernate.daoimpl;

import net.thumbtack.school.concert.error.ErrorCode;
import net.thumbtack.school.concert.error.MyException;
import net.thumbtack.school.concert.hibernate.dao.SongDao;
import net.thumbtack.school.concert.hibernate.model.*;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SongDaoImpl  extends BaseDaoImpl  implements SongDao {


    @Override
    public int addSong(Song song, User user) {
        try (Session session = getSession()) {
            song.setUserAuthor(user);
            session.beginTransaction();
            session.save(song);
            session.save(new Rating(5, song, user));
            session.getTransaction().commit();
            return song.getId();
        }
    }

    public Song getSong(int id) throws MyException {
        try (Session session = getSession()) {
            return session.get(Song.class, id);
        } catch (NullPointerException e) {
            throw new MyException(ErrorCode.SONG_NOT_FOUND);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Song> getAllSongs() {
        try (Session session = getSession()) {
            return session.createQuery("FROM Song").list();
        }
    }

    @Override
    public Set<Song> getSongsByComposers(List<String> composers) {
        Set<Song> set = new HashSet<>(getSongsByComposer(composers.get(0)));
        for (String str : composers) {
            set.retainAll(getSongsByComposer(str));
        }
        return set;
    }

    @SuppressWarnings("unchecked")
    public List<Song> getSongsByComposer(String composer) {
        try (Session session = getSession()) {
            Query<Song> query = session.createQuery("SELECT s FROM Song s join s.composers c WHERE c.name=:paramName");
            query.setParameter("paramName", composer);
            return query.list();
        }
    }

    @Override
    public Set<Song> getAllSongsByEachComposers(List<String> composers) {
        Set<Song> set = new HashSet<>(getSongsByComposer(composers.get(0)));
        for (String str : composers) {
            set.addAll(getSongsByComposer(str));
        }
        return set;
    }

    @Override
    public Set<Song> getSongsByWordAuthors(List<String> wordAuthors) {
        Set<Song> set = new HashSet<>(getSongsByWordAuthor(wordAuthors.get(0)));
        for (String str : wordAuthors) {
            set.retainAll(getSongsByWordAuthor(str));
        }
        return set;
    }

    @SuppressWarnings("unchecked")
    public List<Song> getSongsByWordAuthor(String wordAuthor) {
        try (Session session = getSession()) {
            Query<Song> query = session.createQuery("SELECT s FROM Song s join s.wordAuthors c WHERE c.name=:paramName");
            query.setParameter("paramName", wordAuthor);
            return query.list();
        }
    }

    @Override
    public Set<Song> getAllSongsByEachWordAuthors(List<String> wordAuthors) {
        Set<Song> set = new HashSet<>(getSongsByWordAuthor(wordAuthors.get(0)));
        for (String str : wordAuthors) {
            set.addAll(getSongsByWordAuthor(str));
        }
        return set;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Set<Song> getSongsByArtist(String artist) {
        try (Session session = getSession()) {
            Query<Song> query = session.createQuery("SELECT s FROM Song s join s.artists c WHERE c.name=:paramName");
            query.setParameter("paramName", artist);
            return new HashSet<>(query.list());
        }
    }

    @Override
    public void rateSong(int id, User user, int rate) throws MyException {
        try (Session session = getSession()) {
            Song song = session.get(Song.class, id);
            session.beginTransaction();
            session.save(new Rating(rate, song, user));
            session.getTransaction().commit();
        } catch (NullPointerException e) {
            throw new MyException(ErrorCode.SONG_NOT_FOUND);
        }
    }

    @Override
    public void deleteRatingSong(int id, User user){
        try (Session session = getSession()) {
            Query<Rating> query = session.createQuery("FROM Rating WHERE idSong.id = :paramName AND idUser.id = :paramName1", Rating.class);
            query.setParameter("paramName", id);
            query.setParameter("paramName1", user.getId());
            session.beginTransaction();
            session.delete( query.getSingleResult() );
            session.getTransaction().commit();
        }
    }

    @Override
    public void setRatingSong(int id, User user, int rate) throws MyException {
        try (Session session = getSession()) {
            Query<Rating> query = session.createQuery("FROM Rating WHERE idSong.id = :paramName AND idUser.id = :paramName1", Rating.class);
            query.setParameter("paramName", id);
            query.setParameter("paramName1", user.getId());
            session.beginTransaction();
            query.getSingleResult().setRating(rate);
            session.update(query.getSingleResult());
            session.getTransaction().commit();
        }  catch (NoResultException e) {
            throw new MyException(ErrorCode.NULL_REQUEST);
        }
    }

    @Override
    public void cancelSong(int id, User user) throws MyException {
        try (Session session = getSession()) {
            Song song = session.get(Song.class, id);
            if(!song.getUserAuthor().getLogin().equals(user.getLogin())){
                throw new MyException(ErrorCode.ACTION_NOT_ALLOWED);
            }
            session.beginTransaction();
            session.delete(song);
            session.getTransaction().commit();
        } catch (NullPointerException e) {
            throw new MyException(ErrorCode.SONG_NOT_FOUND);
        }
    }

    @Override
    public double averageRatingOfSongs(int id) throws MyException {
        try (Session session = getSession()) {
            Query<Double> query = session.createQuery("SELECT AVG(rating) FROM Rating WHERE idSong.id = :paramName", Double.class);
            query.setParameter("paramName", id);
            return query.getSingleResult();
        } catch (NoResultException e) {
            throw new MyException(ErrorCode.NULL_REQUEST);
        }
    }

    public int getSumRating(int id) throws MyException {
        try (Session session = getSession()) {
            Query<Integer> query = session.createQuery("SELECT SUM(rating) FROM Rating WHERE idSong.id = :paramName", Integer.class);
            query.setParameter("paramName", id);
            return query.getSingleResult();
        }  catch (NoResultException e) {
            throw new MyException(ErrorCode.NULL_REQUEST);
        }
    }

    @Override
    public void addComment(String comment, User user, int id) throws MyException {
        try (Session session = getSession()) {
            Song song = session.get(Song.class, id);
            session.beginTransaction();
            session.save(new Comment(comment, song, user));
            session.getTransaction().commit();
        } catch (NullPointerException e) {
            throw new MyException(ErrorCode.SONG_NOT_FOUND);
        }
    }

    @Override
    public void deleteComment(String comment, User user, int id) throws MyException {
        try (Session session = getSession()) {
            Query<Comment> query = session.createQuery("FROM Comment WHERE idSong.id = :paramName AND text = :paramName1 ", Comment.class);
            query.setParameter("paramName", id);
            query.setParameter("paramName1", comment);
            if(!query.getSingleResult().getUserC().getLogin().equals(user.getLogin())){
                throw new MyException(ErrorCode.ACTION_NOT_ALLOWED);
            }
            session.beginTransaction();
            session.delete(query.getSingleResult());
            session.getTransaction().commit();
        } catch (NoResultException e) {
            throw new MyException(ErrorCode.COMMENT_NOT_FOUND);
        }
    }

    @Override
    public void setComment(String commentBefore, String commentAfter, User user, int id) throws MyException {
        try (Session session = getSession()) {
            Query<Comment> query = session.createQuery("FROM Comment WHERE idSong.id = :paramName AND text = :paramName1 ", Comment.class);
            query.setParameter("paramName", id);
            query.setParameter("paramName1", commentBefore);
            if(!query.getSingleResult().getUserC().getLogin().equals(user.getLogin())){
                throw new MyException(ErrorCode.ACTION_NOT_ALLOWED);
            }
            session.beginTransaction();
            query.getSingleResult().setText(commentAfter);
            session.update(query.getSingleResult());
            session.getTransaction().commit();
        } catch (NoResultException e) {
            throw new MyException(ErrorCode.COMMENT_NOT_FOUND);
        }
    }

    @Override
    public void addUserAgreeWithComment(String comment, User user, int id) throws MyException {
        try (Session session = getSession()) {
            Query<Comment> query = session.createQuery("FROM Comment WHERE idSong.id = :paramName AND text = :paramName1 ", Comment.class);
            query.setParameter("paramName", id);
            query.setParameter("paramName1", comment);
            session.beginTransaction();
            session.save(new UserAgree(user.getId(), query.getSingleResult().getId()));
            session.getTransaction().commit();
        } catch (NoResultException e) {
            throw new MyException(ErrorCode.COMMENT_NOT_FOUND);
        }
    }

    @Override
    public void deleteUserAgreeWithComment(String comment, User user, int id) throws MyException {
        try (Session session = getSession()) {
            Query<Comment> query = session.createQuery("FROM Comment WHERE idSong.id = :paramName AND text = :paramName1 ", Comment.class);
            query.setParameter("paramName", id);
            query.setParameter("paramName1", comment);
            session.beginTransaction();
            session.delete(new UserAgree(user.getId(), query.getSingleResult().getId()));
            session.getTransaction().commit();
        } catch (NoResultException e) {
            throw new MyException(ErrorCode.COMMENT_NOT_FOUND);
        }
    }

    @Override
    public List<String> getComments(int id) throws MyException {
        return getSong(id).getComments();
    }

    @Override
    public List<User> getUsersAgreeWithComment(int id, String comment) throws MyException {
        try (Session session = getSession()) {
            Query<Comment> query = session.createQuery("FROM Comment WHERE idSong.id = :paramName AND text = :paramName1 ", Comment.class);
            query.setParameter("paramName", id);
            query.setParameter("paramName1", comment);
            return query.getSingleResult().getUsersAgreed();
        }  catch (NoResultException e) {
            throw new MyException(ErrorCode.COMMENT_NOT_FOUND);
        }
    }
}
