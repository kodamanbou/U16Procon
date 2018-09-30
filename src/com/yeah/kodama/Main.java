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
            }

            turn++;
        }
    }
}
