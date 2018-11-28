package com.yeah.kodama;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class Environment implements Game {
    private static final long seed = System.currentTimeMillis();

    private int[][] sample;
    private Point current;
    private int[] value = new int[9];
    private int turn;

    private boolean isGameAlive;

    private static final int GAME_END_SURROUND_BLOCK = 1;
    private static final int GAME_END_PUT_BLOCK = 2;
    private static final int GAME_END_OUT_OF_BOUNDS = 3;
    private static final int GAME_END_TURN_END = 666;

    public Environment(int mode) {
        turn = 0;
        isGameAlive = true;
        sample = new int[17][15];
        current = new Point(0, 0);

        if (mode == 0) {
            makeMap();
        } else {
            loadMap();
        }

    }


    public int[] getReady() {
        int index = 0;

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                value[index] = getGridInfo(current.x + j, current.y + i);
                index++;
            }
        }
        turn++;
        return value;
    }

    private void makeMap() {
        //公式ルールに則って自動でマップを生成してくれるプログラム.
        //中心部をアイテムにする.

        Random random = new Random(seed);
        current = new Point(random.nextInt(7), random.nextInt(9));      //現在地を設定.

        System.out.println(current.toString());

        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 14; j++) {
                //Map
                if (current.x != j || current.y != i) {
                    int rand = new Random(seed).nextInt(3);
                    sample[i][j] = rand == 1 ? 3 : rand;
                    sample[16 - i][14 - j] = sample[i][j];  //マップの反転.
                }
            }
        }

        //外周りを作る.
        for (int i = 0; i < 17; i++) {
            sample[i][0] = new Random().nextInt(2) == 0 ? 0 : 3;
            sample[16 - i][14] = sample[i][0];
        }
        for (int j = 1; j < 14; j++) {
            sample[0][j] = new Random().nextInt(2) == 0 ? 0 : 3;
            sample[16][14 - j] = sample[0][j];
        }

        //現在地の設定等.
        sample[current.y][current.x] = 5;
        sample[16 - current.y][14 - current.x] = 1;
        sample[8][7] = 3;

        //詰み判定を一度行い、ブロックに囲まれていたりしたら、ランダムに取り除く。
        if (checkIfEnd() == GAME_END_SURROUND_BLOCK) {
            long seed = System.currentTimeMillis() + Runtime.getRuntime().freeMemory();

            ArrayList<Point> removable = new ArrayList<>();
            removable.add(new Point(current.x, current.y + 1));     //下
            removable.add(new Point(current.x + 1, current.y));     //右

            if (current.x != 1) {
                removable.add(new Point(current.x - 1, current.y)); //左
            }
            if (current.y != 1) {
                removable.add(new Point(current.x, current.y - 1)); //上
            }

            Point point = removable.get(new Random(seed).nextInt(removable.size()));
            sample[point.y][point.x] = 0;
        }

        //Mapの保存.
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
        File file = new File(sdf.format(calendar.getTime()) + ".map");
        FileWriter fw = null;
        StringBuilder sb = new StringBuilder();

        try {
            fw = new FileWriter(file, false);

            System.out.println("Created a map.");

            for (int i = 0; i < 17; i++) {
                for (int j = 0; j < 15; j++) {
                    sb.append(sample[i][j]);
                }

                System.out.println(sb.toString());
                sb.append(System.lineSeparator());
                fw.write(sb.toString());
                sb.setLength(0);
            }

            fw.flush();
            fw.close();
        } catch (IOException ie) {
            ie.printStackTrace();
        }

    }

    private void loadMap() {
        //Mapの読み込み(2回戦目用).
        turn = 0;

        File[] files = new File(System.getProperty("user.dir"))
                .listFiles(path -> path.isFile() && path.getName().contains(".map"));
        BufferedReader br = null;
        FileReader fr = null;
        String line;
        int column = 0;

        try {
            if (files[0] != null) {
                fr = new FileReader(files[0]);
                br = new BufferedReader(fr);

                while ((line = br.readLine()) != null) {
                    for (int i = 0; i < line.length(); i++) {
                        int value = Integer.parseInt(line.split("")[i]);
                        sample[16 - column][14 - i] = value;
                        if (value == 5) current = new Point(14 - i, 16 - column);
                    }
                    column++;
                }
                System.out.println("Map loaded.");

                for (int i = 0; i < 17; i++) {
                    for (int j = 0; j < 15; j++) {
                        System.out.print(sample[i][j]);
                    }
                    System.out.println();
                }

                files[0].delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int[] walkUp() {
        if (sample[current.y - 1][current.x] == 3) {
            sample[current.y][current.x] = 2;
            sample[current.y - 1][current.x] = 0;
        }
        current.translate(0, -1);
        System.out.println("wu Executed.");
        if (checkIfEnd() != 0) exit(checkIfEnd());
        return getReady();
    }

    public int[] walkLeft() {
        if (sample[current.y][current.x - 1] == 3) {
            sample[current.y][current.x] = 2;
            sample[current.y][current.x - 1] = 0;
        }
        current.translate(-1, 0);
        System.out.println("wl Executed.");
        if (checkIfEnd() != 0) exit(checkIfEnd());
        return getReady();
    }

    public int[] walkRight() {
        if (sample[current.y][current.x + 1] == 3) {
            sample[current.y][current.x] = 2;
            sample[current.y][current.x + 1] = 0;
        }
        current.translate(1, 0);
        System.out.println("wr Executed.");
        if (checkIfEnd() != 0) exit(checkIfEnd());
        return getReady();
    }

    public int[] walkDown() {
        if (sample[current.y + 1][current.x] == 3) {
            sample[current.y][current.x] = 2;
            sample[current.y + 1][current.x] = 0;
        }
        current.translate(0, 1);
        System.out.println("wd Executed.");
        if (checkIfEnd() != 0) exit(checkIfEnd());
        return getReady();
    }

    public int[] lookUp() {
        int index = 0;
        for (int i = -3; i < 0; i++) {
            for (int j = -1; j < 2; j++) {
                value[index] = getGridInfo(current.x + j, current.y + i);
            }
        }
        System.out.println("lu Executed.");
        if (checkIfEnd() != 0) exit(checkIfEnd());
        return value;
    }

    public int[] lookLeft() {
        int index = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -3; j < 0; j++) {
                value[index] = getGridInfo(current.x + j, current.y + i);
            }
        }
        System.out.println("ll Executed.");
        if (checkIfEnd() != 0) exit(checkIfEnd());
        return value;
    }

    public int[] lookRight() {
        int index = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = 1; j < 4; j++) {
                value[index] = getGridInfo(current.x + j, current.y + i);
            }
        }
        System.out.println("lr Executed.");
        if (checkIfEnd() != 0) exit(checkIfEnd());
        return value;
    }

    public int[] lookDown() {
        int index = 0;
        for (int i = 1; i < 4; i++) {
            for (int j = -1; j < 2; j++) {
                value[index] = getGridInfo(current.x + j, current.y + i);
            }
        }
        System.out.println("ld Executed.");
        if (checkIfEnd() != 0) exit(checkIfEnd());
        return value;
    }

    public int[] searchUp() {
        for (int i = 0; i < 9; i++) {
            value[i] = getGridInfo(current.x, current.y - i - 1);
        }
        System.out.println("su Executed.");
        if (checkIfEnd() != 0) exit(checkIfEnd());
        return value;
    }

    public int[] searchLeft() {
        for (int i = 0; i < 9; i++) {
            value[i] = getGridInfo(current.x - i - 1, current.y);
        }
        System.out.println("sl Executed.");
        if (checkIfEnd() != 0) exit(checkIfEnd());
        return value;
    }

    public int[] searchRight() {
        for (int i = 0; i < 9; i++) {
            value[i] = getGridInfo(current.x + i + 1, current.y);
        }
        System.out.println("sr Executed.");
        if (checkIfEnd() != 0) exit(checkIfEnd());
        return value;
    }

    public int[] searchDown() {
        for (int i = 0; i < 9; i++) {
            value[i] = getGridInfo(current.x, current.y + i + 1);
        }
        System.out.println("sd Executed.");
        if (checkIfEnd() != 0) exit(checkIfEnd());
        return value;
    }

    public int[] putUp() {
        if (getGridInfo(current.x, current.y - 1) != 2) {
            sample[current.y - 1][current.x] = 2;
        }
        System.out.println("pu Executed.");
        if (checkIfEnd() != 0) exit(checkIfEnd());
        return getReady();
    }

    public int[] putLeft() {
        if (getGridInfo(current.x - 1, current.y) != 2) {
            sample[current.y][current.x - 1] = 2;
        }
        System.out.println("pl Executed.");
        if (checkIfEnd() != 0) exit(checkIfEnd());
        return getReady();
    }

    public int[] putRight() {
        if (getGridInfo(current.x + 1, current.y) != 2) {
            sample[current.y][current.x + 1] = 2;
        }
        System.out.println("pr Executed.");
        if (checkIfEnd() != 0) exit(checkIfEnd());
        return getReady();
    }

    public int[] putDown() {
        if (getGridInfo(current.x, current.y + 1) != 2) {
            sample[current.y + 1][current.x] = 2;
        }
        System.out.println("pd Executed.");
        if (checkIfEnd() != 0) exit(checkIfEnd());
        return getReady();
    }

    //積みゲー判定.
    private int checkIfEnd() {

        if (sample[current.y][current.x] == 2) {
            return GAME_END_PUT_BLOCK;
        }

        if (getGridInfo(current.x, current.y - 1) == 2 && getGridInfo(current.x, current.y + 1) == 2) {
            if (getGridInfo(current.x - 1, current.y) == 2 && getGridInfo(current.x + 1, current.y) == 2) {
                return GAME_END_SURROUND_BLOCK;
            }
        }

        if (turn == 100) return GAME_END_TURN_END;

        return 0;
    }

    private int getGridInfo(int x, int y) {
        if (x < 0 || x > 14 || y < 0 || y > 16) {
            return 2;
        }
        if (sample[y][x] == 5) {
            return 0;
        }
        return sample[y][x];
    }

    private void exit(int code) {
        Map.getInstance().showHistory();

        if (Map.getInstance().getRound() == 1) {
            Map.getInstance().save();
        } else {
            //ファイルの削除.
            try {
                File file = new File("map.csv");
                if (file.exists()) file.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Dying code was " + code);
        isGameAlive = false;

        //死に様を記録.
        int[] value = getReady();
        for (int i = 0; i < value.length; i++) {
            System.out.print(value[i]);
            if ((i + 1) % 3 == 0) System.out.println();
        }
    }

    public boolean isGameAlive() {
        return isGameAlive;
    }
}
