package server.ServerChat;

import server.Member;
import server.Server;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Serverr implements Serializable {
    private int id;
    transient private static int a = 0;
    private String name;
    private String ownerName;
    private HashMap<String, Naghsh> adminsNames;
    private ArrayList<String> members;
    private ArrayList<Channel> channels;

    private static final long serialVersionUID = 70020330;

    public Serverr(String name ,String ownerName){
        id = a;
        a++;
        this.name = name;
        this.ownerName = ownerName;
        this.adminsNames = new HashMap<>();
        this.members = new ArrayList<>();
        this.channels = new ArrayList<>();
        adminsNames.put(ownerName, new Naghsh("Owner",true,true,true,true,true,true,true,true));
    }

    public static void setA(int n){
        a = n;
    }
    public int getId() {
        return id;
    }

    public ArrayList<Channel> getChannels(){
        return channels;
    }


    public String getName() {
        return name;
    }

    public String membersToString(Server server){
        StringBuilder str = new StringBuilder("");
        int i = 1;
        for(String member : members){
            str.append("\n").append(i).append(".").append(member).append("   ").append(server.getMember(member).getStatus());
            i++;
        }
        return str.toString();
    }

    public int membersSize(){
        return members.size();
    }

    public String channelsToString(){
        StringBuilder str = new StringBuilder("");
        int i = 1;
        for(Channel channel: channels){
            str.append("\n").append(i).append(".").append(channel.getName());
            i++;
        }
        return str.toString();
    }

    public int channelsSize(){
        return channels.size();
    }

    public String getMemberWithIndex(int n){
        return members.get(n);
    }

    public Channel getChannelWithIndex(int n){
        return channels.get(n);
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void addChannel(Channel channel) throws IOException {
        channels.add(channel);
        Server.saveServers();
    }

    public  boolean isInServerr(String name){
        if(members.contains(name)){
            return true;
        }
        return false;
    }

    public void addMember(Member member) throws IOException {
        members.add(member.getUsername());
        member.addServerr(this);
        for(Channel channel : channels){
            channel.addMember(member);
            channel.sendWelcomeMessage(member.getUsername());
        }
        Server.saveServers();
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public void addAdmin(String username,String name,boolean createChannel, boolean deleteChannel, boolean deleteMember,boolean limitMembers, boolean banMembers,boolean changeName, boolean historyChecking,boolean pinMessage) throws IOException {
        adminsNames.remove(username);
        adminsNames.put(username, new Naghsh(name,createChannel, deleteChannel, deleteMember, limitMembers, banMembers, changeName, historyChecking, pinMessage));
        Server.saveServers();
    }

    public Naghsh getNaghshOf(String name){
        return adminsNames.get(name);
    }

    public boolean isAdmin(String name){
        return adminsNames.containsKey(name);
    }

    public void setName(String name) throws IOException {
        this.name = name;
        Server.saveServers();
    }

    public void deleteChannel(Channel channel) throws IOException {
        channels.remove(channel);
        Server.saveServers();
    }

    public void deleteMember(Member member) throws IOException {
        members.remove(member.getUsername());
        for(Channel channel : channels){
            if(channel instanceof TextChannel){
                ((TextChannel) channel).deleteMember(member);
            }
        }
        Server.saveServers();
        member.deleteServerr(this);
    }
}
