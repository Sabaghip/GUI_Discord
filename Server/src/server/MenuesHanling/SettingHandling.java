package server.MenuesHanling;

import server.Message;
import server.User;
import server.ValidationPackage.Validation;
import server.ValidationPackage.invalidPasswordFormatException;

import java.io.IOException;

public class SettingHandling {


    public static void selectStatus(User u) throws IOException {
        int choice = InteractionWithUser.getChoice(1, 5, "1.Online\n2.Idle\n3.Do Not Disturb\n4.invisible\n5.back", u.getfOut(), u.getfIn(), u);
        switch (choice) {
            case 1:
                u.getMember().setStatus("Online");
                break;
            case 2:
                u.getMember().setStatus("Idle");
                break;
            case 3:
                u.getMember().setStatus("Do Not Disturb");
                break;
            case 4:
                u.getMember().setStatus("Invisible");
                break;
        }
    }

    public static void changePassword(User u) throws IOException, ClassNotFoundException {
        InteractionWithUser.write(new Message("enter your last password : "),u);
        String password = InteractionWithUser.read(u).getMessage();
        if(password.equals(u.getPassword())){
            InteractionWithUser.write(new Message("enter new password : "),u);
            password = InteractionWithUser.read(u).getMessage();
            try {
                Validation.passValidation(password);
                u.getMember().setPassword(password);
                InteractionWithUser.write(new Message("password changed."),u);
            } catch (invalidPasswordFormatException e) {
                InteractionWithUser.write(new Message(e.getMessage()),u);
            }

        }
        else{
            InteractionWithUser.write(new Message("incorrect password"),u);
        }
    }

}
