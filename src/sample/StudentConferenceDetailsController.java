package sample;

import connectivity.ConnectionClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import static sample.MyThesisStudentsController.thesisStudent_Clicked;
import static sample.StudentConferencesController.table_Clicked;


public class StudentConferenceDetailsController implements Initializable {

    // our radio buttons
    public RadioButton Submitted;
    public RadioButton Not_Submitted;
    public RadioButton Accepted;
    public RadioButton Not_Accepted;
    public RadioButton Confirmed;
    public RadioButton Not_Confirmed;

    public Integer conf_id = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        // get user_id of the student and conference ID
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        // create sql statement
        Statement statement1 = null;
        try {
            statement1 = connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String sql1 = "SELECT USER_ID FROM USER WHERE USER_NAME ='" + thesisStudent_Clicked + "'";
        // store result in result set
        ResultSet result1 = null;
        try {
            result1 = statement1.executeQuery(sql1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            result1.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        int userId = 0;
        try {
            userId = result1.getInt("user_id");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println(userId);

        // create sql statement
        Statement statement2 = null;
        try {
            statement2 = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql = "SELECT CONFERENCE_ID FROM CONFERENCE WHERE  CONFERENCE_NAME='" + table_Clicked + "' ";
        // store result in result set
        ResultSet result2 = null;
        try {
            result2 = statement2.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (result2.next()) {


                conf_id = result2.getInt("CONFERENCE_ID");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        // create our Toggle Groups in order to make the user only chose one choice
        ToggleGroup submission_Group = new ToggleGroup();
        ToggleGroup notification_Group = new ToggleGroup();
        ToggleGroup confirmation_Group = new ToggleGroup();

        // Add radio buttons to the Toggle Groups
        Submitted.setToggleGroup(submission_Group);
        Not_Submitted.setToggleGroup(submission_Group);
        Accepted.setToggleGroup(notification_Group);
        Not_Accepted.setToggleGroup(notification_Group);
        Confirmed.setToggleGroup(confirmation_Group);
        Not_Confirmed.setToggleGroup(confirmation_Group);


        // setting user data for each radio button
        Submitted.setUserData("submitted");
        Not_Submitted.setUserData("Not submitted");
        Accepted.setUserData("accepted");
        Not_Accepted.setUserData("Not accepted");
        Confirmed.setUserData("confirmed");
        Not_Confirmed.setUserData("Not confirmed");

        Integer finalConf_id = conf_id;


        // set the radio button according to the state in the database

        Statement submission_statement = null;
        try {
            submission_statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // sql query to get the conference id
        String submission_sql = "SELECT my_conference_id From my_conference where submission_state='1' AND conference_id='" + finalConf_id + "' AND user_id='" + userId + "' ";
        // store result in result set
        ResultSet submission_result = null;
        try {
            submission_result = submission_statement.executeQuery(submission_sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (submission_result.next()) {
                Submitted.setSelected(true);
            } else Not_Submitted.setSelected(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        // set the radio button according to the state in the database

        Statement notification_statement = null;
        try {
            notification_statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // sql query to get the conference id
        String notification_sql = "SELECT my_conference_id From my_conference where notification_state='1' AND conference_id='" + finalConf_id + "' AND user_id='" + userId + "' ";
        // store result in result set
        ResultSet notification_result = null;
        try {
            notification_result = notification_statement.executeQuery(notification_sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (notification_result.next()) {
                Accepted.setSelected(true);
            } else Not_Accepted.setSelected(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        // set the radio button according to the state in the database

        Statement confirmation_statement = null;
        try {
            confirmation_statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // sql query to get the conference id
        String confirmation_sql = "SELECT my_conference_id From my_conference where confirmation_state='1' AND conference_id='" + finalConf_id + "' AND user_id='" + userId + "' ";
        // store result in result set
        ResultSet confirmation_result = null;
        try {
            confirmation_result = confirmation_statement.executeQuery(confirmation_sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (confirmation_result.next()) {
                Confirmed.setSelected(true);
            } else Not_Confirmed.setSelected(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Submitted.setDisable(true);
        Not_Submitted.setDisable(true);
        Accepted.setDisable(true);
        Not_Accepted.setDisable(true);
        Confirmed.setDisable(true);
        Not_Confirmed.setDisable(true);


    }


    public void GoToGroupStatistics(ActionEvent actionEvent) throws IOException {
        Parent StatisticsParent = FXMLLoader.load(getClass().getResource("GroupStatistics.fxml"));
        Scene StatisticsScene = new Scene(StatisticsParent);

        // This line gets stage information
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(StatisticsScene);
        window.hide();
        window.setMaximized(true);
        window.show();

    }

    public void GoToThesisStudents(ActionEvent actionEvent) throws IOException {

        Parent ThesisStudentsParent = FXMLLoader.load(getClass().getResource("MyThesisStudents.fxml"));
        Scene ThesisStudentsScene = new Scene(ThesisStudentsParent);

        // This line gets stage information
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(ThesisStudentsScene);
        window.hide();
        window.setMaximized(true);
        window.show();

    }

    public void GoToMyAccount(ActionEvent actionEvent) throws IOException {
        Parent MyAccountParent = FXMLLoader.load(getClass().getResource("MyAccountPageProfessor.fxml"));
        Scene MyAccountPageScene = new Scene(MyAccountParent);

        // This line gets stage information
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(MyAccountPageScene);
        window.show();
    }

    public void GoToConferencesList(ActionEvent actionEvent) throws IOException {
        Parent ConferencesListParent = FXMLLoader.load(getClass().getResource("ProfessorConferencesListPage.fxml"));
        Scene ConferencesListScene = new Scene(ConferencesListParent);

        // This line gets stage information
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(ConferencesListScene);
        window.hide();
        window.setMaximized(true);
        window.show();

    }
}
