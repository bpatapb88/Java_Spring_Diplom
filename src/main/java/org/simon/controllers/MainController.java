package org.simon.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@Controller
public class MainController {

    @GetMapping("/")
    public String home(Model model) throws ExecutionException, InterruptedException {
        /*Iterable<Links> links = linksRepository.findAll();
        model.addAttribute("links", links);
        model.addAttribute("name", " World");*/
        return "home";
    }
}
