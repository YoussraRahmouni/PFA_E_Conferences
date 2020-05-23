package sample;

import connectivity.ConnectionClass;
import javafx.beans.Observable;
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
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import static sample.OpenPageController.user_id;

public class MyConferencesController implements Initializable {


    public Button ADD_MY_CONF;
    public Button DELETE_CONF;


    Label conferenceNameLabel;
    Label subjectNameLabel;
    Label startNameLabel;
    Label endNameLabel;
    Label countryNameLabel;
    Label cityNameLabel;
    Label submissionNameLabel;
    Label notificationNameLabel;
    Label confirmationNameLabel;

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


    // our table
    public TableView<Conference> MyConferences_Table;

    // static variables used in conference
    public static String Conference_Name;
    public static LocalDate SUB_DATE_CONFERENCE;
    public static LocalDate NOTIFY_DATE_CONFERENCE;
    public static LocalDate CONFIRM_DATE_CONFERENCE;






    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        CONFERENCE_NAME.setCellValueFactory(new PropertyValueFactory<>("conference_name"));
        conferenceNameLabel = new Label("CONFERENCE NAME");
        Tooltip tooltip1 = new Tooltip("ONE click to delete conference , DOUBLE click to access the conference");
        conferenceNameLabel.setTooltip(tooltip1);
        tooltip1.setFont(new Font("Arial", 12));
        tooltip1.setStyle("-fx-background-color :#535c68");
        CONFERENCE_NAME.setGraphic(conferenceNameLabel);

        SUBJECT.setCellValueFactory(new PropertyValueFactory<>("subject"));
        subjectNameLabel = new Label("SUBJECT");
        Tooltip tooltip2 = new Tooltip("ONE click to delete conference , DOUBLE click to access the conference");
        subjectNameLabel.setTooltip(tooltip2);
        tooltip2.setFont(new Font("Arial", 12));
        tooltip2.setStyle("-fx-background-color : #535c68");
        SUBJECT.setGraphic(subjectNameLabel);

        START_DATE.setCellValueFactory(new PropertyValueFactory<>("start_date"));
        startNameLabel = new Label("START DATE");
        Tooltip tooltip3 = new Tooltip("ONE click to delete conference , DOUBLE click to access the conference");
        startNameLabel.setTooltip(tooltip3);
        tooltip3.setFont(new Font("Arial", 12));
        tooltip3.setStyle("-fx-background-color : #535c68");
        START_DATE.setGraphic(startNameLabel);

        END_DATE.setCellValueFactory(new PropertyValueFactory<>("end_date"));
        endNameLabel = new Label("END DATE");
        Tooltip tooltip4 = new Tooltip("ONE click to delete conference , DOUBLE click to access the conference");
        endNameLabel.setTooltip(tooltip4);
        tooltip4.setFont(new Font("Arial", 12));
        tooltip4.setStyle("-fx-background-color : #535c68");
        END_DATE.setGraphic(endNameLabel);

        COUNTRY.setCellValueFactory(new PropertyValueFactory<>("country"));
        countryNameLabel = new Label("COUNTRY");
        Tooltip tooltip5 = new Tooltip("ONE click to delete conference , DOUBLE click to access the conference");
        countryNameLabel.setTooltip(tooltip5);
        tooltip5.setFont(new Font("Arial", 12));
        tooltip5.setStyle("-fx-background-color :#535c68");
        COUNTRY.setGraphic(countryNameLabel);

        CITY.setCellValueFactory(new PropertyValueFactory<>("city"));
        cityNameLabel = new Label("CITY");
        Tooltip tooltip6 = new Tooltip("ONE click to delete conference , DOUBLE click to access the conference");
        cityNameLabel.setTooltip(tooltip6);
        tooltip6.setFont(new Font("Arial", 12));
        tooltip6.setStyle("-fx-background-color : #535c68");
        CITY.setGraphic(cityNameLabel);

        SUBMISSION_DATE.setCellValueFactory(new PropertyValueFactory<>("submission_date"));
        submissionNameLabel = new Label("SUBMISSION DATE");
        Tooltip tooltip7 = new Tooltip("ONE click to delete conference , DOUBLE click to access the conference");
        submissionNameLabel.setTooltip(tooltip7);
        tooltip7.setFont(new Font("Arial", 12));
        tooltip7.setStyle("-fx-background-color : #535c68");
        SUBMISSION_DATE.setGraphic(submissionNameLabel);

        NOTIFICATION_DATE.setCellValueFactory(new PropertyValueFactory<>("notification_date"));
        notificationNameLabel = new Label("NOTIFICATION DATE");
        Tooltip tooltip8 = new Tooltip("ONE click to delete conference , DOUBLE click to access the conference");
        notificationNameLabel.setTooltip(tooltip8);
        tooltip8.setFont(new Font("Arial", 12));
        tooltip8.setStyle("-fx-background-color : #535c68");
        NOTIFICATION_DATE.setGraphic(notificationNameLabel);

        CONFIRMATION_DATE.setCellValueFactory(new PropertyValueFactory<>("confirmation_date"));
        confirmationNameLabel = new Label("CONFIRMATION DATE");
        Tooltip tooltip9 = new Tooltip("ONE click to delete conference , DOUBLE click to access the conference");
        confirmationNameLabel.setTooltip(tooltip9);
        tooltip9.setFont(new Font("Arial", 12));
        tooltip9.setStyle("-fx-background-color : #535c68");
        CONFIRMATION_DATE.setGraphic(confirmationNameLabel);






        try {
            MyConferences_Table.setItems(getConferences());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // change row colors
        LocalDate date_now = java.time.LocalDate.now();
        MyConferences_Table.setRowFactory(tableView -> new TableRow<Conference>() {

            protected void updateItem(Conference conference, boolean empty) {
                super.updateItem(conference, empty);
                if (empty) {
                    setStyle("");
                }
                else if (conference.getSubmission_date().equals(date_now.plusDays(10))) {
                    setStyle("-fx-background-color:#fad390;");
                }
                else if (conference.getNotification_date().equals(date_now.plusDays(118))) {
                    setStyle("-fx-background-color:#78e08f;");
                }
                else if (conference.getConfirmation_date().equals(date_now.plusDays(147))){
                    setStyle("-fx-background-color:#7ed6df;");
                }
            }
        });


        Callback<TableView<Conference>, TableRow<Conference>> existingRowFactory = MyConferences_Table.getRowFactory();
        // double click --> conference page
        MyConferences_Table.setRowFactory( tv -> {
            TableRow<Conference> row ;
            if (existingRowFactory == null) {
                row = new TableRow<>();
            } else row = existingRowFactory.call(MyConferences_Table);


            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Conference_Name = MyConferences_Table.getSelectionModel().getSelectedItem().getConference_name();
                    SUB_DATE_CONFERENCE = MyConferences_Table.getSelectionModel().getSelectedItem().getSubmission_date();
                    NOTIFY_DATE_CONFERENCE  = MyConferences_Table.getSelectionModel().getSelectedItem().getNotification_date();
                    CONFIRM_DATE_CONFERENCE = MyConferences_Table.getSelectionModel().getSelectedItem().getConfirmation_date();
                    System.out.println(Conference_Name);

                    Parent ConferenceParent = null;
                    try {
                        ConferenceParent = FXMLLoader.load(getClass().getResource("ConferencePage.fxml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Scene ConferenceScene = new Scene(ConferenceParent);
                    Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
                    window.setScene(ConferenceScene);
                    window.hide();
                    window.setMaximized(true);
                    window.show();
                }
            });
            return row ;
        });








    }
    public ObservableList<Conference> getConferences() throws SQLException {

            ObservableList<Conference> conferences= FXCollections.observableArrayList();

            // connect to the database
            ConnectionClass connectionClass = new ConnectionClass();
            Connection connection=connectionClass.getConnection();

            // create sql statement
            Statement statement = connection.createStatement();
            String sql = "SELECT CONFERENCE_NAME, SUBJECT, COUNTRY, CITY, START_DATE, END_DATE,  SUBMISSION_DATE, NOTIFICATION_DATE, CONFIRMATION_DATE FROM CONFERENCE, MY_CONFERENCE WHERE  MY_CONFERENCE.CONFERENCE_ID=CONFERENCE.CONFERENCE_ID  AND MY_CONFERENCE.USER_ID='"+ user_id +"'";

            // store result in result set
            ResultSet result = statement.executeQuery(sql);

            // loop on all the conferences that have been selected and add them to the observable list
            while (result.next()){
                Conference conf = new Conference(result.getString("CONFERENCE_NAME"), result.getString("SUBJECT"), result.getDate("START_DATE").toLocalDate(), result.getDate("END_DATE").toLocalDate(), result.getString("COUNTRY"), result.getString("CITY"), result.getDate("SUBMISSION_DATE").toLocalDate(),result.getDate("NOTIFICATION_DATE").toLocalDate(),result.getDate("CONFIRMATION_DATE").toLocalDate());
                conferences.add(conf);
            }
        System.out.println("data retrieved successfully");
        return conferences;
    }


    public void DeleteFromMyConf(ActionEvent actionEvent) throws SQLException {

        String confName;
        Integer confId = 0;


        // create two observable lists one will store the table and one the selected items
        ObservableList<Conference>  selectedConference, allConferences;

        // store table conf into allConferences
        allConferences = MyConferences_Table.getItems();

        // store the selected conference 
        selectedConference = MyConferences_Table.getSelectionModel().getSelectedItems();

        // store the name of conference that has been chosen in order to deleted from the data base
        confName=MyConferences_Table.getSelectionModel().getSelectedItem().getConference_name();
        System.out.println("I am here");
        System.out.println(confName);
        // remove the selected conference from the table view
        if (selectedConference != null) {
            ArrayList<Conference> rows = new ArrayList<>(selectedConference);
            rows.forEach(row -> MyConferences_Table.getItems().remove(row));
        }

        // remove the selected conference from the database

        // connect to the database
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection=connectionClass.getConnection();

        // create sql statement
        Statement statement1 = connection.createStatement();
        // get my conference id in order to know which line to delete
        String sql1 = "SELECT my_conference_id From my_conference, conference where conference.conference_id = my_conference.conference_id and conference.conference_name='"+confName+"' and user_id='"+user_id+"'";
        // store result in result set
        ResultSet result1 = statement1.executeQuery(sql1);
        System.out.println("pffffff");
        result1.next();
        confId = result1.getInt("my_conference_id");
        System.out.println(confId);
        PreparedStatement statement2 = connection.prepareStatement(" DELETE FROM my_conference WHERE my_conference_id = '"+ confId + "' ");
        statement2.executeUpdate();

        System.out.println("deleted successfully");
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

    public void AddFromMyConferencesList(ActionEvent actionEvent) throws IOException {

        Parent ConferencesListParent = FXMLLoader.load(getClass().getResource("ConferencesListPage.fxml"));
        Scene ConferencesListScene = new Scene(ConferencesListParent);

        // This line gets stage information
        Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(ConferencesListScene);
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

    public void GoToStatistics(ActionEvent actionEvent) throws IOException {
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

