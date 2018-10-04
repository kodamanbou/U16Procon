package com.yeah.kodama;

import java.awt.*;
import java.util.HashMap;

public class Grid {

    private Point coord;
    private HashMap<Agent.Action, Float> q_table;
    private int id;

    public Grid(Point coord, int id) {
        this.coord = coord;
        this.id = id;
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

    public void setQ_table(HashMap<Agent.Action, Float> q_table) {
        //DeepCopy.
    }
}
