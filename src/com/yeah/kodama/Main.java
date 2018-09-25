package com.yeah.kodama;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println("ポート番号？");
        int port = Integer.parseInt(new Scanner(System.in).nextLine());
        System.out.println("IPアドレス？");
        String ip = new Scanner(System.in).nextLine();

        int turn = 0;
        Client target = new Client(ip, port);

        while (true) {
            if (turn >= 100) {
                break;
            }
            int[] value = target.getReady();

            turn++;
        }
    }
}
