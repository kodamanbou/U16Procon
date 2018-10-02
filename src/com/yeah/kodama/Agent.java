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

    private static final int HIT_WALL_PENALTY = 100;           //ペナルティはあとで調整
    private static final int ENEMY_ENCOUNTER_PENALTY = 100;
    private static final int SAME_PATH_PENALTY = 10;
    private static final int SELF_KILL_PENALTY = 100;
    private static final int ENEMY_DEFEAT_REWARD = 100;
    private static final int GET_ITEM_REWARD = 10;

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
        //壁にぶつかる行動は、評価を下げる。
        if (state[1] == 2) qmap.put(Action.WalkUp, qmap.get(Action.WalkUp) - HIT_WALL_PENALTY);
        if (state[3] == 2) qmap.put(Action.WalkLeft, qmap.get(Action.WalkLeft) - HIT_WALL_PENALTY);
        if (state[5] == 2) qmap.put(Action.WalkRight, qmap.get(Action.WalkRight) - HIT_WALL_PENALTY);
        if (state[7] == 2) qmap.put(Action.WalkDown, qmap.get(Action.WalkDown) - HIT_WALL_PENALTY);

        //アイテムを取得した時に、評価を上げる。但し、アイテムをとってブロックに囲まれるなら、評価を下げる。
        if (state[1] == 3) {
            if (state[0] != 2 || state[2] != 2) {
                qmap.put(Action.WalkUp, qmap.get(Action.WalkUp) + GET_ITEM_REWARD);
            } else {
                qmap.put(Action.WalkUp, qmap.get(Action.WalkUp) - SELF_KILL_PENALTY);
            }
        }

        if (state[3] == 3) {
            if (state[0] != 2 || state[6] != 2) {
                qmap.put(Action.WalkLeft, qmap.get(Action.WalkLeft) + GET_ITEM_REWARD);
            } else {
                qmap.put(Action.WalkLeft, qmap.get(Action.WalkLeft) - SELF_KILL_PENALTY);
            }
        }

        if (state[5] == 3) {
            if (state[2] != 2 || state[8] != 2) {
                qmap.put(Action.WalkRight, qmap.get(Action.WalkRight) + GET_ITEM_REWARD);
            } else {
                qmap.put(Action.WalkRight, qmap.get(Action.WalkRight) + SELF_KILL_PENALTY);
            }
        }

        if (state[7] == 3) {
            if (state[6] != 2 || state[8] != 2) {
                qmap.put(Action.WalkDown, qmap.get(Action.WalkDown) + GET_ITEM_REWARD);
            } else {
                qmap.put(Action.WalkDown, qmap.get(Action.WalkDown) - SELF_KILL_PENALTY);
            }
        }

        //敵と遭遇した時にkillする行動を評価する処理.
        if (state[1] == 1) qmap.put(Action.PutUp, qmap.get(Action.PutUp) + ENEMY_DEFEAT_REWARD);
        if (state[3] == 1) qmap.put(Action.PutLeft, qmap.get(Action.PutLeft) + ENEMY_DEFEAT_REWARD);
        if (state[5] == 1) qmap.put(Action.PutRight, qmap.get(Action.PutRight) + ENEMY_DEFEAT_REWARD);
        if (state[7] == 1) qmap.put(Action.PutDown, qmap.get(Action.PutDown) + ENEMY_DEFEAT_REWARD);
    }

    public Action chooseAction() {
        long seed = System.currentTimeMillis();
        Random rand = new Random(seed);
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
