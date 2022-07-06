package com.example.test2;

import client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private TextField profilePicTextField;

    @FXML
    private TextField phonenumberTextField;

    @FXML
    private Button confirmPhoneButton;
    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField emailTextField;


    @FXML
    private Label invalidCredentialsText;

    @FXML
    private Text phonenumberErrorText;

    @FXML
    void goToSignUp(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("signUp.fxml"));
        loader.setController(Client.controller);
        Parent root = loader.load();
        Client.changeScene(new Scene(root));
    }

    @FXML
    void signInButtonClicked(MouseEvent event) {
        if(usernameTextField.getText() != null && passwordTextField.getText() != null) {
            if(!usernameTextField.getText().equals("") && !passwordTextField.getText().equals("")) {
                Client.checkUserSignIn(usernameTextField.getText(), passwordTextField.getText());
            }
        }
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void signInAccepted() throws IOException {
        goToMain();
    }
    public void signInNotAccepted(){
        invalidCredentialsText.setOpacity(1);
    }

    public void goToSignIn(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("singing.fxml"));
        loader.setController(Client.controller);
        Parent root = loader.load();
        Client.changeScene(new Scene(root));
    }

    @FXML
    public void signUpButtonClicked(MouseEvent mouseEvent) {
        if(usernameTextField.getText() != null && passwordTextField.getText() != null && emailTextField.getText() != null) {
            if(!usernameTextField.getText().equals("") && !passwordTextField.getText().equals("") && !emailTextField.getText().equals("")) {
                Client.checkUserSignUp(usernameTextField.getText(), passwordTextField.getText(), emailTextField.getText());
            }
        }
    }

    @FXML
    public void confirmPhoneButtonClicked(MouseEvent mouseEvent) {
        if(phonenumberTextField.getText() != null) {
            if(!phonenumberTextField.getText().equals("")) {
                Client.checkUserPhoneNumber(phonenumberTextField.getText());
            }
        }
    }

    public void signUpAccepted() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("signupMenu2.fxml"));
        loader.setController(Client.controller);
        Parent root = loader.load();
        Client.changeScene(new Scene(root));
    }
    public void signUpNotAccepted(){
        invalidCredentialsText.setOpacity(1);
    }


    @FXML
    public void skipButtonClicked(MouseEvent event) throws IOException {
        Client.skip();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("signupMenu3.fxml"));
        loader.setController(Client.controller);
        Parent root = loader.load();
        Client.changeScene(new Scene(root));
    }


    public void phoneNumberAccepted() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("signupMenu3.fxml"));
        loader.setController(Client.controller);
        Parent root = loader.load();
        Client.changeScene(new Scene(root));
    }
    public void phoneNumberNotAccepted(){
        phonenumberErrorText.setOpacity(1);
    }

    @FXML
    public void confirmPhotoButtonClicked(MouseEvent mouseEvent){
        if(profilePicTextField.getText() != null) {
            if(!profilePicTextField.getText().equals("")) {
                Client.checkUserProfilePic(profilePicTextField.getText());
            }
        }
    }

    public void profilePicAccepted() throws IOException {
        goToMain();
    }
    public void profilePicNotAccepted(){
        phonenumberErrorText.setOpacity(1);
    }

    @FXML
    public void skipButtonClicked2(MouseEvent event) throws IOException {
        Client.skip();
        goToMain();
    }

    public void goToMain() throws IOException {
        Client.mainController = new MainController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainMenu.fxml"));
        loader.setController(Client.mainController);
        Parent root = loader.load();
        Client.changeScene(new Scene(root));
    }

}