package server.ValidationPackage;

public class invalidUsernameFormatException extends Exception{
    public invalidUsernameFormatException (){
        super("username format is invalid");
    }
}
