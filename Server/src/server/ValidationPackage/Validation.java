package server.ValidationPackage;

import server.Member;
import server.ValidationPackage.*;

import java.util.ArrayList;

public class Validation {

    public static void usernameValidation(String userName, ArrayList<Member> allMembers) throws invalidUsernameFormatException, usernameAlreadyExistsException {
        if(userName == null){
            throw new invalidUsernameFormatException();
        }
        for(Member m : allMembers){
            if(userName.equals(m.getUsername())){
                throw new usernameAlreadyExistsException();
            }
        }
        if(userName.length() < 6){
            throw new invalidUsernameFormatException();
        }
        for(int i = 0; i < userName.length(); i++){
            char c = userName.charAt(i);
            if(!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9'))){
                throw new invalidUsernameFormatException();
            }
        }
    }

    public static void passValidation(String password) throws invalidPasswordFormatException {
        if(password == null){
            throw new invalidPasswordFormatException();
        }
        if(password.length() < 8){
            throw new invalidPasswordFormatException();
        }
        int flag = 3;
        for(int i = 0; i < password.length(); i++){
            char c = password.charAt(i);
            if(c >= 'a' && c <= 'z'){
                flag--;
                break;
            }
        }
        for(int i = 0; i < password.length(); i++){
            char c = password.charAt(i);
            if(c >= 'A' && c <= 'Z'){
                flag--;
                break;
            }
        }
        for(int i = 0; i < password.length(); i++){
            char c = password.charAt(i);
            if(c >= '0' && c <= '9'){
                flag--;
                break;
            }
        }
        if(flag > 0){
            throw new invalidPasswordFormatException();
        }
    }

    public static void emailValidation(String email, ArrayList<Member> allMembers) throws invalidEmailFormatException, emailAlreadyExistsException {
        if(email == null){
            throw new invalidEmailFormatException();
        }
        if(!email.contains("@")){
            throw new invalidEmailFormatException();
        }
        if(!(email.lastIndexOf('@') == email.indexOf('@'))){
            throw new invalidEmailFormatException();
        }
        char c = email.charAt(0);
        if(!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9'))){
            throw new invalidEmailFormatException();
        }
        c = email.charAt(email.length() - 1);
        if(!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9'))){
            throw new invalidEmailFormatException();
        }
        int flag = 0;
        for(int j = email.indexOf('@') + 1; j < email.length(); j++){
            if(email.charAt(j) == '.'){
                flag = 1;
                break;
            }
        }
        if(flag == 0){
            throw new invalidEmailFormatException();
        }

        for(Member m : allMembers){
            if(email.equals(m.getEmail())){
                throw new emailAlreadyExistsException();
            }
        }
    }

    public static void phoneValidation(String phone, ArrayList<Member> allMembers) throws invalidPhoneFormatException, phoneAlreadyExistsException {
        if(phone == null || phone.equals("")){
            return;
        }
        if(!(phone.charAt(0) == '+')){
            throw new invalidPhoneFormatException();
        }
        if(!(phone.length() == 13)){
            throw new invalidPhoneFormatException();
        }
        for(Member m : allMembers){
            if(phone.equals(m.getPhoneNumber())){
                throw new phoneAlreadyExistsException();
            }
        }
        for(int i = 1; i < phone.length(); i++){
            char c = phone.charAt(i);
            if(!(c >= '0' && c <= '9')){
                throw new invalidPhoneFormatException();
            }
        }
    }
}
