package net.thumbtack.school.concert.error;

public class MyException extends Exception {

    private static final long serialVersionUID = 6049904777923589329L;
    private ErrorCode errorCode;
    private String param;

    public MyException(ErrorCode errorCode, String param) {
        this.errorCode = errorCode;
        this.param = param;
    }

    public MyException(ErrorCode errorCode) {
        this(errorCode, null);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        if (param != null)
            return String.format(errorCode.getMessage(), param);
        else
            return errorCode.getMessage();
    }
}
