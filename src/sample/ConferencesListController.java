package sample;

import connectivity.ConnectionClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.util.ResourceBundle;

import static sample.OpenPageController.user_id;

public class ConferencesListController  implements Initializable {

    // our columns
    public TableColumn<Conference, String> CONFERENCE_NAME;
    public TableColumn<Conference, String> SUBJECT;
    public TableColumn<Conference, Date> START_DATE;
    public TableColumn<Conference, Date> END_DATE;
    public TableColumn<Conference, String> COUNTRY;
    public TableColumn<Conference, String> CITY;
    public TableColumn<Conference, Date> SUBMISSION_DATE;
    public TableColumn<Conference, Date> NOTIFICATION_DATE;
    public TableColumn<Conference, Date> CONFIRMATION_DATE;
    public TableColumn<Conference, Hyperlink> CONFERENCE_LINK;

    // our table
    public TableView<Conference> Conferences_Table;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
         // our columns
        CONFERENCE_NAME.setCellValueFactory(new PropertyValueFactory<>("conference_name"));
        SUBJECT.setCellValueFactory(new PropertyValueFactory<>("subject"));
        START_DATE.setCellValueFactory(new PropertyValueFactory<>("start_date"));
        END_DATE.setCellValueFactory(new PropertyValueFactory<>("end_date"));
        COUNTRY.setCellValueFactory(new PropertyValueFactory<>("country"));
        CITY.setCellValueFactory(new PropertyValueFactory<>("city"));
        SUBMISSION_DATE.setCellValueFactory(new PropertyValueFactory<>("submission_date"));
        NOTIFICATION_DATE.setCellValueFactory(new PropertyValueFactory<>("notification_date"));
        CONFIRMATION_DATE.setCellValueFactory(new PropertyValueFactory<>("confirmation_date"));
        CONFERENCE_LINK.setCellValueFactory(new PropertyValueFactory<>("conference_Url"));
        CONFERENCE_LINK.setCellFactory(new HyperlinkCell());



        try {
            Conferences_Table.setItems(getConferences());
        } catch (SQLException e) {
            e.printStackTrace();
        }


        
    }
    public ObservableList<Conference> getConferences() throws SQLException {

        ObservableList<Conference> conferences= FXCollections.observableArrayList();

        // connect to the database
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection=connectionClass.getConnection();

        // create sql statement
        Statement statement = connection.createStatement();
        String sql = "SELECT CONFERENCE_NAME, SUBJECT, COUNTRY, CITY, START_DATE, END_DATE,  SUBMISSION_DATE, NOTIFICATION_DATE, CONFIRMATION_DATE, CONFERENCE_LINK FROM CONFERENCE";

        // store result in result set
        ResultSet result = statement.executeQuery(sql);

        // loop on all the conferences that have been selected and add them to the observable list
        while (result.next()){
            Conference conf = new Conference(result.getString("CONFERENCE_NAME"), result.getString("SUBJECT"), result.getDate("START_DATE").toLocalDate(), result.getDate("END_DATE").toLocalDate(), result.getString("COUNTRY"), result.getString("CITY"), result.getDate("SUBMISSION_DATE").toLocalDate(),result.getDate("NOTIFICATION_DATE").toLocalDate(),result.getDate("CONFIRMATION_DATE").toLocalDate(),result.getString("CONFERENCE_LINK"));
            conferences.add(conf);
        }
        System.out.println("data retrieved successfully");
        return conferences;
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

    public void AddToMyConferences(ActionEvent actionEvent) throws SQLException {

        String conf_Name;
        Integer conf_Id;

                // get the conf name from the selected row and store it in a local variable

        conf_Name = Conferences_Table.getSelectionModel().getSelectedItem().getConference_name();
        // connect to the database
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection=connectionClass.getConnection();
        // create sql statement
        Statement statement = connection.createStatement();
        // sql query to get the conference id
        String sql = "SELECT conference_id From conference where conference.conference_name='"+conf_Name+"' ";
        // store result in result set
        ResultSet result = statement.executeQuery(sql);
        result.next();
        // store the conference id in a local variable
        conf_Id = result.getInt("conference_id");

                // check if conference already exists

        // create sql statement
        Statement statement_check = connection.createStatement();
        // sql query to get the conference id
        String sql_check = "SELECT my_conference_id From my_conference where conference_id='"+conf_Id+"' AND user_id='"+user_id+"' ";
        // store result in result set
        ResultSet result_check = statement.executeQuery(sql_check);
        if(result_check.next())
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Conference already exists");
            alert.setHeaderText(null);
            alert.setContentText("This conference can't be added. ALREADY EXISTS!");
            alert.showAndWait();
            System.out.println("Conference already exists");
        }
        else {
            PreparedStatement statementUpdate = connection.prepareStatement(" insert into my_conference (conference_id,user_id) values ('" + conf_Id + "', '" + user_id + "')");
            statementUpdate.executeUpdate();
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

    public void GoToStatistics(ActionEvent actionEvent) throws IOException {
        Parent StatisticsParent = FXMLLoader.load(getClass().getResource("Statistics.fxml"));
        Scene StatisticsScene = new Scene(StatisticsParent);

        // This line gets stage information
        Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene( StatisticsScene);
        window.hide();
        window.setMaximized(true);
        window.show();
    }
}
