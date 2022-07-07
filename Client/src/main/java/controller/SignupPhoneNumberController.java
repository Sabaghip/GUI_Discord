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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupPhoneNumberController {

    @FXML
    private TextField phoneNumberTxtFld;

    @FXML
    private Button nextBtn;

    @FXML
    private Button backBtn;

    @FXML
    private Button skipBtn;

    @FXML
    private Label invalidCredentialsTxtFld;

    @FXML
    void backClicked(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/signUp.fxml"));
        Parent root = loader.load();
        Client.changeScene(new Scene(root));
    }

    @FXML
    void nextClicked(MouseEvent event) throws IOException{
        Pattern pattern = Pattern.compile("[+]\\d{12}$");
        if (phoneNumberTxtFld.getText().matches(pattern.pattern())){
            accepted();
        }
        else {
            invalidCredentialsTxtFld.setVisible(true);
        }
    }

    @FXML
    void skipClicked(MouseEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/signupProfilePick.fxml"));
        Parent root = loader.load();
        Client.changeScene(new Scene(root));
    }

    public void accepted() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/signupProfilePick.fxml"));
        Parent root = loader.load();
        Client.changeScene(new Scene(root));
    }

}
