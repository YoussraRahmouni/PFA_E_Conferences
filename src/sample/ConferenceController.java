package sample;

import connectivity.ConnectionClass;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static sample.MyConferencesController.*;
import static sample.OpenPageController.user_id;


public class ConferenceController  implements Initializable{

        // our radio buttons
    public RadioButton Submitted;
    public RadioButton Not_Submitted;
    public RadioButton Accepted;
    public RadioButton Not_Accepted;
    public RadioButton Confirmed;
    public RadioButton Not_Confirmed;

    public Integer conf_id=0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        // check if it's submission date and show the associated notification
        showSubmissionDateNotification();
        // check if it's notification date and show the associated notification
        showNotificationDateNotififcation();
        // check if it's confirmation date and show the associated notification
        showConfirmationDateNotification();


          // get conference ID
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection=connectionClass.getConnection();
        // create sql statement
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql = "SELECT CONFERENCE_ID FROM  CONFERENCE WHERE  CONFERENCE_NAME='"+ Conference_Name +"'";
        // store result in result set
        ResultSet result = null;
        try {
            result = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            result.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            conf_id = result.getInt("CONFERENCE_ID");
        } catch (SQLException e) {
            e.printStackTrace();
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
        submission_Group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {

                if (submission_Group.getSelectedToggle() != null) {

                    System.out.println(submission_Group.getSelectedToggle().getUserData().toString());
                    // Do something here with the userData of newly selected radioButton
                    if(submission_Group.getSelectedToggle().getUserData().toString().equals("submitted")){
                        System.out.println("1");

                        // connect to the database
                        ConnectionClass connectionClass = new ConnectionClass();
                        Connection connection=connectionClass.getConnection();
                        // create sql statement
                        PreparedStatement statementUpdate = null;
                        try {
                            statementUpdate = connection.prepareStatement(" UPDATE my_conference SET  submission_state='1' where conference_id='" + finalConf_id + "' and user_id='" + user_id + "'  ");
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        try {
                            statementUpdate.executeUpdate();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    }
                    else  {
                        System.out.println("0");
                        // connect to the database
                        ConnectionClass connectionClass = new ConnectionClass();
                        Connection connection=connectionClass.getConnection();
                        // create sql statement
                        PreparedStatement statementUpdate = null;
                        try {
                            statementUpdate = connection.prepareStatement(" UPDATE my_conference SET  submission_state='0' where conference_id='" + finalConf_id + "' and user_id='" + user_id + "'  ");
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        try {
                            statementUpdate.executeUpdate();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    };

                }

            }
        });

                // set the radio button according to the state in the database

        Statement submission_statement = null;
        try {
            submission_statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // sql query to get the conference id
        String submission_sql = "SELECT my_conference_id From my_conference where submission_state='1' AND conference_id='" + finalConf_id + "' AND user_id='"+user_id+"' ";
        // store result in result set
        ResultSet submission_result = null;
        try {
            submission_result = submission_statement.executeQuery(submission_sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if(submission_result.next()){
                Submitted.setSelected(true);
            }
            else Not_Submitted.setSelected(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }



        notification_Group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {

                if (notification_Group.getSelectedToggle() != null) {

                    System.out.println(notification_Group.getSelectedToggle().getUserData().toString());
                    // Do something here with the userData of newly selected radioButton
                    if(notification_Group.getSelectedToggle().getUserData().toString().equals("accepted")){
                        System.out.println("1");

                        // connect to the database
                        ConnectionClass connectionClass = new ConnectionClass();
                        Connection connection=connectionClass.getConnection();
                        // create sql statement
                        PreparedStatement statementUpdate = null;
                        try {
                            statementUpdate = connection.prepareStatement(" UPDATE my_conference SET  notification_state='1' where conference_id='" + finalConf_id + "' and user_id='" + user_id + "'  ");
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        try {
                            statementUpdate.executeUpdate();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    }
                    else  {
                        System.out.println("0");
                        // connect to the database
                        ConnectionClass connectionClass = new ConnectionClass();
                        Connection connection=connectionClass.getConnection();
                        // create sql statement
                        PreparedStatement statementUpdate = null;
                        try {
                            statementUpdate = connection.prepareStatement(" UPDATE my_conference SET  notification_state='0' where conference_id='" + finalConf_id + "' and user_id='" + user_id + "'  ");
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        try {
                            statementUpdate.executeUpdate();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    };

                }

            }
        });

        // set the radio button according to the state in the database

        Statement notification_statement = null;
        try {
            notification_statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // sql query to get the conference id
        String notification_sql = "SELECT my_conference_id From my_conference where notification_state='1' AND conference_id='" + finalConf_id + "' AND user_id='"+user_id+"' ";
        // store result in result set
        ResultSet notification_result = null;
        try {
            notification_result =  notification_statement.executeQuery(notification_sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if(notification_result.next()){
                Accepted.setSelected(true);
            }
            else Not_Accepted.setSelected(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        confirmation_Group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {

                if (confirmation_Group.getSelectedToggle() != null) {

                    System.out.println(confirmation_Group.getSelectedToggle().getUserData().toString());
                    // Do something here with the userData of newly selected radioButton
                    if(confirmation_Group.getSelectedToggle().getUserData().toString().equals("confirmed")){
                        System.out.println("1");

                        // connect to the database
                        ConnectionClass connectionClass = new ConnectionClass();
                        Connection connection=connectionClass.getConnection();
                        // create sql statement
                        PreparedStatement statementUpdate = null;
                        try {
                            statementUpdate = connection.prepareStatement(" UPDATE my_conference SET  confirmation_state='1' where conference_id='" + finalConf_id + "' and user_id='" + user_id + "'  ");
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        try {
                            statementUpdate.executeUpdate();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    }
                    else  {
                        System.out.println("0");
                        // connect to the database
                        ConnectionClass connectionClass = new ConnectionClass();
                        Connection connection=connectionClass.getConnection();
                        // create sql statement
                        PreparedStatement statementUpdate = null;
                        try {
                            statementUpdate = connection.prepareStatement(" UPDATE my_conference SET  confirmation_state='0' where conference_id='" + finalConf_id + "' and user_id='" + user_id + "'  ");
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        try {
                            statementUpdate.executeUpdate();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    };

                }

            }
        });

        // set the radio button according to the state in the database

        Statement confirmation_statement = null;
        try {
            confirmation_statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // sql query to get the conference id
        String confirmation_sql = "SELECT my_conference_id From my_conference where confirmation_state='1' AND conference_id='" + finalConf_id + "' AND user_id='"+user_id+"' ";
        // store result in result set
        ResultSet confirmation_result = null;
        try {
            confirmation_result = confirmation_statement.executeQuery(confirmation_sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if(confirmation_result.next()){
                Confirmed.setSelected(true);
            }
            else Not_Confirmed.setSelected(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }






    }

    public void showSubmissionDateNotification(){
        if(SUB_DATE_CONFERENCE.minusDays(9).equals(LocalDate.now())){
            String title = "Submission";
            String message = "ALERT! submission date in one week";
            AnimationType type = AnimationType.SLIDE;
            TrayNotification tray = new TrayNotification();
            tray.setAnimationType(type);
            tray.setTitle(title);
            tray.setMessage(message);
            tray.setNotificationType(NotificationType.WARNING);
            tray.showAndWait();
        }
        else if (SUB_DATE_CONFERENCE.equals(LocalDate.now())){
            String title = "Submission";
            String message = "Did you submit your papers? Please answer. ";
            AnimationType type = AnimationType.SLIDE;
            TrayNotification tray = new TrayNotification();
            tray.setAnimationType(type);
            tray.setTitle(title);
            tray.setMessage(message);
            tray.setNotificationType(NotificationType.WARNING);
            tray.showAndWait();
        }

    }

    public void showNotificationDateNotififcation(){

        if (NOTIFY_DATE_CONFERENCE.minusDays(117).equals(LocalDate.now())){
            String title = "Notification";
            String message = "Were you notified? Please answer. ";
            AnimationType type = AnimationType.SLIDE;
            TrayNotification tray = new TrayNotification();
            tray.setAnimationType(type);
            tray.setTitle(title);
            tray.setMessage(message);
            tray.setNotificationType(NotificationType.WARNING);
            tray.showAndWait();
        }

    }
    public void  showConfirmationDateNotification(){
        if (CONFIRM_DATE_CONFERENCE.minusWeeks(1).equals(LocalDate.now())){
            String title = "Confirmation";
            String message = "Confirmation in one week!";
            AnimationType type = AnimationType.SLIDE;
            TrayNotification tray = new TrayNotification();
            tray.setAnimationType(type);
            tray.setTitle(title);
            tray.setMessage(message);
            tray.setNotificationType(NotificationType.WARNING);
            tray.showAndWait();
        }
        else if (CONFIRM_DATE_CONFERENCE.minusDays(146).equals(LocalDate.now())){
            String title = "Confirmation";
            String message = "Did you confirm? Please answer. ";
            AnimationType type = AnimationType.SLIDE;
            TrayNotification tray = new TrayNotification();
            tray.setAnimationType(type);
            tray.setTitle(title);
            tray.setMessage(message);
            tray.setNotificationType(NotificationType.WARNING);
            tray.showAndWait();
        }

    }
    public void GoToConferencesList(ActionEvent actionEvent) throws IOException {
        Parent ConferencesListParent = FXMLLoader.load(getClass().getResource("ConferencesListPage.fxml"));
        Scene ConferencesListScene = new Scene(ConferencesListParent);

        // This line gets stage information
        Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(ConferencesListScene);
        window.hide();
        window.setMaximized(true);

        window.show();

    }


    public void GoToMyAccount(ActionEvent actionEvent) throws IOException {

            Parent MyAccountParent = FXMLLoader.load(getClass().getResource("MyAccountPage.fxml"));
            Scene MyAccountPageScene = new Scene(MyAccountParent);
            // This line gets stage information
            Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene( MyAccountPageScene);
            window.hide();
            window.setMaximized(true);
            window.show();

    }

    public void GoToMyConferences(ActionEvent actionEvent) throws IOException {
        Parent MyConferencesParent = FXMLLoader.load(getClass().getResource("MyConferences.fxml"));
        Scene MyConferencesScene = new Scene(MyConferencesParent);
        // This line gets stage information
        Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene( MyConferencesScene);
        window.hide();
        window.setMaximized(true);
        window.show();
    }

    public void GoToMyStatistics(ActionEvent actionEvent) throws IOException {
        Parent StatisticsParent = FXMLLoader.load(getClass().getResource("Statistics.fxml"));
        Scene StatisticsScene = new Scene(StatisticsParent);
        // This line gets stage information
        Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(StatisticsScene);
        window.hide();
        window.setMaximized(true);
        window.show();
    }
}
