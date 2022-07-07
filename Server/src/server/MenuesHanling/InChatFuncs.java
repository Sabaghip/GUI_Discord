package server.MenuesHanling;

import server.Chat;
import server.Message;
import server.ServerChat.Channel;
import server.ServerChat.Serverr;
import server.ServerChat.TextChannel;
import server.User;

import java.io.IOException;
import java.util.ArrayList;

public class InChatFuncs {
    public static int getInChatChoice(Chat target, User u) throws IOException {
        return InteractionWithUser.getChoice(1, 6, "1.new message\n2.react\n3.pinned message\n4.send file\n5.download files\n6.back", u.getfOut(), u.getfIn(), u);
    }

    public static void reactToAMessageIn(User u, ArrayList<Message> messages) throws IOException {
        Message target = ShowLists.showLast15MessagesAndChooseOne(u, messages);
        if(target == null){
            return;
        }
        InteractionWithUser.write(new Message("which reaction ? \n1.like\n2.dislike\n3.laugh\n4.back"),u);
        int choice = InteractionWithUser.getChoice(1,4,"",u.getfOut(),u.getfIn(),u);
        switch (choice){
            case 1 :
                target.react(u.getUserName(), "like");
                return;
            case 2:
                target.react(u.getUserName(), "dislike");
                return;
            case 3:
                target.react(u.getUserName(), "laugh");
                return;
        }
    }

    public static void sendFile(User u, Chat target) throws IOException, ClassNotFoundException {
        InteractionWithUser.write(new Message("enter file path(#cancel for cancel) : "),u);
        Message m = InteractionWithUser.read(u);
        if(m.getMessage().equals("#cancel")){
            return ;
        }
        String path = m.getMessage();
        InteractionWithUser.write(new Message("enter name of file(#cancel for cancel) : "),u);
        m = InteractionWithUser.read(u);
        if(m.getMessage().equals("#cancel")){
            return ;
        }
        InteractionWithUser.write(new Message("%%!getFile:::" + path + ":::" + m.getMessage(),u.getUserName()),u);
        m = InteractionWithUser.read(u);
        if(m.getMessage().equals("%%!not found")){
            return;
        }
        target.addNewFile(m);
    }

    public static void downloadFile(User u, Chat target) throws IOException {
        InteractionWithUser.write(new Message(target.showFiles() + "\n" + (target.filesSize() + 1) + ".back"),u);
        int choice = InteractionWithUser.getChoice(1, target.filesSize() + 1,"", u.getfOut(),u.getfIn(),u);
        if(choice == target.filesSize() + 1){
            return;
        }
        InteractionWithUser.write(new Message(target.getFileWithIndex(choice - 1),"%%!" + target.getFileNameIndex(choice - 1), u.getUserName()),u);
    }

    public static User.allMenues pinMessage(User u, Serverr serverr) throws IOException, ClassNotFoundException {
        InteractionWithUser.write(new Message(serverr.channelsToString()), u);
        int n = serverr.channelsSize();
        InteractionWithUser.write(new Message("" + (n + 1) + ".back"), u);
        int choice = InteractionWithUser.getChoice(1, n + 1, "", u.getfOut(), u.getfIn(), u);
        if (choice == n + 1) {
            return ServerMenuHandling.adminsMenu(u, serverr);
        }
        Channel channel = serverr.getChannelWithIndex(choice - 1);
        Chat target = ((TextChannel) channel).getChat();
        ArrayList<Message> messages = target.getMessages();
        choice = ShowLists.showLast15MessagesAndChooseOneIndex(u,messages);
        if(choice == -1){
            return ServerMenuHandling.adminsMenu(u,serverr);
        }
        target.setPinnedMessage(choice);
        return User.allMenues.MAIN;
    }

}
