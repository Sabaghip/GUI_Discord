package server.ValidationPackage;

public class usernameAlreadyExistsException extends Exception{
    public usernameAlreadyExistsException(){
        super("you cant choose this username, someone has it");
    }
}
