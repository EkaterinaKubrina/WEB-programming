package com.checker.onlinegame.controllers;

import com.checker.onlinegame.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;


@Controller
public class PlayController {

    @Autowired
    private GameRepository gameRepository;

    @GetMapping("/play")
    public String play(Model model){
        return "play";
    }



}