package com.example.test2;

import client.Client;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FriendsController implements Initializable{

    @FXML
    VBox mainVbox;
    @FXML
    TextField addFriendTextField;
    @FXML
    Text resultText;


    @FXML
    public void addFriendButtonClicked(){
        if(addFriendTextField.getText() != null){
            if(!addFriendTextField.getText().equals("")){
                String res = Client.checkIfCanSendFriendRequestTo(addFriendTextField.getText());
                if(res.equals("yes")){
                    resultText.setText("friend request sent.");
                    resultText.setFill(Color.GREEN);
                    resultText.setOpacity(1);
                }
                else{
                    resultText.setText(res);
                    resultText.setFill(Color.RED);
                    resultText.setOpacity(1);
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] names = Client.getFriendsForFriendsMenu();
        for (String name : names){
            mainVbox.getChildren().add(new HBox(new Text(name)));
        }
        names = Client.getFriendRequestForFriendsMenu();
        for (String name : names){
            mainVbox.getChildren().add(new HBox(new Text(name)));
        }
    }

}
