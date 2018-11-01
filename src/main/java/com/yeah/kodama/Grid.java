package com.yeah.kodama;

import java.awt.*;
import java.util.HashMap;

public class Grid {

    private Point coord;
    private HashMap<Agent.Action, Float> q_table;
    private int count;

    public Grid(Point coord) {
        this.coord = coord;
        this.q_table = new HashMap<>();
        this.count = 0;
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

    public void addFootPrint() {
        count++;
    }

    public int getCount() {
        return count;
    }
}
