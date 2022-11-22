package net.thumbtack.school.concert.hibernate.service;

import com.google.gson.Gson;
import net.thumbtack.school.concert.db.dto.request.*;
import net.thumbtack.school.concert.db.dto.response.*;
import net.thumbtack.school.concert.error.MyException;
import net.thumbtack.school.concert.gson.FromJson;
import net.thumbtack.school.concert.hibernate.dao.UserDao;
import net.thumbtack.school.concert.hibernate.model.User;
import net.thumbtack.school.concert.db.service.SongService;
import net.thumbtack.school.concert.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import static net.thumbtack.school.concert.error.ErrorCode.*;
import static net.thumbtack.school.concert.error.ErrorCode.INVALID_PASSWORD;

public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SongService.class);
    private static Gson gson = new Gson();
    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }


    public Response registerUser(String requestJsonString) {
        LOGGER.debug("Inserting user");
        try {
            RegisterUserDtoRequest registerUserDtoRequest = FromJson.getClassInstanceFromJson(requestJsonString, RegisterUserDtoRequest.class);
            validate(registerUserDtoRequest);
            User user = new User(registerUserDtoRequest.getFirstName(),
                    registerUserDtoRequest.getLastName(),
                    registerUserDtoRequest.getLogin(),
                    registerUserDtoRequest.getPassword());
            userDao.registerUser(user);
            String token = UUID.randomUUID().toString();
            String str = userDao.login(user, token);

            LOGGER.debug("Successfully insert");
            return Response.ok(gson.toJson(new RegisterUserDtoResponse(str)), MediaType.APPLICATION_JSON).build();
        } catch (MyException e) {
            LOGGER.error(e.getMessage());
            return Utils.failureResponse(e);
        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage());
            return Utils.failureResponse(e);
        }
    }

    public Response logout(String token){
        LOGGER.debug("Logout user");
        try {
            LogoutDtoRequest logoutDtoRequest = FromJson.getClassInstanceFromJson(token, LogoutDtoRequest.class);
            validate(logoutDtoRequest.getToken());
            userDao.logout(logoutDtoRequest.getToken());

            LOGGER.debug("Successfully logout");
            return Response.ok(gson.toJson(new EmptyResponse()), MediaType.APPLICATION_JSON).build();
        } catch (MyException e) {
            LOGGER.error(e.getMessage());
            return Utils.failureResponse(e);
        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage());
            return Utils.failureResponse(e);
        }

    }

    public Response login(String requestJsonString){
        LOGGER.debug("Login user");
        try {
            LoginDtoRequest loginDtoRequest = FromJson.getClassInstanceFromJson(requestJsonString, LoginDtoRequest.class);
            validate(loginDtoRequest);
            User user = userDao.getByLogin(loginDtoRequest.getLogin());
            if(user==null){
                throw new MyException(USER_NOT_FOUND);
            }
            validate(user, loginDtoRequest);
            String token = UUID.randomUUID().toString();
            LoginDtoResponse loginDtoResponse = new LoginDtoResponse(userDao.login(user, token));

            LOGGER.debug("Successfully login");
            return Response.ok(gson.toJson(loginDtoResponse), MediaType.APPLICATION_JSON).build();
        } catch (MyException e) {
            LOGGER.error(e.getMessage());
            return Utils.failureResponse(e);
        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage());
            return Utils.failureResponse(e);
        }
    }

    public Response delete(String token){
        LOGGER.debug("Delete user");
        try {
            validate(token);
            userDao.delete(token);

            LOGGER.debug("Successfully delete");
            return Response.ok(gson.toJson(new EmptyResponse()), MediaType.APPLICATION_JSON).build();
        } catch (MyException e) {
            LOGGER.error(e.getMessage());
            return Utils.failureResponse(e);
        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage());
            return Utils.failureResponse(e);
        }
    }

    public Response getAllUsers(){
        LOGGER.debug("Getting all users");
        try {
            List<User> list = userDao.getAllUsers();
            list.sort(Comparator.comparing(User::getFirstName));
            List<UserDtoRequestInternal> list1 = new ArrayList<>();
            for (User user : list) {
                list1.add(new UserDtoRequestInternal(user.getFirstName(),
                        user.getLastName(),
                        user.getLogin(),
                        user.getPassword()));
            }

            LOGGER.debug("Successfully received");
            return Response.ok(gson.toJson(new AllUsersDtoResponse(list1)), MediaType.APPLICATION_JSON).build();
        }
        catch (MyException e){
            LOGGER.error(e.getMessage());
            return Utils.failureResponse(e);
        }
    }

    public Response getAllOnlineUsers() {
        LOGGER.debug("Getting all online users");
        try {
            List<User> list = userDao.getAllOnlineUsers();
            list.sort(Comparator.comparing(User::getFirstName));
            List<UserDtoRequestInternal> list1 = new ArrayList<>();
            for (User user : list) {
                list1.add(new UserDtoRequestInternal(user.getFirstName(),
                        user.getLastName(),
                        user.getLogin(),
                        user.getPassword()));
            }

            LOGGER.debug("Successfully received");
            return Response.ok(gson.toJson(new AllOnlineUsersDtoResponse(list1)), MediaType.APPLICATION_JSON).build();
        } catch (MyException e) {
            LOGGER.error(e.getMessage());
            return Utils.failureResponse(e);
        }
    }


    private void validate(RegisterUserDtoRequest registerUserDtoRequest) throws MyException {
        if (registerUserDtoRequest.getFirstName() == null ||
                registerUserDtoRequest.getLastName() == null) {
            throw new MyException(INCORRECT_INPUT_DATA);
        } else if (registerUserDtoRequest.getLogin() == null ||
                registerUserDtoRequest.getLogin().length() < 4) {
            throw new MyException(INVALID_LOGIN);
        } else if (registerUserDtoRequest.getPassword() == null ||
                registerUserDtoRequest.getPassword().length() < 6 ||
                !registerUserDtoRequest.getPassword().matches("^.*[a-zA-Z].*$") ||
                !registerUserDtoRequest.getPassword().matches("^.*[0-9].*$") ||
                !registerUserDtoRequest.getPassword().matches("^.*[~!@#$%^&*()_+=?:;№|/'<>,.{}-].*$")) {
            throw new MyException(INVALID_PASSWORD);
        }
    }

    private void validate(LoginDtoRequest loginDtoRequest) throws MyException {
        if (loginDtoRequest.getLogin() == null ||
                loginDtoRequest.getLogin().length() < 4) {
            throw new MyException(INVALID_LOGIN);
        } else if (loginDtoRequest.getPassword() == null ||
                loginDtoRequest.getPassword().length() < 6 ||
                !loginDtoRequest.getPassword().matches("^.*[a-zA-Z].*$") ||
                !loginDtoRequest.getPassword().matches("^.*[0-9].*$") ||
                !loginDtoRequest.getPassword().matches("^.*[~!@#$%^&*()_+=?:;№|/'<>,.{}-].*$")) {
            throw new MyException(INVALID_PASSWORD);
        }
    }

    private void validate(User user, LoginDtoRequest loginDtoRequest) throws MyException {
        if (!user.getPassword().equals(loginDtoRequest.getPassword())) {
            throw new MyException(USER_NOT_FOUND);
        }
    }

    private void validate(String deleteDtoRequest) throws MyException {
        if (deleteDtoRequest == null || deleteDtoRequest.equals("")) {
            throw new MyException(INCORRECT_INPUT_DATA);
        }
    }
}
