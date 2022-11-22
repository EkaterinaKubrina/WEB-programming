package net.thumbtack.school.concert.db.mydb.daoimplmydata;

import net.thumbtack.school.concert.db.dao.UserDao;
import net.thumbtack.school.concert.db.mydb.data.DataBase;
import net.thumbtack.school.concert.error.MyException;
import net.thumbtack.school.concert.db.model.User;

import java.util.List;

public class UserDaoImpl implements UserDao {

    @Override
    public void registerUser(User user) throws MyException {
        DataBase.getDataBase().registerUser(user);
    }

    @Override
    public void logout(String token) throws MyException {
        DataBase.getDataBase().logout(token);
    }

    @Override
    public String login(User user, String token) throws MyException {
        return DataBase.getDataBase().login(user.getLogin(), token);
    }

    @Override
    public void delete(String token) throws MyException {
        DataBase.getDataBase().delete(token);
    }

    @Override
    public List<User> getAllUsers(){
        return DataBase.getDataBase().getAllUsers();
    }

    @Override
    public List<User> getAllOnlineUsers(){
        return DataBase.getDataBase().getAllOnlineUsers();
    }

    @Override
    public User getByLogin(String login){
        return DataBase.getDataBase().getByLogin(login);
    }

    @Override
    public User getByToken(String token){
        return DataBase.getDataBase().getByToken(token);
    }
}
