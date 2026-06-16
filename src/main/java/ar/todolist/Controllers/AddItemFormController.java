package ar.todolist.Controllers;

import ar.todolist.Database.DatabaseHandler;
import ar.todolist.model.Task;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.ResourceBundle;

public class AddItemFormController {

    private int userId;
    private DatabaseHandler databaseHandler;

    @FXML
    private Label addItemFormAlertLabel;

    @FXML
    private JFXButton addItemFormMyToDosButton;

    @FXML
    private JFXTextField addItemFormDescriptionTextField;

    @FXML
    private JFXButton addItemFormSaveTaskButton;

    @FXML
    private JFXTextField addItemFormTaskTextField;

    @FXML
    void initialize() {
        databaseHandler = new DatabaseHandler();



        addItemFormSaveTaskButton.setOnAction(event -> {

            Task task = new  Task();

            Calendar calendar = Calendar.getInstance();

            java.sql.Timestamp timestamp =  new java.sql.Timestamp(calendar.getTimeInMillis());

            String taskText = addItemFormTaskTextField.getText().trim();
            String taskDescription = addItemFormDescriptionTextField.getText().trim();

            if (!taskText.equals("") || !taskDescription.equals("")) {

                System.out.println("User Id: " + AddItemController.userId);

                task.setUserId(AddItemController.userId);
                task.setDatecreated(timestamp);
                task.setDescription(taskDescription);
                task.setTask(taskText);

                databaseHandler.insertTask(task);

                addItemFormAlertLabel.setVisible(true);

                addItemFormMyToDosButton.setVisible(true);

                int taskNumber = 0;
                try {
                    taskNumber = databaseHandler.getAllTasks(AddItemController.userId);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                addItemFormMyToDosButton.setText("My ToDos: " + "(" + taskNumber + ")");

                addItemFormTaskTextField.setText("");
                addItemFormDescriptionTextField.setText("");

                addItemFormMyToDosButton.setOnAction(event1 ->{
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/ar/todolist/list.fxml"));
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

            }else {
                System.out.println("Nothing can been added!");
            }




        });

    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
        System.out.println(this.userId);
    }
}
