package com.yeah.kodama;

import java.io.*;
import java.net.Socket;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutionException;

public class Client {

    private Socket sock = null;
    private PrintWriter writer = null;
    private BufferedReader reader = null;

    public Client(String ip, int port, String team) {
        try {
            sock = new Socket(ip, port);
            Thread.sleep(100);
            System.out.println("接続できたお");
            writer = new PrintWriter(sock.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            writer.println(team);
            System.out.println("チーム名 : " + team);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    public void sendCommand(String cmd) {
        try {
            writer.println(cmd);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
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
        sendCommand("gr");
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
        } finally {
            close();
        }
        return value;
    }

    public int[] walkUp() {
        sendCommand("wu");
        return receive();
    }

    public int[] walkRight() {
        sendCommand("wr");
        return receive();
    }

    public int[] walkLeft() {
        sendCommand("wl");
        return receive();
    }

    public int[] walkDown() {
        sendCommand("wd");
        return receive();
    }

    public int[] lookUp() {
        sendCommand("lu");
        return receive();
    }

    public int[] lookRight() {
        sendCommand("lr");
        return receive();
    }

    public int[] lookLeft() {
        sendCommand("ll");
        return receive();
    }

    public int[] lookDown() {
        sendCommand("ld");
        return receive();
    }

    public int[] searchUp() {
        sendCommand("su");
        return receive();
    }

    public int[] searchRight() {
        sendCommand("sr");
        return receive();
    }

    public int[] searchLeft() {
        sendCommand("sl");
        return receive();
    }

    public int[] searchDown() {
        sendCommand("sd");
        return receive();
    }

    public int[] putUp() {
        sendCommand("pu");
        return receive();
    }

    public int[] putRight() {
        sendCommand("pr");
        return receive();
    }

    public int[] putLeft() {
        sendCommand("pl");
        return receive();
    }

    public int[] putDown() {
        sendCommand("pd");
        return receive();
    }

}
