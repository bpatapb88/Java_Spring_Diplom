package org.simon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class JsonReader {
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    public static Object[] getBoxes(JSONArray jsonlist){
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

        Object[] objects = {nodes,edges,shapes};

        return objects;
    }
}
