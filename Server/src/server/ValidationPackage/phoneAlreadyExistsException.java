package server.ValidationPackage;

public class phoneAlreadyExistsException extends Exception{
    public phoneAlreadyExistsException(){
        super("you cant choose this phone number, someone have it.");
    }
}
