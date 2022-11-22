package net.thumbtack.school.concert.db.dto.response;

public class EmptyDataDtoResponse {
    private boolean emptyData;

    public EmptyDataDtoResponse(boolean emptyData) {
        this.emptyData = emptyData;
    }

    public boolean isEmptyData() {
        return emptyData;
    }
}
