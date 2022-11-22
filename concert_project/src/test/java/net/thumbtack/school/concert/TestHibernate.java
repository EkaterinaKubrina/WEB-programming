package net.thumbtack.school.concert;

import static org.junit.Assert.assertNotNull;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

import com.google.gson.Gson;
import net.thumbtack.school.concert.client.MyClient;
import net.thumbtack.school.concert.db.dto.request.*;
import net.thumbtack.school.concert.db.dto.response.*;
import net.thumbtack.school.concert.error.ErrorCode;
import net.thumbtack.school.concert.hibernate.daoimpl.HibernateUtils;
import net.thumbtack.school.concert.rest.response.FailureResponse;
import net.thumbtack.school.concert.server.Server;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestHibernate {
    private static Gson gson = new Gson();
    private static UserDtoRequestInternal user = new UserDtoRequestInternal("James", "Bond", "JamesBond1", "!123456aA");
    private static UserDtoRequestInternal user1 = new UserDtoRequestInternal("Josh", "Smith", "Joshua", "Josh123.");
    private static SongDtoRequestInternal song = new SongDtoRequestInternal("Ive Told Every Little Star",
            Collections.singletonList("Jerome Kern"),
            Collections.singletonList("Oscar Hammerstein"),
            "Linda Scott",
            138);
    private static SongDtoRequestInternal song1 = new SongDtoRequestInternal("Stayin Alive",
            Arrays.asList("Robin Gibb", "Maurice Gibb", "Barry Gibb"),
            Arrays.asList("Robin Gibb", "Maurice Gibb", "Barry Gibb"),
            "Capital Cities",
            224);
    private static SongDtoRequestInternal song2 = new SongDtoRequestInternal("Who?",
            Arrays.asList("Jerome Kern", "Otto Harbach"),
            Arrays.asList("Oscar Hammerstein", "Ralph Blaine"),
            "Judy Garland",
            167);
    private static final Logger LOGGER = LoggerFactory.getLogger(TestClientServer.class);

    protected static MyClient client = new MyClient();
    private static String baseURL;


    private static void setBaseUrl() {
        String hostName = null;
        try {
            hostName = InetAddress.getLocalHost().getCanonicalHostName();
        } catch (UnknownHostException e) {
            LOGGER.debug("Can't determine my own host name", e);
        }
        baseURL = "http://" + hostName + ":" + net.thumbtack.school.concert.server.config.Settings.getRestHTTPPort() + "/api";
    }



    @BeforeAll()
    public static void start()   {
        setBaseUrl();
        Server.createServerHiber();
        try {
            HibernateUtils.buildSessionFactory();
        } catch (ExceptionInInitializerError ex) {
            Assume.assumeNoException(ex);
        }
    }

    @AfterAll
    public static void stopServer() {
        Server.stopServer();
    }


    @BeforeEach
    public void clearDataBase() {
        client.delete(baseURL + "/data", EmptyResponse.class);
    }


    protected void checkFailureResponse(Object response, ErrorCode expectedStatus) {
        Assert.assertTrue(response instanceof FailureResponse);
        FailureResponse failureResponseObject = (FailureResponse) response;
        Assert.assertEquals(expectedStatus, failureResponseObject.getErrorCode());
    }



    @Test
    public void testRegister() {
        RegisterUserDtoRequest registerUserDtoRequest = new RegisterUserDtoRequest(user.getFirstName(),
                user.getLastName(),
                user.getLogin(),
                user.getPassword());
        Object response = client.post(baseURL + "/user", gson.toJson(registerUserDtoRequest), RegisterUserDtoResponse.class);
        RegisterUserDtoResponse token = (RegisterUserDtoResponse) response;
        assertNotNull(token);

        Object response1 = client.get(baseURL + "/user", AllUsersDtoResponse.class);
        AllUsersDtoResponse allUsersDtoResponse1 = new AllUsersDtoResponse(Collections.singletonList(user));
        Assert.assertEquals(gson.toJson(allUsersDtoResponse1), gson.toJson(response1));

        client.delete(baseURL + "/user", EmptyResponse.class, token.getToken());

        Object response3 = client.get(baseURL + "/user", AllUsersDtoResponse.class);
        AllUsersDtoResponse allUsersDtoResponse3 = new AllUsersDtoResponse(new ArrayList<>());
        Assert.assertEquals(gson.toJson(allUsersDtoResponse3), gson.toJson(response3));
    }

    @Test
    public void testRegisterError() {
        RegisterUserDtoRequest registerUserDtoRequest = new RegisterUserDtoRequest(user.getFirstName(),
                user.getLastName(),
                user.getLogin(),
                user.getPassword());
        Object response = client.post(baseURL + "/user", gson.toJson(registerUserDtoRequest), RegisterUserDtoResponse.class);
        RegisterUserDtoResponse token = (RegisterUserDtoResponse) response;
        assertNotNull(token);
        Object response1 = client.post(baseURL + "/user", gson.toJson(registerUserDtoRequest), RegisterUserDtoResponse.class);
        checkFailureResponse(response1, ErrorCode.LOGIN_ALREADY_EXIST);

        RegisterUserDtoRequest registerUserDtoRequest2 = new RegisterUserDtoRequest(user.getFirstName(),
                user.getLastName(),

                "123",
                user.getPassword());
        Object response2 = client.post(baseURL + "/user", gson.toJson(registerUserDtoRequest2), RegisterUserDtoResponse.class);
        checkFailureResponse(response2, ErrorCode.INCORRECT_INPUT_DATA);


        RegisterUserDtoRequest registerUserDtoRequest3 = new RegisterUserDtoRequest(user.getFirstName(),
                user.getLastName(),
                user.getLogin(),
                "123");
        Object response3 = client.post(baseURL + "/user", gson.toJson(registerUserDtoRequest3), RegisterUserDtoResponse.class);
        checkFailureResponse(response3, ErrorCode.INCORRECT_INPUT_DATA);
    }

    @Test
    public void logout() {
        RegisterUserDtoRequest registerUserDtoRequest = new RegisterUserDtoRequest(user.getFirstName(),
                user.getLastName(),
                user.getLogin(),
                user.getPassword());

        RegisterUserDtoRequest registerUserDtoRequest1 = new RegisterUserDtoRequest(user1.getFirstName(),
                user1.getLastName(),
                user1.getLogin(),
                user1.getPassword());

        client.post(baseURL + "/user", gson.toJson(registerUserDtoRequest), RegisterUserDtoResponse.class);
        Object response = client.post(baseURL + "/user", gson.toJson(registerUserDtoRequest1), RegisterUserDtoResponse.class);
        RegisterUserDtoResponse token = (RegisterUserDtoResponse) response;
        assertNotNull(token);

        LogoutDtoRequest logoutDtoRequest = new LogoutDtoRequest(token.getToken());
        client.delete(baseURL + "/user/session", EmptyResponse.class, gson.toJson(logoutDtoRequest));

        LogoutDtoRequest logoutDtoRequest1 = new LogoutDtoRequest("doesntexisttoken123");
        Object response2 = client.delete(baseURL + "/user/session", EmptyResponse.class, gson.toJson(logoutDtoRequest1));
        checkFailureResponse(response2, ErrorCode.USER_NOT_FOUND);


        LogoutDtoRequest logoutDtoRequest2 = new LogoutDtoRequest("");
        Object response3 = client.delete(baseURL + "/user/session", EmptyResponse.class, gson.toJson(logoutDtoRequest2));
        checkFailureResponse(response3, ErrorCode.INCORRECT_INPUT_DATA);

        AllUsersDtoResponse allUsersDtoResponse = new AllUsersDtoResponse(Arrays.asList(user, user1));
        AllUsersDtoResponse allUsersDtoResponse1 = new AllUsersDtoResponse(Collections.singletonList(user));

        Object response4 = client.get(baseURL + "/user", AllUsersDtoResponse.class, token.getToken());
        Assert.assertEquals(gson.toJson(allUsersDtoResponse), gson.toJson(response4));

        Object response5 = client.get(baseURL + "/user/session", AllUsersDtoResponse.class, token.getToken());
        Assert.assertEquals(gson.toJson(allUsersDtoResponse1), gson.toJson(response5));
}

    class MyThread extends Thread {
        private String login;

        public MyThread(String login) {
            this.login = login;
        }

        public void run() {
            RegisterUserDtoRequest registerUserDtoRequest = new RegisterUserDtoRequest(user.getFirstName(),
                    user.getLastName(),
                    login,
                    user.getPassword());

            Object response = client.post(baseURL + "/user", gson.toJson(registerUserDtoRequest), RegisterUserDtoResponse.class);
            RegisterUserDtoResponse token = (RegisterUserDtoResponse) response;
            assertNotNull(token);
        }

    }

    @Test
    public void testRegisterUserServerThreads1() throws InterruptedException {
        Thread thread1 = new MyThread("Erfty123");
        Thread thread2 = new MyThread("Hfcds2");
        Thread thread3 = new MyThread("Jmasjd");
        Thread thread4 = new MyThread("Ddvvf");
        Thread thread5 = new MyThread("FDscsd");
        Thread thread6 = new MyThread("Cdsvcf");

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();

        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();
        thread5.join();
        thread6.join();

        List<UserDtoRequestInternal> list = Arrays.asList(new UserDtoRequestInternal(user.getFirstName(),
                        user.getLastName(),
                        "Erfty123",
                        user.getPassword()),
                new UserDtoRequestInternal(user.getFirstName(),
                        user.getLastName(),
                        "Hfcds2",
                        user.getPassword()),
                new UserDtoRequestInternal(user.getFirstName(),
                        user.getLastName(),
                        "Jmasjd",
                        user.getPassword()),
                new UserDtoRequestInternal(user.getFirstName(),
                        user.getLastName(),
                        "Ddvvf",
                        user.getPassword()),
                new UserDtoRequestInternal(user.getFirstName(),
                        user.getLastName(),
                        "FDscsd",
                        user.getPassword()),
                new UserDtoRequestInternal(user.getFirstName(),
                        user.getLastName(),
                        "Cdsvcf",
                        user.getPassword()));
        list.sort(Comparator.comparing(UserDtoRequestInternal::getLogin));
        AllUsersDtoResponse allUsersDtoResponse = new AllUsersDtoResponse(list);

        Object response1 = client.get(baseURL + "/user", AllUsersDtoResponse.class);
        if (response1 instanceof AllUsersDtoResponse) {
            AllUsersDtoResponse allUsersDtoResponse1 = (AllUsersDtoResponse) response1;
            Assert.assertEquals(gson.toJson(allUsersDtoResponse), gson.toJson(allUsersDtoResponse1));
        } else {
            checkFailureResponse(response1, ErrorCode.SUCCESS);
        }

    }


    @Test
    public void testSong() {
        RegisterUserDtoRequest registerUserDtoRequest = new RegisterUserDtoRequest(user.getFirstName(),
                user.getLastName(),
                user.getLogin(),
                user.getPassword());

        Object response = client.post(baseURL + "/user", gson.toJson(registerUserDtoRequest), RegisterUserDtoResponse.class);
        RegisterUserDtoResponse token = (RegisterUserDtoResponse) response;
        assertNotNull(token);

        AddSongDtoRequest addSongDtoRequest = new AddSongDtoRequest(Collections.singletonList(song), gson.toJson(new TokenDtoRequest(token.getToken())));
        Object response1 = client.post(baseURL + "/song", gson.toJson(addSongDtoRequest), AddSongDtoResponse.class);
        AddSongDtoResponse addSongDtoResponse = (AddSongDtoResponse) response1;
        assertNotNull(gson.toJson(response1));


        Object response2 = client.get(baseURL + "/song", AllSongsDtoResponse.class, token.getToken());
        Assert.assertEquals(gson.toJson(new AllSongsDtoResponse(Collections.singletonList(song))), gson.toJson(response2));

        client.delete(baseURL + "/song/"+addSongDtoResponse.getId().get(0), EmptyResponse.class, token.getToken());

        Object response3 = client.get(baseURL + "/song", AllSongsDtoResponse.class, token.getToken());
        Assert.assertEquals(gson.toJson(new AllSongsDtoResponse(new ArrayList<>())), gson.toJson(response3));
    }


    @Test
    public void testSongError() {
        RegisterUserDtoRequest registerUserDtoRequest = new RegisterUserDtoRequest(user.getFirstName(),
                user.getLastName(),
                user.getLogin(),
                user.getPassword());

        Object response = client.post(baseURL + "/user", gson.toJson(registerUserDtoRequest), RegisterUserDtoResponse.class);
        RegisterUserDtoResponse token = (RegisterUserDtoResponse) response;
        assertNotNull(token);

        AddSongDtoRequest addSongDtoRequest = new AddSongDtoRequest(Collections.singletonList(song), gson.toJson(new TokenDtoRequest(token.getToken())));
        Object response1 = client.post(baseURL + "/song", gson.toJson(addSongDtoRequest), AddSongDtoResponse.class);
        AddSongDtoResponse addSongDtoResponse = (AddSongDtoResponse) response1;
        assertNotNull(gson.toJson(addSongDtoResponse.getId()));


        Object response2 = client.get(baseURL + "/song", AllSongsDtoResponse.class, token.getToken());
        Assert.assertEquals(gson.toJson(new AllSongsDtoResponse(Collections.singletonList(song))), gson.toJson(response2));

        Object response3 = client.delete(baseURL + "/song/"+21, EmptyResponse.class, token.getToken());
        checkFailureResponse(response3, ErrorCode.SONG_NOT_FOUND);

        AddSongDtoRequest addSongDtoRequest1 = new AddSongDtoRequest(Collections.singletonList(song), gson.toJson(new TokenDtoRequest("123")));
        Object response4 = client.post(baseURL + "/song", gson.toJson(addSongDtoRequest1), AddSongDtoResponse.class);
        checkFailureResponse(response4, ErrorCode.USER_NOT_FOUND);

    }


    @Test
    public void testSongGet() {
        RegisterUserDtoRequest registerUserDtoRequest = new RegisterUserDtoRequest(user.getFirstName(),
                user.getLastName(),
                user.getLogin(),
                user.getPassword());

        Object response = client.post(baseURL + "/user", gson.toJson(registerUserDtoRequest), RegisterUserDtoResponse.class);
        RegisterUserDtoResponse token = (RegisterUserDtoResponse) response;
        assertNotNull(token);

        AddSongDtoRequest addSongDtoRequest = new AddSongDtoRequest(Collections.singletonList(song), gson.toJson(new TokenDtoRequest(token.getToken())));
        Object response1 = client.post(baseURL + "/song", gson.toJson(addSongDtoRequest), AddSongDtoResponse.class);
        AddSongDtoResponse addSongDtoResponse = (AddSongDtoResponse) response1;
        assertNotNull(gson.toJson(addSongDtoResponse.getId()));

        AddSongDtoRequest addSongDtoRequest1 = new AddSongDtoRequest(Collections.singletonList(song1), gson.toJson(new TokenDtoRequest(token.getToken())));
        Object response2 = client.post(baseURL + "/song", gson.toJson(addSongDtoRequest1), AddSongDtoResponse.class);
        AddSongDtoResponse addSongDtoResponse1 = (AddSongDtoResponse) response2;
        assertNotNull(gson.toJson(addSongDtoResponse1.getId()));

        AddSongDtoRequest addSongDtoRequest2 = new AddSongDtoRequest(Collections.singletonList(song2), gson.toJson(new TokenDtoRequest(token.getToken())));
        Object response3 = client.post(baseURL + "/song", gson.toJson(addSongDtoRequest2), AddSongDtoResponse.class);
        AddSongDtoResponse addSongDtoResponse2 = (AddSongDtoResponse) response3;
        assertNotNull(gson.toJson(addSongDtoResponse2.getId()));


        Object response4 = client.get(baseURL + "/song", AllSongsDtoResponse.class, token.getToken());
        Assert.assertEquals(gson.toJson(new AllSongsDtoResponse(Arrays.asList(song, song1, song2))), gson.toJson(response4));


        Object response5 = client.get(baseURL + "/song/composer", AllSongsDtoResponse.class, token.getToken(), song.getComposers());
        Assert.assertEquals(gson.toJson(new AllSongsDtoResponse(Arrays.asList(song, song2))), gson.toJson(response5));

        Object response6 = client.get(baseURL + "/song/wordAuthors", AllSongsDtoResponse.class, token.getToken(), song2.getWordAuthors());
        Assert.assertEquals(gson.toJson(new AllSongsDtoResponse(Collections.singletonList(song2))), gson.toJson(response6));

        Object response7 = client.get(baseURL + "/song/wordAuthors/each", AllSongsDtoResponse.class, token.getToken(), song2.getWordAuthors());
        Assert.assertEquals(gson.toJson(new AllSongsDtoResponse(Arrays.asList(song, song2))), gson.toJson(response7));

    }


    @Test
    public void rating() {
        RegisterUserDtoRequest registerUserDtoRequest = new RegisterUserDtoRequest(user.getFirstName(),
                user.getLastName(),
                user.getLogin(),
                user.getPassword());
        Object response = client.post(baseURL + "/user", gson.toJson(registerUserDtoRequest), RegisterUserDtoResponse.class);
        RegisterUserDtoResponse token = (RegisterUserDtoResponse) response;
        assertNotNull(token);

        RegisterUserDtoRequest registerUserDtoRequest1 = new RegisterUserDtoRequest(user1.getFirstName(),
                user1.getLastName(),
                user1.getLogin(),
                user1.getPassword());
        Object response1 = client.post(baseURL + "/user", gson.toJson(registerUserDtoRequest1), RegisterUserDtoResponse.class);
        RegisterUserDtoResponse token1 = (RegisterUserDtoResponse) response1;
        assertNotNull(token1);

        AddSongDtoRequest addSongDtoRequest = new AddSongDtoRequest(Collections.singletonList(song), gson.toJson(new TokenDtoRequest(token.getToken())));
        Object response2 = client.post(baseURL + "/song", gson.toJson(addSongDtoRequest), AddSongDtoResponse.class);
        AddSongDtoResponse addSongDtoResponse = (AddSongDtoResponse) response2;
        assertNotNull(gson.toJson(addSongDtoResponse.getId()));

        RateSongDtoRequest rateSongDtoRequest =  new RateSongDtoRequest(addSongDtoResponse.getId().get(0), gson.toJson(new TokenDtoRequest(token1.getToken())), 4);
        client.post(baseURL + "/song/rate", gson.toJson(rateSongDtoRequest), EmptyResponse.class);


        Object response3 = client.get(baseURL + "/song/rate/"+addSongDtoResponse.getId().get(0),  AverageDtoResponse.class, token1.getToken());
        AverageDtoResponse averageDtoResponse = (AverageDtoResponse) response3;
        Assert.assertEquals(4.5, averageDtoResponse.getAverageRatingOfSong(),0.000001);

        client.delete(baseURL + "/song/rate/"+addSongDtoResponse.getId().get(0),  EmptyResponse.class, token1.getToken());

        Object response4 = client.get(baseURL + "/song/rate/"+addSongDtoResponse.getId().get(0),  AverageDtoResponse.class, token1.getToken());
        AverageDtoResponse averageDtoResponse1 = (AverageDtoResponse) response4;
        Assert.assertEquals(5.0, averageDtoResponse1.getAverageRatingOfSong(),0.000001);

    }

    @Test
    public void comments() {
        RegisterUserDtoRequest registerUserDtoRequest = new RegisterUserDtoRequest(user.getFirstName(),
                user.getLastName(),
                user.getLogin(),
                user.getPassword());
        Object response = client.post(baseURL + "/user", gson.toJson(registerUserDtoRequest), RegisterUserDtoResponse.class);
        RegisterUserDtoResponse token = (RegisterUserDtoResponse) response;
        assertNotNull(token);

        RegisterUserDtoRequest registerUserDtoRequest1 = new RegisterUserDtoRequest(user1.getFirstName(),
                user1.getLastName(),
                user1.getLogin(),
                user1.getPassword());
        Object response1 = client.post(baseURL + "/user", gson.toJson(registerUserDtoRequest1), RegisterUserDtoResponse.class);
        RegisterUserDtoResponse token1 = (RegisterUserDtoResponse) response1;
        assertNotNull(token1);

        AddSongDtoRequest addSongDtoRequest = new AddSongDtoRequest(Collections.singletonList(song), gson.toJson(new TokenDtoRequest(token.getToken())));
        Object response2 = client.post(baseURL + "/song", gson.toJson(addSongDtoRequest), AddSongDtoResponse.class);
        AddSongDtoResponse addSongDtoResponse = (AddSongDtoResponse) response2;
        assertNotNull(gson.toJson(addSongDtoResponse.getId()));



        AddCommentDtoRequest addCommentDtoRequest = new AddCommentDtoRequest("Супер!", gson.toJson(new TokenDtoRequest(token1.getToken())), addSongDtoResponse.getId().get(0));
        client.post(baseURL + "/song/comment", gson.toJson(addCommentDtoRequest), EmptyResponse.class);


        Object response3 = client.get(baseURL + "/song/comment/"+addSongDtoResponse.getId().get(0), GetCommentsDtoResponse.class, token1.getToken());
        GetCommentsDtoResponse getCommentsDtoResponse = new GetCommentsDtoResponse(Collections.singletonList("Супер!"));
        Assert.assertEquals(gson.toJson(getCommentsDtoResponse), gson.toJson(response3));

        String comment1 = "Круто!";
        Object responseDelete = client.delete(baseURL + "/song/comment/"+addSongDtoResponse.getId().get(0) + "?comment="+comment1, EmptyResponse.class, token1.getToken());
        checkFailureResponse(responseDelete, ErrorCode.COMMENT_NOT_FOUND);

        String comment = "Супер!";
        client.delete(baseURL + "/song/comment/"+addSongDtoResponse.getId().get(0) + "?comment="+comment, EmptyResponse.class, token1.getToken());

        AddCommentDtoRequest addCommentDtoRequest3 = new AddCommentDtoRequest("Класс!", gson.toJson(new TokenDtoRequest(token.getToken())), addSongDtoResponse.getId().get(0));
        client.post(baseURL + "/song/comment", gson.toJson(addCommentDtoRequest3), EmptyResponse.class);


        Object response4 = client.get(baseURL + "/song/comment/"+addSongDtoResponse.getId().get(0), GetCommentsDtoResponse.class, token1.getToken());
        GetCommentsDtoResponse getCommentsDtoResponse2 = new GetCommentsDtoResponse(Collections.singletonList("Класс!"));
        Assert.assertEquals(gson.toJson(getCommentsDtoResponse2), gson.toJson(response4));
    }

    @Test
    public void usersAgree() {
        RegisterUserDtoRequest registerUserDtoRequest = new RegisterUserDtoRequest(user.getFirstName(),
                user.getLastName(),
                user.getLogin(),
                user.getPassword());
        Object response = client.post(baseURL + "/user", gson.toJson(registerUserDtoRequest), RegisterUserDtoResponse.class);
        RegisterUserDtoResponse token = (RegisterUserDtoResponse) response;
        assertNotNull(token);

        RegisterUserDtoRequest registerUserDtoRequest1 = new RegisterUserDtoRequest(user1.getFirstName(),
                user1.getLastName(),
                user1.getLogin(),
                user1.getPassword());
        Object response1 = client.post(baseURL + "/user", gson.toJson(registerUserDtoRequest1), RegisterUserDtoResponse.class);
        RegisterUserDtoResponse token1 = (RegisterUserDtoResponse) response1;
        assertNotNull(token1);

        AddSongDtoRequest addSongDtoRequest = new AddSongDtoRequest(Collections.singletonList(song), gson.toJson(new TokenDtoRequest(token.getToken())));
        Object response2 = client.post(baseURL + "/song", gson.toJson(addSongDtoRequest), AddSongDtoResponse.class);
        AddSongDtoResponse addSongDtoResponse = (AddSongDtoResponse) response2;
        assertNotNull(gson.toJson(addSongDtoResponse.getId()));


        AddCommentDtoRequest addCommentDtoRequest = new AddCommentDtoRequest("Супер!",
                gson.toJson(new TokenDtoRequest(token1.getToken())),
                addSongDtoResponse.getId().get(0));
        client.post(baseURL + "/song/comment", gson.toJson(addCommentDtoRequest), EmptyResponse.class);

        AddCommentDtoRequest addUserAgreeWithComment = new AddCommentDtoRequest("Супер!",
                gson.toJson(new TokenDtoRequest(token.getToken())),
                addSongDtoResponse.getId().get(0));
        client.post(baseURL + "/song/comment/users_agree", gson.toJson(addUserAgreeWithComment), EmptyResponse.class);


        String comment = "Супер!";
        Object response4 = client.get(baseURL + "/song/comment/users_agree/"+addSongDtoResponse.getId().get(0)+"?comment="+comment,
                GetUsersAgreeDtoResponse.class,
                token1.getToken());
        GetUsersAgreeDtoResponse getUsersAgreeDtoResponse = new GetUsersAgreeDtoResponse(Collections.singletonList(user));
        Assert.assertEquals(gson.toJson(getUsersAgreeDtoResponse), gson.toJson(response4));

    }
}
