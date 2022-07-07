package server.ServerChat;

import server.Member;
import server.ServerChat.Channel;
import server.ServerChat.Serverr;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class VoiceChannel extends Channel implements Serializable {

    public VoiceChannel(String name, Serverr serverr) {
        super(name,serverr);
    }

    @Override
    public void addMember(Member member) throws IOException {

    }

    @Override
    public void initialize(ArrayList<Member> allMembers) {

    }

}
