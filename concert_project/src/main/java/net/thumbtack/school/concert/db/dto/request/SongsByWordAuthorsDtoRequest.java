package net.thumbtack.school.concert.db.dto.request;


import java.util.List;

public class SongsByWordAuthorsDtoRequest {
    private List<String> wordAuthors;
    private String token;

    public SongsByWordAuthorsDtoRequest(List<String> wordAuthors, String token) {
        this.wordAuthors = wordAuthors;
        this.token = token;
    }

    public List<String> getWordAuthors() {
        return wordAuthors;
    }

    public String getToken() {
        return token;
    }

}
