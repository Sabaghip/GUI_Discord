package server.ServerChat;

import server.Member;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class Channel implements Serializable {
    private static final long serialVersionUID = 70020380;
    private String name;
    private transient Serverr serverr;

    public Channel(String name, Serverr serverr){
        this.name = name;
        this.serverr = serverr;
    }

    public void setServerr(Serverr serverr){
        this.serverr = serverr;
    }

    public String getName() {
        return name;
    }

    public abstract void addMember(Member member) throws IOException;

    public void sendWelcomeMessage(String name) throws IOException {}

    public abstract void initialize(ArrayList<Member> allMembers);
}
