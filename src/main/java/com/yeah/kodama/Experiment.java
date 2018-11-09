package com.yeah.kodama;

public class Experiment {
    //仮想自己対戦を行うためのクラス.

    private int turn;
    Environment environment;

    public Experiment() {
        turn = 0;
        environment = Environment.getInstance();
    }

}
