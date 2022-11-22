package net.thumbtack.school.concert.error;

public enum ServerErrorCode {
    DATA_BASE_ALREADY_EXIST("База данных уже существует"),
    DATA_BASE_NOT_FOUND ("База данных не задана"),
    USER_NOT_FOUND("Пользователь не найден"),
    LOGIN_ALREADY_EXIST("Пользователь с таким логином уже существует"),
    INCORRECT_INPUT_DATA("Некорректные входные данные"),
    ACTION_NOT_ALLOWED("Действие недопустимо"),
    RATING_ALREADY_ADDED("Вы уже оценили эту песню"),
    SONG_NOT_FOUND("Песня не найдена"),
    COMMENT_ALREADY_ADDED("Такой комментарий уже был добавлен к этой песне"),
    COMMENT_NOT_FOUND("Комментарий не найден"),
    SONG_ALREADY_ADDED("Песня уже добавлена"),
    DATABASE_ERROR("Ошибка базы данных");

    private String errorString;

    ServerErrorCode(String errorString) {
        this.errorString = errorString;
    }

    public String getErrorString() {
        return errorString;
    }
}
