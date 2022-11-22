package net.thumbtack.school.concert.error;

public enum ErrorCode {
    SUCCESS("", ""),
    NULL_REQUEST("json", "Null request"),
    JSON_PARSE_EXCEPTION("json", "Json parse exception :  %s"),
    WRONG_URL("url", "Wrong URL"),
    METHOD_NOT_ALLOWED("url", "Method not allowed"),
    VALIDATION_ERROR("validation", "Validation errors : %s"),
    DATA_BASE_NOT_FOUND("data", "Database not found"),
    DATA_BASE_ALREADY_EXIST("data", "Database already exist"),
    USER_NOT_FOUND("user", "user not found"),
    LOGIN_ALREADY_EXIST("user", "login already exist"),
    INCORRECT_INPUT_DATA("data", "incorrect input data"),
    INVALID_LOGIN("user", "incorrect login, length must be greater than 4 characters"),
    INVALID_PASSWORD("user", "incorrect password, length must be greater than 6 characters and also contain a digit, a latin letter and a special character"),
    ACTION_NOT_ALLOWED("data", "action not allowed"),
    RATING_ALREADY_ADDED("song", "rating already added"),
    SONG_NOT_FOUND("song", "song not found"),
    COMMENT_ALREADY_ADDED("song", "comment already added"),
    COMMENT_NOT_FOUND("song", "comment not found"),
    SONG_ALREADY_ADDED("song", "song already added"),
    DATABASE_ERROR("data", "Database error"),
    RuntimeException_ERROR("RuntimeException", "RuntimeException error")
    ;

    private String field;
    private String message;

    ErrorCode(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
