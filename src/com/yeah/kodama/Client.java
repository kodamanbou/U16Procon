package com.yeah.kodama;

import java.io.*;
import java.net.Socket;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutionException;

public class Client {

    private Socket sock = null;
    private PrintWriter writer = null;
    private InputStream is = null;

    public Client(String ip, int port) {
        try {
            sock = new Socket(ip, port);
            writer = new PrintWriter(sock.getOutputStream(), true);
            is = sock.getInputStream();
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
        byte[] data = new byte[1024];
        int[] value = new int[9];
        try {
            while (is.available() == 0);
            while (is.read(data) != -1);
        } catch (IOException ie) {
            ie.printStackTrace();
        }
        return value;
    }

}
