package net.thumbtack.school.concert.db.dto.response;

import net.thumbtack.school.concert.db.dto.request.SongDtoRequestInternal;

import java.util.Set;

public class SongsByArtistDtoResponse {
    private Set<SongDtoRequestInternal> songs;

    public SongsByArtistDtoResponse(Set<SongDtoRequestInternal> songs) {
        this.songs = songs;
    }
}
