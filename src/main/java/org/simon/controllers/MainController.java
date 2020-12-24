package org.simon.controllers;

import org.simon.JsonReader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutionException;
import org.json.JSONException;
import org.json.JSONObject;

@Controller
public class MainController {

    @GetMapping("/")
    public String home(Model model) throws ExecutionException, InterruptedException, IOException {
        /*Iterable<Links> links = linksRepository.findAll();
        model.addAttribute("links", links);
        model.addAttribute("name", " World");*/

        JSONObject json = JsonReader.readJsonFromUrl("https://springboot-dagrejs-default-rtdb.europe-west1.firebasedatabase.app/.json");
        System.out.println(json.toString());
        System.out.println("test #2  " + json.get("148570018_1100"));

        return "home";
    }

    String firebaselink = "https://springboot-dagrejs-default-rtdb.europe-west1.firebasedatabase.app/.json";


}
