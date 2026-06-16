package ar.todolist.Controllers;

import ar.todolist.Database.DatabaseHandler;
import ar.todolist.model.Task;

import com.jfoenix.controls.JFXListCell;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CellController extends JFXListCell<Task> {

    @FXML
    private Label cellDateLabel;

    @FXML
    private ImageView cellDeleteButton;

    @FXML
    private ImageView cellImageView;

    @FXML
    private AnchorPane cellRootAnkerPane;

    @FXML
    private Label cellTaskDescription;

    @FXML
    private Label cellTaskLabel;

    @FXML
    private ImageView editImageButtonCell;

    private FXMLLoader fxmlLoader;

    private DatabaseHandler databaseHandler;

    @FXML
    void initialize() {
        System.out.println("User Id from cell controller: " + AddItemController.userId);
    }

    @Override
    protected void updateItem(Task myTask, boolean empty) {
        super.updateItem(myTask, empty);

        if (empty || myTask == null) {
            setText(null);
            setGraphic(null);
        }else {
            if (fxmlLoader == null) {
                fxmlLoader = new FXMLLoader(getClass().getResource("/ar/todolist/cell.fxml"));
                fxmlLoader.setController(this);
                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            cellTaskLabel.setText(myTask.getTask());
            cellDateLabel.setText(myTask.getDatecreated().toString());
            cellTaskDescription.setText(myTask.getDescription());

            int taskId = myTask.getTaskId();


            cellDeleteButton.setOnMouseClicked((event) -> {
                databaseHandler = new DatabaseHandler();
                try {
                    databaseHandler.deleteTask(AddItemController.userId,taskId);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                getListView().getItems().remove(getItem());

            });

            editImageButtonCell.setOnMouseClicked((event) -> {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/ar/todolist/updateTaskForm.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                //set TaskField and TaskDescription in the opened page
                updateTaskFormController controller = loader.getController();
                controller.setTaskName(myTask.getTask());
                controller.setTaskDescription(myTask.getDescription());
                controller.setTaskId(myTask.getTaskId());

                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();

            });



            setText(null);
            setGraphic(cellRootAnkerPane);


        }



    }

}
