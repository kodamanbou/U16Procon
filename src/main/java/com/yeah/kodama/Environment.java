package com.yeah.kodama;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public final class Environment implements Game {
    private int[][] sample;
    private Point current;
    private int[] value = new int[9];

    private static final int GAME_ALIVE = 0;
    private static final int GAME_END_SURROUND_BLOCK = 1;
    private static final int GAME_END_PUT_BLOCK = 2;
    private static final int GAME_END_TURN_END = 3;

    private static Environment theInstance = new Environment();

    private Environment() {
        sample = new int[17][15];
        makeMap();
    }

    public static Environment getInstance() {
        return theInstance;
    }

    public int[] getReady() {
        int index = 0;

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                value[index] = getGridInfo(current.x + j, current.y + i);
                index++;
            }
        }
        return value;
    }

    private void makeMap() {
        //公式ルールに則って自動でマップを生成してくれるプログラム.
        //中心部をアイテムにする.
        Random random = new Random(System.currentTimeMillis());
        current = new Point(random.nextInt(7), random.nextInt(8));      //現在地を設定.
        sample[current.y][current.x] = 1;
        sample[16 - current.y][14 - current.x] = sample[current.y][current.x];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 15; j++) {
                //Map
                if (current.x != j || current.y != i) {
                    int rand = new Random(System.currentTimeMillis()).nextInt(3);
                    sample[i][j] = rand == 1 ? 3 : rand;
                    sample[16 - i][14 - j] = sample[i][j];  //マップの反転.
                }
            }
        }
        sample[8][7] = 3;

        //詰み判定を一度行い、ブロックに囲まれていたりしたら、ランダムに取り除く。
        if (checkIfEnd() == GAME_END_SURROUND_BLOCK) {
            int rand = new Random(System.currentTimeMillis()).nextInt(4);
        }

    }

    public int[] walkUp() {
        current.translate(0, -1);
        return getReady();
    }

    public int[] walkLeft() {
        current.translate(-1, 0);
        return getReady();
    }

    public int[] walkRight() {
        current.translate(1, 0);
        return getReady();
    }

    public int[] walkDown() {
        current.translate(0, 1);
        return getReady();
    }

    public int[] lookUp() {
        int index = 0;
        for (int i = -3; i < 0; i++) {
            for (int j = -1; j < 2; j++) {
                value[index] = getGridInfo(current.x + j, current.y + i);
            }
        }
        return value;
    }

    public int[] lookLeft() {
        int index = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -3; j < 0; j++) {
                value[index] = getGridInfo(current.x + j, current.y + i);
            }
        }
        return value;
    }

    public int[] lookRight() {
        int index = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = 1; j < 4; j++) {
                value[index] = getGridInfo(current.x + j, current.y + i);
            }
        }
        return value;
    }

    public int[] lookDown() {
        int index = 0;
        for (int i = 1; i < 4; i++) {
            for (int j = -1; j < 2; j++) {
                value[index] = getGridInfo(current.x + j, current.y + i);
            }
        }
        return value;
    }

    public int[] searchUp() {
        for (int i = 0; i < 9; i++) {
            value[i] = getGridInfo(current.x, current.y - i - 1);
        }
        return value;
    }

    public int[] searchLeft() {
        for (int i = 0; i < 9; i++) {
            value[i] = getGridInfo(current.x - i - 1, current.y);
        }
        return value;
    }

    public int[] searchRight() {
        for (int i = 0; i < 9; i++) {
            value[i] = getGridInfo(current.x + i + 1, current.y);
        }
        return value;
    }

    public int[] searchDown() {
        for (int i = 0; i < 9; i++) {
            value[i] = getGridInfo(current.x, current.y + i + 1);
        }
        return value;
    }

    public int[] putUp() {
        value = getReady();
        if (getGridInfo(current.x, current.y - 1) != 2) {
            value[1] = 2;
        }
        return value;
    }

    public int[] putLeft() {
        value = getReady();
        if (getGridInfo(current.x - 1, current.y) != 2) {
            value[3] = 2;
        }
        return value;
    }

    public int[] putRight() {
        value = getReady();
        if (getGridInfo(current.x + 1, current.y) != 2) {
            value[5] = 2;
        }
        return value;
    }

    public int[] putDown() {
        value = getReady();
        if (getGridInfo(current.x, current.y + 1) != 2) {
            value[7] = 2;
        }
        return value;
    }

    //積みゲー判定.
    private int checkIfEnd() {
        //範囲外かどうかの判定.
        if (current.x >= 0 && current.x < 15 && current.y >= 0 && current.y < 17) {
            return GAME_END_SURROUND_BLOCK;
        }

        if (sample[current.y][current.x] == 2) {
            return GAME_END_PUT_BLOCK;
        }

        return GAME_ALIVE;
    }

    private int getGridInfo(int x, int y) {
        if (x < 0 || x > 14 || y < 0 || y > 16) {
            return 2;
        }
        return sample[y][x];
    }
}
