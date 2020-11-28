class MessengerPacket {
    constructor(title,args) {
        if(title.includes(",,§se./././.br§,,")) throw "Messenger Packet Title contains splitter."
        if(Array.from(args.keys()).some(key=>key.includes(",,§se./././.br§,,"))) throw "Messenger Packet Arg Contains splitter."
        this.title = title;
        this.args = args;
    }

    toString() {
        var res = this.title;

        Array.from(this.args.keys()).forEach(key => {
            res += ",,§se./././.br§,," + key + ",,§se./././.br§,," + this.args.get(key);
        });

        return res;
    }
}

exports.resolvePacket = function(message) {
    var args = new Map();
    if(!message.includes(",,§se./././.br§,,")){
        return new MessengerPacket(message,args);
    }else{
        var splitted = message.split(",,§se./././.br§,,");

        var title = splitted[0];
        for(var i = 1; i < splitted.length; i+=2){
            args.set(splitted[i],splitted[i+1]);
        }

        return new MessengerPacket(title,args);
    }
}

exports.createPacket = function(title,map) {
    return new MessengerPacket(title,map);
}

exports.getMessage = function() {
    return "Hey";
}