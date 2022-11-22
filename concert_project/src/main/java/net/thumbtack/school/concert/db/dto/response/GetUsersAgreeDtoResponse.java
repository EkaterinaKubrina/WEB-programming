package net.thumbtack.school.concert.db.dto.response;

import net.thumbtack.school.concert.db.dto.request.UserDtoRequestInternal;

import java.util.List;

public class GetUsersAgreeDtoResponse {
    private List<UserDtoRequestInternal> usersAgree;

    public GetUsersAgreeDtoResponse(List<UserDtoRequestInternal> usersAgree) {
        this.usersAgree = usersAgree;
    }
}
