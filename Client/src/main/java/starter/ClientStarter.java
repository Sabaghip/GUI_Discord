package starter;

import javafx.scene.image.Image;
import model.client.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientStarter extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientStarter.class.getResource("/fxml/connecting.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Discord");
        stage.setScene(scene);
        stage.getIcons().add(new Image("/assets/icons/discord-logo.png"));
        stage.show();
        Client client = new Client();
        client.start(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}