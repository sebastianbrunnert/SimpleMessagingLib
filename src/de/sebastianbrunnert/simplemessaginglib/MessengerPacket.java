package de.sebastianbrunnert.simplemessaginglib;


import java.util.HashMap;

public class MessengerPacket {

    private String title;
    private HashMap<String, String> args;

    public MessengerPacket(String message){

        this.args = new HashMap<String, String>();

        if(!message.contains(",,§se./././.br§,,")){
            this.title = message;
        }else{
            String[] splitted = message.split(",,§se./././.br§,,");

            this.title = splitted[0];
            for(int i = 1; i < splitted.length; i+=2){
                args.put(splitted[i],splitted[i+1]);
            }
        }
    }

    public MessengerPacket(String title, HashMap<String, String> args) throws MessengerPacketContainsSplitterException {
        if(title.contains(",,§se./././.br§,,")){
            throw new MessengerPacketContainsSplitterException();
        }
        for(String key : args.keySet()){
            if(key.contains(",,§se./././.br§,,") || args.get(key).contains(",,§se./././.br§,,")){
                throw new MessengerPacketContainsSplitterException();
            }
        }

        this.title = title;
        this.args = args;
    }

    public String getTitle() {
        return title;
    }

    public HashMap<String, String> getArgs() {
        return args;
    }

    @Override
    public String toString() {
        String res = this.title;

        for(String key : this.getArgs().keySet()){
            res += ",,§se./././.br§,," + key + ",,§se./././.br§,," + this.getArgs().get(key);
        }

        return res;
    }
}
