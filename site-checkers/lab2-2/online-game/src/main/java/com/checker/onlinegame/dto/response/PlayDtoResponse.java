package com.checker.onlinegame.dto.response;

public class PlayDtoResponse {
    private int[][] board;
    private String records;
    private int status;

    public PlayDtoResponse() {
    }

    public PlayDtoResponse(int[][] board, String records, int status) {
        this.board = board;
        this.records = records;
        this.status = status;
    }

    public int[][] getBoard() {
        return board;
    }

    public String getBoardS() {

        return String.valueOf(board[2][1]);
    }

    public String getRecords() {
        return records;
    }

    public int getStatus() {
        return status;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public void setRecords(String records) {
        this.records = records;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
