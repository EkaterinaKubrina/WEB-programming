package net.thumbtack.school.concert.db.mysql.mapper;

import net.thumbtack.school.concert.db.model.Song;
import net.thumbtack.school.concert.db.model.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;
import java.util.Set;

public interface SongMapper {

    @Insert("INSERT INTO songs (name, duration, idAuthorProposal) VALUES "
            + "( #{song.songName}, #{song.duration}, #{user.id} )")
    @Options(useGeneratedKeys = true, keyProperty = "song.id")
    Integer insertSong(@Param("song") Song song, @Param("user") User user);


    @Insert("INSERT INTO songs_wordAuthors (idSong, idWordAuthor) VALUES ( #{song.id}, LAST_INSERT_ID())")
    Integer insert_songsWordAuthors(@Param("song") Song song);


    @Insert("INSERT INTO wordAuthors (name) VALUES ( #{nameWA} )")
    Integer insertWordAuthors(@Param("nameWA") String nameWA);


    @Insert("INSERT INTO artists (name) VALUES (#{nameA})")
    Integer insertArtists(@Param("nameA") String nameA);


    @Insert("INSERT INTO songs_artists (idSong, idArtist) VALUES ( #{song.id}, LAST_INSERT_ID())")
    Integer insert_songsArtists(@Param("song") Song song);


    @Insert("INSERT INTO composers (name) VALUES ( #{nameC} )")
    Integer insertComposers(@Param("nameC") String nameC);


    @Insert("INSERT INTO songs_composers (idSong, idComposer) VALUES ( #{song.id}, LAST_INSERT_ID())")
    Integer insert_songsComposers(@Param("song") Song song);


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


    @Select("select id, name, idAuthorProposal from songs where songs.name = #{song.songName} AND songs.duration = #{song.duration}")
    Song getSong(@Param("song") Song song);


    @Select("select id, name, idAuthorProposal from songs where songs.id = #{id}")
    Song getSongById(@Param("id") int id);


    @Select("select songs.id, songs.name AS nameSong, songs.duration, artists.name AS artist" +
            " FROM songs, artists, songs_artists, composers, songs_composers" +
            " WHERE songs.id = songs_artists.idSong" +
            " AND artists.id = idArtist AND " +
            " songs.id = songs_composers.idSong AND" +
            " composers.id = idComposer AND" +
            " composers.name = #{composer}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "wordAuthors", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.concert.db.mysql.mapper.SongMapper.getWordAuthors", fetchType = FetchType.LAZY)),
            @Result(property = "composers", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.concert.db.mysql.mapper.SongMapper.getComposers", fetchType = FetchType.LAZY))
    })
    Set<Song> getSongsByComposers(@Param("composer") String composer);


    @Select("select songs.id, songs.name AS nameSong, songs.duration, artists.name AS artist" +
            " FROM songs, artists, songs_artists, wordAuthors, songs_wordAuthors" +
            " WHERE songs.id = songs_artists.idSong" +
            " AND artists.id = idArtist AND " +
            " songs.id = songs_wordAuthors.idSong AND" +
            " wordAuthors.id = idWordAuthor AND" +
            " wordAuthors.name = #{wordAuthor}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "wordAuthors", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.concert.db.mysql.mapper.SongMapper.getWordAuthors", fetchType = FetchType.LAZY)),
            @Result(property = "composers", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.concert.db.mysql.mapper.SongMapper.getComposers", fetchType = FetchType.LAZY))
    })
    Set<Song> getSongsByWordAuthors(@Param("wordAuthor") String wordAuthor);


    @Select("select songs.id, songs.name AS nameSong, songs.duration, artists.name AS artist" +
            " FROM songs, artists, songs_artists" +
            " WHERE songs.id = songs_artists.idSong" +
            " AND artists.id = idArtist AND " +
            " artists.name = #{artist}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "wordAuthors", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.concert.db.mysql.mapper.SongMapper.getWordAuthors", fetchType = FetchType.LAZY)),
            @Result(property = "composers", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.concert.db.mysql.mapper.SongMapper.getComposers", fetchType = FetchType.LAZY))
    })
    Set<Song> getSongsByArtist(@Param("artist") String artist);


    @Insert("INSERT INTO ratings (idSong, idUser, rating) VALUES "
            + "( #{song.id}, #{user.id}, #{rate} )")
    Integer insertRate(@Param("song") Song song, @Param("user") User user, @Param("rate") int rate);


    @Select("select rating from ratings where idSong = #{song.id} AND idUser = #{user.id}")
    Object getRate(@Param("song") Song song, @Param("user") User user);


    @Delete("DELETE FROM songs")
    Integer deleteAll();


    @Delete("DELETE FROM songs where songs.id = #{id}")
    Integer cancelSong(@Param("id") int id);


    @Delete("DELETE FROM ratings where idSong = #{song.id} AND idUser = #{user.id}")
    Integer deleteRate(@Param("song") Song song, @Param("user") User user);


    @Select("select name from wordAuthors, songs_wordAuthors where idSong = #{song.id} AND wordAuthors.id = idWordAuthor")
    List<String> getWordAuthors(@Param("song") Song song);


    @Select("select name from composers, songs_composers where idSong = #{song.id} AND composers.id = idComposer")
    List<String> getComposers(@Param("song") Song song);


    @Select("select SUM(rating) FROM ratings WHERE idSong = #{song.id}")
    Integer getSumRating(@Param("song") Song song);

    @Select("select id FROM comments WHERE text = #{comment} and idSong = #{song.id}")
    Object getCommentId(@Param("comment") String comment, @Param("song") Song song);

    @Insert("INSERT INTO comments (idSong, text, idAuthorProposal) VALUES "
            + "( #{song.id}, #{comment}, #{user.id} )")
    Integer insertComment(@Param("comment") String comment, @Param("song") Song song, @Param("user") User user);

    @Delete("DELETE FROM comments where id = #{id} and idAuthorProposal = #{user.id}")
    Integer deleteComment(@Param("id") int id, @Param("user") User user);

    @Update("UPDATE comments SET text = #{comment} WHERE id = #{id} and idAuthorProposal = #{user.id}")
    Integer updateComment(@Param("id") int id, @Param("comment") String comment, @Param("user") User user);

    @Update("UPDATE comments SET idAuthorProposal = NULL WHERE id = #{id} and idAuthorProposal = #{user.id}")
    Integer updateAuthorComment(@Param("id") int id, @Param("user") User user);

    @Insert("INSERT INTO userAgree (idComment, idUser) VALUES "
            + "( #{id}, #{user.id})")
    Integer insertUserAgreeWithComments(@Param("id") int id, @Param("user") User user);

    @Delete("DELETE FROM userAgree where idComment = #{id} and idUser = #{user.id}")
    Integer deleteUserAgreeWithComments(@Param("id") int id, @Param("user") User user);


    @Select("select text from comments where idSong = #{song.id} ")
    List<String> getComments(@Param("song") Song song);


    @Select("select firstName, lastName, login, password from users, userAgree where idComment = #{id} and users.id = idUser")
    List<User> getUsersAgreeWithComment(@Param("id") int id);

}
