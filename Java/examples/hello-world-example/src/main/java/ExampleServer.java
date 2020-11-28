import de.sebastianbrunnert.simplemessaginglib.MessengerPacket;
import de.sebastianbrunnert.simplemessaginglib.MessengerPacketContainsSplitterException;
import de.sebastianbrunnert.simplemessaginglib.MessengerServer;

import java.net.InetAddress;
import java.util.HashMap;

public class ExampleServer {

    public static void server() {
        // Starting Server on port 4444
        MessengerServer messengerServer = new MessengerServer(4444) {
            // Message will be triggered when client send message to server
            @Override
            public void onReceive(MessengerPacket messengerPacket, InetAddress inetAddress, int port) {
                // Printing out the title of the package (will be 'Hello World')
                System.out.println("Received from Client at " + inetAddress.getHostAddress() + ":" + port + ": " + messengerPacket.getTitle());

                // Sending back package with title 'Hello back' to client
                HashMap<String,String> args = new HashMap<String, String>(); // empty args map
                args.put("a","b");
                try {
                    send(new MessengerPacket("Hello back", args), inetAddress, port);
                } catch (MessengerPacketContainsSplitterException e) {
                    e.printStackTrace();
                }
            }
        };
        messengerServer.start();
    }

}
