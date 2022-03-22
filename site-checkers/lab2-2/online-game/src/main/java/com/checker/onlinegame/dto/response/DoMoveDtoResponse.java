package com.checker.onlinegame.dto.response;

public class DoMoveDtoResponse {
    private int newA;
    private int newB;
    private int a;
    private int b;
    private int checker;
    private boolean redMove;

    public DoMoveDtoResponse() {
    }


    public boolean isRedMove() {
        return redMove;
    }

    public void setRedMove(boolean redMove) {
        this.redMove = redMove;
    }

    public void setNewA(int newA) {
        this.newA = newA;
    }

    public void setNewB(int newB) {
        this.newB = newB;
    }

    public void setA(int a) {
        this.a = a;
    }

    public void setB(int b) {
        this.b = b;
    }

    public void setChecker(int checker) {
        this.checker = checker;
    }

    public int getNewA() {
        return newA;
    }

    public int getNewB() {
        return newB;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public int getChecker() {
        return checker;
    }
}
