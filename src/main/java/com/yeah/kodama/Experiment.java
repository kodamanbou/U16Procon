package com.yeah.kodama;

public class Experiment {
    //ゲームの進行を擬似的に再現する.試合進行クラス.

    private int turn;
    private boolean isGameInProgress;
    Environment environment;

    public Experiment() {
        turn = 0;
        isGameInProgress = true;
        environment = Environment.getInstance();
    }

    public boolean isGameInProgress() {
        return isGameInProgress;
    }

    public int[] getReady() {
        return environment.getReady();
    }
}
