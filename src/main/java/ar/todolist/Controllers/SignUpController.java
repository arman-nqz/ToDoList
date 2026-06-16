package ar.todolist.Controllers;

import ar.todolist.Database.DatabaseHandler;
import ar.todolist.model.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignUpController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXCheckBox signUpFemaleCheckbox;

    @FXML
    private JFXTextField signUpFirstnameTextField;

    @FXML
    private JFXTextField signUpLastnameTextField;

    @FXML
    private JFXTextField signUpLocationTextField;

    @FXML
    private JFXCheckBox signUpMaleCheckbox;

    @FXML
    private JFXPasswordField signUpPasswordPasswordField;

    @FXML
    private JFXTextField signUpUsernameTextField;

    @FXML
    private JFXButton SignUpSignUpButton;

    @FXML
     void initialize() {

        DatabaseHandler databaseHandler = new DatabaseHandler();

        SignUpSignUpButton.setOnAction(e -> {


            try {
                createUser();
            } catch (SQLException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }


        });

    }

    private void createUser() throws SQLException, ClassNotFoundException {

        DatabaseHandler databaseHandler = new DatabaseHandler();

        String name = signUpFirstnameTextField.getText();
        String lastName= signUpLastnameTextField.getText();
        String username = signUpUsernameTextField.getText();
        String password = signUpPasswordPasswordField.getText();
        String location = signUpLocationTextField.getText();
        //gender
        String gender = "";
        if (signUpFemaleCheckbox.isSelected()) {gender = "Female";}else{gender = "Male";}

        User user = new User(name, lastName, username, password, location, gender);

        databaseHandler.signUpUser(user);
    }

}
