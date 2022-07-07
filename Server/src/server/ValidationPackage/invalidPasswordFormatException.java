package server.ValidationPackage;

public class invalidPasswordFormatException extends Exception{
    public invalidPasswordFormatException(){
        super("invalid passsword format");
    }
}
