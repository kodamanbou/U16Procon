package com.yeah.kodama;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // write your code here
        Agent ai;
        Game target = null;

        System.out.println("モード？   0:学習フェーズ, 1:対戦フェーズ");
        int mode = Integer.parseInt(new Scanner(System.in).nextLine());
        System.out.println("Please enter epics of trying.");
        int epic = Integer.parseInt(new Scanner(System.in).nextLine());

        for (int i = 0; i < epic * 2; i++) {
            System.out.println("Epic :" + i);

            if (mode == 1) {
                System.out.println("ポート番号？");
                int port = Integer.parseInt(new Scanner(System.in).nextLine());
                System.out.println("IPアドレス？");
                String ip = new Scanner(System.in).nextLine();
                String team_name = "kodamanbou";

                target = new Client(ip, port, team_name);    //クライアント生成.
                ai = new Agent(target.getReady());           //エージェント生成.
            } else {
                target = new Environment(i % 2);
                ai = new Agent(target.getReady());
            }

            int turn = 0;
            int x = 0;
            int y = 0;      //相対座標.
            int[] value = new int[9];

            while (target.isGameAlive()) {

                if (turn != 0) value = target.getReady();
                ai.init(x, y);
                ai.evaluate(value);
                Action action = ai.chooseAction();

                switch (action) {
                    case WalkUp:
                        y--;
                        value = target.walkUp();
                        break;
                    case WalkRight:
                        x++;
                        value = target.walkRight();
                        break;
                    case WalkLeft:
                        x--;
                        value = target.walkLeft();
                        break;
                    case WalkDown:
                        y++;
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

                //Mapに追加.
                ai.add2Map(action, value);

                turn++;
            }
        }
    }
}
