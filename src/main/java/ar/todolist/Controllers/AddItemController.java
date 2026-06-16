package ar.todolist.Controllers;

import ar.todolist.animations.Shaker;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddItemController {

    public static int userId;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView addItemAddButton;

    @FXML
    private Label addItemNoTaskLabel;

    @FXML
    private AnchorPane addItemRootAnchorPane;

    @FXML
    void initialize() {
        addItemAddButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            Shaker buttonShaker = new Shaker(addItemAddButton);
            buttonShaker.shake();

            FadeTransition fadeTransitionButton = new FadeTransition(Duration.millis(500), addItemAddButton);
            FadeTransition fadeTransitionLabel = new FadeTransition(Duration.millis(500), addItemNoTaskLabel);

            System.out.println("Image button clicked!");
            addItemAddButton.relocate(0,20);
            addItemNoTaskLabel.relocate(0,80);

            addItemNoTaskLabel.setOpacity(0);
            addItemAddButton.setOpacity(0);

            fadeTransitionButton.setFromValue(1f);
            fadeTransitionButton.setToValue(0);
            fadeTransitionButton.setCycleCount(1);
            fadeTransitionButton.setAutoReverse(false);
            fadeTransitionButton.play();

            fadeTransitionLabel.setFromValue(1f);
            fadeTransitionLabel.setToValue(0);
            fadeTransitionLabel.setCycleCount(1);
            fadeTransitionLabel.setAutoReverse(false);
            fadeTransitionLabel.play();

            try {
                AnchorPane fromPane =
                        FXMLLoader.load(getClass().getResource("/ar/todolist/addItemForm.fxml"));


                AddItemController.userId = getUserId();

                //AddItemFormController addItemFormController = new AddItemFormController();
                //addItemFormController.setUserId(getUserId());



                FadeTransition rootTransition = new FadeTransition(Duration.millis(1000), fromPane);
                rootTransition.setFromValue(0f);
                rootTransition.setToValue(1);
                rootTransition.setCycleCount(1);
                rootTransition.setAutoReverse(false);
                rootTransition.play();


                addItemRootAnchorPane.getChildren().setAll(fromPane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });

    }


    //getting the userid from login screen controller
    public void setUserId(int userId) {
        this.userId = userId;
        System.out.println("User Id is " + this.userId);
    }



    public int getUserId() {
        return this.userId;
    }

}
