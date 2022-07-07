package server;

import server.MenuesHanling.FriendHandling;
import server.MenuesHanling.InteractionWithUser;
import server.MenuesHanling.Menues;
import server.MenuesHanling.Signing;
import server.ValidationPackage.Validation;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class User implements Runnable {
    private Socket socket;
    private ObjectOutputStream fOut;
    private ObjectInputStream fIn;
    private Server server;
    private Member member;
    private Thread thread;


    public enum allMenues{SIGN, MAIN, PROFILE, FRIENDS, FRIENDREQUESTS, SERVERS, SETTING, Music};


    public User(Socket socket, Server server) throws IOException, ClassNotFoundException {
        this.socket = socket;
        this.server = server;
        fOut = new ObjectOutputStream(socket.getOutputStream());
        fIn = new ObjectInputStream(socket.getInputStream());
    }

    public void stopThread() throws IOException {
        try {
            member.setOffline();
        }
        catch (Exception ignored){

        }
        thread.stop();
    }

    public Thread getThread() {
        server.removeUser(this);
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public void run(){
        while (true) {
            try {
                getCommands();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void getCommands() throws IOException, ClassNotFoundException {
        Message m = InteractionWithUser.read(this);
        //get profile pic of a member
        if(m.getMessage().startsWith("requestProfilePic:::")){
            requestProfilePic(m);
        }

        //get user friends names
        else if(m.getMessage().equals("getFriendsNamesForFriendsMenu")){
            InteractionWithUser.write(new Message("%%!friendsNamesForFriendsMenu:::" + member.convertFriendsNamesToAnString()), this);
        }

        //get a member profile objects
        else if(m.getMessage().startsWith("getMemberProfile")){
            getMemberProfile(m);
        }
        else if (m.getMessage().startsWith("checkUserSignIn")) {
            Member member = new Member(null, null, null);
            Signing.signIn(server,member,this,m);
        }
        else if (m.getMessage().startsWith("checkUserSignUp")) {
            Member member = new Member(null, null, null);
            Signing.signUp(server,member,this, m);
        }
        else if(m.getMessage().startsWith("checkIfCanSendFriendRequestTo:::")){
            FriendHandling.sendFriendRequest(m.getMessage().split(":::")[1],this);
        }
        else if(m.getMessage().startsWith("getFriendRequestForFriendsMenu")){
            InteractionWithUser.write(new Message("%%!friendsRequestsForFriendsMenu:::" + member.convertFriendsRequestsToAnString()), this);
        }

    }

    public void requestProfilePic(Message m) throws IOException {
        String name = m.getMessage().split(":::")[1];
        Member member;
        if(!name.equals("me")){
            member = server.getMember(name);
        }
        else{
            member = this.member;
        }
        if(member.haveProfilePic()){
            InteractionWithUser.write(new Message(member.getPic(), "%%!profilePic:::" + getUserName() + getMember().getPicNum()),this);
        }
        else {
            InteractionWithUser.write(new Message("%%!profilePic:::" + getUserName(),getUserName()),this);
        }
    }

    public void getMemberProfile(Message m) throws IOException {
        String name = m.getMessage().split(":::")[1];
        Member friend = server.getMember(name);
        InteractionWithUser.write(new Message("%%!memberProfile:::" + friend.getUsername() + ":::" + friend.getToken() + ":::" + friend.getStatus() + ":::" + friend.getEmail()), this);
    }



    public void setMember(Member member) {
        this.member = member;
    }

    public String getUserName() {
        return member.getUsername();
    }

    public String getPassword() {
        return member.getPassword();
    }

    public Socket getSocket() {
        return socket;
    }

    public ObjectOutputStream getfOut() {
        return fOut;
    }

    public Member getMember() {
        return member;
    }

    public ObjectInputStream getfIn() {
        return fIn;
    }

    public Server getServer() {
        return server;
    }
}
