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
    private static final int ITEM_CLOSE_REWARD = 5;
    private static final int ITEM_CHECK_REWARD = 5;

    private static final int FLOOR = 0,
                             ENEMY = 1,
                             BLOCK = 2,
                             ITEM = 3;

    private HashMap<Action, Float> qmap;
    private ArrayList<Action> actions;

    private int x = 0,      //スタート地点からの相対座標.
                y = 0;

    private ArrayList<Grid> grids;
    private Grid current;

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
        //コンストラクタ
        qmap = new HashMap<>();
        actions = new ArrayList<>();
        grids = new ArrayList<>();
    }

    public void init(Point current, int id) {
        actions.clear();
        for (Action a : Action.values()) {
            qmap.put(a, 0.0f);  //Q値の初期化.
        }
        this.current;
    }

    public void evaluate(int[] state) {
        //ここで細かいペナルティーを設定していく.
        //壁にぶつかる行動は、評価を下げる。
        if (state[1] == BLOCK) qmap.put(Action.WalkUp, qmap.get(Action.WalkUp) - HIT_WALL_PENALTY);
        if (state[3] == BLOCK) qmap.put(Action.WalkLeft, qmap.get(Action.WalkLeft) - HIT_WALL_PENALTY);
        if (state[5] == BLOCK) qmap.put(Action.WalkRight, qmap.get(Action.WalkRight) - HIT_WALL_PENALTY);
        if (state[7] == BLOCK) qmap.put(Action.WalkDown, qmap.get(Action.WalkDown) - HIT_WALL_PENALTY);

        //アイテムを取得した時に、評価を上げる。但し、アイテムをとってブロックに囲まれるなら、評価を下げる。
        if (state[1] == ITEM) {
            if (state[0] != BLOCK || state[2] != BLOCK) {
                qmap.put(Action.WalkUp, qmap.get(Action.WalkUp) + GET_ITEM_REWARD);
            } else {
                qmap.put(Action.WalkUp, qmap.get(Action.WalkUp) - SELF_KILL_PENALTY);
            }
        }

        if (state[3] == ITEM) {
            if (state[0] != BLOCK || state[6] != BLOCK) {
                qmap.put(Action.WalkLeft, qmap.get(Action.WalkLeft) + GET_ITEM_REWARD);
            } else {
                qmap.put(Action.WalkLeft, qmap.get(Action.WalkLeft) - SELF_KILL_PENALTY);
            }
        }

        if (state[5] == ITEM) {
            if (state[2] != BLOCK || state[8] != BLOCK) {
                qmap.put(Action.WalkRight, qmap.get(Action.WalkRight) + GET_ITEM_REWARD);
            } else {
                qmap.put(Action.WalkRight, qmap.get(Action.WalkRight) + SELF_KILL_PENALTY);
            }
        }

        if (state[7] == ITEM) {
            if (state[6] != BLOCK || state[8] != BLOCK) {
                qmap.put(Action.WalkDown, qmap.get(Action.WalkDown) + GET_ITEM_REWARD);
            } else {
                qmap.put(Action.WalkDown, qmap.get(Action.WalkDown) - SELF_KILL_PENALTY);
            }
        }

        //斜めアイテム取得のための移動を評価.
        if (state[0] == ITEM) {
            if (state[1] != BLOCK) {
                qmap.put(Action.WalkUp, qmap.get(Action.WalkUp) + ITEM_CLOSE_REWARD);
            }
            if (state[3] != BLOCK) {
                qmap.put(Action.WalkLeft, qmap.get(Action.WalkLeft) + ITEM_CLOSE_REWARD);
            }
        }

        if (state[2] == ITEM) {
            if (state[1] != BLOCK) {
                qmap.put(Action.WalkUp, qmap.get(Action.WalkUp) + ITEM_CLOSE_REWARD);
            }
            if (state[5] != BLOCK) {
                qmap.put(Action.WalkRight, qmap.get(Action.WalkRight) + ITEM_CLOSE_REWARD);
            }
        }

        if (state[6] == ITEM) {
            if (state[3] != BLOCK) {
                qmap.put(Action.WalkLeft, qmap.get(Action.WalkLeft) + ITEM_CLOSE_REWARD);
            }
            if (state[7] != BLOCK) {
                qmap.put(Action.WalkDown, qmap.get(Action.WalkDown) + ITEM_CLOSE_REWARD);
            }
        }

        if (state[8] == ITEM) {
            if (state[5] != BLOCK) {
                qmap.put(Action.WalkRight, qmap.get(Action.WalkRight) + ITEM_CLOSE_REWARD);
            }
            if (state[7] != BLOCK) {
                qmap.put(Action.WalkDown, qmap.get(Action.WalkDown) + ITEM_CLOSE_REWARD);
            }
        }

        //敵と遭遇した時にkillする行動を評価する処理.
        if (state[1] == ENEMY) qmap.put(Action.PutUp, qmap.get(Action.PutUp) + ENEMY_DEFEAT_REWARD);
        if (state[3] == ENEMY) qmap.put(Action.PutLeft, qmap.get(Action.PutLeft) + ENEMY_DEFEAT_REWARD);
        if (state[5] == ENEMY) qmap.put(Action.PutRight, qmap.get(Action.PutRight) + ENEMY_DEFEAT_REWARD);
        if (state[7] == ENEMY) qmap.put(Action.PutDown, qmap.get(Action.PutDown) + ENEMY_DEFEAT_REWARD);

        //三方向をブロックで囲まれているときに、残りのFloorにブロックを置くと評価を下げる処理。
        if (state[3] == BLOCK && state[5] == BLOCK && state[7] == BLOCK) {
            qmap.put(Action.PutUp, qmap.get(Action.PutUp) - SELF_KILL_PENALTY);
        }
        if (state[1] == BLOCK && state[5] == BLOCK && state[7] == BLOCK) {
            qmap.put(Action.PutLeft, qmap.get(Action.PutLeft) - SELF_KILL_PENALTY);
        }
        if (state[1] == BLOCK && state[3] == BLOCK && state[7] == BLOCK) {
            qmap.put(Action.PutRight, qmap.get(Action.PutRight) - SELF_KILL_PENALTY);
        }
        if (state[1] == BLOCK && state[3] == BLOCK && state[5] == BLOCK) {
            qmap.put(Action.PutDown, qmap.get(Action.PutDown) - SELF_KILL_PENALTY);
        }

        //敵が斜め方向にいる時、敵前に飛び出す行動の評価を下げる.
        if (state[0] == ENEMY) {
            qmap.put(Action.WalkUp, qmap.get(Action.WalkUp) - ENEMY_ENCOUNTER_PENALTY);
            qmap.put(Action.WalkLeft, qmap.get(Action.WalkLeft) - ENEMY_ENCOUNTER_PENALTY);
        } else if (state[2] == ENEMY) {
            qmap.put(Action.WalkUp, qmap.get(Action.WalkUp) - ENEMY_ENCOUNTER_PENALTY);
            qmap.put(Action.WalkRight, qmap.get(Action.WalkRight) - ENEMY_ENCOUNTER_PENALTY);
        } else if (state[6] == ENEMY) {
            qmap.put(Action.WalkLeft, qmap.get(Action.WalkLeft) - ENEMY_ENCOUNTER_PENALTY);
            qmap.put(Action.WalkDown, qmap.get(Action.WalkDown) - ENEMY_ENCOUNTER_PENALTY);
        } else if (state[8] == ENEMY) {
            qmap.put(Action.WalkDown, qmap.get(Action.WalkDown) - ENEMY_ENCOUNTER_PENALTY);
            qmap.put(Action.WalkRight, qmap.get(Action.WalkRight) - ENEMY_ENCOUNTER_PENALTY);
        }

        //TODO やったらめったらPutしないようにする.(Mapを作成し、まだ通ってない座標にPutしたら若干のペナルティを与える)
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
