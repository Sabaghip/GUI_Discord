package server;


import server.ServerChat.Channel;
import server.ServerChat.Serverr;
import server.ServerChat.TextChannel;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;


public class Server {
    private HashSet<User> users = new HashSet<>();
    static private ArrayList<Member> members = new ArrayList<>();
    static private ArrayList<Serverr> servers = new ArrayList<>();
    static private ArrayList<Chat> chats = new ArrayList<>();
    static FileHandling f = new FileHandling("Server/files/members.bin", "Server/files/servers.bin", "Server/files/chats.bin");


    public void run() throws IOException, ClassNotFoundException, UnsupportedAudioFileException, LineUnavailableException {
        addSavedInformations();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Scanner scanner = new Scanner(System.in);
                while (true) {
                    scanner.next();
                    try {
                        saveInformations();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("saved.");
                }
            }
        }).start();

        ServerSocket serverSocket = new ServerSocket(7878);
        while(true){
            Socket client = serverSocket.accept();
            User c = new User(client, this);
            Thread thread = new Thread(c);
            c.setThread(thread);
            thread.start();
        }
    }

    public static Member getMemberWithToken(int n){
        for(Member m : members){
            if(m.getToken() == n){
                return m;
            }
        }
        return null;
    }

    public void saveInformations() throws IOException {
        f.saveMembers(members);
        f.saveServers(servers);
        f.saveChats(chats);
    }

    public void addSavedInformations() throws IOException, ClassNotFoundException {
        members = f.getMembers();
        servers = f.getServers();
        chats = f.getChats();
        for(Member m : members){
            m.addServers(servers);
            Member.setA(members.get(members.size() -  1).getToken() + 1);
        }
        for(Chat chat : chats){
            chat.initialize(members);
        }
        for (Serverr serverr : servers){
            for(Channel channel : serverr.getChannels()){
                if(channel instanceof TextChannel){
                    ((TextChannel)channel).getChat().setMembersNames(serverr.getMembers());
                    channel.initialize(members);
                }
                channel.setServerr(serverr);
            }
        }
        try {
            Serverr.setA(servers.get(servers.size() -  1).getId() + 1);
            Chat.setA(chats.get(chats.size() - 1).getId() + 1);
        }catch (Exception e){};
    }


    public void removeUser(User user){
        users.remove(user);

    }

    public void addNewMember(Member m) throws IOException {
        members.add(m);
        f.saveMembers(members);
    }
    public Member logIn(User c, String username) {
        users.add(c);
        for(Member m : members){
            if(m.getUsername().equals(username)){
                return m;
            }
        }
        return null;
    }


    public boolean isValidMember(String username, String password){
        for(Member m : members){
            if(m.getUsername().equals(username) && m.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Member> getMembers() {
        return members;
    }

    public Member getMember(String name){
        for(Member m : members){
            if(m.getUsername().equals(name)){
                return m;
            }
        }
        return null;
    }

    public ArrayList<Serverr> getServers() {
        return servers;
    }

    public ArrayList<Chat> getChats() {
        return chats;
    }

    public void addNewChat(Chat chat) throws IOException {
        chats.add(chat);
        f.saveChats(chats);
    }


    public void addServerr(Serverr serverr) throws IOException {
        servers.add(serverr);
        f.saveServers(servers);
    }

    public static void saveServers() throws IOException {
        f.saveServers(servers);
    }
    public static void saveMembers() throws IOException {
        f.saveMembers(members);
    }
    public static void saveChats() throws IOException {
        f.saveChats(chats);
    }
}
