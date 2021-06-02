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

    /**
     * Initialize the sender with the ip of computer and the port default is 9999.
     */
    public void init() {
        takeIp();
        port = 9999;
    }

    /**
     * Get the ip of sender.
     * @return
     */
    public String getIp() {
        return ip;
    }

    /**
     * Set the ip of sender.
     * @param ip of sender.
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * Get the port of sender.
     * @return port of sender.
     */
    public int getPort() {
        return port;
    }

    /**
     * Set the port of sender.
     * @param port of sender.
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Get the ip of receiver.
     * @return ip of receiver.
     */
    public String getIpClient() {
        return ipClient;
    }

    /**
     * Set the ip of client.
     * @param ipClient
     */
    public void setIpClient(String ipClient) {
        this.ipClient = ipClient;
    }

    /**
     * Get port of client
     * @return the port of client.
     */
    public int getPortClient() {
        return portClient;
    }

    /**
     * Set the port of client.
     * @param portClient
     */
    public void setPortClient(int portClient) {
        this.portClient = portClient;
    }

    /**
     * Obtains the ip of the computer and put in ip attribute.
     */
    private void takeIp() {
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {

        }
    }

    /**
     * Start the socket and wait to get a petition of receiver for start sending the image and capture the
     * ip and port of receiver to send the images.
     */
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

    /**
     * Get the receiver address.
     * @param packet send by sender.
     */
    private void getClientAddress(DatagramPacket packet) {
        iaClient = packet.getAddress();
        ipClient = iaClient.getHostAddress();
        portClient = packet.getPort();
    }

    /**
     * Send de split image in packets.
     * @param splitImage matrix with the split image.
     */
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

    /**
     * Get response by receiver.
     * @return the response.
     */
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

    /**
     * Close the socket UDP.
     */
    public void close() {
        socketUDP.close();
    }
}
