package com.checker.onlinegame.controllers;

import com.checker.onlinegame.dto.response.DoMoveDtoResponse;
import com.checker.onlinegame.dto.response.PlayDtoResponse;
import com.checker.onlinegame.models.Game;
import com.checker.onlinegame.repositories.GameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
public class GameController {
    private static final Logger log = LoggerFactory.getLogger(GameController.class);
    @Autowired
    private GameRepository gameRepository;

    @PostMapping("/play") //создать игру
    @ResponseStatus(HttpStatus.CREATED)
    public String play(@RequestBody PlayDtoResponse test, @RequestParam int mode) {
        Game game = new Game(test.getStatus(), test.getRecords(), test.getBoard());
        game.newGame(mode);
        gameRepository.save(game);
        log.info("Партия №" + game.getId() + ": Создана новая партия. ");
        return String.valueOf(game.getId());
    }


    @PutMapping("/play/{id}") //Завершить игру
    public int endGame(@PathVariable int id){
        Game g = gameRepository.getById(id);
        int ans = g.endGame();
        gameRepository.save(g);
        log.info("Партия №" + g.getId() + ": Партия окончена. ");
        return ans;
    }


    @GetMapping("/play/{id}") //Запросить запись партии
    public String recordsGame(@PathVariable int id){
        Game g = gameRepository.getById(id);
        return g.getRecords();
    }


    @GetMapping("/play/{id}/move") //Запросить шашки, у которых есть ударный ход
    public String move(@PathVariable int id){
        Game g = gameRepository.getById(id);
        return g.getCheckerKill().toString();
    }

    @PutMapping("/play/{id}/move") //Сделать ход
    public int doMove(@PathVariable int id, @RequestBody DoMoveDtoResponse test){
        Game g = gameRepository.getById(id);
        int ans = g.doMove(test.getNewA(), test.getNewB(), test.getA(), test.getB(), test.getChecker(), test.isRedMove());
        gameRepository.save(g);
        return ans;
    }

    @GetMapping("/play/{id}/move/available") //Запросить доступные ходы для шашки
    public String availableMove(@PathVariable int id, @RequestParam int a, @RequestParam int b){
        Game g = gameRepository.getById(id);
        return g.getAvailableMoves(a,b);
    }


    @GetMapping("/play/{id}/move/red") //Запросить красные доступные ходы
    public String availableRedMove(@PathVariable int id, @RequestParam int a, @RequestParam int b){
        Game g = gameRepository.getById(id);
        return g.getAvailableRedMove(a,b);
    }
}
