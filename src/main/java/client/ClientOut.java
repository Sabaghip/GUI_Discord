package client;


import server.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientOut implements Runnable{

    private Socket socket;
    private ObjectOutputStream fOut;
    private ObjectInputStream fIn;

    public ClientOut(Socket socket,ObjectInputStream in, ObjectOutputStream out) throws IOException {
        this.socket = socket;
        fOut = out;
        fIn = in;
    }

    public void run(){
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String s = scanner.nextLine();
            try {
                fOut.writeObject(new Message(s));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendCommand(String command){
        try {
            fOut.writeObject(new Message(command));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
