package com.example.test2;

import client.Client;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FriendsController implements Initializable{

    @FXML
    VBox mainVbox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Client.getFriendsForFriendsMenu();
    }
    public void addFriendsNames(String[] names){
        for (String name : names){
            mainVbox.getChildren().add(new HBox(new Text(name)));
        }
    }

}
