package server.MenuesHanling;

import server.Member;
import server.Message;
import server.Server;
import server.ServerChat.Serverr;
import server.User;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ShowLists {

    public static Member showFriendsAndChooseOne(User u) throws IOException {
        ArrayList<Integer> friends = u.getMember().getFriends();
        ObjectOutputStream fOut = u.getfOut();
        int i = 1;
        for (int token : friends) {
            Member m = Server.getMemberWithToken(token);
            InteractionWithUser.write(new Message("" + i + "." + m.getUsername() + "   " + m.getStatus()), u);
        }
        InteractionWithUser.write(new Message("" + (friends.size() + 1) + ".back"), u);
        int choice = InteractionWithUser.getChoice(1, friends.size() + 1, "", u.getfOut(), u.getfIn(), u);
        if (choice == (friends.size() + 1)) {
            return null;
        }
        return Server.getMemberWithToken(friends.get(choice - 1));
    }



    public static Message showLast15MessagesAndChooseOne(User u, ArrayList<Message> messages) throws IOException {
        int i = 1;
        Message target;
        if(messages.size() <= 15) {
            for (Message message : messages) {
                InteractionWithUser.write(new Message("" + i + "." + message.getMessage()), u);
                i++;
            }
            InteractionWithUser.write(new Message("" + i + ".back"), u);
            int choice = InteractionWithUser.getChoice(1,i,"",u.getfOut(),u.getfIn(),u);
            if(choice == i){
                return null;
            }
            target = messages.get(choice - 1);
        }
        else{
            int n = messages.size();
            for(int j = 0; j < 15; j++){
                InteractionWithUser.write(new Message("" + i + messages.get(n - 15 + j)),u);
                i++;
            }
            InteractionWithUser.write(new Message("" + i + ".back"), u);
            int choice = InteractionWithUser.getChoice(1,i,"",u.getfOut(),u.getfIn(),u);
            if(choice == i){
                return null;
            }
            target = messages.get(choice - 1);
        }
        return target;
    }

    public static int showLast15MessagesAndChooseOneIndex(User u, ArrayList<Message> messages) throws IOException, ClassNotFoundException {
        int i = 1,choice;
        if(messages.size() <= 15) {
            for (Message message : messages) {
                InteractionWithUser.write(new Message("" + i + "." + message.getAuthor() + " : " + message.getMessage()),u);
                i++;
            }
            InteractionWithUser.write(new Message("" + i +".back"),u);
            choice = InteractionWithUser.getChoice(1, i, "",u.getfOut(),u.getfIn(),u);
            if(choice == i){
                return -1;
            }
            return choice - 1;
        }
        else{
            for(int j = 0; j < 15; j++){
                Message temp = messages.get(messages.size() - 15 + j);
                InteractionWithUser.write(new Message("" + i + "." + temp.getAuthor() + " : " + temp.getMessage()), u);
                i++;
            }
            InteractionWithUser.write(new Message("" + i +".back"),u);
            choice = InteractionWithUser.getChoice(1, i, "",u.getfOut(),u.getfIn(),u);
            if(choice == i){
                return -1;
            }
            return messages.size() - 16 + choice;
        }
    }

    public static Serverr showServerrsandChooseOne(User u) throws IOException {
        int i = 1;
        ArrayList<Serverr> serverrs = u.getMember().getServers();
        for (Serverr serverr : serverrs) {
            InteractionWithUser.write(new Message("" + i + "." + serverr.getName()), u);
            i++;
        }
        InteractionWithUser.write(new Message("" + i + ".back"), u);
        int choice = InteractionWithUser.getChoice(1, i, "", u.getfOut(), u.getfIn(), u);
        if(choice == i){
            return null;
        }
        return serverrs.get(choice - 1);
    }
}
