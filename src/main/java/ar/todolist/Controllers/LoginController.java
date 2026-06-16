package ar.todolist.Controllers;

import ar.todolist.Database.DatabaseHandler;
import ar.todolist.animations.Shaker;
import ar.todolist.model.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController {

    private int userId;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXButton loginLoginButton;

    @FXML
    private JFXPasswordField loginPasswordField;

    @FXML
    private JFXButton loginSignUpButton;

    @FXML
    private JFXTextField loginUsernameTextField;

    @FXML
    private DatabaseHandler databaseHandler;

    @FXML
    void initialize() {

        databaseHandler = new DatabaseHandler();

        loginLoginButton.setOnAction(event -> {

            String loginText = loginUsernameTextField.getText().trim();
            String loginPwd = loginPasswordField.getText().trim();

            User user = new User();
            user.setUserName(loginText);
            user.setPassword(loginPwd);

            ResultSet userRow = databaseHandler.getUser(user);

            int counter = 0;
            try {
                while (userRow.next()) {
                    counter++;
                    String name = userRow.getString("firstname");

                    //getting the user id
                    userId = userRow.getInt("userid");

                    System.out.println("Welcome! " + name);
                }

                if (counter == 1) {
                    showAddItemScreen();
                }else {
                    Shaker shaker = new Shaker(loginUsernameTextField);
                    Shaker shaker2 = new Shaker(loginPasswordField);
                    shaker.shake();
                    shaker2.shake();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        });

        loginSignUpButton.setOnAction(event -> {
            loginSignUpButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/ar/todolist/signUp.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stage.showAndWait();
        });

    }

    private void showAddItemScreen() {
        //Take users to AddItem screen
        loginSignUpButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("/ar/todolist/addItem.fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));

        //Passing userid to addItem Controller
        AddItemController addItemController = loader.getController();
        addItemController.setUserId(userId);


        stage.showAndWait();
    }


}
