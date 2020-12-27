package org.simon.controllers;

import org.json.JSONArray;
import org.simon.JsonReader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.json.JSONException;
import org.json.JSONObject;

@Controller
public class MainController {

    public static final String firebaseJson = "https://springboot-dagrejs-default-rtdb.europe-west1.firebasedatabase.app/.json";

    @GetMapping("/")
    public String home(Model model) throws IOException {

        JSONObject json = JsonReader.readJsonFromUrl(firebaseJson);
        JSONArray jsonlist = json.getJSONArray("boxes");
        Object[] attributes = JsonReader.getBoxes(jsonlist);
        model.addAttribute("nodes", attributes[0]);
        model.addAttribute("edges", attributes[1]);
        model.addAttribute("shapes", attributes[2]);
        return "home";
    }
}
