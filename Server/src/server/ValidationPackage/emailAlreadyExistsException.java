package server.ValidationPackage;

public class emailAlreadyExistsException extends Exception{
    public emailAlreadyExistsException(){
        super("you cant use this email, someone have it.");
    }
}
