package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class IPPortController {

    @FXML
    public TextField IPAddress;
    @FXML
    public Button send;
    @FXML
    public TextField PortNo;
    @FXML
    public Label Status;

    public String serverIP;
    public String serverPort;

    Socket socket;
    public boolean EnterFlag = false;

    private static IPPortController instance;
    public IPPortController() {
        instance = this;
    }
    public static IPPortController getInstance() {
        return instance;
    }
    public Socket getSocket() {
        return socket;
    }

    public void save(ActionEvent actionEvent) {
        serverIP =  IPAddress.getText();
        serverPort = PortNo.getText();
        try {
            socket = new Socket(serverIP,Integer.parseInt(serverPort));
//            Status.setStyle("-fx-text-inner-color: #03a106");
            EnterFlag = true;
            Status.setText("Connection Successful..");
            System.out.println("Connection Successful....");
        } catch (IOException e) {
//            Status.setStyle("-fx-text-inner-color: #f00707");
            EnterFlag = false;
            Status.setText("Error in Connection");
            System.err.println("Wrong Ip Address or port number...");
        }
    }


    public void Enter(ActionEvent actionEvent) throws IOException {
        if(EnterFlag)
        {
            EnterFlag = false;
            System.out.println("Welcome to Chat room!!!!");
            send.getScene().getWindow().hide();
            Stage clientChat = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/FXML/ClientChat.fxml"));
            Scene scene = new Scene(root);
            clientChat.setScene(scene);
            clientChat.show();
            clientChat.setResizable(false);
        }
    }
}
