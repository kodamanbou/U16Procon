package com.yeah.kodama;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Agent {

    private HashMap<Point, Action> qmap = new HashMap<>();
    private double gamma = 0.99;
    private double learning_rate = 0.5;
    private double epsilon = 0.30;
    private double total_reward = 0;

    private final int HIT_WALL_PENALTY = 100;           //ペナルティはあとで調整
    private final int ENEMY_ENCOUNTER_PENALTY = 100;
    private final int ONE_STEP_PENALTY = 1;
    private final int ENEMY_DEFEAT_REWARD = 100;
    private final int GET_ITEM_REWARD = 10;

    private enum Action {
        WalkUp,
        WalkRight,
        WalkLeft,
        WalkDown,
        LookUp,
        LookRight,
        LookLeft,
        LookDown,
        SearchUp,
        SearchRight,
        SearchLeft,
        SearchDown,
        PutUp,
        PutRight,
        PutLeft,
        PutDown
    }

    public Agent(Point origin) {
    }

}
