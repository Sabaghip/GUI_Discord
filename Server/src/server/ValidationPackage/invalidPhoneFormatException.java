package server.ValidationPackage;

public class invalidPhoneFormatException extends Exception{
    public invalidPhoneFormatException(){
        super("invalid phone number format.");
    }
}
