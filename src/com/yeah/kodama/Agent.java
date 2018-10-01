package com.yeah.kodama;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Agent {

    private double gamma = 0.99;
    private double learning_rate = 0.5;
    private double epsilon = 0.30;
    private double total_reward = 0;

    private final int HIT_WALL_PENALTY = 100;           //ペナルティはあとで調整
    private final int ENEMY_ENCOUNTER_PENALTY = 100;
    private final int ONE_STEP_PENALTY = 1;
    private final int ENEMY_DEFEAT_REWARD = 100;
    private final int GET_ITEM_REWARD = 10;

    private HashMap<Action, Float> qmap = new HashMap<>();
    private ArrayList<Action> actions = new ArrayList<>();

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
        actions.clear();
        for (Action a : Action.values()) {
            qmap.put(a, 0.0f);  //Q値の初期化.
        }
    }

    public void evaluate(int[] state) {
        //ここで細かいペナルティーを設定していく.
        if (state[1] == 2) qmap.put(Action.WalkUp, qmap.get(Action.WalkUp) - HIT_WALL_PENALTY - ONE_STEP_PENALTY);
        if (state[3] == 2) qmap.put(Action.WalkLeft, qmap.get(Action.WalkLeft) - HIT_WALL_PENALTY - ONE_STEP_PENALTY);
        if (state[5] == 2) qmap.put(Action.WalkRight, qmap.get(Action.WalkRight) - HIT_WALL_PENALTY - ONE_STEP_PENALTY);
        if (state[7] == 2) qmap.put(Action.WalkDown, qmap.get(Action.WalkDown) - HIT_WALL_PENALTY - ONE_STEP_PENALTY);
    }

    public Action chooseAction() {
        Random rand = new Random();
        float max_q = -999.0f;
        for (Action act : qmap.keySet()) {
            if (qmap.get(act) > max_q) {
                max_q = qmap.get(act);
                actions.clear();
                actions.add(act);
            } else if (qmap.get(act) == max_q) {
                actions.add(act);
            }
        }

        return actions.get(rand.nextInt(actions.size()));
    }

}
