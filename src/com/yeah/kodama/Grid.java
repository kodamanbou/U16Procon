package com.yeah.kodama;

import java.awt.*;
import java.util.HashMap;

public class Grid {

    private Point coord;
    private HashMap<Agent.Action, Float> q_table;
    private static HashMap<Point, Integer> map_data = new HashMap<>();

    public Grid(Point origin) {
        this.coord = origin;
    }
}
