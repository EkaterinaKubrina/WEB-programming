package net.thumbtack.school.concert.gson;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import net.thumbtack.school.concert.error.ErrorCode;
import net.thumbtack.school.concert.error.MyException;

public class FromJson {

    public static <T> T getClassInstanceFromJson(String json, Class<T> clazz) throws  MyException {
        try {
            Gson gson = new Gson();
            return gson.fromJson(json, clazz);
        } catch (JsonSyntaxException e) {
            throw new MyException(ErrorCode.INCORRECT_INPUT_DATA);
        }
    }

}
