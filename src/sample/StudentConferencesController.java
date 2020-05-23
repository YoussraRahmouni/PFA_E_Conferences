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
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.ResourceBundle;

import static sample.MyThesisStudentsController.thesisStudent_Clicked;

public class StudentConferencesController implements Initializable {

    public TableColumn<Conference, String> CONFERENCE_NAME;
    public TableColumn<Conference, String> SUBJECT;
    public TableColumn<Conference, Date> START_DATE;
    public TableColumn<Conference, Date> END_DATE;
    public TableColumn<Conference, String> COUNTRY;
    public TableColumn<Conference, String> CITY;
    public TableColumn<Conference, Date> SUBMISSION_DATE;
    public TableColumn<Conference, Date> NOTIFICATION_DATE;
    public TableColumn<Conference, Date> CONFIRMATION_DATE;

    // our table
    public TableView<Conference> StudentConferences_Table;
    public static String table_Clicked;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        CONFERENCE_NAME.setCellValueFactory(new PropertyValueFactory<>("conference_name"));
        SUBJECT.setCellValueFactory(new PropertyValueFactory<>("subject"));
        START_DATE.setCellValueFactory(new PropertyValueFactory<>("start_date"));
        END_DATE.setCellValueFactory(new PropertyValueFactory<>("end_date"));
        COUNTRY.setCellValueFactory(new PropertyValueFactory<>("country"));
        CITY.setCellValueFactory(new PropertyValueFactory<>("city"));
        SUBMISSION_DATE.setCellValueFactory(new PropertyValueFactory<>("submission_date"));
        NOTIFICATION_DATE.setCellValueFactory(new PropertyValueFactory<>("notification_date"));
        CONFIRMATION_DATE.setCellValueFactory(new PropertyValueFactory<>("confirmation_date"));

        /*StudentConferences_Table.setRowFactory( tv -> {
            TableRow<Conference> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    table_Clicked = StudentConferences_Table.getSelectionModel().getSelectedItem().getConference_name();

                    System.out.println(table_Clicked);

                    Parent ConferencePageParent = null;
                    try {
                        ConferencePageParent = FXMLLoader.load(getClass().getResource("ConferencePage.fxml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Scene ConferencePageScene = new Scene(ConferencePageParent);
                    Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
                    window.setScene(ConferencePageScene);
                    window.hide();
                    window.setMaximized(true);
                    window.show();


                }
            });
            return row ;
        });*/

        try {
            StudentConferences_Table.setItems(getStudentConferences());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }




    public ObservableList<Conference> getStudentConferences() throws SQLException {

        ObservableList<Conference> conferences= FXCollections.observableArrayList();

        // connect to the database
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection=connectionClass.getConnection();

        // create sql statement
        Statement statement1 = connection.createStatement();
        String sql1 = "SELECT USER_ID FROM USER WHERE USER_NAME='"+thesisStudent_Clicked+"'";

        // store result in result set
        ResultSet result1 = statement1.executeQuery(sql1);

        result1.next();
        // create sql statement
        Statement statement2 = connection.createStatement();
        String sql2 = "SELECT CONFERENCE_NAME, SUBJECT, COUNTRY, CITY, START_DATE, END_DATE,  SUBMISSION_DATE, NOTIFICATION_DATE, CONFIRMATION_DATE FROM CONFERENCE, MY_CONFERENCE WHERE  MY_CONFERENCE.CONFERENCE_ID=CONFERENCE.CONFERENCE_ID  AND MY_CONFERENCE.USER_ID='"+ result1.getInt("USER_ID") +"'";

        // store result in result set
        ResultSet result2 = statement2.executeQuery(sql2);
        // loop on all the conferences that have been selected and add them to the observable list
        while (result2.next()){
            Conference conf = new Conference(result2.getString("CONFERENCE_NAME"), result2.getString("SUBJECT"), result2.getDate("START_DATE").toLocalDate(), result2.getDate("END_DATE").toLocalDate(), result2.getString("COUNTRY"), result2.getString("CITY"), result2.getDate("SUBMISSION_DATE").toLocalDate(),result2.getDate("NOTIFICATION_DATE").toLocalDate(),result2.getDate("CONFIRMATION_DATE").toLocalDate());
            conferences.add(conf);
        }
        return conferences;
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
