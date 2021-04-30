package com.screensharek.net;

import java.io.IOException;
import java.net.*;

public class Sender {
    private String ip;
    private int port;
    private String ipClient;
    private int portClient;
    private DatagramSocket socketUDP;
    private InetAddress iaClient;

    public void init() {
        takeIp();
        port = 9999;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getIpClient() {
        return ipClient;
    }

    public void setIpClient(String ipClient) {
        this.ipClient = ipClient;
    }

    public int getPortClient() {
        return portClient;
    }

    public void setPortClient(int portClient) {
        this.portClient = portClient;
    }

    private void takeIp() {
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void startShare() {
        try {
            SocketAddress sa = new InetSocketAddress(ip, port);
            socketUDP = new DatagramSocket(sa);
            byte[] buff = new byte[1];
            DatagramPacket packet = new DatagramPacket(buff, buff.length);
            socketUDP.receive(packet);
            if (buff[0] == 1) {
                getClientAddress(packet);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getClientAddress(DatagramPacket packet) {
        iaClient = packet.getAddress();
        ipClient = iaClient.getHostAddress();
        portClient = packet.getPort();
    }

    public void sendImage(byte[][] splitImage) {
        for (int i = 0; i < splitImage.length; i++) {
            try {
                DatagramPacket packet = new DatagramPacket(splitImage[i], splitImage[i].length, iaClient, portClient);
                socketUDP.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int getResponse() {
        int response;
        byte[] buff = new byte[1];
        DatagramPacket packet = new DatagramPacket(buff, buff.length);
        try {
            socketUDP.receive(packet);
            response = packet.getData()[0];
        } catch (IOException e) {
            response = 2;
        }
        return response;
    }

    public void close() {
        socketUDP.close();
    }
}
