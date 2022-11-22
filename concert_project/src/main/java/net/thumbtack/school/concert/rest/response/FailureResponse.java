package net.thumbtack.school.concert.rest.response;


import net.thumbtack.school.concert.error.ErrorCode;

public class FailureResponse  extends BaseResponseObject {

    private ErrorCode errorCode;
    private String message;

    public FailureResponse(ErrorCode errorCode, String message) {
        super();
        this.errorCode = errorCode;
        this.message = message;
    }

    public FailureResponse( String message) {
        super();
        this.message = message;
    }

    public FailureResponse(ErrorCode errorCode) {
        this(errorCode, "");
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }


    public String getMessage() {
        return message;
    }


}
