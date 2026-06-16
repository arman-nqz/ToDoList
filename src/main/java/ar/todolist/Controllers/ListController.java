package ar.todolist.Controllers;

import ar.todolist.Database.DatabaseHandler;
import ar.todolist.model.Task;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;


public class ListController {

    @FXML
    private JFXTextField listDescriptionTextField;

    @FXML
    private JFXButton listSaveTaskButton;

    @FXML
    private JFXTextField listTaskTextField;

    @FXML
    private ImageView listImageViewRefresh;

    @FXML
    private JFXListView<Task> listTasksView;

    private ObservableList<Task> tasksObservableList;
    private ObservableList<Task> refreshedTasksObservableList1;

    private DatabaseHandler databaseHandler;

    private static ListController instance;

    @FXML
    void initialize() throws SQLException {

        instance = this;

        tasksObservableList = FXCollections.observableArrayList();

        databaseHandler = new DatabaseHandler();
        ResultSet resultSet = databaseHandler.getTasksByUser(AddItemController.userId);

        while (resultSet.next()){
            Task myTask = new Task();
            myTask.setTaskId(resultSet.getInt("taskId"));
            myTask.setTask(resultSet.getString("task"));
            myTask.setDescription(resultSet.getString("description"));
            myTask.setDatecreated(resultSet.getTimestamp("datecreated"));

            tasksObservableList.add(myTask);
        }

        listTasksView.setItems(tasksObservableList);
        listTasksView.setCellFactory(CellController -> new CellController());

        listSaveTaskButton.setOnAction(event -> {
            addNewTask();
        });

        listImageViewRefresh.setOnMouseClicked(event -> {
            try {
                refreshList();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });



    }

    public void addNewTask(){
        if (!listTaskTextField.getText().equals("") || !listDescriptionTextField.getText().equals("")) {

            Task myNewTask = new Task();

            Calendar calendar = Calendar.getInstance();
            Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());

            myNewTask.setUserId(AddItemController.userId);
            myNewTask.setTask(listTaskTextField.getText().trim());
            myNewTask.setDescription(listDescriptionTextField.getText().trim());
            myNewTask.setDatecreated(timestamp);

            databaseHandler.insertTask(myNewTask);

            listTaskTextField.setText("");
            listDescriptionTextField.setText("");

            try {
                initialize();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void refreshList() throws SQLException {

        refreshedTasksObservableList1 = FXCollections.observableArrayList();

        DatabaseHandler databaseHandler = new DatabaseHandler();
        ResultSet resultSet = databaseHandler.getTasksByUser(AddItemController.userId);

        while (resultSet.next()){
            Task myTask = new Task();
            myTask.setTaskId(resultSet.getInt("taskId"));
            myTask.setTask(resultSet.getString("task"));
            myTask.setDatecreated(resultSet.getTimestamp("datecreated"));
            myTask.setDescription(resultSet.getString("description"));

            refreshedTasksObservableList1.addAll(myTask);
        }

        listTasksView.setItems(refreshedTasksObservableList1);
        listTasksView.setCellFactory(CellController -> new CellController());
    }


    //for updating list after the edit page closes
    public static ListController getInstance() {
        return instance;
    }



}
