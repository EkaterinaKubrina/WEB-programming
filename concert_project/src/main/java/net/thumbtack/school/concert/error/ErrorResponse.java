package net.thumbtack.school.concert.error;

public class ErrorResponse {
    private String error;

    public ErrorResponse(ServerException e) {
        this.error = e.getErrorCode().getErrorString();
    }
}
