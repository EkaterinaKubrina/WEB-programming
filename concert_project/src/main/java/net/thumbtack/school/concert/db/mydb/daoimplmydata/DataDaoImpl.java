package net.thumbtack.school.concert.db.mydb.daoimplmydata;

import net.thumbtack.school.concert.db.dao.DataDao;
import net.thumbtack.school.concert.db.mydb.data.DataBase;
import net.thumbtack.school.concert.error.MyException;
import net.thumbtack.school.concert.db.model.User;

import java.util.List;

public class DataDaoImpl implements DataDao {
    @Override
    public void clearServer() throws MyException {
        DataBase.clearServer();
    }

    @Override
    public boolean emptyData() {
        return DataBase.emptyData();
    }

    @Override
    public List<String> getPilotConcertProgram() throws MyException {
        return DataBase.getDataBase().pilotProgramOfConcert();
    }

    @Override
    public User getUserByToken(String token) {
        return DataBase.getDataBase().getByToken(token);
    }


}
