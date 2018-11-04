package com.yeah.kodama;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

public final class Environment {
    private int[][] sample;
    private Point current;

    private static Environment theInstance = new Environment();

    private Environment() {
        sample = new int[17][15];
        makeMap();
    }

    public static Environment getInstance() {
        return theInstance;
    }

    public int[] getReady() {
        int[] value = new int[9];
        int index = 0;

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
    }
}
