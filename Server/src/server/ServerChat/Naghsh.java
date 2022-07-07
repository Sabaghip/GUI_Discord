package server.ServerChat;

import java.io.Serializable;

public class Naghsh implements Serializable {
    private String name;
    private int acessability;
    //2 for create channel, 3 for delete channel, 5 for delete member, 7 for  limit member, 11 for ban member, 13 for change name, 17 for history check, 19 for pin message
    public Naghsh(String name,boolean createChannel, boolean deleteChannel, boolean deleteMember,boolean limitMembers, boolean banMembers,boolean changeName, boolean historyChecking,boolean pinMessage){
        this.name = name;
        int res = 1;
        if(createChannel){
            res *= 2;
        }
        if(deleteChannel){
            res*=3;
        }
        if(deleteMember){
            res*=5;
        }
        if(limitMembers){
            res*=7;
        }
        if(banMembers){
            res*=11;
        }
        if(changeName){
            res*=13;
        }
        if(historyChecking){
            res*=17;
        }
        if(pinMessage){
            res*=19;
        }
        this.acessability = res;
    }

    public boolean canCreateChanel(){
        return acessability % 2 == 0;
    }
    public boolean canDeleteChanel(){
        return acessability % 3 == 0;
    }
    public boolean canDeleteMember(){
        return acessability % 5 == 0;
    }
    public boolean canLimitMembers(){
        return acessability % 7 == 0;
    }
    public boolean canBanMembers(){
        return acessability % 11 == 0;
    }
    public boolean canChangeName(){
        return acessability % 13 == 0;
    }
    public boolean canCheckHistory(){
        return acessability % 17 == 0;
    }
    public boolean canPinMessage(){
        return acessability % 19 == 0;
    }
}
