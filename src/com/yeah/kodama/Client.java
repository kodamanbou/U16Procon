package com.yeah.kodama;

import java.io.*;
import java.net.Socket;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutionException;

public class Client {

    private Socket sock = null;
    private PrintWriter writer = null;
    private BufferedReader reader = null;

    public Client(String ip, int port) {
        try {
            sock = new Socket(ip, port);
            writer = new PrintWriter(sock.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        } catch (Exception e) {
            close();
            e.printStackTrace();
        }
    }

    public void sendCommand(String cmd) {
        writer.println(cmd);
    }

    public void close() {
        try {
            if (sock != null) {
                sock.close();
            }
            if (writer != null) {
                writer.close();
            }
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    public int[] getReady() {
        return receive();
    }

    public int[] receive() {
        int[] value = new int[9];
        try {
            String[] data = reader.readLine().split("");
            for (int i = 0; i < 9; i++) {
                value[i] = Integer.parseInt(data[i + 1]);
            }
        } catch (IOException ie) {
            ie.printStackTrace();
        }
        return value;
    }

    public void WalkUp() {
        sendCommand("wu");
    }

}
