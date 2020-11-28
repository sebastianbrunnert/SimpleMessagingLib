package de.sebastianbrunnert.simplemessaginglib;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public abstract class MessengerServer {

    private DatagramSocket socket;
    private byte[] buf = new byte[256];
    private Thread thread;
    private int port;
    private boolean running = false;

    public MessengerServer(int port){
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public boolean isRunning() {
        return running;
    }

    public void start() {
        try {
            this.socket = new DatagramSocket(getPort());
        } catch (SocketException e) {
            e.printStackTrace();
        }

        this.running = true;

        this.thread = new Thread(new Runnable() {
            public void run() {
                while (isRunning()){
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);

                    try {
                        socket.receive(packet);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    onReceive(new MessengerPacket(new String(packet.getData(), 0, packet.getLength())),packet.getAddress(),packet.getPort());
                }
            }
        });
        this.thread.start();
    }

    public void send(MessengerPacket messengerPacket, InetAddress inetAddress, int port) {
        String message = messengerPacket.toString();
        DatagramPacket datagramPacket = new DatagramPacket(message.getBytes(),message.getBytes().length,inetAddress,port);
        try {
            this.socket.send(datagramPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract void onReceive(MessengerPacket messengerPacket, InetAddress inetAddress, int port);

    public void stop() {
        this.thread.stop();
        this.thread = null;
        this.running = false;
        this.socket.close();
    }
}
