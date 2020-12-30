package org.simon.controllers;

import org.json.JSONArray;
import org.json.JSONObject;
import org.simon.JsonReader;
import org.simon.Attributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class MainController {

    public static final String firebaseJson = "https://springboot-dagrejs-default-rtdb.europe-west1.firebasedatabase.app/.json";

    @GetMapping("/")
    public String home(Model model) throws IOException {
        JsonReader jsonReader = new JsonReader();

        JSONObject json = jsonReader.readJsonFromUrl(firebaseJson);
        JSONArray jsonList = json.getJSONArray("boxes");
        Attributes attributes = jsonReader.getBoxes(jsonList);
        model.addAttribute("nodes", attributes.getNodes());
        model.addAttribute("edges", attributes.getEdges());
        model.addAttribute("shapes", attributes.getShapes());
        return "home";
    }
}
