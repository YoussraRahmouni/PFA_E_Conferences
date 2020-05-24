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

public class ProfessorConferenceListController implements Initializable {

        // add button
    public Button Add_To_Conf_List;

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

        // our text fields and date pickers
    public TextField Conference_Name;
    public TextField Subject;
    public DatePicker Start_Date;
    public DatePicker End_Date;
    public TextField Country;
    public TextField City;
    public DatePicker Submission_Date;
    public DatePicker Notification_Date;
    public DatePicker Confirmation_Date;
    public TextField Conference_Link;

        // our table view
    public TableView Conferences_Table;


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


    public void AddToConferenceList(ActionEvent actionEvent) throws SQLException {

        // create a new empty object
        Conference conference = new Conference();
        // set our information input
        conference.setConference_name(Conference_Name.getText());
        conference.setSubject(Subject.getText());
        conference.setStart_date(Start_Date.getValue());
        conference.setEnd_date(End_Date.getValue());
        conference.setCountry(Country.getText());
        conference.setCity(City.getText());
        conference.setSubmission_date(Submission_Date.getValue());
        conference.setNotification_date(Notification_Date.getValue());
        conference.setConfirmation_date(Confirmation_Date.getValue());
        conference.setConference_Url(Conference_Link.getText());
        //  add the object to the table view
        Conferences_Table.getItems().add(conference);

        // add the conference into the database
        // connect to the database
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection=connectionClass.getConnection();

        // create sql statement
        PreparedStatement statement = connection.prepareStatement("insert into  conference(conference_name,subject,start_date,end_date,country,city,submission_date,notification_date,confirmation_date,conference_link) values ('"+ Conference_Name.getText()+"', '"+ Subject.getText()+"', '"+ Start_Date.getValue()+"', '"+ End_Date.getValue()+"', '"+Country.getText()+"', '"+City.getText()+"', '"+ Submission_Date.getValue()+"', '"+Notification_Date.getValue()+"','"+Confirmation_Date.getValue()+"', '"+ Conference_Link.getText()+"')");
        statement.executeUpdate();

        System.out.println("added successfully");

        // clear all fields
        Conference_Name.clear();
        Subject.clear();
        Start_Date.getEditor().clear();
        End_Date.getEditor().clear();
        Country.clear();
        City.clear();
        Submission_Date.getEditor().clear();
        Notification_Date.getEditor().clear();
        Confirmation_Date.getEditor().clear();
        Conference_Link.clear();


    }

    public void GoToMyAccount(ActionEvent actionEvent) throws IOException {
        Parent MyAccountParent = FXMLLoader.load(getClass().getResource("MyAccountPageProfessor.fxml"));
        Scene MyAccountPageScene = new Scene(MyAccountParent);

        // This line gets stage information
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(MyAccountPageScene);
        window.hide();
        window.setMaximized(true);
        window.show();
    }

    public void GoToMyThesisStudents(ActionEvent actionEvent) throws IOException {
        Parent ThesisStudentsParent = FXMLLoader.load(getClass().getResource("MyThesisStudents.fxml"));
        Scene ThesisStudentsScene = new Scene(ThesisStudentsParent);

        // This line gets stage information
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(ThesisStudentsScene);
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

    public void GoToGroupStatistics(ActionEvent actionEvent) {
    }
}
