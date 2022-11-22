package net.thumbtack.school.concert.error;


public class ServerException extends Exception {
    private ServerErrorCode serverErrorCode;

    public ServerException(ServerErrorCode serverErrorCode) {
        this.serverErrorCode = serverErrorCode;
    }

    public ServerErrorCode getErrorCode() {
        return serverErrorCode;
    }
}
