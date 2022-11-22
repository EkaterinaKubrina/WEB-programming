package net.thumbtack.school.concert.rest.mappers;

import javassist.NotFoundException;
import net.thumbtack.school.concert.error.ErrorCode;
import net.thumbtack.school.concert.error.MyException;
import net.thumbtack.school.concert.utils.Utils;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class WrongURLExceptionMapper implements ExceptionMapper<NotFoundException> {

    @Override
    public Response toResponse(NotFoundException exception) {
        return Utils.failureResponse(Response.Status.NOT_FOUND, new MyException(ErrorCode.WRONG_URL));
    }
}
