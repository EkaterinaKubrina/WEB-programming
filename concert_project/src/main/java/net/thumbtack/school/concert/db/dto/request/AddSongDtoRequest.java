package net.thumbtack.school.concert.db.dto.request;


import java.util.List;

public class AddSongDtoRequest {
    List<SongDtoRequestInternal> songs;
    private String token;

    public AddSongDtoRequest(List<SongDtoRequestInternal> songs, String token)  {
        this.songs = songs;
        this.token = token;
    }

    public List<SongDtoRequestInternal> getSongs() {
        return songs;
    }

    public String getToken() {
        return token;
    }
}
