const dgram = require("dgram");
const packetManager = require("./packetManager");

class Server {

    constructor(port,receiveListener) {
        this.port = port;
        this.socket = dgram.createSocket('udp4');
        this.socket.bind(port);
        this.socket.on("message", (message,rinfo) => {
            receiveListener(packetManager.resolvePacket(message.toString()),rinfo);
        });
    }

    send(address,port,title,args) {
        if(args == null) {
            this.socket.send(Buffer.from(title), port, address);
        } else {
            this.socket.send(Buffer.from(packetManager.createPacket(title,args).toString()), port, address);
        }
    }
}

exports.createServer = function(port,receiveListener) {
    return new Server(port,receiveListener);
}