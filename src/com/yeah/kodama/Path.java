package com.yeah.kodama;

import java.awt.*;
import java.util.HashMap;

public class Path {

    private Point coord;
    private HashMap<Agent.Action, Float> q_table;
    private int id;

    public Path(Point coord, int id) {
        this.coord = coord;
        this.id = id;
        this.q_table = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public Point getCoord() {
        return coord;
    }

    public HashMap<Agent.Action, Float> getQ_table() {
        return q_table;
    }

    public void setQ_table(HashMap<Agent.Action, Float> q_map) {
        //DeepCopy.
        for (Agent.Action action : q_map.keySet()) {
            this.q_table.put(action, q_map.get(action));
        }
    }
}
