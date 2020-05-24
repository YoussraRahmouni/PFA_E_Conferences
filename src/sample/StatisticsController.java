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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import static sample.OpenPageController.user_id;

public class StatisticsController implements Initializable {


    @FXML
    private BarChart<String, Integer> BarChart;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    @FXML
    private Pane paneView;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        //showing the bar chart
        try {
            loadData1();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    private void loadData1() throws SQLException {

        //creating a XYChart
        XYChart.Series series = new XYChart.Series<>();

        // connect to database
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        // selecting sum of notification, submission and confirmation state that will be respectively accepted submissions, submitted and confrmed values
        String query = "SELECT SUM(NOTIFICATION_STATE), SUM(SUBMISSION_STATE), SUM(CONFIRMATION_STATE) FROM MY_CONFERENCE WHERE USER_ID ='" + user_id + "' ";
        //create statement
        Statement statement = connection.createStatement();
        Statement state = connection.createStatement();

        // sum of the lines of the table
        ResultSet result1 = state.executeQuery("SELECT COUNT(*) FROM MY_CONFERENCE WHERE USER_ID ='" + user_id + "'");
        ResultSet result = statement.executeQuery(query);

        while (result.next() && result1.next()) {
            int sum1 = 0;
            int sum2 = 0;
            int nbr_columns = 0;
            int sum3 = 0;
            // calculating accepted submissions
            sum1 = result.getInt("sum(NOTIFICATION_STATE)");
            series.getData().add(new XYChart.Data("accepted submissions", sum1));


            nbr_columns = result1.getInt("count(*)");
            // calculating unaccepted submissions
            int inaccepted = 0;
            if (sum1 < nbr_columns) {
                inaccepted = nbr_columns - sum1;
            } else {
                inaccepted = sum1 - nbr_columns;
            }

            series.getData().add(new XYChart.Data("unaccepted submissions", inaccepted));

            // calculating submitted papers
            sum2 = result.getInt("sum(SUBMISSION_STATE)");
            series.getData().add(new XYChart.Data("submitted", sum2));


            // calculating not submitted papers
            int unsubmitted = 0;
            if (sum2 < nbr_columns) {
                unsubmitted = nbr_columns - sum2;
            } else {
                unsubmitted = sum2 - nbr_columns;
            }

            series.getData().add(new XYChart.Data("not submitted", unsubmitted));

            // calculating confirmed participation
            sum3 = result.getInt("sum(CONFIRMATION_STATE)");
            series.getData().add(new XYChart.Data("Presence confirmed", sum3));

            // calculating unconfirmed participation
            int unconfirmed = 0;
            if (sum3 < nbr_columns) {
                unconfirmed = nbr_columns - sum3;
            } else {
                unconfirmed = sum3 - nbr_columns;
            }

            series.getData().add(new XYChart.Data("Presence unconfirmed", unconfirmed));


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
