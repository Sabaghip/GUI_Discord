package server.ServerChat;

import server.Chat;
import server.Member;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class TextChannel extends Channel implements Serializable {

    private static final long serialVersionUID = 70020124;

    Chat chat;

    public TextChannel(String name, Serverr serverr, String firstMesasge) {
        super(name,serverr);
        chat = new Chat(firstMesasge);
    }

    public Chat getChat() {
        return chat;
    }

    public void sendWelcomeMessage(String name) throws IOException {
        chat.addWelcomeMessage(name);
    }
    @Override
    public void addMember(Member member) throws IOException {
        chat.addMember(member);
    }

    public void deleteMember(Member member){
        chat.removeInChatMember(member);
    }

    @Override
    public void initialize(ArrayList<Member> allMembers){
        chat.initialize(allMembers);
    }

}
