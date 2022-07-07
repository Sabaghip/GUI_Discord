package client;

import com.example.test2.FriendsController;
import com.example.test2.HelloController;
import com.example.test2.MainController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import javafx.scene.Node;
import server.Message;


public class Client {

    private static ObjectOutputStream fOut;
    private static ObjectInputStream fIn;
    private Socket socket;
    private static ClientIn cIn;
    private static ClientOut cOut;
    public static HelloController controller;
    public static MainController mainController;
    public static FriendsController friendsController;
    static Stage stage;

    public void start(Stage stage) {
        controller = new HelloController();
        Client.stage = stage;
        new Thread(new Runnable() {
            @Override
            public void run() {
                fOut = null;
                fIn = null;
                try {
                    socket = new Socket("127.0.0.1", 7878);

                } catch (IOException e) {
                    System.out.println("server not connected.\nwaiting for connection...");
                    while (true) {
                        try {
                            TimeUnit.SECONDS.sleep(3);
                            socket = new Socket("127.0.0.1", 7878);
                            System.out.println("connected.");
                            break;
                        } catch (ConnectException ignored) {

                        } catch (InterruptedException | IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                try {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            Parent p = null;
                            try {
                                p = FXMLLoader.load(getClass().getResource("signing.fxml"));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Scene scene2 = new Scene(p);
                            stage.setScene(scene2);
                            stage.show();
                        }
                    });
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("signing.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    changeScene(scene);
                    controller = loader.getController();
                    fOut = new ObjectOutputStream(socket.getOutputStream());
                    fIn = new ObjectInputStream(socket.getInputStream());
                    cIn = new ClientIn(socket, fIn, fOut);
                    cOut = new ClientOut(socket, fIn, fOut);
                    cIn.setcOut(cOut);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }



    public static boolean checkUserSignIn(String username, String password) {
        cOut.sendCommand("checkUserSignIn:::" + username + ":::" + password);
        String s = cIn.getMessage().getMessage();
        if(Integer.parseInt(s.split(":")[1]) == 1){
            return true;
        }
        else{
            return false;
        }
    }

    public static void changeScene(Scene scene) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                stage.setScene(scene);
                stage.show();
            }
        });
    }

    public static boolean checkUserSignUp(String username, String password, String email) {
        cOut.sendCommand("checkUserSignUp:::" + username + ":::" + password + ":::" + email);
        String s = cIn.getMessage().getMessage();
        if(Integer.parseInt(s.split(":")[1]) == 1){
            return true;
        }
        else{
            return false;
        }
    }

    public static boolean checkUserPhoneNumber(String phonenumber) {
        cOut.sendCommand("checkUserPhoneNumber:::" + phonenumber);
        String s = cIn.getMessage().getMessage();
        if(Integer.parseInt(s.split(":")[1]) == 1){
            return true;
        }
        else{
            return false;
        }
    }

    public static void skip() {
        cOut.sendCommand("skip");
    }

    public static boolean checkUserProfilePic(String path) {
        if (!path.endsWith("jpg")) {
            return false;
        }
        try {
            File f = new File(path);
            FileInputStream fl = new FileInputStream(f);
            byte[] content = new byte[(int) f.length()];
            content = fl.readAllBytes();
            fOut.writeObject(new Message(content, "profilepic"));
            fOut.flush();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static Message requestProfilePic(){
        cOut.sendCommand("requestProfilePic:::me");
        Message m = cIn.getMessage();
        return m;
    }
    public static String[] getFriendsForFriendsMenu(){
        cOut.sendCommand("getFriendsNamesForFriendsMenu");
        Message m = cIn.getMessage();
        String[] temp = m.getMessage().split(":::");
        if(temp.length > 1) {
            String[] names = temp[1].split(",");
            return names;
        }
        else {
            String[] names = new String[0];
            return names;
        }
    }

    public static String[] getFriendRequestForFriendsMenu(){
        cOut.sendCommand("getFriendRequestForFriendsMenu");
        Message m = cIn.getMessage();
        String[] temp = m.getMessage().split(":::");
        if(temp.length > 1) {
            String[] names = temp[1].split(",");
            return names;
        }
        else {
            String[] names = new String[0];
            return names;
        }
    }

    public static String checkIfCanSendFriendRequestTo(String name){
        cOut.sendCommand("checkIfCanSendFriendRequestTo:::" + name);
        Message m = cIn.getMessage();
        return m.getMessage();
    }
}