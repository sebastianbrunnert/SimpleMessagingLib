// Creating Server

const { createServer } = require("../server")

var server = createServer(4444, function(packet,rinfo) {
    console.log("Received from " + rinfo.address + ":" + rinfo.port + ": " + packet.title);
    server.send(rinfo.address, rinfo.port, "Hello back", new Map().set("test1","a"));
    server.send(rinfo.address, rinfo.port, "Hello back without Parameters");
});

// Creating Client

const { createClient } = require("../client")

var client = createClient("127.0.0.1", 4444, function(packet) {
    console.log("Received from Server: " + packet.title);
});

client.send("MyPacketWithParameters", new Map().set("test","a").set("test2","b"));
client.send("JustTitleNoParameters");
