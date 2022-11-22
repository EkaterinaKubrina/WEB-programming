package net.thumbtack.school.concert.hibernate.service;

import com.google.gson.Gson;
import net.thumbtack.school.concert.db.dto.request.*;
import net.thumbtack.school.concert.db.dto.response.*;
import net.thumbtack.school.concert.error.MyException;
import net.thumbtack.school.concert.gson.FromJson;
import net.thumbtack.school.concert.hibernate.dao.SongDao;
import net.thumbtack.school.concert.hibernate.daoimpl.BaseDaoImpl;
import net.thumbtack.school.concert.hibernate.model.Song;
import net.thumbtack.school.concert.hibernate.model.User;
import net.thumbtack.school.concert.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

import static net.thumbtack.school.concert.error.ErrorCode.INCORRECT_INPUT_DATA;
import static net.thumbtack.school.concert.error.ErrorCode.USER_NOT_FOUND;

public class SongService {

    private static final Logger LOGGER = LoggerFactory.getLogger(net.thumbtack.school.concert.db.service.SongService.class);
    private static Gson gson = new Gson();
    private SongDao songDao;
    private BaseDaoImpl baseDao = new BaseDaoImpl();

    public SongService(SongDao songDao) {
        this.songDao = songDao;
    }

    public Response addSong(String requestJsonString) {
        LOGGER.debug("Inserting song");
        try {
            AddSongDtoRequest addSongDtoRequest = FromJson.getClassInstanceFromJson(requestJsonString, AddSongDtoRequest.class);
            TokenDtoRequest tokenDtoRequest = FromJson.getClassInstanceFromJson(addSongDtoRequest.getToken(), TokenDtoRequest.class);
            if(baseDao.getByToken(tokenDtoRequest.getToken())==null){
                throw new MyException(USER_NOT_FOUND);
            }
            ArrayList<Integer> id = new ArrayList<>();
            for (SongDtoRequestInternal song : addSongDtoRequest.getSongs()) {
                validate(song);
                Song song1 = new Song(song.getSongName(),
                        song.getComposers(),
                        song.getWordAuthors(),
                        song.getArtist(),
                        song.getDuration());
                id.add(songDao.addSong(song1, baseDao.getByToken(tokenDtoRequest.getToken())));
            }

            LOGGER.debug("Successfully inserting song");
            return Response.ok(gson.toJson(new AddSongDtoResponse(id)), MediaType.APPLICATION_JSON).build();
        } catch (MyException e) {
            LOGGER.error(e.getMessage());
            return Utils.failureResponse(e);
        }
    }



    public Response getAllSongs(String tokenDtoRequest) {
        LOGGER.debug("Getting all songs");
        try {
            if(baseDao.getByToken(tokenDtoRequest)==null){
                throw new MyException(USER_NOT_FOUND);
            }
            List<Song> list = songDao.getAllSongs();
            List<SongDtoRequestInternal> list1 = new ArrayList<>();
            for(Song song : list){
                list1.add(new SongDtoRequestInternal(song.getSongName(),
                        song.getComposers(),
                        song.getWordAuthors(),
                        song.getArtist().get(0),
                        song.getDuration()));
            }

            LOGGER.debug("Successfully received");
            return Response.ok(gson.toJson(new AllSongsDtoResponse(list1)), MediaType.APPLICATION_JSON).build();
        } catch (MyException e) {
            LOGGER.error(e.getMessage());
            return  Utils.failureResponse(e);
        }
    }

    public Response getSongsByComposer(String tokenDtoRequest, List<String> composers) {
        LOGGER.debug("Getting all songs by composer");
        try {
            if(baseDao.getByToken(tokenDtoRequest)==null){
                throw new MyException(USER_NOT_FOUND);
            }
            Set<Song> set = songDao.getSongsByComposers(composers);
            Set<SongDtoRequestInternal> set1 = new TreeSet<>(Comparator.comparing(SongDtoRequestInternal::getSongName));
            for(Song song : set){
                set1.add(new SongDtoRequestInternal(song.getSongName(),
                        song.getComposers(),
                        song.getWordAuthors(),
                        song.getArtist().get(0),
                        song.getDuration()));
            }

            LOGGER.debug("Successfully received");
            return Response.ok(gson.toJson(new SongsByComposerDtoResponse(set1)), MediaType.APPLICATION_JSON).build();
        } catch (MyException e) {
            LOGGER.error(e.getMessage());
            return Utils.failureResponse(e);
        }
    }

    public Response getAllSongsByEachComposers(String tokenDtoRequest, List<String> composers) {
        LOGGER.debug("Getting all songs by Each Composers");
        try {
            if(baseDao.getByToken(tokenDtoRequest)==null){
                throw new MyException(USER_NOT_FOUND);
            }
            Set<Song> set = songDao.getAllSongsByEachComposers(composers);
            Set<SongDtoRequestInternal> set1 = new TreeSet<>(Comparator.comparing(SongDtoRequestInternal::getSongName));
            for(Song song : set){
                set1.add(new SongDtoRequestInternal(song.getSongName(),
                        song.getComposers(),
                        song.getWordAuthors(),
                        song.getArtist().get(0),
                        song.getDuration()));
            }

            LOGGER.debug("Successfully received");
            return Response.ok(gson.toJson(new SongsByComposerDtoResponse(set1)), MediaType.APPLICATION_JSON).build();
        } catch (MyException e) {
            LOGGER.error(e.getMessage());
            return Utils.failureResponse(e);
        }
    }

    public Response getSongsByWordAuthors(String tokenDtoRequest, List<String> wordAuthors) {
        LOGGER.debug("Getting all songs by Word Authors");
        try {
            if(baseDao.getByToken(tokenDtoRequest)==null){
                throw new MyException(USER_NOT_FOUND);
            }
            Set<Song> set = songDao.getSongsByWordAuthors(wordAuthors);
            Set<SongDtoRequestInternal> set1 = new TreeSet<>(Comparator.comparing(SongDtoRequestInternal::getSongName));
            for(Song song : set){
                set1.add(new SongDtoRequestInternal(song.getSongName(),
                        song.getComposers(),
                        song.getWordAuthors(),
                        song.getArtist().get(0),
                        song.getDuration()));
            }

            LOGGER.debug("Successfully received");
            return Response.ok(gson.toJson(new SongsByWordAuthorsDtoResponse(set1)), MediaType.APPLICATION_JSON).build();
        } catch (MyException e) {
            LOGGER.error(e.getMessage());
            return Utils.failureResponse(e);
        }
    }

    public Response getAllSongsByEachWordAuthors(String tokenDtoRequest, List<String> wordAuthors) {
        LOGGER.debug("Getting all songs by Each Word Authors");
        try {
            if(baseDao.getByToken(tokenDtoRequest)==null){
                throw new MyException(USER_NOT_FOUND);
            }
            Set<Song> set = songDao.getAllSongsByEachWordAuthors(wordAuthors);
            Set<SongDtoRequestInternal> set1 = new TreeSet<>(Comparator.comparing(SongDtoRequestInternal::getSongName));
            for(Song song : set){
                set1.add(new SongDtoRequestInternal(song.getSongName(),
                        song.getComposers(),
                        song.getWordAuthors(),
                        song.getArtist().get(0),
                        song.getDuration()));
            }

            LOGGER.debug("Successfully received");
            return Response.ok(gson.toJson(new SongsByWordAuthorsDtoResponse(set1)), MediaType.APPLICATION_JSON).build();
        } catch (MyException e) {
            LOGGER.error(e.getMessage());
            return Utils.failureResponse(e);
        }
    }

    public Response getSongsByArtist(String tokenDtoRequest, String artist) {
        LOGGER.debug("Getting all songs By Artist");
        try {
            if(baseDao.getByToken(tokenDtoRequest)==null){
                throw new MyException(USER_NOT_FOUND);
            }
            Set<Song> set = songDao.getSongsByArtist(artist);
            Set<SongDtoRequestInternal> set1 = new TreeSet<>(Comparator.comparing(SongDtoRequestInternal::getSongName));
            for(Song song : set){
                set1.add(new SongDtoRequestInternal(song.getSongName(),
                        song.getComposers(),
                        song.getWordAuthors(),
                        song.getArtist().get(0),
                        song.getDuration()));
            }

            LOGGER.debug("Successfully received");
            return Response.ok(gson.toJson(new SongsByArtistDtoResponse(set1)), MediaType.APPLICATION_JSON).build();
        } catch (MyException e) {
            LOGGER.error(e.getMessage());
            return Utils.failureResponse(e);
        }
    }

    public Response rateSong(String requestJsonString) {
        LOGGER.debug("Inserting rate song");
        try {
            RateSongDtoRequest rateSongDtoRequest = FromJson.getClassInstanceFromJson(requestJsonString, RateSongDtoRequest.class);
            TokenDtoRequest tokenDtoRequest = FromJson.getClassInstanceFromJson(rateSongDtoRequest.getToken(), TokenDtoRequest.class);
            User user = baseDao.getByToken(tokenDtoRequest.getToken());
            if(user==null){
                throw new MyException(USER_NOT_FOUND);
            }
            songDao.rateSong(rateSongDtoRequest.getId(),
                    baseDao.getByToken(tokenDtoRequest.getToken()),
                    rateSongDtoRequest.getRate());

            LOGGER.debug("Successfully insert");
            return Response.ok(gson.toJson(new EmptyResponse()), MediaType.APPLICATION_JSON).build();
        } catch (MyException e) {
            LOGGER.error(e.getMessage());
            return Utils.failureResponse(e);
        }
    }

    public Response deleteRatingSong(int id, String tokenDtoRequest) {
        LOGGER.debug("Deleting rating song");
        try {
            if(baseDao.getByToken(tokenDtoRequest)==null){
                throw new MyException(USER_NOT_FOUND);
            }
            songDao.deleteRatingSong(id,
                    baseDao.getByToken(tokenDtoRequest));

            LOGGER.debug("Successfully delete");
            return Response.ok(gson.toJson(new EmptyResponse()), MediaType.APPLICATION_JSON).build();
        } catch (MyException e) {
            LOGGER.error(e.getMessage());
            return Utils.failureResponse(e);
        }
    }

    public Response setRatingSong(String requestJsonString) {
        LOGGER.debug("Setting song rating");
        try {
            RateSongDtoRequest rateSongDtoRequest = FromJson.getClassInstanceFromJson(requestJsonString, RateSongDtoRequest.class);
            TokenDtoRequest tokenDtoRequest = FromJson.getClassInstanceFromJson(rateSongDtoRequest.getToken(), TokenDtoRequest.class);
            if(baseDao.getByToken(tokenDtoRequest.getToken())==null){
                throw new MyException(USER_NOT_FOUND);
            }
            songDao.setRatingSong(rateSongDtoRequest.getId(),
                    baseDao.getByToken(tokenDtoRequest.getToken()),
                    rateSongDtoRequest.getRate());

            LOGGER.debug("Successfully change");
            return Response.ok(gson.toJson(new EmptyResponse()), MediaType.APPLICATION_JSON).build();
        } catch (MyException e) {
            LOGGER.error(e.getMessage());
            return Utils.failureResponse(e);
        }
    }

    public Response cancelSong(String tokenDtoRequest, int id) {
        LOGGER.debug("Canceling Song");
        try {
            if(baseDao.getByToken(tokenDtoRequest)==null){
                throw new MyException(USER_NOT_FOUND);
            }
            songDao.cancelSong(id, baseDao.getByToken(tokenDtoRequest));

            LOGGER.debug("Successfully cancel");
            return Response.ok(gson.toJson(new EmptyResponse()), MediaType.APPLICATION_JSON).build();
        } catch (MyException e) {
            LOGGER.error(e.getMessage());
            return Utils.failureResponse(e);
        }
    }

    public Response averageRatingOfSongs(int id, String tokenDtoRequest) {
        LOGGER.debug("Getting average Rating Of Songs");
        try {
            if(baseDao.getByToken(tokenDtoRequest)==null){
                throw new MyException(USER_NOT_FOUND);
            }
            double average = songDao.averageRatingOfSongs(id);

            LOGGER.debug("Successfully received");
            return Response.ok(gson.toJson(new AverageDtoResponse(average)), MediaType.APPLICATION_JSON).build();
        } catch (MyException e) {
            LOGGER.error(e.getMessage());
            return Utils.failureResponse(e);
        }
    }


    public Response addComment(String requestJsonString) {
        LOGGER.debug("Inserting comment");
        try{
            AddCommentDtoRequest addCommentDtoRequest = FromJson.getClassInstanceFromJson(requestJsonString, AddCommentDtoRequest.class);
            TokenDtoRequest tokenDtoRequest = FromJson.getClassInstanceFromJson(addCommentDtoRequest.getToken(), TokenDtoRequest.class);
            if(baseDao.getByToken(tokenDtoRequest.getToken())==null){
                throw new MyException(USER_NOT_FOUND);
            }
            songDao.addComment(addCommentDtoRequest.getComment(),
                    baseDao.getByToken(tokenDtoRequest.getToken()),
                    addCommentDtoRequest.getId());

            LOGGER.debug("Successfully insert");
            return Response.ok(gson.toJson(new EmptyResponse()), MediaType.APPLICATION_JSON).build();
        } catch (MyException e) {
            LOGGER.error(e.getMessage());
            return Utils.failureResponse(e);
        }
    }


    public Response deleteComment(int id, String tokenDtoRequest, String comment){
        LOGGER.debug("Deleting comment");
        try{
            if(baseDao.getByToken(tokenDtoRequest)==null){
                throw new MyException(USER_NOT_FOUND);
            }
            songDao.deleteComment(comment,
                    baseDao.getByToken(tokenDtoRequest),
                    id);

            LOGGER.debug("Successfully delete");
            return Response.ok(gson.toJson(new EmptyResponse()), MediaType.APPLICATION_JSON).build();
        } catch (MyException e) {
            LOGGER.error(e.getMessage());
            return Utils.failureResponse(e);
        }
    }

    public Response setComment(String requestJsonString){
        LOGGER.debug("Setting comment");
        try{
            SetCommentDtoRequest setCommentDtoRequest = FromJson.getClassInstanceFromJson(requestJsonString, SetCommentDtoRequest.class);
            TokenDtoRequest tokenDtoRequest = FromJson.getClassInstanceFromJson(setCommentDtoRequest.getToken(), TokenDtoRequest.class);
            if(baseDao.getByToken(tokenDtoRequest.getToken())==null){
                throw new MyException(USER_NOT_FOUND);
            }
            songDao.setComment(setCommentDtoRequest.getCommentBefore(),
                    setCommentDtoRequest.getCommentAfter(),
                    baseDao.getByToken(tokenDtoRequest.getToken()),
                    setCommentDtoRequest.getId());

            LOGGER.debug("Successfully change");
            return Response.ok(gson.toJson(new EmptyResponse()), MediaType.APPLICATION_JSON).build();
        } catch (MyException e) {
            LOGGER.error(e.getMessage());
            return Utils.failureResponse(e);
        }
    }

    public Response addUserAgreeWithComment(String requestJsonString) {
        LOGGER.debug("Inserting User Agree With Comment");
        try {
            AddCommentDtoRequest addCommentDtoRequest = FromJson.getClassInstanceFromJson(requestJsonString, AddCommentDtoRequest.class);
            TokenDtoRequest tokenDtoRequest = FromJson.getClassInstanceFromJson(addCommentDtoRequest.getToken(), TokenDtoRequest.class);
            if (baseDao.getByToken(tokenDtoRequest.getToken()) == null) {
                throw new MyException(USER_NOT_FOUND);
            }
            songDao.addUserAgreeWithComment(addCommentDtoRequest.getComment(),
                    baseDao.getByToken(tokenDtoRequest.getToken()),
                    addCommentDtoRequest.getId());

            LOGGER.debug("Successfully insert");
            return Response.ok(gson.toJson(new EmptyResponse()), MediaType.APPLICATION_JSON).build();
        } catch (MyException e) {
            LOGGER.error(e.getMessage());
            return Utils.failureResponse(e);
        }
    }

    public Response deleteUserAgreeWithComment(int id, String tokenDtoRequest, String comment) {
        LOGGER.debug("Deleting User Agree With Comment");
        try {
            if(baseDao.getByToken(tokenDtoRequest)==null){
                throw new MyException(USER_NOT_FOUND);
            }
            songDao.deleteUserAgreeWithComment(comment,
                    baseDao.getByToken(tokenDtoRequest),
                    id);

            LOGGER.debug("Successfully delete");
            return Response.ok(gson.toJson(new EmptyResponse()), MediaType.APPLICATION_JSON).build();
        } catch (MyException e) {
            LOGGER.error(e.getMessage());
            return Utils.failureResponse(e);
        }
    }

    public Response getComments(int id, String tokenDtoRequest) {
        LOGGER.debug("Getting all comments");
        try{
            if(baseDao.getByToken(tokenDtoRequest)==null){
                throw new MyException(USER_NOT_FOUND);
            }
            List<String> list = songDao.getComments(id);

            LOGGER.debug("Successfully received");
            return Response.ok(gson.toJson(new GetCommentsDtoResponse(list)), MediaType.APPLICATION_JSON).build();
        } catch (MyException e) {
            LOGGER.error(e.getMessage());
            return Utils.failureResponse(e);
        }
    }

    public Response getUsersAgreeWithComment(int id, String tokenDtoRequest, String comment) {
        LOGGER.debug("Getting Users Agree With Comment");
        try{
            if(baseDao.getByToken(tokenDtoRequest)==null){
                throw new MyException(USER_NOT_FOUND);
            }
            List<User> list = songDao.getUsersAgreeWithComment(id, comment);
            List<UserDtoRequestInternal> list2 = new ArrayList<>();
            for(User user : list){
                list2.add(new UserDtoRequestInternal(user.getFirstName(),
                        user.getLastName(),
                        user.getLogin(),
                        user.getPassword()));
            }

            LOGGER.debug("Successfully received");
            return Response.ok(gson.toJson(new GetUsersAgreeDtoResponse(list2)), MediaType.APPLICATION_JSON).build();
        } catch (MyException e) {
            LOGGER.error(e.getMessage());
            return Utils.failureResponse(e);
        }
    }

    private void validate(SongDtoRequestInternal addSongDtoRequest) throws MyException {
        if (addSongDtoRequest.getArtist() == null ||
                addSongDtoRequest.getSongName() == null ||
                addSongDtoRequest.getDuration() < 60 ||
                addSongDtoRequest.getComposers() == null ||
                addSongDtoRequest.getWordAuthors() == null)
            throw new MyException(INCORRECT_INPUT_DATA);
    }


}
