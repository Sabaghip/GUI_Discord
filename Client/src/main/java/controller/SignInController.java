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

public class SignInController {

    @FXML
    private TextField usernameTxtFld;

    @FXML
    private TextField passwordTxtFld;

    @FXML
    private Button signInBtn;

    @FXML
    private Label createAccntTxtFld;

    @FXML
    private Label invalidCredentialsTxtFld;

    @FXML
    void goToSignUp(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/signUp.fxml"));
        Parent root = loader.load();
        Client.changeScene(new Scene(root));
    }

    @FXML
    void signInButtonClicked(MouseEvent event) throws IOException {
        if(usernameTxtFld.getText() != null && passwordTxtFld.getText() != null) {
            if(!usernameTxtFld.getText().equals("") && !passwordTxtFld.getText().equals("")) {
                if(Client.checkUserSignIn(usernameTxtFld.getText(), passwordTxtFld.getText())){
                    signInAccepted();
                }
                else {
                    signInNotAccepted();
                }
            }
        }
    }

    public void signInAccepted() throws IOException {
        goToMain();
    }

    public void signInNotAccepted(){
        invalidCredentialsTxtFld.setVisible(true);
    }

    public void goToMain() throws IOException {
        Client.mainController = new MainController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainMenu.fxml"));
        loader.setController(Client.mainController);
        Parent root = loader.load();
        Client.changeScene(new Scene(root));
    }




}
