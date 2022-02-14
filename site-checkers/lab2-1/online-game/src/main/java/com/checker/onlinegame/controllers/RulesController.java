package com.checker.onlinegame.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RulesController {

    @GetMapping("/rules")
    public String rules(Model model){
        return "rules";
    }

    @GetMapping("/rules1")
    public String rules1(Model model){
        return "rules1";
    }

    @GetMapping("/rules2")
    public String rules2(Model model){
        return "rules2";
    }
}