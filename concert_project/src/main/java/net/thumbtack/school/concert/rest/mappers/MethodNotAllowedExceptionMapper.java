package net.thumbtack.school.concert.rest.mappers;

import net.thumbtack.school.concert.error.ErrorCode;
import net.thumbtack.school.concert.error.MyException;
import net.thumbtack.school.concert.utils.Utils;

import javax.ws.rs.NotAllowedException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class MethodNotAllowedExceptionMapper implements ExceptionMapper<NotAllowedException> {

    @Override
    public Response toResponse(NotAllowedException exception) {
        return Utils.failureResponse(Response.Status.METHOD_NOT_ALLOWED, new MyException(ErrorCode.METHOD_NOT_ALLOWED));
    }
}
