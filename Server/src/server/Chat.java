package server;

import server.MenuesHanling.InteractionWithUser;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

public class Chat implements Serializable {
    transient private ArrayList<Member> members;
    private ArrayList<String> membersNames;
    private ArrayList<Message> messages;
    transient private HashSet<User> inChat;
    private int id;
    private String firstMessage;
    private int pinnedMessageIndex = -1;
    private ArrayList<Message> files;
    private transient ArrayList<Member> isTyping;
    private ArrayList<String> limitedMemberNames;
    transient private static int a = 0;
    private static final long serialVersionUID = 70020325;

    //constructor for private chat
    public Chat(ArrayList<Member> members){
        this.members = members;
        membersNames = new ArrayList<>();
        for(Member m : members){
            membersNames.add(m.getUsername());
        }
        messages = new ArrayList<>();
        inChat = new HashSet<>();
        files = new ArrayList<>();
        isTyping = new ArrayList<>();
        id = a;
        a++;
    }

    public void addMember(Member member) throws IOException {
        membersNames.add(member.getUsername());
        this.members.add(member);
        Server.saveChats();
    }

    //constructor for channels chat
    public Chat(String firstMessage){
        this.membersNames = new ArrayList<>();
        this.members = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.inChat = new HashSet<>();
        this.firstMessage = firstMessage;
        isTyping = new ArrayList<>();
        limitedMemberNames = new ArrayList<>();
    }

    public static void setA(int b){
        a = b;
    }

    public int getId() {
        return id;
    }

    public void addNewMessage(Message m) throws IOException {
        messages.add(m);
        if (m.getContent() == null) {
            for (User temp : inChat) {
                InteractionWithUser.write(new Message(m.getAuthor() + " : " + m.getMessage()), temp);
            }
        }
        Server.saveChats();
    }

    public void addNewFile(Message message) throws IOException {
        files.add(message);
        for(User temp : inChat){
            InteractionWithUser.write(new Message(message.getAuthor() + " : sent file : " + message.getMessage()), temp);
        }
        Server.saveChats();
    }

    public byte[] getFileWithIndex(int n){
        return files.get(n).getContent();
    }
    public String getFileNameIndex(int n){
        return files.get(n).getMessage();
    }

    public void addWelcomeMessage(String name) throws IOException {
        messages.add(new Message("new member (" + name + ") joined.\n" + firstMessage, "server"));
        Server.saveChats();
    }

    public ArrayList<String> getMembersNames() {
        return membersNames;
    }
    public String showFiles(){
        int i = 1;
        StringBuilder str = new StringBuilder("");
        for(Message file : files){
            str.append(i).append(".").append(file.getMessage()).append("\n");
            i++;
        }
        return str.toString();
    }

    public int filesSize(){
        return files.size();
    }



    public boolean isFor(ArrayList<Member> members1){
        for(Member m : members){
            if(!members1.contains(m)){
                return false;
            }
        }
        return true;
    }

    public void newInChatMember(User u) throws IOException {
        if(membersNames.contains(u.getUserName()) && !inChat.contains(u)){
            inChat.add(u);
            if(messages.size() <= 15) {
                for (Message message : messages) {
                    InteractionWithUser.write(new Message(message.getAuthor() + " : " + message.getMessage() + "        " + message.reactionsToString()), u);
                }
            }
            else{
                for(int i = 0; i < 15; i++){
                    Message temp = messages.get(messages.size() - 15 + i);
                    InteractionWithUser.write(new Message(temp.getAuthor() + " : " + temp.getMessage() + "        " + temp.reactionsToString()), u);
                }
            }
        }
        for (Member m : isTyping){
            InteractionWithUser.write(new Message(m.getUsername() + " is not typing anymore."), u);
        }
    }
    public void removeInChatMember(User u){
        inChat.remove(u);
    }
    public void removeInChatMember(Member member){
        for(User u : inChat){
            if(u.getUserName().equals(member.getUsername())){
                inChat.remove(u);
                return;
            }
        }
    }

    public void initialize(ArrayList<Member> allMembers){
        members = new ArrayList<>();
        inChat = new HashSet<>();
        isTyping = new ArrayList<>();
        for(String member : membersNames){
            for(Member m : allMembers){
                if(m.getUsername().equals(member)){
                    this.members.add(m);
                    break;
                }
            }
        }
    }

    public void setMembersNames(ArrayList<String> membersNames) {
        this.membersNames = membersNames;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setPinnedMessage(int pinnedMessageIndex) throws IOException {
        this.pinnedMessageIndex = pinnedMessageIndex;
        Server.saveChats();
    }

    public Message getPinnedMessage() {
        if(pinnedMessageIndex == -1){
            return null;
        }
        return messages.get(pinnedMessageIndex);
    }

    public void isTyping(User u) throws IOException {
        isTyping.add(u.getMember());
        for(User online : inChat){
            if(online != u){
                InteractionWithUser.write(new Message(u.getUserName() + " is typing..."), online);
            }
        }
    }

    public void endTyping(User u) throws IOException {
        isTyping.remove(u.getMember());
        for(User online : inChat){
            if(online != u){
                InteractionWithUser.write(new Message(u.getUserName() + " is not typing anymore."), online);
            }
        }
    }

    public void addToLimitedList(String username){
        if(!limitedMemberNames.contains(username)){
            limitedMemberNames.add(username);
        }
    }

    public void removeFromLimitedList(String username){
        limitedMemberNames.remove(username);
    }

    public boolean isInLimitedList(String username){
        return limitedMemberNames.contains(username);
    }
}
