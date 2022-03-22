package com.checker.onlinegame;

import com.checker.onlinegame.models.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.*;


public class GameTests {

    private static Game game;
    private static Game gameExample;

    @BeforeEach
    public void play() {
        game = new Game(1, "1.", new int[][]{{1, 0, 1, 0, 0, 0, 2, 0},
                {0, 1, 0, 0, 0, 2, 0, 2},
                {1, 0, 1, 0, 0, 0, 2, 0},
                {0, 1, 0, 0, 0, 2, 0, 2},
                {1, 0, 1, 0, 0, 0, 2, 0},
                {0, 1, 0, 0, 0, 2, 0, 2},
                {1, 0, 1, 0, 0, 0, 2, 0},
                {0, 1, 0, 0, 0, 2, 0, 2},
        });
        game.newGame(1);

        gameExample = new Game(1, "1.", new int[][]{{0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 2},
                {4, 0, 0, 0, 2, 0, 2, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 2, 0},
                {0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 2, 0, 0},
        });
        gameExample.newGame(2);
    }

    @Test
    public void testNewGame() {
        assertEquals(new ArrayList<>(), game.getCheckerKill());
        assertEquals(1, game.getNextMove());
        assertEquals(1, game.getNum());
        assertEquals(1, game.getStatus());
        assertArrayEquals(new int[][]{{1, 0, 1, 0, 0, 0, 2, 0},
                {0, 1, 0, 0, 0, 2, 0, 2},
                {1, 0, 1, 0, 0, 0, 2, 0},
                {0, 1, 0, 0, 0, 2, 0, 2},
                {1, 0, 1, 0, 0, 0, 2, 0},
                {0, 1, 0, 0, 0, 2, 0, 2},
                {1, 0, 1, 0, 0, 0, 2, 0},
                {0, 1, 0, 0, 0, 2, 0, 2}}, game.getBoard());
    }

    @Test
    public void testEndGameWhite() {
        game.endGame();

        assertEquals(0, game.getNextMove());
        assertEquals(0, game.getStatus());
        assertEquals("1.\nБелые сдались", game.getRecords());
    }


    @Test
    public void testEndGameBlack() {
        gameExample.endGame();

        assertEquals(0, gameExample.getNextMove());
        assertEquals(0, gameExample.getStatus());
        assertEquals("1.x-x \nЧерные сдались", gameExample.getRecords());
    }

    @Test
    public void testCheckRed() {
        gameExample.checkRed();

        ArrayList<Integer> list = new ArrayList<>();
        list.add(2); //координаты дамки в примере
        list.add(0);

        assertEquals(list, gameExample.getCheckerKill());
    }

    @Test
    public void testCanMove() {
        gameExample.checkRed();

        assertTrue(gameExample.canMoveNoPrompt(2, 0)); //координаты дамки в примере
    }


    @Test
    public void testAvailableMove() {
        assertEquals("13", game.getAvailableMoves(0, 2)); //a3 может пойти в b4
        assertEquals("", game.getAvailableMoves(1, 1)); //b2 никуда
        assertEquals("1333", game.getAvailableMoves(2, 2)); //c3 может пойти в b4 и d4
    }

    @Test
    public void testAvailableMoveRed() {
        gameExample.getAvailableRedMove(2, 0);
        assertEquals("64", gameExample.getAvailableRedMove(2, 0)); //Дамка из примера может сделать красный ход только на g5
    }

    @Test
    public void testSimpleMove() {
        assertEquals("13", game.getAvailableMoves(0, 2)); //a3 может пойти в b4

        game.doMove(1, 3, 0, 2, 1, false);

        assertEquals(new ArrayList<>(), game.getCheckerKill());
        assertEquals(2, game.getNextMove());
        assertEquals(1, game.getNum());
        assertEquals(1, game.getStatus());
        assertArrayEquals(new int[][]{{1, 0, 0, 0, 0, 0, 2, 0},
                {0, 1, 0, 1, 0, 2, 0, 2},
                {1, 0, 1, 0, 0, 0, 2, 0},
                {0, 1, 0, 0, 0, 2, 0, 2},
                {1, 0, 1, 0, 0, 0, 2, 0},
                {0, 1, 0, 0, 0, 2, 0, 2},
                {1, 0, 1, 0, 0, 0, 2, 0},
                {0, 1, 0, 0, 0, 2, 0, 2}}, game.getBoard());
    }


    @Test
    public void testRedMove() {
        assertEquals("64", gameExample.getAvailableRedMove(2, 0)); //Дамка из примера может сделать красный ход только на g5

        gameExample.doMove(6, 4, 2, 0, 4, true);
        gameExample.checkRed();

        ArrayList<Integer> list = new ArrayList<>();
        list.add(7); //белая шашка на h4
        list.add(3);

        assertEquals(list, gameExample.getCheckerKill());
        assertEquals(1, gameExample.getNextMove());
        assertEquals(2, gameExample.getNum());
        assertEquals(1, gameExample.getStatus());
        assertArrayEquals(new int[][]{{0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 2},
                {0, 0, 0, 0, 2, 0, 2, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 2, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 4, 0, 0, 0},
                {0, 0, 0, 1, 0, 2, 0, 0}}, gameExample.getBoard());
    }

    @Test
    public void testCheckLoss() {
        assertEquals(0, gameExample.checkLoss());
        assertEquals(0, game.checkLoss());
    }

}
