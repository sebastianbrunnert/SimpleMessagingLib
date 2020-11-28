import de.sebastianbrunnert.simplemessaginglib.MessengerClient;
import de.sebastianbrunnert.simplemessaginglib.MessengerPacket;
import de.sebastianbrunnert.simplemessaginglib.MessengerPacketContainsSplitterException;

import java.util.HashMap;

public class ExampleClient {

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
        HashMap<String,String> args = new HashMap<String, String>(); // empty args map
        try {
            messengerClient.send(new MessengerPacket("Hello World",args));
        } catch (MessengerPacketContainsSplitterException e) {
            e.printStackTrace();
        }
    }

}
