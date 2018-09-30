package com.yeah.kodama;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

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

    private ArrayList<Action> movableLocation = new ArrayList<>();

    public enum Action {
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

    public Agent() {
        //何かしらの初期化処理.
    }

    public void init() {
        movableLocation.clear();
        movableLocation.add(Action.WalkUp);
        movableLocation.add(Action.WalkRight);
        movableLocation.add(Action.WalkLeft);
        movableLocation.add(Action.WalkDown);
    }

    public void infoSearch(int[] state) {
        if (state[1] == 2) movableLocation.remove(Action.WalkUp);
        if (state[3] == 2) movableLocation.remove(Action.WalkLeft);
        if (state[5] == 2) movableLocation.remove(Action.WalkRight);
        if (state[7] == 2) movableLocation.remove(Action.WalkDown);
    }

    public Action chooseAction() {
        Random rand = new Random();
        return movableLocation.get(rand.nextInt(movableLocation.size()));
    }

}
