const dgram = require("dgram");
const packetManager = require("./packetManager");

class Client {

    constructor(address,port,receiveListener) {
        this.address = address;
        this.port = port;
        this.client = dgram.createSocket('udp4');
        this.client.on("message", (message) => {
            receiveListener(packetManager.resolvePacket(message.toString()));
        });
    }

    send(title,args) {
        if(args == null) {
            this.client.send(Buffer.from(title), this.port, this.address);
        } else {
            this.client.send(Buffer.from(packetManager.createPacket(title,args).toString()), this.port, this.address);
        }
    }
}

exports.createClient = function(address,port,receiveListener) {
    return new Client(address,port,receiveListener);
}