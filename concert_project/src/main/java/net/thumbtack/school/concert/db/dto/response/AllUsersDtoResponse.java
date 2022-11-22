package net.thumbtack.school.concert.db.dto.response;

import net.thumbtack.school.concert.db.dto.request.UserDtoRequestInternal;

import java.util.Comparator;
import java.util.List;

public class AllUsersDtoResponse {
    List<UserDtoRequestInternal> users;

    public AllUsersDtoResponse(List<UserDtoRequestInternal> users) {
        users.sort(Comparator.comparing(UserDtoRequestInternal::getLogin));
        this.users = users;
    }

    public List<UserDtoRequestInternal> getUsers() {
        return users;
    }
}
