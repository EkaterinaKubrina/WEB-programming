package net.thumbtack.school.concert.db.mysql.mapper;

import net.thumbtack.school.concert.db.model.Song;
import net.thumbtack.school.concert.db.model.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;


public interface UserMapper {

    @Insert("INSERT INTO users (firstname, lastname, login, password ) VALUES "
            + "( #{user.firstName}, #{user.lastName}, #{user.login}, #{user.password} )")
    @Options(useGeneratedKeys = true, keyProperty = "user.id")
    Integer insertUser(@Param("user") User user);

    @Insert("INSERT INTO sessions (idUser, token ) VALUES "
            + "( #{user.id}, #{token})")
    Integer insert(@Param("user") User user, @Param("token") String token);

    @Select("SELECT id, firstName, lastName, login, password FROM users WHERE login = #{login}")
    User getByLogin(String login);

    @Select("SELECT id, firstName, lastName, login, password FROM users JOIN sessions ON idUser = users.id WHERE token = #{token}")
    User getByToken(String token);

    @Delete("DELETE FROM sessions WHERE token = #{token}")
    Integer logout(String token);

    @Delete("DELETE FROM users WHERE id = #{id}")
    Integer delete(int id);

    @Delete("DELETE FROM users")
    Integer deleteAll();

    @Select("SELECT id, firstName, lastName, login, password FROM users")
    List<User> getAll();

    @Select("SELECT id, firstName, lastName, login, password FROM users JOIN sessions ON idUser = users.id")
    List<User> getAllOnlineUsers();

    @Select("select COUNT(1) from ratings where idSong = #{song.id}")
    Integer getTheCountOfUserRatedTheSong(@Param("song") Song song);

    @Select("select songs.id, songs.name AS nameSong, songs.duration, artists.name AS artist" +
            " FROM songs, artists, songs_artists" +
            " WHERE songs.id = songs_artists.idSong" +
            " AND artists.id = idArtist AND songs.idAuthorProposal = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "wordAuthors", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.concert.db.mysql.mapper.SongMapper.getWordAuthors", fetchType = FetchType.LAZY)),
            @Result(property = "composers", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.concert.db.mysql.mapper.SongMapper.getComposers", fetchType = FetchType.LAZY))
    })
    List<Song> getAllSongsByUser(@Param("id") int id);
}
