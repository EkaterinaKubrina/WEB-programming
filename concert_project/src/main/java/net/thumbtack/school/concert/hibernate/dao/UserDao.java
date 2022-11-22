package net.thumbtack.school.concert.hibernate.dao;

import net.thumbtack.school.concert.error.MyException;
import net.thumbtack.school.concert.hibernate.model.User;

import java.util.List;

public interface UserDao {
    void registerUser(User user) throws MyException;
    void logout(String token) throws MyException;
    String login(User user, String token) throws MyException;
    void delete(String token) throws MyException;
    List<User> getAllUsers() throws MyException;
    List<User> getAllOnlineUsers() throws MyException;
    User getByLogin(String login) throws MyException;
}
