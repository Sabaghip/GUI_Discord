package client;


import com.example.test2.HelloApplication;
import com.example.test2.HelloController;
import server.Message;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientIn{

    private Socket socket;
    ObjectOutputStream fOut;
    ObjectInputStream fIn;
    ClientOut cOut;
    Scanner scanner = new Scanner(System.in);

    public ClientIn(Socket socket,ObjectInputStream in, ObjectOutputStream out) throws IOException {
        this.socket = socket;
        fOut = out;
        fIn = in;
    }

    public Message getMessage() {
        try {
            Message m = (Message) fIn.readObject();
            return m;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setcOut(ClientOut cOut){
        this.cOut = cOut;
    }


    public void command(Message m) throws IOException {
        if(m.getMessage().startsWith("%%!getFile:::")) {
            getFile(m);
        }
        else if(m.getMessage().startsWith("%%!getPPic:::")){
            getPPic(m);
        }
        else if(m.getContent() != null){
            if(m.getMessage().startsWith("%%!downloadPic:::")){
                downloadPPic(m);
            }
            else {
                saveFile(m);
            }
        }
    }

    public void getFile(Message m) throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String filePath = m.getMessage().split(":::")[1];
                    String fileName = m.getMessage().split(":::")[2];
                    File f = new File(filePath);
                    FileInputStream fl = new FileInputStream(f);
                    byte[] content = new byte[(int) f.length()];
                    content = fl.readAllBytes();
                    fOut.writeObject(new Message(content, fileName, m.getAuthor()));
                    fOut.flush();
                }catch (FileNotFoundException e){
                    System.out.println("this file not found.");
                    try {
                        fOut.writeObject(new Message("%%!not found"));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void getPPic(Message m) throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String filePath = m.getMessage().split(":::")[1];
                    File f = new File(filePath);
                    FileInputStream fl = new FileInputStream(f);
                    byte[] content = new byte[(int) f.length()];
                    content = fl.readAllBytes();
                    fOut.writeObject(new Message(content, "profilepic", m.getAuthor()));
                    fOut.flush();
                }catch (FileNotFoundException e){
                    System.out.println("this file not found.");
                    try {
                        fOut.writeObject(new Message("%%!not found"));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void saveFile(Message m) throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                    try {
                        String fileName = String.copyValueOf(m.getMessage().toCharArray(),3,m.getMessage().length() - 3);
                        byte[] temp = m.getContent();
                        File f = new File("downloads\\" + fileName);
                        FileOutputStream fO = new FileOutputStream(f);
                        fO.write(temp);
                        fO.flush();
                        fO.close();
                        System.out.println("downloaded.");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        }).start();
    }

    public static void saveProfilePic(Message m){
        try {
            File f = new File("profilePics\\" + m.getMessage().split(":::")[1] + ".jpg");
            FileInputStream fl = new FileInputStream(f);
        }catch (FileNotFoundException e) {
            try {
                String fileName = m.getMessage().split(":::")[1] + ".jpg";
                byte[] temp = m.getContent();
                File f = new File("profilePics\\" + fileName);
                FileOutputStream fO = new FileOutputStream(f);
                fO.write(temp);
                fO.flush();
                fO.close();
            } catch (IOException ee) {
                e.printStackTrace();
            }
        }
    }

    public void downloadPPic(Message m) throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    byte[] temp = m.getContent();
                    File f = new File("downloads\\profile pics\\" + m.getMessage().split(":::")[1] + ".jpg");
                    FileOutputStream fO = new FileOutputStream(f);
                    fO.write(temp);
                    fO.flush();
                    fO.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}
