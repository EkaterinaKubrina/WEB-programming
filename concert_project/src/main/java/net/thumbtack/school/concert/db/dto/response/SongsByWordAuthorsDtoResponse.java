package net.thumbtack.school.concert.db.dto.response;

import net.thumbtack.school.concert.db.dto.request.SongDtoRequestInternal;

import java.util.Set;

public class SongsByWordAuthorsDtoResponse {
    private Set<SongDtoRequestInternal> songs;

    public SongsByWordAuthorsDtoResponse(Set<SongDtoRequestInternal> songs) {
        this.songs = songs;
    }
}
