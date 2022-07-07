package server.MenuesHanling;

import java.io.*;
import java.util.Arrays;

import server.*;
import server.User.allMenues;

import javax.sound.sampled.*;

public class Menues {


    public static allMenues mainMenu(ObjectOutputStream fOut, ObjectInputStream fIn, User u) throws IOException, UnsupportedAudioFileException, LineUnavailableException, ClassNotFoundException {
        Message m = InteractionWithUser.read(u);
        if(m.getMessage().equals("requestProfilePic")){
            if(u.getMember().haveProfilePic()){
                InteractionWithUser.write(new Message(u.getMember().getPic(), "%%!profilePic:::" + u.getUserName()),u);
            }
            else {
                InteractionWithUser.write(new Message("%%!profilePic:::" + u.getUserName(),u.getUserName()),u);
            }
        }
        int choice = 0;
        m = InteractionWithUser.read(u);
        if(m.getMessage().startsWith("mainMenuChoice:::")){
            choice = Integer.parseInt(m.getMessage().split(":::")[1]);
        }
        if (choice == 1) {
            return allMenues.PROFILE;
        }
        if (choice == 2) {
            return allMenues.FRIENDS;
        }
        if (choice == 3) {
            return allMenues.FRIENDREQUESTS;
        }
        if (choice == 4) {
            return allMenues.SERVERS;
        }
        if (choice == 5) {
            return allMenues.SETTING;
        }
        if (choice == 6) {
            return allMenues.SIGN;
        }
        return null;
    }

    public static allMenues showProfile(Member member, ObjectOutputStream fOut, ObjectInputStream fIn, User u) throws IOException {
        int choice = InteractionWithUser.getChoice(1, 2, member.profile() + "\n1.profie pic\n2.back", fOut, fIn, u);
        if(choice == 1){
            downloadPPic(u, u.getMember());
        }
        if (choice == 2) {
            return allMenues.MAIN;
        }
        return allMenues.MAIN;
    }
    public static void downloadPPic(User u, Member member) throws IOException {
        if(member.getPic() != null) {
            InteractionWithUser.write(new Message(member.getPic(), "%%!downloadPic:::" + member.getUsername(), "nobody"),u);
        }
        else {
            InteractionWithUser.write(new Message("this user doesnt have profile picture."),u);
        }
    }


    public static allMenues friendsMenu(User u) throws IOException, ClassNotFoundException {
        //todo:
        Member friend = ShowLists.showFriendsAndChooseOne(u);
        if(friend == null){
            return allMenues.MAIN;
        }
        int choice = FriendHandling.showProfileAndChoose(friend, u);
        switch (choice) {
            case 1 :
                downloadPPic(u, friend);
                return friendsMenu(u);
            case 2 :
                FriendHandling.privateChat(friend, u);
                return friendsMenu(u);
            case 3 :
                FriendHandling.blockUnblock(friend, u);
                return friendsMenu(u);

            case 4 :
                return allMenues.FRIENDS;
        }
        return allMenues.MAIN;
    }

    public static allMenues friendRequestMenue(User u) throws IOException, ClassNotFoundException {
        int choice = InteractionWithUser.getChoice(1, 3, "1.send friend request\n2.recieved friend requests\n3.back", u.getfOut(), u.getfIn(), u);
        switch (choice) {
            case 1:
           //     FriendHandling.sendFriendRequest(u);
                return allMenues.FRIENDREQUESTS;
            case 2:
                FriendHandling.recievedFriendRequests(u);
                return allMenues.FRIENDREQUESTS;
            case 3:
                return allMenues.MAIN;
        }
        return allMenues.MAIN;
    }






    public static allMenues settingMenu(User u) throws IOException, ClassNotFoundException {
        int choice = InteractionWithUser.getChoice(1, 3, "1.change status\n2.change password\n3.back", u.getfOut(), u.getfIn(), u);
        switch (choice) {
            case 1:
                SettingHandling.selectStatus(u);
                return allMenues.SETTING;
            case 2:
                SettingHandling.changePassword(u);
                return allMenues.SETTING;
            case 3:
                return allMenues.MAIN;
        }
        return allMenues.MAIN;
    }




    public static allMenues serversMenu(User u) throws IOException, ClassNotFoundException {
        int choice = InteractionWithUser.getChoice(1, 3, "1.All servers\n2.New server\n3.back", u.getfOut(), u.getfIn(), u);
        switch (choice) {
            case 1:
                return ServerMenuHandling.allServersMenu(u);
            case 2:
                return ServerMenuHandling.newServerMenu(u);
            case 3:
                return allMenues.MAIN;

        }
        return allMenues.MAIN;
    }


}

