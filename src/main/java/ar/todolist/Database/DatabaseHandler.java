package ar.todolist.Database;

import ar.todolist.model.Task;
import ar.todolist.model.User;

import java.sql.*;

public class DatabaseHandler extends Configs {
    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;

        Class.forName("com.mysql.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);

        return dbConnection;
    }

    //Delete a user task by taskId
    public void deleteTask(int userId, int taskId) throws SQLException, ClassNotFoundException {
        String query = "DELETE FROM " + Const.TASKS_TABLE + " WHERE " + Const.USERS_ID + "=?"
                + " AND " + Const.TASKS_ID + "=?";

        PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
        preparedStatement.setInt(1,userId);
        preparedStatement.setInt(2,taskId);
        preparedStatement.execute();
        preparedStatement.close();
    }

    //edit(Update) Task
    public void editTask(String taskName, String taskDescription, Timestamp datecreated, int taskId) throws SQLException, ClassNotFoundException {
        String query = "UPDATE " + Const.TASKS_TABLE + " SET " +
                Const.TASKS_TASK + "=?, "+
                Const.TASKS_DESCRIPTION + "=?, "+
                Const.TASKS_DATE + "=? " +
                " WHERE " + Const.TASKS_ID + "=?";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
        preparedStatement.setString(1,taskName);
        preparedStatement.setString(2,taskDescription);
        preparedStatement.setTimestamp(3,datecreated);
        preparedStatement.setInt(4,taskId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    //Write to db
    public void signUpUser(User user) throws ClassNotFoundException, SQLException {
        String insert = "INSERT INTO "+ Const.USERS_TABLE+"("+Const.USERS_FIRSTNAME+","+Const.USERS_LASTNAME+","
                +Const.USERS_USERNAME+","+Const.USERS_PASSWORD+","+Const.USERS_LOCATION+","+Const.USERS_GENDER+")"+
                " VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);

            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getUserName());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5,user.getLocation());
            preparedStatement.setString(6,user.getGender());

            preparedStatement.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet getTasksByUser(int userId) {

        ResultSet resultSet = null;

        String query = "SELECT * FROM  " + Const.TASKS_TABLE + " WHERE " + Const.USERS_ID + "=?";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setInt(1, userId);

            resultSet = preparedStatement.executeQuery();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


        return resultSet;
    }

    public ResultSet getUser(User user) {

        ResultSet resultSet = null;

        if (!user.getUserName().equals("") || !user.getPassword().equals("")) {
            String query = "SELECT * FROM " + Const.USERS_TABLE + " WHERE " + Const.USERS_USERNAME
                    + "=?" + " AND " + Const.USERS_PASSWORD + "=?";
            // select all from users where username="Arman" and Password="password"

            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = getDbConnection().prepareStatement(query);
                preparedStatement.setString(1, user.getUserName());
                preparedStatement.setString(2, user.getPassword());

                resultSet = preparedStatement.executeQuery();

            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }else {
            System.out.println("Please enter your credentials");
        }

        return resultSet;
    }

    public int getAllTasks(int userId) throws SQLException, ClassNotFoundException {

        String query = "SELECT COUNT(*) FROM " + Const.TASKS_TABLE + " WHERE " + Const.USERS_ID + "=?";

        PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
        preparedStatement.setInt(1, userId);

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            return resultSet.getInt(1);
        }

        return resultSet.getInt(1);

    }

    public void insertTask(Task task) {
        String insert = "INSERT INTO "+ Const.TASKS_TABLE+"("+Const.USERS_ID +","+Const.TASKS_DATE+","+Const.TASKS_DESCRIPTION+","
                +Const.TASKS_TASK+")"+ " VALUES (?,?,?,?)";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);

            preparedStatement.setInt(1, task.getUserId());
            preparedStatement.setTimestamp(2, task.getDatecreated());
            preparedStatement.setString(3, task.getDescription());
            preparedStatement.setString(4, task.getTask());

            preparedStatement.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


}
