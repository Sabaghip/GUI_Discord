package server;

import server.ServerChat.Serverr;

import java.io.*;
import java.util.ArrayList;

public class FileHandling {
    private String membersPath;
    private String serversPath;
    private String chatsPath;

    public FileHandling(String membersPath, String serversPath,String chatsPath){
        this.membersPath = membersPath;
        this.serversPath = serversPath;
        this.chatsPath = chatsPath;
    }


    public ArrayList<Member> getMembers() throws IOException, ClassNotFoundException {
        try(ObjectInputStream fIn = new ObjectInputStream(new FileInputStream(membersPath))) {
            Object temp = fIn.readObject();
            ArrayList<Member> members = (ArrayList<Member>) temp;
            return members;
        }
        catch (EOFException e){
            return new ArrayList<Member>();
        }
    }

    public void saveMembers(ArrayList<Member> members) throws IOException {
        ObjectOutputStream fOut= new ObjectOutputStream(new FileOutputStream(membersPath));
        fOut.writeObject(members);
    }


    public ArrayList<Serverr> getServers() throws IOException, ClassNotFoundException {
        try(ObjectInputStream fIn = new ObjectInputStream(new FileInputStream(serversPath))) {
            Object temp = fIn.readObject();
            ArrayList<Serverr> serverrs = (ArrayList<Serverr>) temp;
            return serverrs;
        }
        catch(EOFException e){
            return new ArrayList<Serverr>();
        }
    }

    public void saveServers(ArrayList<Serverr> serverrs) throws IOException {
        ObjectOutputStream fOut= new ObjectOutputStream(new FileOutputStream(serversPath));
        fOut.writeObject(serverrs);
    }

    public ArrayList<Chat> getChats() throws IOException, ClassNotFoundException {
        try(ObjectInputStream fIn = new ObjectInputStream(new FileInputStream(chatsPath))) {
            Object temp = fIn.readObject();
            ArrayList<Chat> chats = (ArrayList<Chat>) temp;
            return chats;
        }
        catch(EOFException e){
            System.out.println(e.getMessage());
            return new ArrayList<Chat>();
        }
    }

    public void saveChats(ArrayList<Chat> chats) throws IOException {
        ObjectOutputStream fOut= new ObjectOutputStream(new FileOutputStream(chatsPath));
        fOut.writeObject(chats);
    }
}
