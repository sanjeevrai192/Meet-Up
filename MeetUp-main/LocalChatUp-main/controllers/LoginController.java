package controllers;


import DBConnection.DBhandler;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    public TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button login;

    @FXML
    private ImageView progress;

    private DBhandler handler;
    private Connection connection;
    private PreparedStatement pst;

    private static LoginController instance;

    public LoginController() {
        instance = this;
    }
    public static LoginController getInstance() {
        return instance;
    }

    public String username() {
        return username.getText();
}
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        progress.setVisible(false);
        username.setStyle("-fx-text-inner-color: #a0a2ab");
        password.setStyle("-fx-text-inner-color: #a0a2ab");

        handler = new DBhandler();
    }


    @FXML
    public void loginAction(ActionEvent e){
        progress.setVisible(true);
        PauseTransition pt = new PauseTransition();
        pt.setDuration(Duration.seconds(3));
        pt.setOnFinished(ev -> {
        });
        pt.play();
        //retrieve data from database
        connection = handler.getConnection();

        String query = "SELECT * FROM rangerover WHERE username = ? and password = ?";
        try {
            pst = connection.prepareStatement(query);
            pst.setString(1, username.getText());
            pst.setString(2, password.getText());
            ResultSet rs = pst.executeQuery();

            int cnt = 0;
            while(rs.next()){
                cnt = cnt+1;
            }
            if(cnt==1){
                System.out.println("login successful");
                login.getScene().getWindow().hide();
                Stage ipport = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/FXML/IPPort.fxml"));
                Scene scene = new Scene(root);
                ipport.setScene(scene);
                ipport.show();
                ipport.setResizable(false);
            }
            else{
                System.out.println("username and password is not correct");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }
    @FXML
    public void signUp(ActionEvent e) throws IOException {
        login.getScene().getWindow().hide();
        Stage signup = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/SignUp.fxml"));
        Scene scene = new Scene(root);
        signup.setScene(scene);
        signup.show();
        signup.setResizable(false);
    }

    public  String getUserName(){
        String user = username.getText();
        return user;
    }
}
