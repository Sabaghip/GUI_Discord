package server.MenuesHanling;

import server.*;
import server.ValidationPackage.Validation;

import java.io.IOException;
import java.util.ArrayList;

public class Signing {

    public static void signIn(Server server, Member member, User u, Message m) throws IOException, ClassNotFoundException {
        String[] objcts = m.getMessage().split(":::");
        String username = objcts[1];
        String password = objcts[2];
        if (server.isValidMember(username, password)) {
            member = server.logIn(u, username);
            u.setMember(member);
            member.setOnline();
            InteractionWithUser.write(new Message("%%!getCheckUserSignInResult:1"), u);
        } else {
            InteractionWithUser.write(new Message("%%!getCheckUserSignInResult:0"), u);
        }
    }

    public static void signUp(Server server, Member member, User u, Message m) throws IOException, ClassNotFoundException {
        boolean flag = false;
        String[] objcts = m.getMessage().split(":::");
        String username = objcts[1];
        String password = objcts[2];
        String email = objcts[3];
        try {
            Validation.usernameValidation(username, server.getMembers());
            Validation.emailValidation(email, server.getMembers());
            Validation.passValidation(password);
            InteractionWithUser.write(new Message("%%!getCheckUserSignUpResult:1"), u);
            member = new Member(username, password, email);
            u.setMember(member);
            member.setOnline();
            server.addNewMember(member);
            flag = true;
        } catch (Exception e) {
            InteractionWithUser.write(new Message("%%!getCheckUserSignUpResult:0"), u);
        }
        if (flag) {
            while (true) {
                m = InteractionWithUser.read(u);
                if (m.getMessage().startsWith("checkUserPhoneNumber")) {
                    String phonenumber = m.getMessage().split(":::")[1];
                    try {
                        Validation.phoneValidation(phonenumber, server.getMembers());
                        InteractionWithUser.write(new Message("%%!getCheckUserPhoneNumber:1"), u);
                        member.setPhoneNumber(phonenumber);
                        break;
                    } catch (Exception e) {
                        InteractionWithUser.write(new Message("%%!getCheckUserPhoneNumber:0"), u);
                    }
                } else if (m.getMessage().equals("skip")) {
                    break;
                }
            }
            while (true) {
                m = InteractionWithUser.read(u);
                if (m.getMessage().equals("profilepic")) {
                    member.setPic(m.getContent());
                    break;
                }
                else if(m.getMessage().equals("skip")){
                    break;
                }
            }
        }
    }

}
