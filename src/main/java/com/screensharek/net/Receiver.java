package com.screensharek.net;

import com.screensharek.utils.ByteUtils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Receiver {
    private String ipServer;
    private int portServer;
    private InetAddress iaServer;
    private DatagramSocket socketUDP;
    private boolean disconnect = false;

    public String getIpServer() {
        return ipServer;
    }

    public void setIpServer(String ipServer) {
        this.ipServer = ipServer;
    }

    public int getPortServer() {
        return portServer;
    }

    public void setPortServer(int portServer) {
        this.portServer = portServer;
    }

    public InetAddress getIaServer() {
        return iaServer;
    }

    public void setIaServer(InetAddress iaServer) {
        this.iaServer = iaServer;
    }

    public DatagramSocket getSocketUDP() {
        return socketUDP;
    }

    public void setSocketUDP(DatagramSocket socketUDP) {
        this.socketUDP = socketUDP;
    }

    public void connect() {
        if (iaServer == null)
            return;
        try {
            socketUDP = new DatagramSocket();
            byte[] buff = new byte[] {1};
            DatagramPacket packet = new DatagramPacket(buff, buff.length, iaServer, portServer);
            socketUDP.send(packet);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[][] getSplitImage() {
        try {
            byte[][] imageMessy = null;
            int count = 0;
            int packets = 0;
            do {
                byte[] buff = new byte[60000];
                DatagramPacket packet = new DatagramPacket(buff, buff.length);
                socketUDP.receive(packet);
                if (imageMessy == null) {
                    packets = getNumberOfPackets(new byte[]{0, 0, 0, 0, 0, 0, 0, buff[0]});
                    imageMessy = new byte[packets][];
                }
                imageMessy[count] = buff;
                count++;
            } while (count < packets);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getNumberOfPackets(byte[] packet) {
        ByteUtils.resetBuffer();
        long packetInfo = ByteUtils.bytesToLong(packet);
        long numOfPackets = packetInfo & 15;
        return (int) numOfPackets;
    }

    public boolean checkDisconnect() {
        byte[] buff = new byte[] {1};
        DatagramPacket packet = new DatagramPacket(buff, buff.length, iaServer, portServer);
        try {
            if (disconnect)
                buff[0] = 2;
            socketUDP.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return disconnect;
    }

    public void disconnect() {
        disconnect = true;
    }

    public void close() {
        socketUDP.close();
    }
}
