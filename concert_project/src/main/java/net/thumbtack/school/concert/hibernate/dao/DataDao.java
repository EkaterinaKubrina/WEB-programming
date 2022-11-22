package net.thumbtack.school.concert.hibernate.dao;

import net.thumbtack.school.concert.error.MyException;
import net.thumbtack.school.concert.hibernate.model.User;

import java.util.List;

public interface DataDao {
    void clearServer();
    boolean emptyData();
    List<String> getPilotConcertProgram() throws MyException;
}
