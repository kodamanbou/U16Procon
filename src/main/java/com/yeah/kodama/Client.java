package com.yeah.kodama;

import java.io.*;
import java.net.Socket;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutionException;

public class Client implements Game {

    private Socket sock = null;
    private BufferedReader reader = null;
    private OutputStream os = null;
    private boolean isGameAlive = true;

    public Client(String ip, int port, String team) {
        try {
            sock = new Socket(ip, port);
            Thread.sleep(100);
            System.out.println("接続が完了しました");
            os = sock.getOutputStream();
            reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            os.write(team.getBytes("Shift-JIS"));
            System.out.println("チーム名 : " + team);
            isGameAlive = true;
        } catch (Exception e) {
            e.printStackTrace();
            close();
        }
    }

    private boolean checkIfStart() {
        try {
            String atmark = reader.readLine();
            if (atmark.equals("@")) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void sendCommand(String cmd) {
        try {
            os.write((cmd + "\r\n").getBytes("Shift-JIS"));
        } catch (Exception e) {
            e.printStackTrace();
            close();
        }
        System.out.println(cmd + " executed.");
    }

    private void close() {
        try {
            if (sock != null) {
                sock.close();
            }
            if (os != null) {
                os.close();
            }
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    public int[] getReady() {
        if (checkIfStart()) {
            sendCommand("gr");
        } else {
            System.exit(1);
        }
        return receive();
    }

    private int[] receive() {
        int[] value = new int[9];
        try {
            String line = reader.readLine();
            if (line == null) exit();
            String[] data = line.split("");

            if (data[0].equals("0")) exit();

            for (int i = 0; i < 9; i++) {
                value[i] = Integer.parseInt(data[i + 1]);
            }
        } catch (IOException ie) {
            ie.printStackTrace();
            close();
        }
        return value;
    }

    private void exit() {
        Map.getInstance().showHistory();

        if (Map.getInstance().getRound() == 1) {
            Map.getInstance().save();
        } else {
            //ファイルの削除.
            try {
                File file = new File("map.csv");
                if (file.exists()) file.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        isGameAlive = false;
    }

    public boolean isGameAlive() {
        return isGameAlive;
    }

    public int[] walkUp() {
        sendCommand("wu");
        int[] result = receive();
        sendCommand("#");
        return result;
    }

    public int[] walkRight() {
        sendCommand("wr");
        int[] result = receive();
        sendCommand("#");
        return result;
    }

    public int[] walkLeft() {
        sendCommand("wl");
        int[] result = receive();
        sendCommand("#");
        return result;
    }

    public int[] walkDown() {
        sendCommand("wd");
        int[] result = receive();
        sendCommand("#");
        return result;
    }

    public int[] lookUp() {
        sendCommand("lu");
        int[] result = receive();
        sendCommand("#");
        return result;
    }

    public int[] lookRight() {
        sendCommand("lr");
        int[] result = receive();
        sendCommand("#");
        return result;
    }

    public int[] lookLeft() {
        sendCommand("ll");
        int[] result = receive();
        sendCommand("#");
        return result;
    }

    public int[] lookDown() {
        sendCommand("ld");
        int[] result = receive();
        sendCommand("#");
        return result;
    }

    public int[] searchUp() {
        sendCommand("su");
        int[] result = receive();
        sendCommand("#");
        return result;
    }

    public int[] searchRight() {
        sendCommand("sr");
        int[] result = receive();
        sendCommand("#");
        return result;
    }

    public int[] searchLeft() {
        sendCommand("sl");
        int[] result = receive();
        sendCommand("#");
        return result;
    }

    public int[] searchDown() {
        sendCommand("sd");
        int[] result = receive();
        sendCommand("#");
        return result;
    }

    public int[] putUp() {
        sendCommand("pu");
        int[] result = receive();
        sendCommand("#");
        return result;
    }

    public int[] putRight() {
        sendCommand("pr");
        int[] result = receive();
        sendCommand("#");
        return result;
    }

    public int[] putLeft() {
        sendCommand("pl");
        int[] result = receive();
        sendCommand("#");
        return result;
    }

    public int[] putDown() {
        sendCommand("pd");
        int[] result = receive();
        sendCommand("#");
        return result;
    }

}
