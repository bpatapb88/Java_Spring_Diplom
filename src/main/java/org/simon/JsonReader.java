package org.simon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

public class JsonReader {

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
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

    public Attributes getBoxes(JSONArray jsonList) throws JSONException{
        Attributes attributes = new Attributes();
            for (int i = 0; i < jsonList.length(); i++) {
                final JSONObject jsonObject = jsonList.getJSONObject(i);
                attributes.getNodes().add(jsonObject.getString("name"));
                attributes.getShapes().add(jsonObject.getString("shape"));
                if (jsonObject.has("links")) {
                    for (int j = 0; j < jsonObject.getJSONArray("links").length(); j++) {
                        String[] temp = {jsonObject.getString("name"), jsonObject.getJSONArray("links").getString(j)};
                        attributes.getEdges().add(temp);
                    }
                }
            }
        return attributes;
    }
}
