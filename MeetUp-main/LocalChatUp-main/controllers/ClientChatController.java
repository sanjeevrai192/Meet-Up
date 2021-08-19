package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.fxml.FXML;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientChatController implements Initializable, Runnable {
    @FXML
    private Label currentUser;

    @FXML
    private TextArea OnlineUserList;

    @FXML
    private TextArea msgTA;

    @FXML
    private TextField msgInputTF;

    @FXML
    private Label time;
    public Socket socket;

    private InputStream serverIn;
    private OutputStream serverOut;
    private BufferedReader bufferedIn;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUsername(LoginController.getInstance().username());
        setSocket(IPPortController.getInstance().getSocket());

        try {
            this.serverOut = socket.getOutputStream();
            this.serverIn = socket.getInputStream();
            this.bufferedIn = new BufferedReader(new InputStreamReader(serverIn));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setUsername(String user) {
        this.currentUser.setText(user);
    }
    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        readMessageLoop();
    }

    private void readMessageLoop() {
        try {
            String line;
            while ((line = bufferedIn.readLine()) != null) {
                msgTA.appendText("user: "+line+"\n");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    public void SendMsg(ActionEvent actionEvent) throws IOException {
        String line = msgInputTF.getText();
        msgTA.appendText("Me: "+line+"\n");
        serverOut.write(line.getBytes());
        msgInputTF.setText("");
    }


}
