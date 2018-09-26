package com.yeah.kodama;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Agent {

    private double q[][][] = new double[17][15][16];
    private HashMap<Point, Integer> qmap = new HashMap<>();
    private double gamma = 0.90;
    private double learning_rate = 0.10;
    private double epsilon = 0.30;

    private final int HIT_WALL_PENALTY = 100;           //ペナルティはあとで調整
    private final int ENEMY_ENCOUNTER_PENALTY = 100;
    private final int GET_ITEM_REWARD = 10;
    private final int ONE_STEP_PENALTY = 1;

}
