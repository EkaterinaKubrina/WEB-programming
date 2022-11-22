package net.thumbtack.school.concert.resources;


import net.thumbtack.school.concert.db.mysql.daoimpl.DataDaoImpl;
import net.thumbtack.school.concert.db.mysql.daoimpl.SongDaoImpl;
import net.thumbtack.school.concert.db.mysql.daoimpl.UserDaoImpl;
import net.thumbtack.school.concert.db.service.DataService;
import net.thumbtack.school.concert.db.service.SongService;
import net.thumbtack.school.concert.db.service.UserService;
import net.thumbtack.school.concert.settings.Mode;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/api")
public class ConcertResource {
    private static UserService userService;
    private static SongService songService;
    private static DataService dataService;

    public static void setMode(Mode mode) {
        if (mode == Mode.SQL) {
            userService = new UserService(new UserDaoImpl());
            songService = new SongService(new SongDaoImpl());
            dataService = new DataService(new DataDaoImpl());
        } else if (mode == Mode.RAM){
            userService = new UserService(new net.thumbtack.school.concert.db.mydb.daoimplmydata.UserDaoImpl());
            songService = new SongService(new net.thumbtack.school.concert.db.mydb.daoimplmydata.SongDaoImpl());
            dataService = new DataService(new net.thumbtack.school.concert.db.mydb.daoimplmydata.DataDaoImpl());
        }
    }

    public ConcertResource() {

    }

    @DELETE
    @Path("/data")
    @Produces("application/json")
    public Response clearServer() {
        return dataService.clearServer();
    }

    @GET
    @Path("/data")
    @Produces("application/json")
    public Response emptyData() {
        return dataService.emptyData();
    }

    @GET
    @Path("/data/pilot")
    @Produces("application/json")
    public Response pilotProgramOfConcert(@HeaderParam("token") String token) {
        return dataService.pilotProgramOfConcert(token);
    }

    @POST
    @Path("/user")
    @Consumes("application/json")
    @Produces("application/json")
    public Response registerUser(String json) {
        return userService.registerUser(json);
    }

    @DELETE
    @Path("/user")
    @Produces("application/json")
    public Response delete(@HeaderParam("token") String token) {
        return userService.delete(token);
    }

    @GET
    @Path("/user")
    @Produces("application/json")
    public Response getAllUsers() {
        return userService.getAllUsers();
    }

    @POST
    @Path("/user/session")
    @Consumes("application/json")
    @Produces("application/json")
    public Response login(String json) {
        return userService.login(json);
    }

    @DELETE
    @Path("/user/session")
    @Produces("application/json")
    public Response logout(@HeaderParam("token") String token) {
        return userService.logout(token);
    }


    @GET
    @Path("/user/session")
    @Produces("application/json")
    public Response getAllOnlineUsers() {
        return userService.getAllOnlineUsers();
    }


    @POST
    @Path("/song")
    @Consumes("application/json")
    @Produces("application/json")
    public Response addSong(String json) {
        return songService.addSong(json);
    }

    @DELETE
    @Path("/song/{id}")
    @Produces("application/json")
    public Response cancelSong(@PathParam("id") Integer id, @HeaderParam("token") String token) {
        return songService.cancelSong(token, id);
    }

    @GET
    @Path("/song")
    @Produces("application/json")
    public Response getAllSongs(@HeaderParam("token") String token) {
        return songService.getAllSongs(token);
    }


    @GET
    @Path("/song/composer/each")
    @Produces("application/json")
    public Response getAllSongsByEachComposers(@HeaderParam("token") String token, @QueryParam("list") List<String> list) {
        return songService.getAllSongsByEachComposers(token, list);
    }

    @GET
    @Path("/song/composer")
    @Produces("application/json")
    public Response getAllSongsByComposers(@HeaderParam("token") String token, @QueryParam("list") List<String> list) {
        return songService.getSongsByComposer(token, list);
    }

    @GET
    @Path("/song/wordAuthors")
    @Produces("application/json")
    public Response getSongsByWordAuthors(@HeaderParam("token") String token, @QueryParam("list") List<String> list) {
        return songService.getSongsByWordAuthors(token, list);
    }

    @GET
    @Path("/song/wordAuthors/each")
    @Produces("application/json")
    public Response getAllSongsByEachWordAuthors(@HeaderParam("token") String token, @QueryParam("list") List<String> list) {
        return songService.getAllSongsByEachWordAuthors(token, list);
    }

    @GET
    @Path("/song/artist")
    @Produces("application/json")
    public Response getSongsByArtist(@HeaderParam("token") String token, @QueryParam("artist") String artist) {
        return songService.getSongsByArtist(token, artist);
    }


    @POST
    @Path("/song/rate")
    @Consumes("application/json")
    @Produces("application/json")
    public Response rateSong(String json) {
        return songService.rateSong(json);
    }


    @DELETE
    @Path("/song/rate/{id}")
    @Produces("application/json")
    public Response deleteRatingSong(@PathParam("id") Integer id, @HeaderParam("token") String token) {
        return songService.deleteRatingSong(id, token);
    }


    @PUT
    @Path("/song/rate")
    @Consumes("application/json")
    @Produces("application/json")
    public Response setRatingSong(@HeaderParam("token") String token) {
        return songService.setRatingSong(token);
    }


    @GET
    @Path("/song/rate/{id}")
    @Produces("application/json")
    public Response averageRatingOfSongs(@PathParam("id") Integer id, @HeaderParam("token") String token) {
        return songService.averageRatingOfSongs(id, token);
    }

    @POST
    @Path("/song/comment")
    @Consumes("application/json")
    @Produces("application/json")
    public Response addComment(String json) {
        return songService.addComment(json);
    }


    @DELETE
    @Path("/song/comment/{id}")
    @Produces("application/json")
    public Response deleteComment(@PathParam("id") Integer id, @HeaderParam("token") String token, @QueryParam("comment") String comment) {
        return songService.deleteComment(id, token, comment);
    }


    @PUT
    @Path("/song/comment")
    @Consumes("application/json")
    @Produces("application/json")
    public Response setComment(String json) {
        return songService.setComment(json);
    }


    @GET
    @Path("/song/comment/{id}")
    @Produces("application/json")
    public Response getComments(@PathParam("id") Integer id, @HeaderParam("token") String token) {
        return songService.getComments(id, token);
    }


    @POST
    @Path("/song/comment/users_agree")
    @Consumes("application/json")
    @Produces("application/json")
    public Response addUserAgreeWithComment(String json) {
        return songService.addUserAgreeWithComment(json);
    }

    @DELETE
    @Path("/song/comment/users_agree/{id}")
    @Produces("application/json")
    public Response deleteUserAgreeWithComment(@PathParam("id") Integer id, @HeaderParam("token") String token, @QueryParam("comment") String comment) {
        return songService.deleteUserAgreeWithComment(id, token, comment);
    }

    @GET
    @Path("/song/comment/users_agree/{id}")
    @Produces("application/json")
    public Response getUsersAgreeWithComment(@PathParam("id") Integer id, @HeaderParam("token") String token, @QueryParam("comment") String comment) {
        return songService.getUsersAgreeWithComment(id, token, comment);
    }


}
