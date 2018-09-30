package com.yeah.kodama;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println("ポート番号？");
        int port = Integer.parseInt(new Scanner(System.in).nextLine());
        System.out.println("IPアドレス？");
        String ip = new Scanner(System.in).nextLine();
        System.out.println("チーム名？");
        String team_name = new Scanner(System.in).nextLine();

        int turn = 0;
        Client target = new Client(ip, port, team_name);
        Agent ai = new Agent();

        while (true) {

            if (turn >= 100) {
                break;
            }
            int[] value = target.getReady();
            ai.init();
            ai.infoSearch(value);

            switch (ai.chooseAction()) {
                case WalkUp:
                    value = target.walkUp();
                    break;
                case WalkRight:
                    value = target.walkRight();
                    break;
                case WalkLeft:
                    value = target.walkLeft();
                    break;
                case WalkDown:
                    value = target.walkDown();
                    break;
                case LookUp:
                    value = target.lookUp();
                    break;
                case LookRight:
                    value = target.lookRight();
                    break;
                case LookLeft:
                    value = target.lookLeft();
                    break;
                case LookDown:
                    value = target.lookDown();
                    break;
                case SearchUp:
                    value = target.searchUp();
                    break;
                case SearchRight:
                    value = target.searchRight();
                    break;
                case SearchLeft:
                    value = target.searchLeft();
                    break;
                case SearchDown:
                    value = target.searchDown();
                    break;
                case PutUp:
                    value = target.putUp();
                    break;
                case PutRight:
                    value = target.putRight();
                    break;
                case PutLeft:
                    value = target.putLeft();
                    break;
                case PutDown:
                    value = target.putDown();
                    break;
            }

            turn++;
        }
    }
}
