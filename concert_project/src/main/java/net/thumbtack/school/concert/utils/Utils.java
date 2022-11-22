package net.thumbtack.school.concert.utils;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;
import net.thumbtack.school.concert.error.MyException;
import net.thumbtack.school.concert.rest.response.FailureResponse;

import static net.thumbtack.school.concert.error.ErrorCode.RuntimeException_ERROR;

public class Utils {
    private static final Gson GSON = new Gson();

    public static Response failureResponse(Status status, MyException ex) {
        return Response.status(status).entity(GSON.toJson(new FailureResponse(ex.getErrorCode(), ex.getMessage())))
                .type(MediaType.APPLICATION_JSON).build();
    }

    public static Response failureResponse(MyException ex) {
        return failureResponse(Status.BAD_REQUEST, ex);
    }

    public static Response failureResponse(Status status, RuntimeException ex) {
        return Response.status(status).entity(GSON.toJson(new FailureResponse(RuntimeException_ERROR, ex.getMessage())))
                .type(MediaType.APPLICATION_JSON).build();
    }

    public static Response failureResponse(RuntimeException ex) {
        return failureResponse(Status.BAD_REQUEST, ex);
    }

}
