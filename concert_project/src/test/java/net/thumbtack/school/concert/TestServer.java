package net.thumbtack.school.concert;

public class TestServer {
/*    private static Gson gson = new Gson();
    private static Server server;
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

    private static boolean setUpIsDone = false;




    @BeforeAll()
    public static void setUp() throws ServerException, ConfigurationException {
        Configuration config = new PropertiesConfiguration("conf/conf.properties");
        Settings settings = new Settings(config.getString("mode"));
        if (settings.getMode() == Mode.SQL) {
            if (!setUpIsDone) {
                boolean initSqlSessionFactory = MyBatisUtils.initSqlSessionFactory();
                if (!initSqlSessionFactory) {
                    throw new RuntimeException("Can't create connection, stop");
                }
                setUpIsDone = true;
            }
            server = new Server(Mode.SQL);
        }
        else  if (settings.getMode() == Mode.RAM){
            DataBase.startServer();
            server = new Server(Mode.RAM);
        }
    }



    @BeforeEach()
    public void clearDatabase() throws IOException {
        server.clearServer();
    }



    @Test
    public void testRegisterUserServer1() {
        RegisterUserDtoRequest registerUserDtoRequest = new RegisterUserDtoRequest(user.getFirstName(),
                user.getLastName(),
                user.getLogin(),
                user.getPassword());
        assertNotNull(server.registerUser(gson.toJson(registerUserDtoRequest)));

        ServerException serverException = new ServerException(LOGIN_ALREADY_EXIST);
        ErrorResponse error = new ErrorResponse(serverException);
        assertEquals(gson.toJson(error), server.registerUser(gson.toJson(user)));

        ServerException serverException1 = new ServerException(INCORRECT_INPUT_DATA);
        ErrorResponse error1 = new ErrorResponse(serverException1);
        assertEquals(gson.toJson(error1), server.registerUser(gson.toJson(new UserDtoRequestInternal("Иван",
                "Иванов", "123", "123"))));

        AllUsersDtoResponse allUsersDtoResponse = new AllUsersDtoResponse(Collections.singletonList(user));
        assertEquals(gson.toJson(allUsersDtoResponse), server.getAllUsers());
    }

    @Test
    public void testRegisterUserServerThreads() throws InterruptedException {
        RegisterUserDtoRequest registerUserDtoRequest = new RegisterUserDtoRequest(user.getFirstName(),
                user.getLastName(),
                user.getLogin(),
                user.getPassword());


        Runnable run = () -> {
            assertNotNull(server.registerUser(gson.toJson(registerUserDtoRequest)));
        };

        Thread thread1 = new Thread(run);
        Thread thread2 = new Thread(run);
        Thread thread3 = new Thread(run);
        Thread thread4 = new Thread(run);
        Thread thread5 = new Thread(run);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();

        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();
        thread5.join();

        AllUsersDtoResponse allUsersDtoResponse = new AllUsersDtoResponse(Collections.singletonList(user));
        assertEquals(gson.toJson(allUsersDtoResponse), server.getAllUsers());
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
            assertNotNull( server.registerUser(gson.toJson(registerUserDtoRequest)));
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


        assertEquals(gson.toJson(allUsersDtoResponse), server.getAllUsers());

    }


    @Test
    public void testRegisterUserServer2() {
        assertNotNull(server.registerUser(gson.toJson(user)));
        assertNotNull(server.registerUser(gson.toJson(user1)));
        AllUsersDtoResponse allUsersDtoResponse = new AllUsersDtoResponse(Arrays.asList(user, user1));
        assertEquals(gson.toJson(allUsersDtoResponse), server.getAllUsers());

    }

    @Test
    public void logout() {
        assertNotNull(server.registerUser(gson.toJson(user)));
        String token1 = server.registerUser(gson.toJson(user1));
        assertNotNull(token1);
        assertEquals(gson.toJson(new EmptyResponse()), server.logout(token1));


        ServerException serverException = new ServerException(USER_NOT_FOUND);
        ErrorResponse error = new ErrorResponse(serverException);
        LogoutDtoRequest logoutDtoRequest = new LogoutDtoRequest("doesntexisttoken123");
        server.logout(gson.toJson(logoutDtoRequest));
        assertEquals(gson.toJson(error), server.logout(gson.toJson(logoutDtoRequest)));

        ServerException serverException1 = new ServerException(INCORRECT_INPUT_DATA);
        ErrorResponse error1 = new ErrorResponse(serverException1);
        LogoutDtoRequest logoutDtoRequest1 = new LogoutDtoRequest("");
        assertEquals(gson.toJson(error1), server.logout(gson.toJson(logoutDtoRequest1)));

        AllUsersDtoResponse allUsersDtoResponse = new AllUsersDtoResponse(Arrays.asList(user, user1));
        AllUsersDtoResponse allUsersDtoResponse1 = new AllUsersDtoResponse(Collections.singletonList(user));
        assertEquals(gson.toJson(allUsersDtoResponse), server.getAllUsers());
        assertEquals(gson.toJson(allUsersDtoResponse1), server.getAllOnlineUsers());

    }

    @Test
    public void logout1() {
        String token = server.registerUser(gson.toJson(user));
        assertNotNull(token);
        String token1 = server.registerUser(gson.toJson(user1));
        assertNotNull(token1);
        assertEquals(gson.toJson(new EmptyResponse()), server.logout(token));
        assertEquals(gson.toJson(new EmptyResponse()), server.logout(token1));
        AllUsersDtoResponse allUsersDtoResponse = new AllUsersDtoResponse(Arrays.asList(user, user1));
        AllUsersDtoResponse allUsersDtoResponse1 = new AllUsersDtoResponse(new ArrayList<>());
        assertEquals(gson.toJson(allUsersDtoResponse), server.getAllUsers());
        assertEquals(gson.toJson(allUsersDtoResponse1), server.getAllOnlineUsers());

    }

    @Test
    public void login()  {
        String token = server.registerUser(gson.toJson(user));

        ServerException serverException = new ServerException(USER_NOT_FOUND);
        ErrorResponse error = new ErrorResponse(serverException);
        LoginDtoRequest loginDtoRequest1 = new LoginDtoRequest("NotUser", "!123456aA");
        assertEquals(gson.toJson(error), server.login(gson.toJson(loginDtoRequest1)));

        assertNotNull(token);
        assertNotNull(server.registerUser(gson.toJson(user1)));
        assertEquals(gson.toJson(new EmptyResponse()), server.logout(token));

        ServerException serverException1 = new ServerException(INCORRECT_INPUT_DATA);
        ErrorResponse error1 = new ErrorResponse(serverException1);
        LoginDtoRequest loginDtoRequest2 = new LoginDtoRequest("JamesBond1", "notPassword1!");
        assertEquals(gson.toJson(error1), server.login(gson.toJson(loginDtoRequest2)));
        LoginDtoRequest loginDtoRequest3 = new LoginDtoRequest("123", "123");
        assertEquals(gson.toJson(error1), server.login(gson.toJson(loginDtoRequest3)));

        LoginDtoRequest loginDtoRequest = new LoginDtoRequest("JamesBond1", "!123456aA");
        assertNotNull(server.login(gson.toJson(loginDtoRequest)));
        AllUsersDtoResponse allUsersDtoResponse = new AllUsersDtoResponse(Arrays.asList(user, user1));
        assertEquals(gson.toJson(allUsersDtoResponse), server.getAllUsers());
        assertEquals(gson.toJson(allUsersDtoResponse), server.getAllOnlineUsers());
    }


    @Test
    public void login1()  {
        String token = server.registerUser(gson.toJson(user));
        assertNotNull(token);
        String token1 = server.registerUser(gson.toJson(user1));
        assertNotNull(token1);
        assertEquals(gson.toJson(new EmptyResponse()), server.logout(token));
        assertEquals(gson.toJson(new EmptyResponse()), server.logout(token1));
        LoginDtoRequest loginDtoRequest = new LoginDtoRequest("JamesBond1", "!123456aA");
        LoginDtoRequest loginDtoRequest1 = new LoginDtoRequest("Joshua", "Josh123.");
        assertNotNull(server.login(gson.toJson(loginDtoRequest)));
        assertNotNull(server.login(gson.toJson(loginDtoRequest1)));
        AllUsersDtoResponse allUsersDtoResponse = new AllUsersDtoResponse(Arrays.asList(user, user1));
        assertEquals(gson.toJson(allUsersDtoResponse), server.getAllUsers());
        assertEquals(gson.toJson(allUsersDtoResponse), server.getAllOnlineUsers());
    }

    @Test
    public void logoff() {
        assertNotNull(server.registerUser(gson.toJson(user)));
        String token1 = server.registerUser(gson.toJson(user1));
        assertNotNull(token1);

        ServerException serverException = new ServerException(USER_NOT_FOUND);
        ErrorResponse error = new ErrorResponse(serverException);
        DeleteDtoRequest deleteDtoRequest = new DeleteDtoRequest("doesntexisttoken123");
        assertEquals(gson.toJson(error), server.delete(gson.toJson(deleteDtoRequest)));

        ServerException serverException1 = new ServerException(INCORRECT_INPUT_DATA);
        ErrorResponse error1 = new ErrorResponse(serverException1);
        DeleteDtoRequest deleteDtoRequest1 = new DeleteDtoRequest("");
        assertEquals(gson.toJson(error1), server.delete(gson.toJson(deleteDtoRequest1)));


        assertEquals(gson.toJson(new EmptyResponse()), server.delete(token1));
        AllUsersDtoResponse allUsersDtoResponse = new AllUsersDtoResponse(Collections.singletonList(user));
        assertEquals(gson.toJson(allUsersDtoResponse), server.getAllUsers());
        assertEquals(gson.toJson(allUsersDtoResponse), server.getAllOnlineUsers());

    }

    @Test
    public void logoff1() {
        String token = server.registerUser(gson.toJson(user));
        assertNotNull(token);
        String token1 = server.registerUser(gson.toJson(user1));
        assertNotNull(token1);
        assertEquals(gson.toJson(new EmptyResponse()), server.delete(token));
        assertEquals(gson.toJson(new EmptyResponse()), server.delete(token1));
        AllUsersDtoResponse allUsersDtoResponse = new AllUsersDtoResponse(new ArrayList<>());
        assertEquals(gson.toJson(allUsersDtoResponse), server.getAllUsers());
        assertEquals(gson.toJson(allUsersDtoResponse), server.getAllOnlineUsers());

    }



    @Test
    public void addAndDeleteSong() {
        String token = server.registerUser(gson.toJson(user));
        assertNotNull(token);
        AddSongDtoRequest addSongDtoRequest = new AddSongDtoRequest(Collections.singletonList(song), token);
        assertEquals(gson.toJson(new EmptyResponse()), server.addSong(gson.toJson(addSongDtoRequest)));

        ServerException serverException = new ServerException(USER_NOT_FOUND);
        ErrorResponse error = new ErrorResponse(serverException);
        AddSongDtoRequest addSongDtoRequest1 = new AddSongDtoRequest(Collections.singletonList(song),
                gson.toJson(new LogoutDtoRequest("doesntexisttoken123")));
        assertEquals(gson.toJson(error), server.getAllSongs(gson.toJson(new LogoutDtoRequest("doesntexisttoken123"))));

        assertEquals(gson.toJson(error), server.addSong(gson.toJson(addSongDtoRequest1)));
        DeleteRatingSongDtoRequest dtoRequest2 = new DeleteRatingSongDtoRequest(song, gson.toJson(new LogoutDtoRequest("doesntexisttoken123")));
        assertEquals(gson.toJson(error), server.cancelSong(gson.toJson(dtoRequest2)));


        ServerException serverException0 = new ServerException(INCORRECT_INPUT_DATA);
        ErrorResponse error0 = new ErrorResponse(serverException0);
        assertEquals(gson.toJson(error0), server.addSong(gson.toJson(new AddSongDtoRequest(Collections.singletonList(new SongDtoRequestInternal("123",
                Collections.singletonList("123"),
                Collections.singletonList("123"),
                "123",
                20)), token))));
        assertEquals(gson.toJson(error0), server.addSong(gson.toJson(new AddSongDtoRequest(Collections.singletonList(new SongDtoRequestInternal("123",
                Collections.singletonList("123"),
                Collections.singletonList("123"),
                "123",
                20)), "некорректные данные"))));

        ServerException serverException1 = new ServerException(SONG_ALREADY_ADDED);
        ErrorResponse error1 = new ErrorResponse(serverException1);
        assertEquals(gson.toJson(error1), server.addSong(gson.toJson(addSongDtoRequest)));

        AllSongsDtoResponse allSongsDtoResponse = new AllSongsDtoResponse(Collections.singletonList(song));
        server.getAllSongs(token);
        assertEquals(gson.toJson(allSongsDtoResponse), server.getAllSongs(token));

        DeleteRatingSongDtoRequest dtoRequest = new DeleteRatingSongDtoRequest(song, token);
        assertEquals(gson.toJson(new EmptyResponse()), server.cancelSong(gson.toJson(dtoRequest)));
        AllSongsDtoResponse allSongsDtoResponse1 = new AllSongsDtoResponse(new ArrayList<>());
        assertEquals(gson.toJson(allSongsDtoResponse1), server.getAllSongs(token));

        ServerException serverException2 = new ServerException(SONG_NOT_FOUND);
        ErrorResponse error2 = new ErrorResponse(serverException2);
        assertEquals(gson.toJson(error2), server.cancelSong(gson.toJson(dtoRequest)));

    }

    @Test
    public void addAndDeleteSong1()  {
        String token = server.registerUser(gson.toJson(user));
        assertNotNull(token);
        String token1 = server.registerUser(gson.toJson(user1));
        assertNotNull(token1);
        AddSongDtoRequest addSongDtoRequest = new AddSongDtoRequest(Collections.singletonList(song), token);
        assertEquals(gson.toJson(new EmptyResponse()), server.addSong(gson.toJson(addSongDtoRequest)));
        AddSongDtoRequest addSongDtoRequest2 = new AddSongDtoRequest(Collections.singletonList(song2), token);
        AddSongDtoRequest addSongDtoRequest1 = new AddSongDtoRequest(Collections.singletonList(song), token1);
        ServerException serverException = new ServerException(SONG_ALREADY_ADDED);
        ErrorResponse error = new ErrorResponse(serverException);
        assertEquals(gson.toJson(error), server.addSong(gson.toJson(addSongDtoRequest1)));

        DeleteRatingSongDtoRequest dtoRequest = new DeleteRatingSongDtoRequest(song, token);
        assertEquals(gson.toJson(new EmptyResponse()), server.cancelSong(gson.toJson(dtoRequest)));
        DeleteRatingSongDtoRequest dtoRequest1 = new DeleteRatingSongDtoRequest(song1, token);
        ServerException serverException1 = new ServerException(SONG_NOT_FOUND);
        ErrorResponse error1 = new ErrorResponse(serverException1);
        assertEquals(gson.toJson(error1), server.cancelSong(gson.toJson(dtoRequest1)));
        AllSongsDtoResponse allSongsDtoResponse1 = new AllSongsDtoResponse(new ArrayList<>());
        assertEquals(gson.toJson(allSongsDtoResponse1), server.getAllSongs(token));

        assertEquals(gson.toJson(new EmptyResponse()), server.addSong(gson.toJson(addSongDtoRequest2)));
        assertEquals(gson.toJson(new EmptyResponse()), server.addSong(gson.toJson(addSongDtoRequest)));
        assertEquals(gson.toJson(new EmptyResponse()), server.delete(token));
        assertEquals(gson.toJson(allSongsDtoResponse1), server.getAllSongs(token1));


    }


    @Test
    public void getByComposer(){
        String token = server.registerUser(gson.toJson(user));
        assertNotNull(token);
        AddSongDtoRequest addSongDtoRequest = new AddSongDtoRequest(Arrays.asList(song, song1, song2), token);
        assertEquals(gson.toJson(new EmptyResponse()), server.addSong(gson.toJson(addSongDtoRequest)));
        Set<SongDtoRequestInternal> songs = new TreeSet<>(Comparator.comparing(SongDtoRequestInternal::getSongName));
        songs.add(song);
        songs.add(song2);
        SongsByComposerDtoRequest songsByComposerDtoRequest = new SongsByComposerDtoRequest(Collections.singletonList("Jerome Kern"), token);
        SongsByComposerDtoResponse songsByComposerDtoResponse = new SongsByComposerDtoResponse(songs);
        assertEquals(gson.toJson(songsByComposerDtoResponse), server.getSongsByComposer(gson.toJson(songsByComposerDtoRequest)));


        ServerException serverException = new ServerException(INCORRECT_INPUT_DATA);
        ErrorResponse error = new ErrorResponse(serverException);
        assertEquals(gson.toJson(error), server.getSongsByComposer("%6&5"));

        ServerException serverException1 = new ServerException(USER_NOT_FOUND);
        ErrorResponse error1 = new ErrorResponse(serverException1);
        SongsByComposerDtoRequest songsByComposerDtoRequest3 = new SongsByComposerDtoRequest(Collections.singletonList("Jerome Kern"), gson.toJson(new LogoutDtoRequest("doesntexisttoken123")));
        assertEquals(gson.toJson(error1), server.getSongsByComposer(gson.toJson(songsByComposerDtoRequest3)));

        SongsByComposerDtoRequest songsByComposerDtoRequest1 = new SongsByComposerDtoRequest(Arrays.asList("Jerome Kern", "Otto Harbach"), token);
        SongsByComposerDtoResponse songsByComposerDtoResponse1 = new SongsByComposerDtoResponse(Collections.singleton(song2));
        assertEquals(gson.toJson(songsByComposerDtoResponse1), server.getSongsByComposer(gson.toJson(songsByComposerDtoRequest1)));
    }


    @Test
    public void getAllByComposers() {
        String token = server.registerUser(gson.toJson(user));
        assertNotNull(token);
        AddSongDtoRequest addSongDtoRequest = new AddSongDtoRequest(Arrays.asList(song, song1, song2), token);
        assertEquals(gson.toJson(new EmptyResponse()), server.addSong(gson.toJson(addSongDtoRequest)));
        SongsByComposerDtoRequest songsByComposerDtoRequest = new SongsByComposerDtoRequest(Collections.singletonList("Jerome Kern"), token);
        Set<SongDtoRequestInternal> songs = new TreeSet<>(Comparator.comparing(SongDtoRequestInternal::getSongName));
        songs.add(song);
        songs.add(song2);
        SongsByComposerDtoResponse songsByComposerDtoResponse = new SongsByComposerDtoResponse(songs);
        assertEquals(gson.toJson(songsByComposerDtoResponse), server.getAllSongsByEachComposers(gson.toJson(songsByComposerDtoRequest)));
        SongsByComposerDtoRequest songsByComposerDtoRequest1 = new SongsByComposerDtoRequest(Arrays.asList("Jerome Kern", "Otto Harbach"), token);
        SongsByComposerDtoResponse songsByComposerDtoResponse1 = new SongsByComposerDtoResponse(songs);
        assertEquals(gson.toJson(songsByComposerDtoResponse1), server.getAllSongsByEachComposers(gson.toJson(songsByComposerDtoRequest1)));

        ServerException serverException = new ServerException(INCORRECT_INPUT_DATA);
        ErrorResponse error = new ErrorResponse(serverException);
        assertEquals(gson.toJson(error), server.getAllSongsByEachComposers("%6&5"));

        ServerException serverException1 = new ServerException(USER_NOT_FOUND);
        ErrorResponse error1 = new ErrorResponse(serverException1);
        SongsByComposerDtoRequest songsByComposerDtoRequest3 = new SongsByComposerDtoRequest(Collections.singletonList("Jerome Kern"), gson.toJson(new LogoutDtoRequest("doesntexisttoken123")));
        assertEquals(gson.toJson(error1), server.getAllSongsByEachComposers(gson.toJson(songsByComposerDtoRequest3)));

    }

    @Test
    public void getByWordAuthors() {
        String token = server.registerUser(gson.toJson(user));
        assertNotNull(token);

        AddSongDtoRequest addSongDtoRequest = new AddSongDtoRequest(Arrays.asList(song, song1, song2), token);
        assertEquals(gson.toJson(new EmptyResponse()), server.addSong(gson.toJson(addSongDtoRequest)));

        SongsByWordAuthorsDtoRequest songsByWordAuthorsDtoRequest = new SongsByWordAuthorsDtoRequest(Collections.singletonList("Oscar Hammerstein"), token);
        Set<SongDtoRequestInternal> songs = new TreeSet<>(Comparator.comparing(SongDtoRequestInternal::getSongName));
        songs.add(song);
        songs.add(song2);
        SongsByWordAuthorsDtoResponse songsByWordAuthorsDtoResponse = new SongsByWordAuthorsDtoResponse(songs);
        assertEquals(gson.toJson(songsByWordAuthorsDtoResponse), server.getSongsByWordAuthors(gson.toJson(songsByWordAuthorsDtoRequest)));
        SongsByWordAuthorsDtoRequest songsByComposerDtoRequest1 = new SongsByWordAuthorsDtoRequest(Arrays.asList("Oscar Hammerstein", "Ralph Blaine"), token);
        SongsByWordAuthorsDtoResponse songsByComposerDtoResponse1 = new SongsByWordAuthorsDtoResponse(Collections.singleton(song2));
        assertEquals(gson.toJson(songsByComposerDtoResponse1), server.getSongsByWordAuthors(gson.toJson(songsByComposerDtoRequest1)));

        ServerException serverException = new ServerException(INCORRECT_INPUT_DATA);
        ErrorResponse error = new ErrorResponse(serverException);
        assertEquals(gson.toJson(error), server.getSongsByWordAuthors("%6&5"));

        ServerException serverException1 = new ServerException(USER_NOT_FOUND);
        ErrorResponse error1 = new ErrorResponse(serverException1);
        SongsByWordAuthorsDtoRequest songsByWordAuthorsDtoRequest3 = new SongsByWordAuthorsDtoRequest(Collections.singletonList("Jerome Kern"), gson.toJson(new LogoutDtoRequest("doesntexisttoken123")));
        assertEquals(gson.toJson(error1), server.getSongsByWordAuthors(gson.toJson(songsByWordAuthorsDtoRequest3)));

    }


    @Test
    public void getAllByWordAuthors() {
        String token = server.registerUser(gson.toJson(user));
        assertNotNull(token);
        AddSongDtoRequest addSongDtoRequest = new AddSongDtoRequest(Arrays.asList(song, song1, song2), token);
        assertEquals(gson.toJson(new EmptyResponse()), server.addSong(gson.toJson(addSongDtoRequest)));
        SongsByWordAuthorsDtoRequest songsByWordAuthorsDtoRequest = new SongsByWordAuthorsDtoRequest(Collections.singletonList("Oscar Hammerstein"), token);
        Set<SongDtoRequestInternal> songs = new TreeSet<>(Comparator.comparing(SongDtoRequestInternal::getSongName));
        songs.add(song);
        songs.add(song2);
        SongsByWordAuthorsDtoResponse songsByWordAuthorsDtoResponse = new SongsByWordAuthorsDtoResponse(songs);
        server.getAllSongsByEachWordAuthors(gson.toJson(songsByWordAuthorsDtoRequest));
        assertEquals(gson.toJson(songsByWordAuthorsDtoResponse), server.getAllSongsByEachWordAuthors(gson.toJson(songsByWordAuthorsDtoRequest)));

        ServerException serverException = new ServerException(INCORRECT_INPUT_DATA);
        ErrorResponse error = new ErrorResponse(serverException);
        assertEquals(gson.toJson(error), server.getAllSongsByEachWordAuthors("%6&5"));

        ServerException serverException1 = new ServerException(USER_NOT_FOUND);
        ErrorResponse error1 = new ErrorResponse(serverException1);
        SongsByWordAuthorsDtoRequest songsByWordAuthorsDtoRequest3 = new SongsByWordAuthorsDtoRequest(Collections.singletonList("Jerome Kern"), gson.toJson(new LogoutDtoRequest("doesntexisttoken123")));
        assertEquals(gson.toJson(error1), server.getAllSongsByEachWordAuthors(gson.toJson(songsByWordAuthorsDtoRequest3)));

        SongsByWordAuthorsDtoRequest songsByComposerDtoRequest = new SongsByWordAuthorsDtoRequest(Arrays.asList("Oscar Hammerstein", "Ralph Blaine"), token);
        SongsByWordAuthorsDtoResponse songsByComposerDtoResponse = new SongsByWordAuthorsDtoResponse(songs);
        assertEquals(gson.toJson(songsByComposerDtoResponse), server.getAllSongsByEachWordAuthors(gson.toJson(songsByComposerDtoRequest)));
    }


    @Test
    public void getByArtist()  {
        String token = server.registerUser(gson.toJson(user));
        assertNotNull(token);
        AddSongDtoRequest addSongDtoRequest = new AddSongDtoRequest(Arrays.asList(song, song1, song2), token);
        assertEquals(gson.toJson(new EmptyResponse()), server.addSong(gson.toJson(addSongDtoRequest)));
        SongsByArtistDtoRequest songsByArtistDtoRequest = new SongsByArtistDtoRequest("Linda Scott", token);
        SongsByArtistDtoResponse songsByArtistDtoResponse = new SongsByArtistDtoResponse(new HashSet<>(Collections.singletonList(song)));
        assertEquals(gson.toJson(songsByArtistDtoResponse), server.getSongsByArtist(gson.toJson(songsByArtistDtoRequest)));

        ServerException serverException = new ServerException(INCORRECT_INPUT_DATA);
        ErrorResponse error = new ErrorResponse(serverException);
        assertEquals(gson.toJson(error), server.getSongsByArtist("%6&5"));

        ServerException serverException1 = new ServerException(USER_NOT_FOUND);
        ErrorResponse error1 = new ErrorResponse(serverException1);
        SongsByArtistDtoRequest songsByArtistDtoRequest3 = new SongsByArtistDtoRequest("Linda Scott", gson.toJson(new LogoutDtoRequest("doesntexisttoken123")));
        assertEquals(gson.toJson(error1), server.getSongsByArtist(gson.toJson(songsByArtistDtoRequest3)));

    }


    @Test
    public void ratingError() {
        String token = server.registerUser(gson.toJson(user));
        assertNotNull(token);
        String token1 = server.registerUser(gson.toJson(user1));
        assertNotNull(token1);
        AddSongDtoRequest addSongDtoRequest = new AddSongDtoRequest(Collections.singletonList(song), token);

        assertEquals(gson.toJson(new EmptyResponse()), server.addSong(gson.toJson(addSongDtoRequest)));

        ServerException serverException0 = new ServerException(SONG_NOT_FOUND);
        ErrorResponse error0 = new ErrorResponse(serverException0);
        RateSongDtoRequest dtoRequest2 = new RateSongDtoRequest(song2, token1, 4);
        DeleteRatingSongDtoRequest dtoRequest3 = new DeleteRatingSongDtoRequest(song2, token1);
        RateSongDtoRequest dtoRequest5 = new RateSongDtoRequest(song2, token, 4);
        AverageDtoRequest averageDtoRequest = new AverageDtoRequest(song2, token);
        assertEquals(gson.toJson(error0), server.averageRatingOfSongs(gson.toJson(averageDtoRequest)));
        assertEquals(gson.toJson(error0), server.setRatingSong(gson.toJson(dtoRequest5)));
        assertEquals(gson.toJson(error0), server.deleteRatingSong(gson.toJson(dtoRequest3)));
        assertEquals(gson.toJson(error0), server.rateSong(gson.toJson(dtoRequest2)));

        ServerException serverException = new ServerException(USER_NOT_FOUND);
        ErrorResponse error = new ErrorResponse(serverException);
        RateSongDtoRequest dtoRequest0 = new RateSongDtoRequest(song, gson.toJson(new LogoutDtoRequest("doesntexisttoken123")), 4);
        DeleteRatingSongDtoRequest dtoRequest4 = new DeleteRatingSongDtoRequest(song, gson.toJson(new LogoutDtoRequest("doesntexisttoken123")));
        RateSongDtoRequest dtoRequest7 = new RateSongDtoRequest(song, gson.toJson(new LogoutDtoRequest("doesntexisttoken123")), 4);
        AverageDtoRequest averageDtoRequest1 = new AverageDtoRequest(song, gson.toJson(new LogoutDtoRequest("doesntexisttoken123")));
        assertEquals(gson.toJson(error), server.averageRatingOfSongs(gson.toJson(averageDtoRequest1)));
        assertEquals(gson.toJson(error), server.setRatingSong(gson.toJson(dtoRequest7)));
        assertEquals(gson.toJson(error), server.deleteRatingSong(gson.toJson(dtoRequest4)));
        assertEquals(gson.toJson(error), server.rateSong(gson.toJson(dtoRequest0)));


        ServerException serverException2 = new ServerException(ACTION_NOT_ALLOWED);
        ErrorResponse error2 = new ErrorResponse(serverException2);
        RateSongDtoRequest dtoRequest8 = new RateSongDtoRequest(song, token, 4);
        assertEquals(gson.toJson(error2), server.setRatingSong(gson.toJson(dtoRequest8)));
        DeleteRatingSongDtoRequest dtoRequest6 = new DeleteRatingSongDtoRequest(song, token);
        assertEquals(gson.toJson(error2), server.deleteRatingSong(gson.toJson(dtoRequest6)));


    }


    @Test
    public void rating() {
        String token = server.registerUser(gson.toJson(user));
        assertNotNull(token);
        String token1 = server.registerUser(gson.toJson(user1));
        assertNotNull(token1);
        AddSongDtoRequest addSongDtoRequest = new AddSongDtoRequest(Collections.singletonList(song), token);

        assertEquals(gson.toJson(new EmptyResponse()), server.addSong(gson.toJson(addSongDtoRequest)));
        RateSongDtoRequest dtoRequest = new RateSongDtoRequest(song, token1, 4);
        assertEquals(gson.toJson(new EmptyResponse()), server.rateSong(gson.toJson(dtoRequest)));

        ServerException serverException1 = new ServerException(RATING_ALREADY_ADDED);
        ErrorResponse error1 = new ErrorResponse(serverException1);
        assertEquals(gson.toJson(error1), server.rateSong(gson.toJson(dtoRequest)));

        AverageDtoRequest averageDtoRequest = new AverageDtoRequest(song, token);
        AverageDtoResponse averageDtoResponse = new AverageDtoResponse((4.0 + 5.0) / 2.0);
        server.averageRatingOfSongs(gson.toJson(averageDtoRequest));
        assertEquals(gson.toJson(averageDtoResponse), server.averageRatingOfSongs(gson.toJson(averageDtoRequest)));

        RateSongDtoRequest rateSongDtoRequest = new RateSongDtoRequest(song, token1, 5);
        assertEquals(gson.toJson(new EmptyResponse()), server.setRatingSong(gson.toJson(rateSongDtoRequest)));
        AverageDtoResponse averageDtoResponse1 = new AverageDtoResponse((5.0 + 5.0) / 2.0);
        assertEquals(gson.toJson(averageDtoResponse1), server.averageRatingOfSongs(gson.toJson(averageDtoRequest)));

        DeleteRatingSongDtoRequest dtoRequest1 = new DeleteRatingSongDtoRequest(song, token1);
        assertEquals(gson.toJson(new EmptyResponse()), server.deleteRatingSong(gson.toJson(dtoRequest1)));
        AverageDtoResponse averageDtoResponse2 = new AverageDtoResponse(5.0 / 1.0);
        assertEquals(gson.toJson(averageDtoResponse2), server.averageRatingOfSongs(gson.toJson(averageDtoRequest)));

        assertEquals(gson.toJson(new EmptyResponse()), server.rateSong(gson.toJson(dtoRequest)));
        assertEquals(gson.toJson(new EmptyResponse()), server.cancelSong(gson.toJson(
                new DeleteRatingSongDtoRequest(
                        new SongDtoRequestInternal(song.getSongName(),
                                song.getComposers(),
                                song.getWordAuthors(),
                                song.getArtist(),
                                song.getDuration()),
                        token))
                )
        );


    }



    @Test
    public void comments() {
        String token = server.registerUser(gson.toJson(user));
        assertNotNull(token);
        String token1 = server.registerUser(gson.toJson(user1));
        assertNotNull(token1);
        AddSongDtoRequest addSongDtoRequest = new AddSongDtoRequest(Collections.singletonList(song), token);

        assertEquals(gson.toJson(new EmptyResponse()), server.addSong(gson.toJson(addSongDtoRequest)));
        AddCommentDtoRequest addCommentDtoRequest = new AddCommentDtoRequest("Супер!", token1, song);
        assertEquals(gson.toJson(new EmptyResponse()), server.addComment(gson.toJson(addCommentDtoRequest)));

        ServerException serverException = new ServerException(COMMENT_ALREADY_ADDED);
        ErrorResponse error = new ErrorResponse(serverException);
        assertEquals(gson.toJson(error), server.addComment(gson.toJson(addCommentDtoRequest)));


        GetCommentsDtoResponse getCommentsDtoResponse = new GetCommentsDtoResponse(Collections.singletonList("Супер!"));
        DeleteRatingSongDtoRequest dtoRequest = new DeleteRatingSongDtoRequest(song, token);
        assertEquals(gson.toJson(getCommentsDtoResponse), server.getComments(gson.toJson(dtoRequest)));

        SetCommentDtoRequest setCommentDtoRequest = new SetCommentDtoRequest("Супер!", "Супер-пупер!", token1, song);
        assertEquals(gson.toJson(new EmptyResponse()), server.setComment(gson.toJson(setCommentDtoRequest)));
        GetCommentsDtoResponse getCommentsDtoResponse1 = new GetCommentsDtoResponse(Collections.singletonList("Супер-пупер!"));
        DeleteRatingSongDtoRequest dtoRequest1 = new DeleteRatingSongDtoRequest(song, token);
        assertEquals(gson.toJson(getCommentsDtoResponse1), server.getComments(gson.toJson(dtoRequest1)));

        AddCommentDtoRequest deleteComment = new AddCommentDtoRequest("Супер-пупер!", token1, song);
        assertEquals(gson.toJson(new EmptyResponse()), server.deleteComment(gson.toJson(deleteComment)));
        GetCommentsDtoResponse getCommentsDtoResponse2 = new GetCommentsDtoResponse(new ArrayList<>());
        DeleteRatingSongDtoRequest dtoRequest2 = new DeleteRatingSongDtoRequest(song, token);
        assertEquals(gson.toJson(getCommentsDtoResponse2), server.getComments(gson.toJson(dtoRequest2)));

    }

    @Test
    public void commentsError() {
        String token = server.registerUser(gson.toJson(user));
        assertNotNull(token);
        String token1 = server.registerUser(gson.toJson(user1));
        assertNotNull(token1);
        AddSongDtoRequest addSongDtoRequest = new AddSongDtoRequest(Collections.singletonList(song), token);

        assertEquals(gson.toJson(new EmptyResponse()), server.addSong(gson.toJson(addSongDtoRequest)));

        ServerException serverException0 = new ServerException(SONG_NOT_FOUND);
        ErrorResponse error0 = new ErrorResponse(serverException0);
        AddCommentDtoRequest addCommentDtoRequest0 = new AddCommentDtoRequest("Супер!", token1, song2);
        SetCommentDtoRequest setCommentDtoRequest0 = new SetCommentDtoRequest("Супер!", "Супер-пупер!", token1, song2);
        AddCommentDtoRequest deleteComment0 = new AddCommentDtoRequest("Супер-пупер!", token1, song2);
        DeleteRatingSongDtoRequest dtoRequest = new DeleteRatingSongDtoRequest(song2, token);
        assertEquals(gson.toJson(error0), server.getComments(gson.toJson(dtoRequest)));
        assertEquals(gson.toJson(error0), server.deleteComment(gson.toJson(deleteComment0)));
        assertEquals(gson.toJson(error0), server.setComment(gson.toJson(setCommentDtoRequest0)));
        assertEquals(gson.toJson(error0), server.addComment(gson.toJson(addCommentDtoRequest0)));


        ServerException serverException = new ServerException(USER_NOT_FOUND);
        ErrorResponse error = new ErrorResponse(serverException);
        AddCommentDtoRequest addCommentDtoRequest = new AddCommentDtoRequest("Супер!", gson.toJson(new LogoutDtoRequest("doesntexisttoken123")), song);
        SetCommentDtoRequest setCommentDtoRequest = new SetCommentDtoRequest("Супер!", "Супер-пупер!", gson.toJson(new LogoutDtoRequest("doesntexisttoken123")), song);
        AddCommentDtoRequest deleteComment = new AddCommentDtoRequest("Супер-пупер!", gson.toJson(new LogoutDtoRequest("doesntexisttoken123")), song);
        DeleteRatingSongDtoRequest dtoRequest1 = new DeleteRatingSongDtoRequest(song, gson.toJson(new LogoutDtoRequest("doesntexisttoken123")));
        assertEquals(gson.toJson(error), server.getComments(gson.toJson(dtoRequest1)));
        assertEquals(gson.toJson(error), server.deleteComment(gson.toJson(deleteComment)));
        assertEquals(gson.toJson(error), server.setComment(gson.toJson(setCommentDtoRequest)));
        assertEquals(gson.toJson(error), server.addComment(gson.toJson(addCommentDtoRequest)));

        ServerException serverException1 = new ServerException(COMMENT_NOT_FOUND);
        ErrorResponse error1 = new ErrorResponse(serverException1);
        SetCommentDtoRequest setCommentDtoRequest1 = new SetCommentDtoRequest("Супер!", "Супер-пупер!", token1, song);
        AddCommentDtoRequest deleteComment1 = new AddCommentDtoRequest("Супер-пупер!", token1, song);
        assertEquals(gson.toJson(error1), server.deleteComment(gson.toJson(deleteComment1)));
        assertEquals(gson.toJson(error1), server.setComment(gson.toJson(setCommentDtoRequest1)));

    }

    @Test
    public void usersAgree() {
        String token = server.registerUser(gson.toJson(user));
        assertNotNull(token);
        String token1 = server.registerUser(gson.toJson(user1));
        assertNotNull(token1);
        AddSongDtoRequest addSongDtoRequest = new AddSongDtoRequest(Collections.singletonList(song), token);

        assertEquals(gson.toJson(new EmptyResponse()), server.addSong(gson.toJson(addSongDtoRequest)));
        AddCommentDtoRequest addCommentDtoRequest = new AddCommentDtoRequest("Супер!", token1, song);
        assertEquals(gson.toJson(new EmptyResponse()), server.addComment(gson.toJson(addCommentDtoRequest)));

        AddCommentDtoRequest addUserAgree = new AddCommentDtoRequest("Супер!", token, song);
        assertEquals(gson.toJson(new EmptyResponse()), server.addUserAgreeWithComment(gson.toJson(addUserAgree)));
        GetUsersAgreeDtoRequest getUsersAgreeDtoRequest = new GetUsersAgreeDtoRequest(song, "Супер!", token);
        assertEquals(gson.toJson(new GetUsersAgreeDtoResponse(Collections.singletonList(user))), server.getUsersAgreeWithComment(gson.toJson(getUsersAgreeDtoRequest)));

        SetCommentDtoRequest setCommentDtoRequest = new SetCommentDtoRequest("Супер!", "Супер-пупер!", token1, song);
        assertEquals(gson.toJson(new EmptyResponse()), server.setComment(gson.toJson(setCommentDtoRequest)));
        AddCommentDtoRequest addUserAgree1 = new AddCommentDtoRequest("Супер-пупер!", token, song);
        assertEquals(gson.toJson(new EmptyResponse()), server.addUserAgreeWithComment(gson.toJson(addUserAgree1)));
        AddCommentDtoRequest deleteComment = new AddCommentDtoRequest("Супер-пупер!", token1, song);
        assertEquals(gson.toJson(new EmptyResponse()), server.deleteComment(gson.toJson(deleteComment)));


        AddCommentDtoRequest deleteUserAgree = new AddCommentDtoRequest("Супер!", token, song);
        assertEquals(gson.toJson(new EmptyResponse()), server.deleteUserAgreeWithComment(gson.toJson(deleteUserAgree)));
        assertEquals(gson.toJson(new GetUsersAgreeDtoResponse(new ArrayList<>())), server.getUsersAgreeWithComment(gson.toJson(getUsersAgreeDtoRequest)));


    }

    @Test
    public void usersAgreeError() {
        String token = server.registerUser(gson.toJson(user));
        assertNotNull(token);
        String token1 = server.registerUser(gson.toJson(user1));
        assertNotNull(token1);
        AddSongDtoRequest addSongDtoRequest = new AddSongDtoRequest(Collections.singletonList(song), token);

        assertEquals(gson.toJson(new EmptyResponse()), server.addSong(gson.toJson(addSongDtoRequest)));
        AddCommentDtoRequest addCommentDtoRequest = new AddCommentDtoRequest("Супер!", token1, song);
        assertEquals(gson.toJson(new EmptyResponse()), server.addComment(gson.toJson(addCommentDtoRequest)));

        ServerException serverException0 = new ServerException(SONG_NOT_FOUND);
        ErrorResponse error0 = new ErrorResponse(serverException0);
        AddCommentDtoRequest addUserAgree0 = new AddCommentDtoRequest("Супер!", token, song2);
        AddCommentDtoRequest deleteUserAgree0 = new AddCommentDtoRequest("Супер!", token, song2);
        GetUsersAgreeDtoRequest getUsersAgreeDtoRequest0 = new GetUsersAgreeDtoRequest(song2, "Супер!", token);
        assertEquals(gson.toJson(error0), server.getUsersAgreeWithComment(gson.toJson(getUsersAgreeDtoRequest0)));
        assertEquals(gson.toJson(error0), server.deleteUserAgreeWithComment(gson.toJson(deleteUserAgree0)));
        assertEquals(gson.toJson(error0), server.addUserAgreeWithComment(gson.toJson(addUserAgree0)));


        ServerException serverException = new ServerException(USER_NOT_FOUND);
        ErrorResponse error = new ErrorResponse(serverException);
        AddCommentDtoRequest addUserAgree = new AddCommentDtoRequest("Супер!", gson.toJson(new LogoutDtoRequest("doesntexisttoken123")), song);
        AddCommentDtoRequest deleteUserAgree = new AddCommentDtoRequest("Супер!", gson.toJson(new LogoutDtoRequest("doesntexisttoken123")), song);
        GetUsersAgreeDtoRequest getUsersAgreeDtoRequest = new GetUsersAgreeDtoRequest(song, "Супер!", gson.toJson(new LogoutDtoRequest("doesntexisttoken123")));
        assertEquals(gson.toJson(error), server.deleteUserAgreeWithComment(gson.toJson(deleteUserAgree)));
        assertEquals(gson.toJson(error), server.addUserAgreeWithComment(gson.toJson(addUserAgree)));
        assertEquals(gson.toJson(error), server.getUsersAgreeWithComment(gson.toJson(getUsersAgreeDtoRequest)));



        ServerException serverException1 = new ServerException(COMMENT_NOT_FOUND);
        ErrorResponse error1 = new ErrorResponse(serverException1);
        AddCommentDtoRequest addUserAgree1 = new AddCommentDtoRequest("Круто!", token, song);
        AddCommentDtoRequest deleteUserAgree1 = new AddCommentDtoRequest("Круто!", token, song);
        GetUsersAgreeDtoRequest getUsersAgreeDtoRequest1 = new GetUsersAgreeDtoRequest(song, "Круто!", token);
        assertEquals(gson.toJson(error1), server.getUsersAgreeWithComment(gson.toJson(getUsersAgreeDtoRequest1)));
        assertEquals(gson.toJson(error1), server.deleteUserAgreeWithComment(gson.toJson(deleteUserAgree1)));
        assertEquals(gson.toJson(error1), server.addUserAgreeWithComment(gson.toJson(addUserAgree1)));

    }

    @Test
    public void pilot()  {
        String token = server.registerUser(gson.toJson(user));
        assertNotNull(token);
        String token1 = server.registerUser(gson.toJson(user1));
        assertNotNull(token1);
        AddSongDtoRequest addSongDtoRequest = new AddSongDtoRequest(Arrays.asList(song, song1, song2), token);
        assertEquals(gson.toJson(new EmptyResponse()), server.addSong(gson.toJson(addSongDtoRequest)));

        AddCommentDtoRequest addCommentDtoRequest = new AddCommentDtoRequest("Супер!", token1, song);
        assertEquals(gson.toJson(new EmptyResponse()), server.addComment(gson.toJson(addCommentDtoRequest)));
        RateSongDtoRequest dtoRequest = new RateSongDtoRequest(song, token1, 5);
        assertEquals(gson.toJson(new EmptyResponse()), server.rateSong(gson.toJson(dtoRequest)));

        AddCommentDtoRequest addCommentDtoRequest1 = new AddCommentDtoRequest("Отлично!", token1, song1);
        assertEquals(gson.toJson(new EmptyResponse()), server.addComment(gson.toJson(addCommentDtoRequest1)));
        RateSongDtoRequest dtoRequest1 = new RateSongDtoRequest(song1, token1, 5);
        assertEquals(gson.toJson(new EmptyResponse()), server.rateSong(gson.toJson(dtoRequest1)));

        AddCommentDtoRequest addCommentDtoRequest2 = new AddCommentDtoRequest("Очень хорошо!", token1, song2);
        assertEquals(gson.toJson(new EmptyResponse()), server.addComment(gson.toJson(addCommentDtoRequest2)));
        RateSongDtoRequest dtoRequest2 = new RateSongDtoRequest(song2, token1, 5);
        assertEquals(gson.toJson(new EmptyResponse()), server.rateSong(gson.toJson(dtoRequest2)));


        PilotProgramConcertDtoResponse pilotProgramConcertDtoResponse = new PilotProgramConcertDtoResponse(Arrays.asList(
                "1st song:\n" +
                        "1. The song is title - Ive Told Every Little Star," +
                        " composer (s) - [Jerome Kern]," +
                        " author (s) of words- [Oscar Hammerstein]," +
                        " artist - Linda Scott.\n" +
                        "2. Proposal author - James Bond, radio listener login - JamesBond1\n" +
                        "3. average rating - 5.0\n" +
                        "4. \"Супер!\"; ",
                "2st song:\n" +
                        "1. The song is title - Stayin Alive," +
                        " composer (s) - [Robin Gibb, Maurice Gibb, Barry Gibb]," +
                        " author (s) of words- [Robin Gibb, Maurice Gibb, Barry Gibb]," +
                        " artist - Capital Cities.\n" +
                        "2. Proposal author - James Bond, radio listener login - JamesBond1\n" +
                        "3. average rating - 5.0\n" +
                        "4. \"Отлично!\"; ",
                "3st song:\n" +
                        "1. The song is title - Who?," +
                        " composer (s) - [Jerome Kern, Otto Harbach]," +
                        " author (s) of words- [Oscar Hammerstein, Ralph Blaine]," +
                        " artist - Judy Garland.\n" +
                        "2. Proposal author - James Bond, radio listener login - JamesBond1\n" +
                        "3. average rating - 5.0\n" +
                        "4. \"Очень хорошо!\"; "

        ));
        server.pilotProgramConcert(token);
        assertEquals(gson.toJson(pilotProgramConcertDtoResponse), server.pilotProgramConcert(token));

        assertEquals(gson.toJson(new EmptyResponse()), server.delete(token));
        PilotProgramConcertDtoResponse pilotProgramConcertDtoResponse1 = new PilotProgramConcertDtoResponse(Arrays.asList(
                "1st song:\n" +
                        "1. The song is title - Ive Told Every Little Star," +
                        " composer (s) - [Jerome Kern]," +
                        " author (s) of words- [Oscar Hammerstein]," +
                        " artist - Linda Scott.\n" +
                        "2. Proposal author - community.\n" +
                        "3. average rating - 5.0\n" +
                        "4. \"Супер!\"; ",
                "2st song:\n" +
                        "1. The song is title - Stayin Alive," +
                        " composer (s) - [Robin Gibb, Maurice Gibb, Barry Gibb]," +
                        " author (s) of words- [Robin Gibb, Maurice Gibb, Barry Gibb]," +
                        " artist - Capital Cities.\n" +
                        "2. Proposal author - community.\n" +
                        "3. average rating - 5.0\n" +
                        "4. \"Отлично!\"; ",
                "3st song:\n" +
                        "1. The song is title - Who?," +
                        " composer (s) - [Jerome Kern, Otto Harbach]," +
                        " author (s) of words- [Oscar Hammerstein, Ralph Blaine]," +
                        " artist - Judy Garland.\n" +
                        "2. Proposal author - community.\n" +
                        "3. average rating - 5.0\n" +
                        "4. \"Очень хорошо!\"; "

        ));
        assertEquals(gson.toJson(pilotProgramConcertDtoResponse1), server.pilotProgramConcert(token1));


    }

    @Test
    public void pilotError() {

        ServerException serverException = new ServerException(USER_NOT_FOUND);
        ErrorResponse error = new ErrorResponse(serverException);
        assertEquals(gson.toJson(error), server.pilotProgramConcert(gson.toJson(new LogoutDtoRequest("doesntexisttoken123"))));

    }

*/
}
