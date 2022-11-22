package net.thumbtack.school.concert.db.dto.request;


import java.util.List;

public class SongsByComposerDtoRequest {
    private List<String> composers;
    private String token;

    public SongsByComposerDtoRequest(List<String> composers, String token) {
        this.composers = composers;
        this.token = token;
    }

    public List<String> getComposers() {
        return composers;
    }

    public String getToken() {
        return token;
    }

}
