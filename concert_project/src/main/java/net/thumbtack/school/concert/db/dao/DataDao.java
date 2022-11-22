package net.thumbtack.school.concert.db.dao;

import net.thumbtack.school.concert.error.MyException;
import net.thumbtack.school.concert.db.model.*;

import java.util.List;

public interface DataDao {
    void clearServer() throws MyException;
    boolean emptyData();
    List<String> getPilotConcertProgram() throws MyException;
    User getUserByToken(String token) throws MyException;
}
