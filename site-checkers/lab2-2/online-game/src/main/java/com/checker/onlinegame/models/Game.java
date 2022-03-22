package com.checker.onlinegame.models;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int status;
    private String records;
    private int[][] board;

    private ArrayList<Integer> checkerKill;
    private boolean oneMoveFlag;
    private int nextMove;
    private int num;


    public Game(int id, int status, String records, int[][] board, boolean redFlag, ArrayList<Integer> checkerKill, boolean oneMoveFlag, int nextMove, int num) {
        this.id = id;
        this.status = status;
        this.records = records;
        this.board = board;
        this.checkerKill = checkerKill;
        this.oneMoveFlag = oneMoveFlag;
        this.nextMove = nextMove;
        this.num = num;
    }

    public Game(int status, String records, int[][] board) {
        this.status = status;
        this.records = records;
        this.board = board;
    }

    public int getId() {
        return id;
    }

    public String getRecords() {
        return records;
    }

    public ArrayList<Integer> getCheckerKill() {
        return checkerKill;
    }

    public int getStatus() {
        return status;
    }

    public int[][] getBoard() {
        return board;
    }

    public int getNextMove() {
        return nextMove;
    }

    public int getNum() {
        return num;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public void newGame(int mode) {
        checkerKill = new ArrayList<>();
        oneMoveFlag = false;
        num = 1;
        if (mode == 1) {
            nextMove = 1;
            records = "1.";
        } else {
            nextMove = 2;
            records = "1.x-x ";
        }
        checkRed();
    }

    public int endGame() {
        StringBuilder text = new StringBuilder();
        if (nextMove == 1) {
            text.append("\n").append("Белые сдались");
            System.out.println("Game -" + id + ": " + "Белые сдались");
        } else {
            text.append("\n").append("Черные сдались");
            System.out.println("Game -" + id + ": " + "Черные сдались");
        }
        records = records + text;
        nextMove = 0;
        status = 0;
        return 0;
    }

    public void checkRed() { //обновляет список шашек, которые должны побить в этом ходе
        if (status != 0) {
            checkerKill.clear();

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (board[i][j] == nextMove || board[i][j] == nextMove + 2) {
                        if (canMoveNoPrompt(i, j)) {
                            checkerKill.add(i);
                            checkerKill.add(j);
                        }
                    }
                }
            }
        }
    }

    public boolean canMoveNoPrompt(int a, int b) { //проверяет, что шашка может бить

        boolean localRedFlag = false;

        if (status != 0) {

            int aPlus = a + 1;
            int aMinus = a - 1;
            int bPlus = b + 1;
            int bMinus = b - 1;

            while (aMinus > -1) {
                if (bMinus > -1) {
                    if (board[aMinus][bMinus] != 0 && (aMinus - 1 > -1 && bMinus - 1 > -1)) {
                        if (!equals(aMinus, bMinus, a, b) && board[aMinus - 1][bMinus - 1] == 0) {
                            localRedFlag = true;
                        }
                        bMinus = -2;
                    }
                }
                if (bPlus < 8) {
                    if (board[aMinus][bPlus] != 0 && (aMinus - 1 > -1 && bPlus + 1 < 8)) {
                        if (!equals(aMinus, bPlus, a, b) && board[aMinus - 1][bPlus + 1] == 0) {
                            localRedFlag = true;
                        }
                        bPlus = 9;
                    }
                }
                if (board[a][b] > 2) {
                    aMinus--;
                    bMinus--;
                    bPlus++;
                } else {
                    aMinus = -2;
                }
            }

            bPlus = b + 1;
            bMinus = b - 1;

            while (aPlus < 8) {
                if (bMinus > -1) {
                    if (board[aPlus][bMinus] != 0 && (aPlus + 1 < 8 && bMinus - 1 > -1)) {
                        if (!equals(aPlus, bMinus, a, b) && board[aPlus + 1][bMinus - 1] == 0) {
                            localRedFlag = true;
                        }
                        bMinus = -2;
                    }
                }
                if (bPlus < 8) {
                    if (board[aPlus][bPlus] != 0 && (aPlus + 1 < 8 && bPlus + 1 < 8)) {
                        if (!equals(aPlus, bPlus, a, b) && board[aPlus + 1][bPlus + 1] == 0) {
                            localRedFlag = true;
                        }
                        bPlus = 9;
                    }
                }
                if (board[a][b] > 2) {
                    aPlus++;
                    bPlus++;
                    bMinus--;
                } else {
                    aPlus = 9;
                }
            }
        }
        return localRedFlag;
    }

    public boolean equals(int a, int b, int a2, int b2) { //проверяет что шашки одинокового цвета
        if (board[a][b] == 1 || board[a][b] == 3) {
            return board[a2][b2] == 1 || board[a2][b2] == 3;
        } else return board[a2][b2] == 2 || board[a2][b2] == 4;
    }

    public String getAvailableMoves(int a, int b) { //функция вовзращает доступные поля для хода
        StringBuilder availableMoves = new StringBuilder();

        if (status != 0) {
            int aPlus = a + 1;
            int aMinus = a - 1;
            int bPlus = b + 1;
            int bMinus = b - 1;
            while (aMinus > -1) {
                if (bMinus > -1) {
                    if (board[aMinus][bMinus] == 0) {
                        if (board[a][b] != 1) {
                            availableMoves.append(aMinus);
                            availableMoves.append(bMinus);
                        }
                    }
                }
                if (bPlus < 8) {
                    if (board[aMinus][bPlus] == 0) {
                        if (board[a][b] != 2) {
                            availableMoves.append(aMinus);
                            availableMoves.append(bPlus);
                        }
                    }

                }

                if (board[a][b] > 2) {
                    aMinus--;
                    bMinus--;
                    bPlus++;
                } else {
                    aMinus = -2;
                }
            }

            bPlus = b + 1;
            bMinus = b - 1;

            while (aPlus < 8) {
                if (bMinus > -1) {
                    if (board[aPlus][bMinus] == 0) {
                        if (board[a][b] != 1) {
                            availableMoves.append(aPlus);
                            availableMoves.append(bMinus);
                        }
                    }
                }
                if (bPlus < 8) {
                    if (board[aPlus][bPlus] == 0) {
                        if (board[a][b] != 2) {
                            availableMoves.append(aPlus);
                            availableMoves.append(bPlus);
                        }
                    }
                }
                if (board[a][b] > 2) {
                    aPlus++;
                    bPlus++;
                    bMinus--;
                } else {
                    aPlus = 9;
                }
            }
        }
        return availableMoves.toString();
    }

    public String getAvailableRedMove(int a, int b) { //отмечает красные клетки
        StringBuilder availableMoves = new StringBuilder();

        if (status != 0) {
            int aPlus = a + 1;
            int aMinus = a - 1;
            int bPlus = b + 1;
            int bMinus = b - 1;

            while (aMinus > -1) {
                if (bMinus > -1) {
                    if (board[aMinus][bMinus] != 0 && (aMinus - 1 > -1 && bMinus - 1 > -1)) {
                        if (!equals(aMinus, bMinus, a, b) && board[aMinus - 1][bMinus - 1] == 0) {
                            availableMoves.append(aMinus - 1);
                            availableMoves.append(bMinus - 1);
                            if (board[a][b] > 2 && (aMinus - 2 > -1 && bMinus - 2 > -1)) {
                                int i = 2;
                                while (aMinus - i > -1 && bMinus - i > -1) {
                                    if (board[aMinus - i][bMinus - i] == 0) {
                                        availableMoves.append(aMinus - i);
                                        availableMoves.append(bMinus - i);
                                        i++;
                                    } else {
                                        break;
                                    }
                                }
                            }
                        }
                        bMinus = -2;
                    }
                }
                if (bPlus < 8) {
                    if (board[aMinus][bPlus] != 0 && (aMinus - 1 > -1 && bPlus + 1 < 8)) {
                        if (!equals(aMinus, bPlus, a, b) && board[aMinus - 1][bPlus + 1] == 0) {
                            availableMoves.append(aMinus - 1);
                            availableMoves.append(bPlus + 1);
                            if (board[a][b] > 2 && (bPlus + 2 < 8 && aMinus - 2 > -1)) {
                                int i = 2;
                                while (bPlus + i < 8 && aMinus - i > -1) {
                                    if (board[aMinus - i][bPlus + i] == 0) {
                                        availableMoves.append(aMinus - i);
                                        availableMoves.append(bPlus + i);
                                        i++;
                                    } else {
                                        break;
                                    }
                                }
                            }
                        }
                        bPlus = 9;
                    }
                }

                if (board[a][b] > 2) {
                    aMinus--;
                    bMinus--;
                    bPlus++;
                } else {
                    aMinus = -2;
                }
            }

            bPlus = b + 1;
            bMinus = b - 1;

            while (aPlus < 8) {
                if (bMinus > -1) {
                    if (board[aPlus][bMinus] != 0 && (aPlus + 1 < 8 && bMinus - 1 > -1)) {
                        if (!equals(aPlus, bMinus, a, b) && board[aPlus + 1][bMinus - 1] == 0) {
                            availableMoves.append(aPlus + 1);
                            availableMoves.append(bMinus - 1);
                            if (board[a][b] > 2 && (aPlus + 2 < 8 && bMinus - 2 > -1)) {
                                int i = 2;
                                while (aPlus + i < 8 && bMinus - i > -1) {
                                    if (board[aPlus + i][bMinus - i] == 0) {
                                        availableMoves.append(aPlus + i);
                                        availableMoves.append(bMinus - i);
                                        i++;
                                    } else {
                                        break;
                                    }
                                }
                            }
                        }
                        bMinus = -2;
                    }
                }
                if (bPlus < 8) {
                    if (board[aPlus][bPlus] != 0 && (aPlus + 1 < 8 && bPlus + 1 < 8)) {
                        if (!equals(aPlus, bPlus, a, b) && board[aPlus + 1][bPlus + 1] == 0) {
                            availableMoves.append(aPlus + 1);
                            availableMoves.append(bPlus + 1);
                            if (board[a][b] > 2 && (aPlus + 2 < 8 && bPlus + 2 < 8)) {
                                int i = 2;
                                while (aPlus + i < 8 && bPlus + i < 8) {
                                    if (board[aPlus + i][bPlus + i] == 0) {
                                        availableMoves.append(aPlus + i);
                                        availableMoves.append(bPlus + i);
                                        i++;
                                    } else {
                                        break;
                                    }

                                }
                            }
                        }
                        bPlus = 9;
                    }
                }
                if (board[a][b] > 2) {
                    aPlus++;
                    bPlus++;
                    bMinus--;
                } else {
                    aPlus = 9;
                }
            }
        }
        return availableMoves.toString();
    }

    public void simpleMove(int newA, int newB, int a, int b, int checker) {
        if (checker == 1 || checker == 3) {
            if (newB != 7 && checker == 1) {
                board[newA][newB] = 1;
            } else {
                board[newA][newB] = 3;
            }
        } else {
            if (newB != 0 && checker == 2) {
                board[newA][newB] = 2;
            } else {
                board[newA][newB] = 4;
            }
        }
        board[a][b] = 0;
    }

    public void redMove(int newA, int newB, int a, int b, int checker) {
        int i = 1;
        if (a < newA) {
            if (b < newB) {
                while (board[a + i][b + i] == 0) {
                    i++;
                }
                board[a + i][b + i] = 0;
            } else {
                while (board[a + i][b - i] == 0) {
                    i++;
                }
                board[a + i][b - i] = 0;
            }

        } else {
            if (b < newB) {
                while (board[a - i][b + i] == 0) {
                    i++;
                }
                board[a - i][b + i] = 0;
            } else {
                while (board[a - i][b - i] == 0) {
                    i++;
                }
                board[a - i][b - i] = 0;
            }

        }

        simpleMove(newA, newB, a, b, checker);
    }

    public int doMove(int newA, int newB, int a, int b, int checker, boolean redMove) {
        if (redMove) {
            redMove(newA, newB, a, b, checker);
        } else {
            simpleMove(newA, newB, a, b, checker);
        }

        if (redMove && canMoveNoPrompt(newA, newB)) {
            oneMoveFlag = true;
            addMove( a, b, newA, newB, true);
            checkerKill.clear();
            checkerKill.add(newA);
            checkerKill.add(newB);
            return 2;
        } else {
            oneMoveFlag = false;
            addMove(a, b, newA, newB, redMove);
            if (nextMove == 1) {
                nextMove = 2;
            } else {
                nextMove = 1;
            }
            checkRed();
        }
        if (checkLoss() == 0) {
            return 1;
        }
        return 0;
    }

    public int checkLoss() { // 1- белые проиграли, 2 - черные, 0 - игра идет
        if (!Arrays.deepToString(board).contains("1") && !Arrays.deepToString(board).contains("3")) {
            nextMove = 0;
            status = 0;
            return 1;
        } else if (!Arrays.deepToString(board).contains("2") && !Arrays.deepToString(board).contains("4")) {
            nextMove = 0;
            status = 0;
            return 2;
        }
        return 0;
    }

    public void addMove(int fromA, int fromB, int toA, int toB, boolean redMove) {//добавляет ход в запись ходов
        StringBuilder text = new StringBuilder();
        if (oneMoveFlag) {
            text.append(latterOfNum(fromA)).append(fromB + 1);
            text.append(":");
            records = records + text;
        } else {
            if (nextMove == 1 || nextMove == 3) {
                text.append(latterOfNum(fromA)).append(fromB + 1);
                if (redMove) {
                    text.append(":");
                } else {
                    text.append("-");
                }
                text.append(latterOfNum(toA)).append(toB + 1).append(" ");
                System.out.println("Game -" + id + ": " + text);
                records = records + text;
            } else {
                text.append(latterOfNum(fromA)).append(fromB + 1);
                if (redMove) {
                    text.append(":");
                } else {
                    text.append("-");
                }
                text.append(latterOfNum(toA)).append(toB + 1);

                System.out.println("Game -" + id + ": " + text);

                int check = checkLoss();
                if (check == 0) {
                    num++;
                    text.append("\n").append(num).append(".");
                } else if (check == 1) {
                    text.append("\n").append("Белые сдались");
                    System.out.println("Game -" + id + ": " + "Белые сдались");
                } else {
                    text.append("\n").append("Черные сдались");
                    System.out.println("Game -" + id + ": " + "Черные сдались");
                }
                records = records + text;
            }
        }
    }

    public char latterOfNum(int x) {
        if (x == 0) {
            return 'a';
        } else if (x == 1) {
            return 'b';
        } else if (x == 2) {
            return 'c';
        } else if (x == 3) {
            return 'd';
        } else if (x == 4) {
            return 'e';
        } else if (x == 5) {
            return 'f';
        } else if (x == 6) {
            return 'g';
        }
        return 'h';

    }


}
