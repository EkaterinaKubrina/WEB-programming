package net.thumbtack.school.concert.db.dto.response;

import java.util.List;

public class AddSongDtoResponse {
    private List<Integer> id;

    public AddSongDtoResponse(List<Integer> id) {
        this.id = id;
    }

    public List<Integer> getId() {
        return id;
    }
}
