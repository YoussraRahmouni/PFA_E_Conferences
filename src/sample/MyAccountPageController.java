package sample;

import connectivity.ConnectionClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import static sample.OpenPageController.user_id;

public class MyAccountPageController implements Initializable {
    @FXML
    public Label Username;
    @FXML
    public Label Profession;
    @FXML
    public Label Country;
    @FXML
    public Label Email;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("oups");

        ResultSet result = null;
        try {
            result = statement.executeQuery("SELECT user_name,email,profession,country FROM USER WHERE user_id='" + user_id + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if( result.next()) {
                String USERNAME = result.getString("user_name");
                String PROFESSION = result.getString("profession");
                String COUNTRY = result.getString("country");
                String EMAIL = result.getString("email");
                System.out.println("oups2");
                Username.setText(USERNAME);
                Profession.setText(PROFESSION);
                Country.setText(COUNTRY);
                Email.setText(EMAIL);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void GoToMyConferences(ActionEvent actionEvent) throws IOException {
        Parent MyConferencesParent = FXMLLoader.load(getClass().getResource("MyConferences.fxml"));
        Scene MyConferencesPageScene = new Scene(MyConferencesParent);

        // This line gets stage information
        Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene( MyConferencesPageScene);
        window.hide();
        window.setMaximized(true);
        window.show();
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

    public void GoToMyStatistics(ActionEvent actionEvent) throws IOException {
        Parent StatisticsParent = FXMLLoader.load(getClass().getResource("Statistics.fxml"));
        Scene StatisticsPageScene = new Scene(StatisticsParent);

        // This line gets stage information
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(StatisticsPageScene);
        window.hide();
        window.setMaximized(true);

        window.show();
    }
}
