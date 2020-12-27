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

    @GetMapping("/")
    public String home(Model model) throws IOException {

        JSONObject json = JsonReader.readJsonFromUrl("https://springboot-dagrejs-default-rtdb.europe-west1.firebasedatabase.app/.json");
        JSONArray jsonlist = json.getJSONArray("boxes");
        ArrayList<String> nodes = new ArrayList<>();
        ArrayList<String> shapes = new ArrayList<>();
        ArrayList<String[]> edges = new ArrayList<>();
        for (int i = 0; i< jsonlist.length(); i++){
            nodes.add(jsonlist.getJSONObject(i).getString("name"));
            shapes.add(jsonlist.getJSONObject(i).getString("shape"));
            if(jsonlist.getJSONObject(i).length() == 3) {
                for (int j = 0; j < jsonlist.getJSONObject(i).getJSONArray("links").length(); j++) {
                    String[] temp = {jsonlist.getJSONObject(i).getString("name"), jsonlist.getJSONObject(i).getJSONArray("links").getString(j)};
                    edges.add(temp);
                }

            }
        }
        model.addAttribute("nodes", nodes);
        model.addAttribute("edges", edges);
        model.addAttribute("shapes", shapes);
        return "home";
    }
}
