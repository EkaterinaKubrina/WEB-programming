package net.thumbtack.school.concert.db.dto.response;

import net.thumbtack.school.concert.db.dto.request.UserDtoRequestInternal;

import java.util.List;

public class AllOnlineUsersDtoResponse {
    List<UserDtoRequestInternal> users;

    public AllOnlineUsersDtoResponse(List<UserDtoRequestInternal> users) {
        this.users = users;
    }
}
