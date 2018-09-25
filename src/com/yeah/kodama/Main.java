package com.yeah.kodama;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println("ポート番号？");
        int port = Integer.parseInt(new Scanner(System.in).nextLine());
        System.out.println("IPアドレス？");
        String ip = new Scanner(System.in).nextLine();
        Client target = new Client(ip, port);
    }
}
