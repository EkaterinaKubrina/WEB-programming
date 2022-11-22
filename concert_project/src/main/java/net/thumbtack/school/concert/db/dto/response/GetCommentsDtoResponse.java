package net.thumbtack.school.concert.db.dto.response;

import java.util.List;

public class GetCommentsDtoResponse {
    List<String> comments;

    public GetCommentsDtoResponse(List<String> comments) {
        this.comments = comments;
    }
}
