package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.client.Client;

import java.io.IOException;

public class SignupProfilePicController {

    @FXML
    private TextField pathToPicTxtFld;

    @FXML
    private Button doneBtn;

    @FXML
    private Button skipBtn;

    @FXML
    private Button backBtn;

    @FXML
    private Label invalidCredentialsTxtFld;

    @FXML
    void backClicked(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/signupPhoneConfirm.fxml"));
        Parent root = loader.load();
        Client.changeScene(new Scene(root));
    }

    @FXML
    void doneClicked(MouseEvent event) {

    }

    @FXML
    void skipClicked(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/signIn.fxml"));
        Parent root = loader.load();
        Client.changeScene(new Scene(root));
    }

}
