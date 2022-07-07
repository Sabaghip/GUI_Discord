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

public class SignUpController {

    @FXML
    private TextField usernameTxtFld;

    @FXML
    private TextField passwordTxtFld;

    @FXML
    private TextField passwordTxtFldCnf;

    @FXML
    private TextField emailTxtFld;

    @FXML
    private Button nextBtn;

    @FXML
    private Button backBtn;

    @FXML
    private Label invalidCredentialsTxtFld;



    @FXML
    void backClicked(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/signIn.fxml"));
        Parent root = loader.load();
        Client.changeScene(new Scene(root));

    }

    @FXML
    void nextClicked(MouseEvent event) throws IOException{
        if(usernameTxtFld.getText() != null && passwordTxtFld.getText() != null && emailTxtFld.getText() != null) {
            if(!usernameTxtFld.getText().equals("") && !passwordTxtFld.getText().equals("") && !emailTxtFld.getText().equals("")) {
                if(Client.checkUserSignUp(usernameTxtFld.getText(), passwordTxtFld.getText(), emailTxtFld.getText())){
                    accepted();
                }
                else {
                    rejected();
                }
            }
        }
    }

    public void accepted() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/signupPhoneConfirm.fxml"));
        Parent root = loader.load();
        Client.changeScene(new Scene(root));
    }
    public void rejected(){
        invalidCredentialsTxtFld.setVisible(true);
    }

}
