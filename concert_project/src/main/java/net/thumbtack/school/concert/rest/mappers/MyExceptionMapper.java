package net.thumbtack.school.concert.rest.mappers;

import net.thumbtack.school.concert.error.ErrorCode;
import net.thumbtack.school.concert.error.MyException;
import net.thumbtack.school.concert.utils.Utils;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class MyExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        StringBuilder message = new StringBuilder();
        for (ConstraintViolation<?> cv : exception.getConstraintViolations()) {
            message.append(cv.getPropertyPath() + " " + cv.getMessage() + "\n");
        }

        return Utils.failureResponse(Response.Status.BAD_REQUEST,
                new MyException(ErrorCode.VALIDATION_ERROR, message.toString()));
    }
}
