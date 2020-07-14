package de.sebastianbrunnert.simplemessaginglib;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public abstract class MessengerClient {

    private DatagramSocket socket;
    private InetAddress address;
    private int port;
    private Thread thread;
    private byte[] buf = new byte[256];

    public MessengerClient(String address, int port) {
        try {
            this.address = InetAddress.getByName(address);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        this.port = port;
    }

    public void connect() {
        try {
            this.socket = new DatagramSocket(MessengerUtils.getFreePort());
            this.socket.connect(address,port);

            this.thread = new Thread(new Runnable() {
                public void run() {
                    while (isConnected()){
                        DatagramPacket packet = new DatagramPacket(buf, buf.length);

                        try {
                            socket.receive(packet);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        onReceive(new MessengerPacket(new String(packet.getData(), 0, packet.getLength())));
                    }
                }
            });
            this.thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        this.thread.stop();
        this.thread = null;
        this.socket.disconnect();
    }

    public boolean isConnected() {
        return this.socket.isConnected();
    }

    public void send(MessengerPacket messengerPacket) {
        String message = messengerPacket.toString();
        DatagramPacket datagramPacket = new DatagramPacket(message.getBytes(),message.getBytes().length);
        try {
            this.socket.send(datagramPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract void onReceive(MessengerPacket messengerPacket);

}
