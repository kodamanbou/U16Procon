package com.yeah.kodama;

public interface Game {

    int[] getReady();
    int[] walkUp();
    int[] walkLeft();
    int[] walkRight();
    int[] walkDown();
    int[] lookUp();
    int[] lookLeft();
    int[] lookRight();
    int[] lookDown();
    int[] searchUp();
    int[] searchLeft();
    int[] searchRight();
    int[] searchDown();
    int[] putUp();
    int[] putLeft();
    int[] putRight();
    int[] putDown();
    boolean isGameAlive();
}
