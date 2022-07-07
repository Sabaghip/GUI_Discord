package server.MenuesHanling;

import server.Message;
import server.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class InteractionWithUser {
    public static void write(Message message, User u) throws IOException {
        ObjectOutputStream fOut = u.getfOut();
        if (!u.getSocket().isClosed() && u.getSocket().isConnected()) {
            try {
                fOut.writeObject(message);
            } catch (Exception e) {
                System.out.println("someone left.");
                u.stopThread();
            }
        }
    }

    public static Message read(User u) throws IOException, ClassNotFoundException {
        ObjectInputStream fIn = u.getfIn();
        if (!u.getSocket().isClosed() && u.getSocket().isConnected()) {
            try {
                return (Message) fIn.readObject();
            } catch (Exception e) {
                System.out.println("someone left.");
                u.stopThread();
            }
        }
        return null;
    }

    public static Message read(User u, String author) throws IOException, ClassNotFoundException {
        ObjectInputStream fIn = u.getfIn();
        if (!u.getSocket().isClosed() && u.getSocket().isConnected()) {
            try {
                return new Message(((Message) fIn.readObject()).getMessage(), author);
            } catch (Exception e) {
                System.out.println("someone left.");
                u.stopThread();
            }
        }
        return null;
    }

    public static int getChoice(int l, int h, String matn, ObjectOutputStream fOut, ObjectInputStream fIn, User u) throws IOException {
        int choice = 0;
        while (true) {
            try {
                InteractionWithUser.write(new Message(matn), u);
                String m = InteractionWithUser.read(u).getMessage();
                choice = Integer.parseInt(m);
                if (choice <= h && choice >= l - 1) {
                    break;
                }
                InteractionWithUser.write(new Message("invalid input"), u);
            } catch (Exception e) {
                InteractionWithUser.write(new Message("invalid input"), u);
            }
        }
        return choice;
    }

}
