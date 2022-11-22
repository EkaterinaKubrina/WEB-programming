package net.thumbtack.school.concert.db.mysql.mapper;

import net.thumbtack.school.concert.db.model.Song;
import net.thumbtack.school.concert.db.model.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface DataMapper {

    @Select("select songs.id, songs.name AS nameSong, songs.duration, artists.name AS artist" +
            " FROM songs, artists, songs_artists" +
            " WHERE songs.id = songs_artists.idSong" +
            " AND artists.id = idArtist ")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "wordAuthors", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.concert.db.mysql.mapper.SongMapper.getWordAuthors", fetchType = FetchType.LAZY)),
            @Result(property = "composers", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.concert.db.mysql.mapper.SongMapper.getComposers", fetchType = FetchType.LAZY))
    })
    List<Song> getAllSongs();

    @Select("SELECT users.id, firstName, lastName, login, password FROM users, songs WHERE idAuthorProposal = users.id and songs.id = #{song.id}")
    User getUserProposal(@Param("song") Song song);

    @Select("select text from comments where idSong = #{song.id} ")
    List<String> getComments(@Param("song") Song song);
}
