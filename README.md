# SimpleMessagingLib
This is a simple libary which allows you to make messaging easy at your project. At the moment you can just use it in Java. You build up UDP-Server to which multiple Clients can connect, send their messages and receive messages from the Server.

# How to use - Java
Actually, this libary is simple to use, so i would recommend you to look at the example(s).
After integrating this libary to your project you can: 
##### Startup Server
####
```
MessengerServer messengerServer = new MessengerServer(4444) { // this creates the Server
    @Override
    public void onReceive(MessengerPacket messengerPacket, InetAddress inetAddress, int port) { // this message gets triggered when receiving data from a Client
        
    }
};
messengerServer.start(); // this actually starts the Server
```
##### Create a Client
####
```
MessengerClient messengerClient = new MessengerClient("127.0.0.1", 4444) { // this creates a Client
    @Override
    public void onReceive(MessengerPacket messengerPacket) { // this message gets triggered when receiving data from a Server

    }
};
messengerClient.connect(); // this actually starts the Client
```
##### Creating a Messaging Packet
####
```
HashMap<String,String> args = new HashMap<>(); // this map represents the arguments that will be send
args.put("testArg","testKey") // use the HashMap just as known
try {
    MessengerPacket messengerPacket = new MessengerPacket("Title of the Packet", args); // creating the actuall packet
} catch (MessengerPacketContainsSplitterException e) {
    e.printStackTrace();
}
```
##### Sending a Messaging Packet from Client to Server
####
```
// @arg1 messengerPacket (MessengerPacket)
messengerClient.send(messengerPacket);
```
##### Sending a Messaging Packet from Server to Client
####
```
// @arg1 messengerPacket (MessengerPacket)
// @arg2 inetAdress (InetAdress)
// @arg3 port (int)
messengerSever.send(messengerPacket,inetAddress,port)

// you can get the InetAdress and the port from the abstract function
onReceive(MessengerPacket messengerPacket, InetAddress inetAddress, int port)
// look at the example to see concret uses of this technique
```



# Todo
- Broadcast Function
- Other Languages 
- More examples
- Publish on Maven-Repository

# Warning
No encryption implemented!
