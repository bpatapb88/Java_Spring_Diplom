package org.simon;

import java.util.ArrayList;

public class Attributes {
    private ArrayList<String> nodes = new ArrayList<>();
    private ArrayList<String> shapes = new ArrayList<>();
    private ArrayList<String[]> edges = new ArrayList<>();

    public ArrayList<String> getNodes() {
        return nodes;
    }

    public ArrayList<String> getShapes() {
        return shapes;
    }

    public ArrayList<String[]> getEdges() {
        return edges;
    }
}
