package net.thumbtack.school.concert.db.dto.response;

import net.thumbtack.school.concert.db.dto.request.SongDtoRequestInternal;

import java.util.List;

public class AllSongsDtoResponse {
    private List<SongDtoRequestInternal> songs;

    public AllSongsDtoResponse(List<SongDtoRequestInternal> songs) {
        this.songs = songs;
    }
}
