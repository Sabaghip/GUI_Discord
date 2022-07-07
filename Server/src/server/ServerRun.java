package server;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class ServerRun {
    public static void main(String[] args) throws IOException, ClassNotFoundException, UnsupportedAudioFileException, LineUnavailableException {
        Server server = new Server();
        server.run();
    }
}
