package sample;

import connectivity.ConnectionClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableRow;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import javax.management.Notification;
import javax.swing.*;
import java.io.IOException;
import java.sql.*;

import static sample.OpenPageController.user_id;
import sample.MyConferencesController;
import sample.Conference;

public class OpenPageController {
    public TextField password;
    public TextField email;
    public static int user_id;
    public static boolean alert;
    public static String profession;






    public void signUp(ActionEvent actionEvent) throws IOException {
        Parent SignUpPageParent = FXMLLoader.load(getClass().getResource("SignUpPage.fxml"));
        Scene SignUpPageScene = new Scene(SignUpPageParent);

        // This line gets stage information
        Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(SignUpPageScene);
        window.hide();
        window.setMaximized(true);

        window.show();

    }


    public void signIn(ActionEvent actionEvent) throws SQLException, IOException {
        try{
            ConnectionClass connectionClass = new ConnectionClass();
            Connection connection=connectionClass.getConnection();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT PROFESSION,USER_ID,EMAIL,PASSWORD FROM USER WHERE EMAIL='"+email.getText()+"' AND PASSWORD='"+password.getText()+"'");

            if (result.next()) {
                if ((email.getText().equals(result.getString("EMAIL"))) & (password.getText().equals(result.getString("PASSWORD")))) {
                    user_id = result.getInt("USER_ID");
                    profession = result.getString("PROFESSION");
                    System.out.println(user_id);
                    System.out.println("welcome to e-conference!");
                    if(profession.equals("Thesis student") || profession.equals("Independent User"))
                            {Parent HomePageParent = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
                            Scene HomePageScene = new Scene(HomePageParent);
                            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                            window.setScene(HomePageScene);
                            window.hide();
                            window.setMaximized(true);

                            window.show();
                            alert=thereIsAnAlert();}
                    else {

                        Parent HomePageParent = FXMLLoader.load(getClass().getResource("HomePageForProfessor.fxml"));
                        Scene HomePageScene = new Scene(HomePageParent);
                        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        window.setScene(HomePageScene);
                        window.hide();
                        window.setMaximized(true);

                        window.show();


                    }

                }
            }
            else {
                System.out.println("your email or password are incorrect!");
                Parent signUpParent = FXMLLoader.load(getClass().getResource("OpenPage.fxml"));
                Scene signUpScene = new Scene(signUpParent);
                Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                window.setScene(signUpScene);
                window.hide();
                window.setMaximized(true);

                window.show();

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("password or email invalid");
                alert.setHeaderText(null);
                alert.setContentText("please enter valid email or password");
                alert.showAndWait();
            }
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }




    public boolean thereIsAnAlert() throws SQLException {
            // connect to the database
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection=connectionClass.getConnection();
            // create statement to first get the conference id's of the user
        Statement statement = connection.createStatement();
        String sql="SELECT conference_id FROM my_conference where user_id='"+user_id+"'";
        ResultSet result = statement.executeQuery(sql);
        while (result.next())
                {
                        // check if any of the user_conferences has a submission, notification, or confirmation date that is approaching
                    // connect to the database
                    ConnectionClass connectionClass_check = new ConnectionClass();
                    Connection connection_check = connectionClass_check.getConnection();
                    Statement statement_check = connection_check.createStatement();
                    String sql_check="SELECT conference_id FROM CONFERENCE WHERE conference_id='"+ result.getInt("conference_id")+"' AND ( DATEDIFF(submission_date, CURDATE()) = 11 OR DATEDIFF(notification_date, CURDATE()) = 32 OR DATEDIFF(confirmation_date, CURDATE()) = 45) ";
                    ResultSet result_check = statement_check.executeQuery(sql_check);
                        if(result_check.next()) {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("YOU HAVE NEW NOTIFICATIONS");
                            alert.setHeaderText(null);
                            alert.setContentText("please go to my conferences page to check your notifications!");
                            alert.showAndWait();
                            return true;
                        }
                }
            // case where user has no conference
        System.out.println("User has no conferences");
        return false;

    }



}


