package server.MenuesHanling;

import server.*;
import server.ServerChat.Channel;
import server.ServerChat.Serverr;
import server.ServerChat.TextChannel;
import server.ServerChat.VoiceChannel;

import java.io.IOException;
import java.util.ArrayList;

public class ServerMenuHandling {

    public static User.allMenues newServerMenu(User u) throws IOException, ClassNotFoundException {
        InteractionWithUser.write(new Message("name : "), u);
        String name = InteractionWithUser.read(u).getMessage();
        Serverr serverr = new Serverr(name, u.getUserName());
        serverr.addMember(u.getMember());
        u.getServer().addServerr(serverr);
        return User.allMenues.MAIN;
    }

    public static User.allMenues allServersMenu(User u) throws IOException, ClassNotFoundException {
        Serverr serverr = ShowLists.showServerrsandChooseOne(u);
        if (serverr == null) {
            return User.allMenues.SERVERS;
        }
        return chosenServerrMenu(u, serverr);
    }


    public static User.allMenues chosenServerrMenu(User u, Serverr serverr) throws IOException, ClassNotFoundException {
        int choice;
        if (serverr.getOwnerName().equals(u.getUserName()) || serverr.isAdmin(u.getUserName())) {
            choice = InteractionWithUser.getChoice(1, 5, "<<name : " + serverr.getName() + ">>\n<<owner : " + serverr.getOwnerName() + ">>\n1.Channels\n2.Members\n3.Add member\n4.Back\n5.admins menu", u.getfOut(), u.getfIn(), u);
        } else {
            choice = InteractionWithUser.getChoice(1, 4, "<<name : " + serverr.getName() + ">>\n<<owner : " + serverr.getOwnerName() + ">>\n1.Channels\n2.Members\n3.Add member\n4.Back", u.getfOut(), u.getfIn(), u);
        }
        switch (choice) {
            case 1:
                return channelsOfServerMenu(u, serverr);
            case 2:
                return membersOfServerrMenu(u, serverr);
            case 3:
                return addNewMemberToServerr(u,serverr);
            case 4:
                return User.allMenues.SERVERS;
            case 5:
                return adminsMenu(u, serverr);
        }
        return User.allMenues.MAIN;
    }

    public static User.allMenues adminsMenu(User u, Serverr serverr) throws IOException, ClassNotFoundException {
        int choice = InteractionWithUser.getChoice(1,11,"1.new channel\n2.change name\n3.add new admin\n4.delete channel\n5.delete member\n6.limit member\n7.ban member\n8.check history\n9.pin message\n10.delete server\n11.back",u.getfOut(),u.getfIn(),u);
        switch (choice){
            case 1 :
                System.out.println(u.getUserName());
                if(serverr.getNaghshOf(u.getUserName()).canCreateChanel()) {
                    return createNewChannel(u, serverr);
                }
                else {
                    InteractionWithUser.write(new Message("you cant do this."), u);
                }
                break;
            case 2 :
                if(serverr.getNaghshOf(u.getUserName()).canChangeName()) {
                    return changeServerName(u, serverr);
                }
                else {
                    InteractionWithUser.write(new Message("you cant do this."), u);
                }
                break;
            case 3 :
                if(serverr.getOwnerName().equals(u.getUserName())) {
                    return addNewAdmin(u, serverr);
                }
                else {
                    InteractionWithUser.write(new Message("you cant do this."), u);
                }
                break;
            case 4 :
                if(serverr.getNaghshOf(u.getUserName()).canDeleteChanel()) {
                    return deleteChannel(u, serverr);
                }
                else {
                    InteractionWithUser.write(new Message("you cant do this."), u);
                }
                break;
            case 5 :
                if(serverr.getNaghshOf(u.getUserName()).canDeleteMember()) {
                    return deleteMember(u, serverr);
                }
                else {
                    InteractionWithUser.write(new Message("you cant do this."), u);
                }
                break;
            case 6 :
                if(serverr.getNaghshOf(u.getUserName()).canLimitMembers()) {
                    return limitMember(u, serverr);
                }
                else {
                    InteractionWithUser.write(new Message("you cant do this."), u);
                }
                break;
            case 7 :
                if(serverr.getNaghshOf(u.getUserName()).canBanMembers()) {
                    return banMember(u, serverr);
                }
                else {
                    InteractionWithUser.write(new Message("you cant do this."), u);
                }
                break;
            case 8 :
                if(serverr.getNaghshOf(u.getUserName()).canCheckHistory()) {
                    return checkHistory(u, serverr);
                }
                else {
                    InteractionWithUser.write(new Message("you cant do this."), u);
                }
                break;
            case 9 :
                if(serverr.getNaghshOf(u.getUserName()).canPinMessage()) {
                    return InChatFuncs.pinMessage(u, serverr);
                }
                else {
                    InteractionWithUser.write(new Message("you cant do this."), u);
                }
                break;
            case 10 :
                if(serverr.getOwnerName().equals(u.getUserName())) {
                    ArrayList<String> membersNames = serverr.getMembers();
                    for(String memberName : membersNames){
                        u.getServer().getMember(memberName).deleteServerr(serverr);
                    }
                    u.getServer().getServers().remove(serverr);
                }
                else {
                    InteractionWithUser.write(new Message("you cant do this."), u);
                }
                break;
            case 11 :
                return User.allMenues.SERVERS;
        }
        return User.allMenues.SERVERS;
    }


    public static User.allMenues changeServerName(User u, Serverr serverr) throws IOException, ClassNotFoundException {
        InteractionWithUser.write(new Message("enter name : (nothing for cancel)"),u);
        String name = InteractionWithUser.read(u).getMessage();
        if(name == null || name.equals("")){
            return User.allMenues.SERVERS;
        }
        serverr.setName(name);
        return User.allMenues.MAIN;
    }

    public static User.allMenues deleteChannel(User u, Serverr serverr) throws IOException, ClassNotFoundException {
        InteractionWithUser.write(new Message(serverr.channelsToString()), u);
        int n = serverr.channelsSize();
        InteractionWithUser.write(new Message("" + (n + 1) + ".back"), u);
        int choice = InteractionWithUser.getChoice(1, n + 1, "", u.getfOut(), u.getfIn(), u);
        if (choice == n + 1) {
            return adminsMenu(u, serverr);
        }
        Channel channel = serverr.getChannelWithIndex(choice - 1);
        serverr.deleteChannel(channel);
        return adminsMenu(u,serverr);
    }

    public static User.allMenues deleteMember(User u, Serverr serverr) throws IOException, ClassNotFoundException {
        InteractionWithUser.write(new Message(serverr.membersToString(u.getServer())), u);
        int n = serverr.membersSize();
        InteractionWithUser.write(new Message("" + (n + 1) + "back"), u);
        int choice = InteractionWithUser.getChoice(1, n + 1, "", u.getfOut(), u.getfIn(), u);
        if (choice == n + 1) {
            return adminsMenu(u, serverr);
        }
        Member member = u.getServer().getMember(serverr.getMemberWithIndex(choice - 1));
        serverr.deleteMember(member);
        return User.allMenues.MAIN;
    }

    public static User.allMenues limitMember(User u, Serverr serverr) throws IOException, ClassNotFoundException {
        InteractionWithUser.write(new Message(serverr.membersToString(u.getServer())), u);
        int n = serverr.membersSize();
        InteractionWithUser.write(new Message("" + (n + 1) + ".back"), u);
        int choice = InteractionWithUser.getChoice(1, n + 1, "", u.getfOut(), u.getfIn(), u);
        if (choice == n + 1) {
            return adminsMenu(u, serverr);
        }
        Member member = u.getServer().getMember(serverr.getMemberWithIndex(choice - 1));


        InteractionWithUser.write(new Message(serverr.channelsToString()), u);
        n = serverr.channelsSize();
        InteractionWithUser.write(new Message("" + (n + 1) + ".back"), u);
        choice = InteractionWithUser.getChoice(1, n + 1, "", u.getfOut(), u.getfIn(), u);
        if (choice == n + 1) {
            return adminsMenu(u, serverr);
        }
        Channel channel = serverr.getChannelWithIndex(choice - 1);
        Chat target = ((TextChannel) channel).getChat();
        InteractionWithUser.write(new Message("add to or remove from ban list\n1.add\n2.remove\n3.back : "),u);
        choice = InteractionWithUser.getChoice(1,3,"",u.getfOut(), u.getfIn(),u);
        if(choice == 3){
            return adminsMenu(u,serverr);
        }
        if(choice == 1){
            target.addToLimitedList(member.getUsername());
        }
        else{
            target.removeFromLimitedList(member.getUsername());
        }
        return adminsMenu(u,serverr);
    }

    public static User.allMenues banMember(User u, Serverr serverr) throws IOException, ClassNotFoundException {
        InteractionWithUser.write(new Message(serverr.membersToString(u.getServer())), u);
        int n = serverr.membersSize();
        InteractionWithUser.write(new Message("" + (n + 1) + "back"), u);
        int choice = InteractionWithUser.getChoice(1, n + 1, "", u.getfOut(), u.getfIn(), u);
        if (choice == n + 1) {
            return adminsMenu(u, serverr);
        }
        Member member = u.getServer().getMember(serverr.getMemberWithIndex(choice - 1));
        InteractionWithUser.write(new Message("add to or remove from ban list\n1.add\n2.remove\n3.back : "),u);
        choice = InteractionWithUser.getChoice(1,3,"",u.getfOut(), u.getfIn(),u);
        if(choice == 3){
            return adminsMenu(u,serverr);
        }
        for(Channel c : serverr.getChannels()){
            if(c instanceof TextChannel) {
                Chat target = ((TextChannel)c).getChat();
                if(choice == 1){
                    target.addToLimitedList(member.getUsername());
                }
                else{
                    target.removeFromLimitedList(member.getUsername());
                }
            }
        }

        return adminsMenu(u,serverr);
    }

    public static User.allMenues checkHistory(User u, Serverr serverr){
        //todo:
        return User.allMenues.MAIN;
    }


    public static User.allMenues membersOfServerrMenu(User u, Serverr serverr) throws IOException, ClassNotFoundException {
        InteractionWithUser.write(new Message(serverr.membersToString(u.getServer())), u);
        int n = serverr.membersSize() + 1;
        InteractionWithUser.write(new Message("" + n + ".back"), u);
        int choice = InteractionWithUser.getChoice(1, n, "", u.getfOut(), u.getfIn(), u);
        if (choice == n) {
            return chosenServerrMenu(u, serverr);
        }
        Member member = u.getServer().getMember(serverr.getMemberWithIndex(choice - 1));
        InteractionWithUser.write(new Message(member.profile() + "\n1.back"), u);
        choice = InteractionWithUser.getChoice(1, 1, "", u.getfOut(), u.getfIn(), u);
        if (choice == 1) {
            return membersOfServerrMenu(u, serverr);
        }
        return User.allMenues.SERVERS;
    }

    public static User.allMenues channelsOfServerMenu(User u, Serverr serverr) throws IOException, ClassNotFoundException {
        InteractionWithUser.write(new Message(serverr.channelsToString()), u);
        int n = serverr.channelsSize();
        InteractionWithUser.write(new Message("" + (n + 1) + ".back"), u);
        int choice = InteractionWithUser.getChoice(1, n + 1, "", u.getfOut(), u.getfIn(), u);
        if (choice == n + 1) {
            return chosenServerrMenu(u, serverr);
        }
        Channel channel = serverr.getChannelWithIndex(choice - 1);
        Chat target = ((TextChannel) channel).getChat();
        target.newInChatMember(u);
        while (true) {
            switch (InChatFuncs.getInChatChoice(target, u)) {
                case 1:
                    if(!target.isInLimitedList(u.getUserName())) {
                    target.isTyping(u);
                    target.addNewMessage(InteractionWithUser.read(u, u.getUserName()));
                    target.endTyping(u);
                    }
                    else {
                        InteractionWithUser.write(new Message("you are limited."),u);
                    }
                    break;
                case 2:
                    InChatFuncs.reactToAMessageIn(u ,target.getMessages());
                    break;
                case 3 :
                    Message temp = target.getPinnedMessage();
                    if(temp == null){
                        InteractionWithUser.write(new Message("there is no pinned message."),u);
                    }
                    else{
                        InteractionWithUser.write(temp, u);
                    }
                    break;
                case 4 :
                    InChatFuncs.sendFile(u,target);
                    break;
                case 5 :
                    InChatFuncs.downloadFile(u,target);
                    break;
                case 6:
                    target.removeInChatMember(u);
                    return channelsOfServerMenu(u, serverr);
            }
        }
    }

    public static User.allMenues createNewChannel(User u, Serverr serverr) throws IOException, ClassNotFoundException {
        InteractionWithUser.write(new Message("name : "), u);
        String name = InteractionWithUser.read(u).getMessage();
        int choice = InteractionWithUser.getChoice(1, 2, "1.text channel\n2.voice channel", u.getfOut(), u.getfIn(), u);
        if (choice == 1) {
            InteractionWithUser.write(new Message("welcome message :"), u);
            String welcomeMessage = InteractionWithUser.read(u).getMessage();
            TextChannel t = new TextChannel(name,serverr,welcomeMessage);
            t.addMember(u.getMember());
            serverr.addChannel(t);
        } else {
            serverr.addChannel(new VoiceChannel(name, serverr));
        }
        return chosenServerrMenu(u, serverr);
    }


    public static User.allMenues addNewMemberToServerr(User u, Serverr serverr) throws IOException, ClassNotFoundException {
        InteractionWithUser.write(new Message("enter user username"), u);
        String username = InteractionWithUser.read(u).getMessage();
        Member m = u.getServer().getMember(username);
        if (m != null) {
            if (m.equals(u.getMember())) {
                InteractionWithUser.write(new Message("yot cant add yourself to server."), u);
            } else {
                if (serverr.isInServerr(username)) {
                    InteractionWithUser.write(new Message("this user is in this server."), u);
                } else {
                    serverr.addMember(m);
                }
            }
        } else {
            InteractionWithUser.write(new Message("there is no user with this username."), u);
        }
        return chosenServerrMenu(u, serverr);
    }



    public static User.allMenues addNewAdmin(User u, Serverr serverr) throws IOException, ClassNotFoundException {
        InteractionWithUser.write(new Message("enter user username"), u);
        String username = InteractionWithUser.read(u).getMessage();
        if (serverr.isInServerr(username)) {
            InteractionWithUser.write(new Message("name of this acessibility:"), u);
            String naghshName = InteractionWithUser.read(u).getMessage();
            boolean createChannel = false, deleteChannel = false, deleteMembers = false, limitMembers = false, banMembers = false, changeName = false, checkHistory = false, pinMessage = false;
            InteractionWithUser.write(new Message("can create channel ? (1.yes, 2.no)"), u);
            int choice = InteractionWithUser.getChoice(1, 2, "", u.getfOut(), u.getfIn(), u);
            if (choice == 1) {
                createChannel = true;
            }

            InteractionWithUser.write(new Message("can delete channel ? (1.yes, 2.no)"), u);
            choice = InteractionWithUser.getChoice(1, 2, "", u.getfOut(), u.getfIn(), u);
            if (choice == 1) {
                deleteChannel = true;
            }
            InteractionWithUser.write(new Message("can delete member ? (1.yes, 2.no)"), u);
            choice = InteractionWithUser.getChoice(1, 2, "", u.getfOut(), u.getfIn(), u);
            if (choice == 1) {
                deleteMembers = true;
            }

            InteractionWithUser.write(new Message("can limit member ? (1.yes, 2.no)"), u);
            choice = InteractionWithUser.getChoice(1, 2, "", u.getfOut(), u.getfIn(), u);
            if (choice == 1) {
                limitMembers = true;
            }
            InteractionWithUser.write(new Message("can ban member ? (1.yes, 2.no)"), u);
            choice = InteractionWithUser.getChoice(1, 2, "", u.getfOut(), u.getfIn(), u);
            if (choice == 1) {
                banMembers = true;
            }
            InteractionWithUser.write(new Message("can change server name ? (1.yes, 2.no)"), u);
            choice = InteractionWithUser.getChoice(1, 2, "", u.getfOut(), u.getfIn(), u);
            if (choice == 1) {
                changeName = true;
            }
            InteractionWithUser.write(new Message("can check history ? (1.yes, 2.no)"), u);
            choice = InteractionWithUser.getChoice(1, 2, "", u.getfOut(), u.getfIn(), u);
            if (choice == 1) {
                checkHistory = true;
            }
            InteractionWithUser.write(new Message("can pin messsage ? (1.yes, 2.no)"), u);
            choice = InteractionWithUser.getChoice(1, 2, "", u.getfOut(), u.getfIn(), u);
            if (choice == 1) {
                pinMessage = true;
            }
            serverr.addAdmin(username,naghshName,createChannel,deleteChannel,deleteMembers,limitMembers,banMembers,changeName,checkHistory,pinMessage);

        } else {
            InteractionWithUser.write(new Message("there is no user with this username in this serverr."), u);
        }
        return chosenServerrMenu(u, serverr);
    }

}
