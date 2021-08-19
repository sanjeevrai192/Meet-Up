package controllers;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.util.Duration;

import DBConnection.DBhandler;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    @FXML
    private AnchorPane parentPane;

    @FXML
    private Button signup;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    private Button loginBTN;

    private Connection connection;
    private DBhandler handler;
    private PreparedStatement pst;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        username.setStyle("-fx-text-inner-color: #a0a2ab");
        password.setStyle("-fx-text-inner-color: #a0a2ab");

        handler = new DBhandler();
    }

    public void signUp(ActionEvent actionEvent) {
        PauseTransition pt = new PauseTransition();
        pt.setDuration(Duration.seconds(3));
        pt.setOnFinished(ev -> {
        });
        pt.play();

        // Saving data
        String insert  = "INSERT INTO rangerover(username,password)" + "VALUES (?, ?)";
        connection = handler.getConnection();

        try {
            pst = connection.prepareStatement(insert);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            pst.setString(1, username.getText());
            pst.setString(2, password.getText());

            pst.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void login(ActionEvent actionEvent) throws IOException {
        signup.getScene().getWindow().hide();
        Stage login = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/LoginMain.fxml"));
        Scene scene = new Scene(root);
        login.setScene(scene);
        login.show();
        login.setResizable(false);
    }
}
