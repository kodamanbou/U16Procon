package com.yeah.kodama;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public final class Environment {
    private int[][] sample;
    private Point current;
    private int gameStatus = 0;

    private static Environment theInstance = new Environment();

    private Environment() {
        sample = new int[17][15];
        makeMap();
    }

    public static Environment getInstance() {
        return theInstance;
    }

    public int[] getReady() {
        int[] value = new int[10];
        int index = 1;

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (current.y + i < 0 || current.y + i > 16 || current.x + j < 0 || current.x + j > 16) {
                    value[index] = 2;
                } else {
                    value[index] = sample[current.y + i][current.x + j];
                }
                index++;
            }
        }
        return value;
    }

    private void makeMap() {
        //公式ルールに則って自動でマップを生成してくれるプログラム.
        //中心部をアイテムにする.
        Random random = new Random(System.currentTimeMillis());
        sample[8][7] = 3;
        current = new Point(random.nextInt(7), random.nextInt(8));      //現在地を設定.
    }

    public int[] walkUp() {
        current.translate(0, -1);
        return getReady();
    }

    public int[] walkLeft() {
        return new int[0];
    }

    public int[] walkRight() {
        return new int[0];
    }

    public int[] walkDown() {
        return new int[0];
    }

    public int[] lookUp() {
        return new int[0];
    }

    public int[] lookLeft() {
        return new int[0];
    }

    public int[] lookRight() {
        return new int[0];
    }

    public int[] lookDown() {
        return new int[0];
    }

    public int[] searchUp() {
        return new int[0];
    }

    public int[] searchLeft() {
        return new int[0];
    }

    public int[] searchRight() {
        return new int[0];
    }

    public int[] searchDown() {
        return new int[0];
    }

    public int[] putUp() {
        return new int[0];
    }

    public int[] putLeft() {
        return new int[0];
    }

    public int[] putRight() {
        return new int[0];
    }

    public int[] putDown() {
        return new int[0];
    }

    //積みゲー判定.
    private boolean isGameEnd() {
        //範囲外かどうかの判定.
        return current.x >= 0 && current.x < 15 && current.y >= 0 && current.y < 17;
    }
}
