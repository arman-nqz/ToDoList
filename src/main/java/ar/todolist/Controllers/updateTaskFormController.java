package ar.todolist.Controllers;

import ar.todolist.Database.DatabaseHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class updateTaskFormController {

    @FXML
    private JFXButton editTaskButtunUpdate;

    @FXML
    private JFXTextField taskDescriptionTestFieldUpdate;

    @FXML
    private JFXTextField taskNameTestFieldUpdate;

    private DatabaseHandler databaseHandler;

    private int taskId;


    @FXML
    void initialize() {
        editTaskButtunUpdate.setOnAction(event -> {editTasks();});
    }

    public void editTasks(){

        if (!taskDescriptionTestFieldUpdate.getText().equals("") && !taskNameTestFieldUpdate.getText().equals("")) {
            String taskName = taskNameTestFieldUpdate.getText().trim();
            String taskDescription = taskDescriptionTestFieldUpdate.getText().trim();
            Timestamp datecreated = new Timestamp(System.currentTimeMillis());

            databaseHandler = new DatabaseHandler();
            System.out.println("TaskId is :" +taskId);
            try {
                databaseHandler.editTask(taskName, taskDescription, datecreated, taskId);

                // Grab the active ListController and refresh it directly!
                if(ListController.getInstance() != null) {
                    ListController.getInstance().refreshList();
                }

                editTaskButtunUpdate.getScene().getWindow().hide();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        } else if (!taskDescriptionTestFieldUpdate.getText().equals("")) {
            System.out.println("Please enter the task description");
        } else if (!taskNameTestFieldUpdate.getText().equals("")) {
            System.out.println("Please enter the task name");
        } else if (!taskNameTestFieldUpdate.getText().equals("") && !taskDescriptionTestFieldUpdate.getText().equals("")) {
            System.out.println("Don't leave blank");
        } else {
            System.out.println("Unexpected error happened");
        }


    }



    //getter and setters for text fields and taskId
    public void setTaskName(String taskName) {
        this.taskNameTestFieldUpdate.setText(taskName);
    }
    public void setTaskDescription(String taskDescription) {
        this.taskDescriptionTestFieldUpdate.setText(taskDescription);
    }
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}
