package com.yeah.kodama;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public final class Map {

    private HashMap<Point, Integer> map_data;
    private static Map theInstance = new Map();

    private Map() {
        map_data = new HashMap<>();
    }

    public static Map getInstance() {
        return theInstance;
    }

    public void getReady(Point point, int[] value) {
        //周辺情報を取得してput.
        int i = 0;
        for (int j = -1; j < 2; j++) {
            for (int k = -1; k < 2; k++) {
                Point point1 = new Point(point.x + k, point.y + j);
                if (!isExist(point1)) {
                    map_data.put(new Point(point.x + k, point.y + j), value[i]);
                }
                i++;
            }
        }
    }

    public void walkTo(Point point, int direction, int[] value) {
        //Walk〇〇関数による移動の結果をMapに登録する処理.
        switch (direction) {
            case 1:
                for (int i = -1; i < 2; i++) {
                    Point point1 = new Point(point.x + i, point.y - 2);
                    if (!isExist(point1)) map_data.put(point1, value[i + 1]);
                }
                break;
            case 3:
                for (int i = -1; i < 2; i++) {
                    Point point1 = new Point(point.x - 2, point.y + i);
                    if (!isExist(point1)) map_data.put(point1, value[(i + 1) * 3]);
                }
                break;
            case 5:
                for (int i = -1; i < 2; i++) {
                    Point point1 = new Point(point.x + 2, point.y + i);
                    if (!isExist(point1)) map_data.put(point1, value[i * 3 + 5]);
                }
                break;
            case 7:
                for (int i = -1; i < 2; i++) {
                    Point point1 = new Point(point.x + i, point.y + 1);
                    if (!isExist(point1)) map_data.put(point1, value[i + 7]);
                }
                break;
        }
    }

    public void lookTo(Point point, int direction, int[] value) {
        //ここでLookの結果を座標と一緒にHashMapにぶち込む.もう妥協します.
        Point[] offset = new Point[9];
        int x = point.x;
        int y = point.y;
        switch (direction) {
            case 1:
                //上にLookする.
                offset = new Point[] {
                        new Point(x - 1, y - 3), new Point(x, y - 3), new Point(x + 1, y - 3),
                        new Point(x - 1, y - 2), new Point(x, y - 2), new Point(x + 1, y - 2),
                        new Point(x - 1, y - 1), new Point(x, y - 1), new Point(x + 1, y - 1)
                };
                break;
            case 3:
                //左にLookする.
                offset = new Point[] {
                        new Point(x - 3, y - 1), new Point(x - 2, y - 1), new Point(x - 1, y - 1),
                        new Point(x - 3, y), new Point(x - 2, y), new Point(x - 1, y),
                        new Point(x - 3, y + 1), new Point(x - 2, y + 1), new Point(x - 1, y + 1)
                };
                break;
            case 5:
                //右にLookする.
                offset = new Point[] {
                        new Point(x + 1, y -1), new Point(x + 2, y - 1), new Point(x + 3, y - 1),
                        new Point(x + 1, y), new Point(x + 2, y), new Point(x + 3, y),
                        new Point(x + 1, y + 1), new Point(x + 2, y + 1), new Point(x + 3, y + 1)
                };
                break;
            case 7:
                //下にLookする.
                offset = new Point[] {
                        new Point(x - 1, y + 1), new Point(x, y + 1), new Point(x + 1, y + 1),
                        new Point(x - 1, y + 2), new Point(x, y + 2), new Point(x + 1, y + 2),
                        new Point(x - 1, y + 3), new Point(x, y + 3), new Point(x + 1, y + 3)
                };
                break;
        }

        for (int i = 0; i < 9; i++) {
            if (!isExist(offset[i])) map_data.put(offset[i], value[i]);
        }
    }

    public void searchTo(Point point, int direction, int[] value) {
        //ここでsearchの結果をMapに入れる.
        switch (direction) {
            case 1:
                for (int i = 0; i < 9; i++) {
                    Point point1 = new Point(point.x, point.y + i + 1);
                    if (!isExist(point1)) map_data.put(point1, value[i]);
                }
                break;
            case 3:
                for (int i = 0; i < 9; i++) {
                    Point point1 = new Point(point.x - i - 1, point.y);
                    if (!isExist(point1)) map_data.put(point1, value[i]);
                }
                break;
            case 5:
                for (int i = 0; i < 9; i++) {
                    Point point1 = new Point(point.x + i + 1, point.y);
                    if (!isExist(point1)) map_data.put(point1, value[i]);
                }
                break;
            case 7:
                for (int i = 0; i < 9; i++) {
                    Point point1 = new Point(point.x, point.y - i - 1);
                    if (!isExist(point1)) map_data.put(point1, value[i]);
                }
                break;
        }
    }

    public boolean isExist(Point point) {
        //すでにMapに情報が登録されている場合は、追加しないようにする。
        //例：アイテム取った後に、元いた場所がブロックに変わるが、それは追加しない.
        for (Point path : map_data.keySet()) {
            if (path.equals(point)) return true;
        }
        return false;
    }

    public void showHistory() {
        for (Point point : map_data.keySet()) {
            System.out.println("(" + point.x + ", " + point.y + ")" + "   " + map_data.get(point));
        }
    }
}
