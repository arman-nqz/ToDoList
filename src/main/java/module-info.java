module ar.todolist {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires java.sql;
    requires mysql.connector.j;


    opens ar.todolist to javafx.fxml;
    exports ar.todolist;
    exports ar.todolist.Controllers;
    opens ar.todolist.Controllers to javafx.fxml;
}