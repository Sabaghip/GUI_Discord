package server;

import server.ServerChat.Serverr;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Member implements Serializable {
    private static final long serialVersionUID = 70020312;

    private String username;
    private String password;
    private ArrayList<Integer> friendsTokens;
    private HashMap<Integer, Integer> chatsId;
    private String email;
    private String phoneNumber;
    private String status;
    private ArrayList<Integer> friendRequestsTokens;
    private ArrayList<Integer> blocksTokens;
    transient private ArrayList<Serverr> servers;
    private ArrayList<Integer> serversIDs;
    private boolean isOnline;
    private byte[] pic;
    private int token;
    transient private static int a = 1;
    private int picNum = 0;

    public Member(String username, String password, String email){
        token = a;
        a++;
        this.password = password;
        this.username = username;
        this.email = email;
        servers = new ArrayList<>();
        friendsTokens = new ArrayList<>();
        friendRequestsTokens = new ArrayList<>();
        blocksTokens = new ArrayList<>();
        serversIDs = new ArrayList<>();
        chatsId = new HashMap<>();
        status = "online";
        isOnline = false;
    }

    public static void setA(int n){
        a = n;
    }

    public void setPic(byte[] pic) throws IOException {
        this.pic = pic;
        Server.saveMembers();
        picNum++;
    }

    public byte[] getPic(){
        return pic;
    }

    public int getPicNum() {
        return picNum;
    }

    public String getEmail(){
        return email;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public void setPassword(String password) throws IOException {
        this.password = password;
        Server.saveMembers();
    }

    public void setPhoneNumber(String phoneNumber) throws IOException {
        this.phoneNumber = phoneNumber;
        Server.saveMembers();
    }


    public void setStatus(String status) throws IOException {
        this.status = status;
        Server.saveMembers();
    }

    public boolean haveProfilePic(){
        return pic != null;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void addFriendRequest(Member friend) throws IOException {
        friendRequestsTokens.add(friend.getToken());
        Server.saveMembers();
    }

    public int getToken() {
        return token;
    }

    public void addBlock(Member friend) throws IOException {
        blocksTokens.add(friend.getToken());
        Server.saveMembers();
    }
    public void addServerr(Serverr serverr) throws IOException {
        servers.add(serverr);
        serversIDs.add(serverr.getId());
        Server.saveMembers();
    }



    public void addServers(ArrayList<Serverr> allServers){
        servers = new ArrayList<>();
        for(int id : serversIDs){
            for(Serverr serverr : allServers){
                if(serverr.getId() == id){
                    servers.add(serverr);
                    break;
                }
            }
        }
    }

    public ArrayList<Serverr> getServers() {
        return servers;
    }

    public ArrayList<Integer> getFriends(){
        return friendsTokens;
    }

    public boolean isFriend(int token){
        if(friendsTokens.contains(token)){
            return true;
        }
        return false;
    }

    public boolean isInFriendRequest(int token){
        if(friendRequestsTokens.contains(token)){
            return true;
        }
        return false;
    }

    public String showFriendRequests(){
        StringBuilder res = new StringBuilder("");
        int i = 1;
        for(int n : friendRequestsTokens){
            res.append(i).append(".").append(Server.getMemberWithToken(n).getPassword()).append("\n");
            i++;
        }
        return res.toString();
    }

    public int friendRequestsSize(){
        return friendRequestsTokens.size();
    }

    public boolean haveFriendRequestFrom(int n){
        if(friendRequestsTokens.contains(n)){
            return true;
        }
        return false;
    }

    public void acceptFriendRequest(Member m) throws IOException {
        friendsTokens.add(m.getToken());
        friendRequestsTokens.remove(m.getToken());
        m.addFriend(this.token);
        Server.saveMembers();
    }

    public void addFriend(int n) throws IOException {
        if(!friendsTokens.contains(n)){
            friendsTokens.add(n);
            friendRequestsTokens.remove(n);
        }
        Server.saveMembers();
    }

    public int friendRequestWithNumber(int n){
        return friendRequestsTokens.get(n);
    }

    public void addChatId(int n, int chatId) throws IOException {
        chatsId.put(n, chatId);
        Server.saveMembers();
    }

    public boolean isInMembersOfChat(int n){
        if(chatsId.containsKey(n)){
            return true;
        }
        return false;
    }

    public Chat getChatWithName(int token, ArrayList<Chat> chats){
        int n = chatsId.get(token);
        for(Chat chat : chats){
            if(chat.getId() == n){
                return chat;
            }
        }
        return null;
    }


    public void setOnline() throws IOException {
        isOnline = true;
        Server.saveMembers();
    }

    public void setOffline() throws IOException {
        isOnline = false;
        Server.saveMembers();
    }

    public String getStatus(){
        if(isOnline) {
            return status;
        }
        else{
            return "offline";
        }
    }

    public void block(int token) throws IOException {
        blocksTokens.add(token);
        Server.saveMembers();
    }

     public boolean isBlocked(int token){
        if(blocksTokens.contains(token)){
            return true;
        }
        return false;
     }

     public void unblock(int token) throws IOException {
         blocksTokens.remove(token);
         Server.saveMembers();
     }

     public String profile(){
         return "Username : " + username + "\nEmail : " + email + "\nPhone number : " + phoneNumber;
     }

     public void deleteServerr(Serverr serverr) throws IOException {
        servers.remove(serverr);
         Server.saveMembers();
     }

     public String convertFriendsNamesToAnString(){
        StringBuilder str = new StringBuilder();
        for (int token : friendsTokens){
            Member m = Server.getMemberWithToken(token);
            str.append(m.getUsername()).append("#").append(m.getToken()).append(",");
        }
        if(str.length() > 0) {
            str.deleteCharAt(str.length() - 1);
        }
        return str.toString();
     }


    public String convertFriendsRequestsToAnString(){
        StringBuilder str = new StringBuilder();
        for (int token : friendRequestsTokens){
            Member m = Server.getMemberWithToken(token);
            str.append(m.getUsername()).append("#").append(m.getToken()).append(",");
        }
        if(str.length() > 0) {
            str.deleteCharAt(str.length() - 1);
        }
        return str.toString();
    }


}
