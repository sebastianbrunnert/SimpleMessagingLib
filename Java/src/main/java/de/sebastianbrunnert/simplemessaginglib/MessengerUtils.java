package de.sebastianbrunnert.simplemessaginglib;

import java.io.IOException;
import java.net.ServerSocket;

public class MessengerUtils {

    public static int getFreePort() throws IOException {
        ServerSocket socket = new ServerSocket(0);
        socket.close();
        return socket.getLocalPort();
    }

}
