package model.client;


import model.other.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientOut{

    private Socket socket;
    private ObjectOutputStream fOut;
    private ObjectInputStream fIn;

    public ClientOut(Socket socket,ObjectInputStream in, ObjectOutputStream out) throws IOException {
        this.socket = socket;
        fOut = out;
        fIn = in;
    }


    public void sendCommand(String command){
        try {
            fOut.writeObject(new Message(command));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
