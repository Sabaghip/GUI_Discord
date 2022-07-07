package com.example.test2;

import client.Client;
import client.ClientIn;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import server.Message;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private ImageView profilePicInMenu;

    @FXML
    void friendRequestsButtonClicked(MouseEvent event) {

    }

    @FXML
    void friendsButtonClicked(MouseEvent event) throws IOException {
        Client.friendsController = new FriendsController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("friendsMenu.fxml"));
        FriendsController friendsController = new FriendsController();
        loader.setController(friendsController);
        Parent root = loader.load();
        Client.changeScene(new Scene(root));
    }

    @FXML
    void logoutButtonClicked(MouseEvent event) {

    }

    @FXML
    void profileButtonClicked(MouseEvent event) {

    }

    @FXML
    void serversButtonClicked(MouseEvent event) {

    }

    @FXML
    void settingButtonClicked(MouseEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Message m = Client.requestProfilePic();
        if(m.getContent() != null){
            ClientIn.saveProfilePic(m);
            setProfilePic(m.getMessage().split(":::")[1]);
        }
        else{
            defaultProfilePic();
        }
    }

    public void setProfilePic(String fileName){
        profilePicInMenu.setImage(new Image("file:profilePics\\" + fileName + ".jpg"));
    }

    public void defaultProfilePic(){
        profilePicInMenu.setImage(new Image("file:profilePics\\default.jpg"));
    }

}
