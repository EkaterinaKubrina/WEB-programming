package net.thumbtack.school.concert.db.mydb.data;

import net.thumbtack.school.concert.db.mydb.concerrentMap.ConcurrentMultiValuedMap;
import net.thumbtack.school.concert.error.ErrorCode;
import net.thumbtack.school.concert.error.MyException;
import net.thumbtack.school.concert.db.model.*;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;


public class DataBase {
    private static DataBase instance;
    private ConcurrentMap<String, User> userByToken;
    private ConcurrentMap<String, User> userByLogin;
    private ConcurrentMap<Integer, Song> songs;
    private ConcurrentMultiValuedMap<Song, Rating> ratings;
    private ConcurrentMultiValuedMap<AuthorProposal, Song> authorSongSuggestion;
    private ConcurrentMultiValuedMap<String, Song> songsByComposers;
    private ConcurrentMultiValuedMap<String, Song> songsByWordAuthors;
    private ConcurrentMultiValuedMap<String, Song> songsByArtist;
    private Community community;
    private AtomicInteger idSong = new AtomicInteger(0);


    private DataBase(ConcurrentMap<String, User> userByToken,
                     ConcurrentMap<String, User> userByLogin,
                     ConcurrentMap<Integer, Song> songs,
                     ConcurrentMultiValuedMap<Song, Rating> ratings,
                     ConcurrentMultiValuedMap<AuthorProposal, Song> authorSongSuggestion,
                     ConcurrentMultiValuedMap<String, Song> songsByComposers,
                     ConcurrentMultiValuedMap<String, Song> songsByWordAuthors,
                     ConcurrentMultiValuedMap<String, Song> songsByArtist,
                     Community community) {
        this.userByToken = userByToken;
        this.userByLogin = userByLogin;
        this.songs = songs;
        this.ratings = ratings;
        this.authorSongSuggestion = authorSongSuggestion;
        this.songsByComposers = songsByComposers;
        this.songsByWordAuthors = songsByWordAuthors;
        this.songsByArtist = songsByArtist;
        this.community = community;
    }



    public static DataBase getInstance(ConcurrentMap<String, User> userByToken,
                                       ConcurrentMap<String, User> userByLogin,
                                       ConcurrentMap<Integer, Song> songs,
                                       ConcurrentMultiValuedMap<Song, Rating> rating,
                                       ConcurrentMultiValuedMap<AuthorProposal, Song> authorSongSuggestion,
                                       ConcurrentMultiValuedMap<String, Song> songsByComposers,
                                       ConcurrentMultiValuedMap<String, Song> songsByWordAuthors,
                                       ConcurrentMultiValuedMap<String, Song> songsByArtist,
                                       Community community) {
        if (instance == null) {
            instance = new DataBase(userByToken,
                    userByLogin,
                    songs,
                    rating,
                    authorSongSuggestion,
                    songsByComposers,
                    songsByWordAuthors,
                    songsByArtist,
                    community);
        }
        return instance;
    }

    public static DataBase getDataBase() {
        return instance;
    }

    public static void startServer() throws MyException {
        if (instance != null) {
            throw new MyException(ErrorCode.DATA_BASE_ALREADY_EXIST);
        }
        getInstance(new ConcurrentHashMap<>(),
                new ConcurrentHashMap<>(),
                new ConcurrentHashMap<>(),
                new ConcurrentMultiValuedMap<>(new ArrayListValuedHashMap<>()),
                new ConcurrentMultiValuedMap<>(new ArrayListValuedHashMap<>()),
                new ConcurrentMultiValuedMap<>(new ArrayListValuedHashMap<>()),
                new ConcurrentMultiValuedMap<>(new ArrayListValuedHashMap<>()),
                new ConcurrentMultiValuedMap<>(new ArrayListValuedHashMap<>()),
                new Community());
    }


    public static void clearServer() throws MyException{
        if (instance == null) {
            throw new MyException(ErrorCode.DATA_BASE_NOT_FOUND);
        }
        instance.userByLogin.clear();
        instance.userByToken.clear();
        instance.songs.clear();
        instance.ratings.clear();
        instance.songsByArtist.clear();
        instance.songsByComposers.clear();
        instance.songsByWordAuthors.clear();
        instance.authorSongSuggestion.clear();
    }

    public static boolean emptyData(){
        return instance==null;
    }

    public User getByToken(String token){
        return userByToken.get(token);
    }

    public void registerUser(User user) throws MyException {
        if (userByLogin.putIfAbsent(user.getLogin(), user) != null) {
            throw new MyException(ErrorCode.LOGIN_ALREADY_EXIST);
        }
    }

    public void logout(String token) throws MyException {
        if (userByToken.remove(token) == null) {
            throw new MyException(ErrorCode.USER_NOT_FOUND);
        }
    }

    public String login(String login, String token) throws MyException {
        User user = userByLogin.get(login);
        if (user == null) {
            throw new MyException(ErrorCode.USER_NOT_FOUND);
        }
        userByToken.put(token, userByLogin.get(login));
        return token;
    }

    public User getByLogin(String login){
        return userByLogin.get(login);
    }


    public void delete(String token) throws MyException {
        if(userByToken.get(token)==null){
            throw new MyException(ErrorCode.USER_NOT_FOUND);
        }
        userByLogin.remove(userByToken.get(token).getLogin());
        for(int i = 0; i < authorSongSuggestion.get(userByToken.get(token)).size(); i++){
            (authorSongSuggestion.get(userByToken.get(token))).get(i).deleteComments(userByToken.get(token));
            cancelSong((authorSongSuggestion.get(userByToken.get(token))).get(i).getId(), userByToken.get(token));
            i--;
        }
        logout(token);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(userByLogin.values());
    }

    public List<User> getAllOnlineUsers() {
        return new ArrayList<>(userByToken.values());
    }

    public List<Song> getAllSongs() {
        List<Song> s = new ArrayList<>(songs.values());
        s.sort(Comparator.comparing(Song::getSongName));
        return s;
    }

    public Set<Song> getAllSongsByEachWordAuthors(List<String> wordAuthors) {
        Set<Song> set = new TreeSet<>(Comparator.comparing(Song::getSongName));
        for(String s: wordAuthors){
            set.addAll(songsByWordAuthors.get(s));
        }
        return set;
    }


    public Set<Song> getSongsByWordAuthors(List<String> wordAuthors) {
        Set<Song> set = new TreeSet<>(Comparator.comparing(Song::getSongName));
        for(String s: wordAuthors){
            set.addAll(songsByWordAuthors.get(s));
            set.retainAll(songsByWordAuthors.get(s));
        }
        return set;
    }


    public Set<Song> getSongsByArtist(String artist) {
        Set<Song> set = new TreeSet<>(Comparator.comparing(Song::getSongName));
        set.addAll(songsByArtist.get(artist));
        return set;
    }

    public Set<Song> getAllSongsByEachComposers(List<String> composers) {
        Set<Song> set = new TreeSet<>(Comparator.comparing(Song::getSongName));
        for(String s: composers){
            set.addAll(songsByComposers.get(s));
        }
        return set;
    }


    public Set<Song> getSongsByComposers(List<String> composers) {
        Set<Song> set = new TreeSet<>(Comparator.comparing(Song::getSongName));
        for(String s: composers){
            set.addAll(songsByComposers.get(s));
            set.retainAll(songsByComposers.get(s));
        }
        return set;
    }


    public int addSong(Song song, User user) throws MyException {
        if (songs.containsValue(song)) {
            throw new MyException(ErrorCode.SONG_ALREADY_ADDED);
        }
        song.setId(idSong.incrementAndGet());
        songs.put(song.getId(), song);
        ratings.put(song, new Rating(user, 5));
        songs.get(song.getId()).setSumRating(5);
        authorSongSuggestion.put(user, song);
        songs.get(song.getId()).setIdAuthorOfSuggestion(user);
        for(String s: song.getWordAuthors()){
            songsByWordAuthors.put(s, song);
        }
        for(String s: song.getComposers()){
            songsByComposers.put(s, song);
        }
        songsByArtist.put(song.getArtist(), song);
        return song.getId();
    }

    public void cancelSong(int id, User user) throws MyException {
        if (!songs.containsKey(id)) {
            throw new MyException(ErrorCode.SONG_NOT_FOUND);
        }
        if(ratings.get(songs.get(id)).size()==1){
            songs.get(id).deleteSumRating();
            ratings.remove(songs.get(id));
            authorSongSuggestion.removeMapping(user, songs.get(id));
            for(String s: songs.get(id).getWordAuthors()){
                songsByWordAuthors.removeMapping(s, songs.get(id));
            }
            for(String s: songs.get(id).getComposers()){
                songsByComposers.removeMapping(s, songs.get(id));
            }
            songsByArtist.removeMapping(songs.get(id).getArtist(), songs.get(id));
            songs.remove(id);
        }
        else{
            authorSongSuggestion.removeMapping(user, songs.get(id));
            authorSongSuggestion.put(community, songs.get(id));
            songs.get(id).setIdAuthorOfSuggestion(community);
            deleteRatingSong(id, user);
        }
    }

    public void rateSong(int id, User user, int rate) throws MyException {
        if (!songs.containsKey(id)) {
            throw new MyException(ErrorCode.SONG_NOT_FOUND);
        }
        for(Rating rating : ratings.get(songs.get(id))){
            if(rating.getRatingAuthor().equals(user)){
                throw new MyException(ErrorCode.RATING_ALREADY_ADDED);
            }
        }
        ratings.put(songs.get(id), new Rating(user, rate));
        songs.get(id).setSumRating(rate);
    }

    public void deleteRatingSong(int id, User user) throws MyException {
        if (!songs.containsKey(id)) {
            throw new MyException(ErrorCode.SONG_NOT_FOUND);
        }
        if(authorSongSuggestion.get(user).contains(songs.get(id))){
            throw new MyException(ErrorCode.ACTION_NOT_ALLOWED);
        }
        for(Rating rating : ratings.get(songs.get(id))){
            if(rating.getRatingAuthor().equals(user)){
                songs.get(id).setSumRating(-rating.getRating());
                ratings.removeMapping(songs.get(id), rating);
                break;
            }
        }
    }

    public void setRatingSong(int id, User user, int rate) throws MyException {
        if (!songs.containsKey(id)) {
            throw new MyException(ErrorCode.SONG_NOT_FOUND);
        }
        if(authorSongSuggestion.get(user).contains(songs.get(id))){
            throw new MyException(ErrorCode.ACTION_NOT_ALLOWED);
        }
        for(Rating rating : ratings.get(songs.get(id))){
            if(rating.getRatingAuthor().equals(user)){
                songs.get(id).setSumRating(-rating.getRating()+rate);
                rating.setRating(rate);
            }
        }
    }

    public double averageRatingOfSongs(int id) throws MyException {
        if (!songs.containsKey(id)) {
            throw new MyException(ErrorCode.SONG_NOT_FOUND);
        }
        return (double) songs.get(id).getSumRating()/ratings.get(songs.get(id)).size();
    }

    public void addComment(String comment, User user, int id) throws MyException {
        if (!songs.containsKey(id)) {
            throw new MyException(ErrorCode.SONG_NOT_FOUND);
        }
        if(songs.get(id).getComments().containsValue(comment)){
            throw new MyException(ErrorCode.COMMENT_ALREADY_ADDED);
        }
        songs.get(id).addComment(user, comment);
    }

    public void deleteComment(String comment, User user, int id) throws MyException {
        if (!songs.containsKey(id)) {
            throw new MyException(ErrorCode.SONG_NOT_FOUND);
        }
        if(!songs.get(id).getComments().containsValue(comment)){
            throw new MyException(ErrorCode.COMMENT_NOT_FOUND);
        }
        if(songs.get(id).getUsersAgreeWithComment().get(comment).size()>0){
            songs.get(id).addComment(community, comment);
        }
        songs.get(id).deleteComment(user, comment);
    }

    public void setComment(String commentBefore, String commentAfter, User user,  int id) throws MyException {
        if (!songs.containsKey(id)) {
            throw new MyException(ErrorCode.SONG_NOT_FOUND);
        }
        if(!songs.get(id).getComments().containsValue(commentBefore)){
            throw new MyException(ErrorCode.COMMENT_NOT_FOUND);
        }
        if(songs.get(id).getUsersAgreeWithComment().get(commentBefore).size()>0){
            songs.get(id).addComment(community, commentBefore);
        }
        songs.get(id).deleteComment(user, commentBefore);
        songs.get(id).addComment(user, commentAfter);
    }

    public void addUserAgreeWithComment(String comment, User user, int id) throws MyException {
        if (!songs.containsKey(id)) {
            throw new MyException(ErrorCode.SONG_NOT_FOUND);
        }
        if(!songs.get(id).getComments().containsValue(comment)){
            throw new MyException(ErrorCode.COMMENT_NOT_FOUND);
        }
        songs.get(id).addUserAgree(user, comment);
    }

    public void deleteUserAgreeWithComment(String comment, User user, int id) throws MyException {
        if (!songs.containsKey(id)) {
            throw new MyException(ErrorCode.SONG_NOT_FOUND);
        }
        if(!songs.get(id).getComments().containsValue(comment)){
            throw new MyException(ErrorCode.COMMENT_NOT_FOUND);
        }
        songs.get(id).deleteUserAgree(comment, user);
    }

    public List<String> getComments(int id) throws MyException {
        if (!songs.containsKey(id)) {
            throw new MyException(ErrorCode.SONG_NOT_FOUND);
        }
        return new ArrayList<>(songs.get(id).getComments().values());
    }

    public List<User> getUsersAgreeWithComment( int id, String comment) throws MyException {
        if (!songs.containsKey(id)) {
            throw new MyException(ErrorCode.SONG_NOT_FOUND);
        }
        if(!songs.get(id).getComments().containsValue(comment)){
            throw new MyException(ErrorCode.COMMENT_NOT_FOUND);
        }
        return new ArrayList<>(songs.get(id).getUsersAgreeWithComment().get(comment));
    }

    public List<String> pilotProgramOfConcert() throws MyException {
        List<String> answer = new ArrayList<>();
        List<Song> listSort = new ArrayList<>(songs.values());
        listSort.sort(Comparator.comparing(Song::getSumRating));
        int amountOfDuration = 0;
        int numberOfSong = 0;
        for(Song song : listSort){
            if(amountOfDuration+song.getDuration()+10<=3600){
                amountOfDuration += song.getDuration()+10;
                numberOfSong ++;
                answer.add(numberOfSong + "st song:\n"+ "1. " + song.toString() + "\n" +
                        "2. " + song.getIdAuthorOfSuggestion().toString() + "\n" +
                        "3. average rating - " + averageRatingOfSongs(song.getId()) + "\n" +
                        "4. " + song.getCommentsToString());
            }
            else if(amountOfDuration+song.getDuration()<=3600){
                amountOfDuration += song.getDuration();
                numberOfSong ++;
                answer.add(numberOfSong + "st song:\n"+ "1. " + song.toString() + "\n" +
                        "2. " + song.getIdAuthorOfSuggestion().toString() + "\n" +
                        "3. average rating - " + averageRatingOfSongs(song.getId()) + "\n" +
                        "4. " + song.getCommentsToString() + "\n");
            }
        }
        return answer;
    }


}
