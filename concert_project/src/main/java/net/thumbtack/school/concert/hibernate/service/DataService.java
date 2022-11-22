package net.thumbtack.school.concert.hibernate.service;

import com.google.gson.Gson;
import net.thumbtack.school.concert.db.dto.response.EmptyDataDtoResponse;
import net.thumbtack.school.concert.db.dto.response.EmptyResponse;
import net.thumbtack.school.concert.db.dto.response.PilotProgramConcertDtoResponse;
import net.thumbtack.school.concert.error.ErrorCode;
import net.thumbtack.school.concert.error.MyException;
import net.thumbtack.school.concert.hibernate.dao.DataDao;
import net.thumbtack.school.concert.hibernate.daoimpl.BaseDaoImpl;
import net.thumbtack.school.concert.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class DataService {
    private static final Logger LOGGER = LoggerFactory.getLogger(net.thumbtack.school.concert.db.service.DataService.class);
    private Gson gson = new Gson();
    private DataDao dataDao;
    private BaseDaoImpl baseDao = new BaseDaoImpl();

    public DataService(DataDao dataDao) {
        this.dataDao = dataDao;
    }


    public Response clearServer() {
        LOGGER.debug("Clear server...");
        dataDao.clearServer();
        return Response.ok(gson.toJson(new EmptyResponse()), MediaType.APPLICATION_JSON).build();
    }

    public Response emptyData() {
        LOGGER.debug("Getting empty data");
        boolean emptyData = dataDao.emptyData();
        return Response.ok(gson.toJson(new EmptyDataDtoResponse(emptyData)), MediaType.APPLICATION_JSON).build();
    }

    public Response pilotProgramOfConcert(String tokenDtoRequest) {
        LOGGER.debug("Getting pilot program of concert");
        try {
            if (baseDao.getByToken(tokenDtoRequest) == null) {
                throw new MyException(ErrorCode.DATA_BASE_NOT_FOUND);
            }
            dataDao.getPilotConcertProgram();

            LOGGER.debug("Successfully received");
            return Response.ok(gson.toJson(new PilotProgramConcertDtoResponse(dataDao.getPilotConcertProgram())), MediaType.APPLICATION_JSON).build();
        } catch (MyException e) {
            LOGGER.error(e.getMessage());
            return Utils.failureResponse(e);
        }
    }

}
