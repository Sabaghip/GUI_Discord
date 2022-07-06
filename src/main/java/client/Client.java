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
    private static Thread threadIn;
    private static Thread threadOut;

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
                    threadIn = new Thread(cIn);
                    threadIn.start();
                    threadOut = new Thread(cOut);
                    threadOut.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    public static void stopThreads() {
        threadIn.stop();
        threadOut.stop();
    }

    public static void checkUserSignIn(String username, String password) {
        cOut.sendCommand("checkUserSignIn:::" + username + ":::" + password);
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

    public static void checkUserSignUp(String username, String password, String email) {
        cOut.sendCommand("checkUserSignUp:::" + username + ":::" + password + ":::" + email);
    }

    public static void checkUserPhoneNumber(String phonenumber) {
        cOut.sendCommand("checkUserPhoneNumber:::" + phonenumber);
    }

    public static void skip() {
        cOut.sendCommand("skip");
    }

    public static void checkUserProfilePic(String path) {
        if (!path.endsWith("jpg")) {
            controller.profilePicNotAccepted();
        }
        try {
            File f = new File(path);
            FileInputStream fl = new FileInputStream(f);
            byte[] content = new byte[(int) f.length()];
            content = fl.readAllBytes();
            fOut.writeObject(new Message(content, "profilepic"));
            fOut.flush();
            controller.profilePicAccepted();
        } catch (IOException e) {
            controller.profilePicNotAccepted();
        }
    }

    public static void requestProfilePic(){
        cOut.sendCommand("requestProfilePic:::me");
    }
    public static void getFriendsForFriendsMenu(){
        cOut.sendCommand("getFriendsNamesForFriendsMenu");
    }
}