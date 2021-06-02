package com.screensharek.net;

import com.screensharek.utils.ByteUtils;

import java.io.IOException;
import java.net.*;

public class Receiver {
    private String ipServer;
    private int portServer;
    private InetAddress iaServer;
    private DatagramSocket socketUDP;
    private boolean disconnect = false;

    /**
     * Get the ip server configured in sender.
     * @return ip of sender.
     */
    public String getIpServer() {
        return ipServer;
    }

    /**
     * Set ip of sender.
     * @param ipServer
     */
    public void setIpServer(String ipServer) {
        this.ipServer = ipServer;
    }

    /**
     * Get the port of sender.
     * @return the port  of sender.
     */
    public int getPortServer() {
        return portServer;
    }

    /**
     * Set the port of the sender.
     * @param portServer
     */
    public void setPortServer(int portServer) {
        this.portServer = portServer;
    }

    /**
     * Get the InetAddress of the sender.
     * @return InetAddress with sender configuration.
     */
    public InetAddress getIaServer() {
        return iaServer;
    }

    /**
     * Set InetAddress of sender.
     * @param iaServer
     */
    public void setIaServer(InetAddress iaServer) {
        this.iaServer = iaServer;
    }

    /**
     * Get the socket UDP of receiver.
     * @return DatagramSocket of receiver.
     */
    public DatagramSocket getSocketUDP() {
        return socketUDP;
    }

    /**
     * Set the socket of the receiver.
     * @param socketUDP
     */
    public void setSocketUDP(DatagramSocket socketUDP) {
        this.socketUDP = socketUDP;
    }

    /**
     * Connect the receiver with the sender.
     */
    public void connect() {
        if (iaServer == null) {
            try {
                iaServer = InetAddress.getByName(ipServer);
            } catch (UnknownHostException e) {
                System.out.println("Wrong ip");
                return;
            }
        }
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

    /**
     * Receive the split image from sender.
     * @return the split image without any order.
     */
    public byte[][] getSplitImage() {
        byte[][] imageMessy = null;
        try {
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
        return imageMessy;
    }

    /**
     * Return the number of packets that compose an image.
     * @param packet first byte of the packet.
     * @return the number of packets that compose an image.
     */
    public static int getNumberOfPackets(byte[] packet) {
        ByteUtils.resetBuffer();
        long packetInfo = ByteUtils.bytesToLong(packet);
        long numOfPackets = packetInfo & 15;
        return (int) numOfPackets;
    }

    /**
     * Check if the sender is disconnected
     * @return true if is disconnected or false if keeps connected.
     */
    public boolean checkDisconnect() {
        byte[] buff = new byte[] {1};
        DatagramPacket packet = new DatagramPacket(buff, buff.length, iaServer, portServer);
        try {
            if (disconnect)
                buff[0] = 2;
            System.out.println(buff[0]);
            socketUDP.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return disconnect;
    }

    /**
     * Disconnect receiver.
     */
    public void disconnect() {
        disconnect = true;
    }

    /**
     * Close the socket.
     */
    public void close() {
        socketUDP.close();
    }
}
