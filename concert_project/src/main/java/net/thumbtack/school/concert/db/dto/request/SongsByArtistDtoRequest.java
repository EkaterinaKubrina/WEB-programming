package net.thumbtack.school.concert.db.dto.request;

public class SongsByArtistDtoRequest {
    private String artist;
    private String token;


    public SongsByArtistDtoRequest(String artist, String token)  {
        this.artist = artist;
        this.token = token;
    }

    public String getArtist() {
        return artist;
    }

    public String getToken() {
        return token;
    }

}
