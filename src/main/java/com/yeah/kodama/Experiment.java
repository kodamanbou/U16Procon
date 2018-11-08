package com.yeah.kodama;

public class Experiment implements Game {
    //仮想自己対戦を行うためのクラス.

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

    @Override
    public int[] walkUp() {
        return environment.walkUp();
    }

    @Override
    public int[] walkLeft() {
        return environment.walkLeft();
    }

    @Override
    public int[] walkRight() {
        return environment.walkRight();
    }

    @Override
    public int[] walkDown() {
        return environment.walkDown();
    }

    @Override
    public int[] lookUp() {
        return environment.lookUp();
    }

    @Override
    public int[] lookLeft() {
        return environment.lookLeft();
    }

    @Override
    public int[] lookRight() {
        return environment.lookRight();
    }

    @Override
    public int[] lookDown() {
        return environment.lookDown();
    }

    @Override
    public int[] searchUp() {
        return environment.searchUp();
    }

    @Override
    public int[] searchLeft() {
        return environment.searchLeft();
    }

    @Override
    public int[] searchRight() {
        return environment.searchRight();
    }

    @Override
    public int[] searchDown() {
        return environment.searchDown();
    }

    @Override
    public int[] putUp() {
        return environment.putUp();
    }

    @Override
    public int[] putLeft() {
        return environment.putLeft();
    }

    @Override
    public int[] putRight() {
        return environment.putRight();
    }

    @Override
    public int[] putDown() {
        return environment.putDown();
    }
}
