package server.ValidationPackage;

public class invalidEmailFormatException extends Exception{
    public invalidEmailFormatException(){
        super("invalid email format");
    }
}
