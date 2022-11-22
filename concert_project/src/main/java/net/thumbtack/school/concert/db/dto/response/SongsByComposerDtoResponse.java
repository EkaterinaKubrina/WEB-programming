package net.thumbtack.school.concert.db.dto.response;

import net.thumbtack.school.concert.db.dto.request.SongDtoRequestInternal;

import java.util.Set;

public class SongsByComposerDtoResponse {
    private Set<SongDtoRequestInternal> songs;

    public SongsByComposerDtoResponse(Set<SongDtoRequestInternal> songs) {
        this.songs = songs;
    }
}
