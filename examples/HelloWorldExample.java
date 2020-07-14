package de.sebastianbrunnert.simplemessaginglib;

import java.net.InetAddress;
import java.util.HashMap;

public class HelloWorldExample {

    public static void main(String[] args){
        server();
        client();
    }

    public static void server() {
        // Starting Server on port 4444
        MessengerServer messengerServer = new MessengerServer(4444) {
            // Message will be triggered when client send message to server
            @Override
            public void onReceive(MessengerPacket messengerPacket, InetAddress inetAddress, int port) {
                // Printing out the title of the package (will be 'Hello World')
                System.out.println("Received from Client at " + inetAddress.getHostAddress() + ":" + port + ": " + messengerPacket.getTitle());

                // Sending back package with title 'Hello back' to client
                HashMap<String,String> args = new HashMap<>(); // empty args map
                try {
                    send(new MessengerPacket("Hello back", args), inetAddress, port);
                } catch (MessengerPacketContainsSplitterException e) {
                    e.printStackTrace();
                }
            }
        };
        messengerServer.start();
    }

    public static void client() {
        // Creating client that will connect to server at 127.0.0.1:4444
        MessengerClient messengerClient = new MessengerClient("127.0.0.1", 4444) {
            @Override
            public void onReceive(MessengerPacket messengerPacket) {
                // Printing out the title of the package (will be 'Hello back')
                System.out.println("Received from Server: " + messengerPacket.getTitle());
            }
        };
        messengerClient.connect();

        // Sending package to server with title 'Hello World'
        HashMap<String,String> args = new HashMap<>(); // empty args map
        try {
            messengerClient.send(new MessengerPacket("Hello World",args));
        } catch (MessengerPacketContainsSplitterException e) {
            e.printStackTrace();
        }
    }

}
