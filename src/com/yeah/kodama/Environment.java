package com.yeah.kodama;

public final class Environment {
    public static final int FLOOR = 0;  //各定数を、それぞれのアクションを取った時の報酬(Rewards)とする。
    public static final int ENEMY = 1;
    public static final int WALL = 2;
    public static final int ITEM = 3;

    private int[][] state = new int[17][15];

    private static Environment theInstance;
    private Environment() {}

    public static Environment getInstance() {
        if (theInstance == null) {
            theInstance = new Environment();
        }
        return theInstance;
    }

}
