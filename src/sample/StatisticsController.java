package sample;

import connectivity.ConnectionClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;


import static sample.OpenPageController.user_id;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static sample.OpenPageController.alert;

public class StatisticsController implements Initializable {


    @FXML
    private BarChart<String, Integer> BarChart;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        XYChart.Series series = new XYChart.Series<>();





        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();

        String query = "SELECT SUM(NOTIFICATION_STATE) FROM MY_CONFERENCE WHERE USER_ID ='" + user_id + "' ";
        Statement statement = null;

        Statement state = null;
        try {
            state = connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        ResultSet result1 = null;
        try {
            result1 = state.executeQuery("SELECT * FROM MY_CONFERENCE WHERE USER_ID ='"+ user_id + "'");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            statement = connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        ResultSet result = null;

        try {
            result = statement.executeQuery(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }



        while (true) {

            try {
                if (!result.next()) break;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                System.out.println(result.getString("sum(notification_state)"));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            int sum = 0;
            try {
                sum = result.getInt("sum(notification_state)");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            series.getData().add(new XYChart.Data("accepted_submissions",sum));

            ResultSetMetaData resultMeta = null;
            try {
                resultMeta = result1.getMetaData();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            int nbr_columns = 0;
            try {
                nbr_columns = resultMeta.getColumnCount();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            int inaccepted = nbr_columns-sum;
            series.getData().add(new XYChart.Data("inaccepted_submissions",inaccepted));

            BarChart.getData().addAll(series);


        }
    }
    public void GoToMyConferences(ActionEvent actionEvent) throws IOException {
        Parent MyConferencesParent = FXMLLoader.load(getClass().getResource("MyConferences.fxml"));
        Scene MyConferencesPageScene = new Scene(MyConferencesParent);

        // This line gets stage information
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(MyConferencesPageScene);
        window.hide();
        window.setMaximized(true);
        window.show();

    }

    public void GoToMyAccount(ActionEvent actionEvent) throws IOException {
        Parent MyAccountParent = FXMLLoader.load(getClass().getResource("MyAccountPage.fxml"));
        Scene MyAccountPageScene = new Scene(MyAccountParent);

        // This line gets stage information
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(MyAccountPageScene);
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

}
