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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static sample.OpenPageController.user_id;


public class MyThesisStudentsController implements Initializable {





    // our columns
    public TableColumn<ThesisStudent, String> STUDENT_NAME;


    // our table
    public TableView<ThesisStudent> MyThesisStudents_Table;
    public static String thesisStudent_Clicked;
    public Button thesisStudents;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        STUDENT_NAME.setCellValueFactory(new PropertyValueFactory<>("student_name"));

        try {
            MyThesisStudents_Table.setItems(getStudents());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        MyThesisStudents_Table.setRowFactory( tv -> {
            TableRow<ThesisStudent> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if ( event.getClickCount() == 2 && (! row.isEmpty()))  {
                    thesisStudent_Clicked = MyThesisStudents_Table.getSelectionModel().getSelectedItem().getStudent_name();




                    Parent StudentConferencesParent = null;
                    try {
                        StudentConferencesParent = FXMLLoader.load(getClass().getResource("StudentConferences.fxml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Scene StudentConferencesScene = new Scene(StudentConferencesParent);
                    Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
                    window.setScene(StudentConferencesScene);
                    window.hide();
                    window.setMaximized(true);
                    window.show();


                }
            });
            return row ;
        });




    }

    public ObservableList<ThesisStudent> getStudents() throws SQLException {

        ObservableList<ThesisStudent> Students = FXCollections.observableArrayList();
        // connect to the database
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection=connectionClass.getConnection();

        // create sql statement
        Statement statement = connection.createStatement();
        Statement state = connection.createStatement();
        System.out.println("hey");
        String sql = "SELECT USER_NAME FROM USER WHERE USER_ID ='"+user_id+"'";

        // store result in result set
        ResultSet result = statement.executeQuery(sql);
        result.next();
        String query = "SELECT USER_NAME FROM USER WHERE PROFESSOR='"+ result.getString("USER_NAME") +"'";

        // store result in result set
        ResultSet result1 = state.executeQuery(query);

        // loop on all the students that have been selected and add them to the observable list
        while (result1.next()){
            ThesisStudent student = new ThesisStudent(result1.getString("USER_NAME") );
            Students.add(student);
        }
        return Students;
    }





    public void GoToStatistics (ActionEvent actionEvent) throws IOException {
        Parent StatisticsParent = FXMLLoader.load(getClass().getResource("Statistics.fxml"));
        Scene StatisticsPageScene = new Scene(StatisticsParent);

        // This line gets stage information
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(StatisticsPageScene);
        window.hide();
        window.setMaximized(true);
        window.show();
    }

    public void GoToConferencesList(ActionEvent actionEvent) throws IOException {
        Parent ConferencesListParent = FXMLLoader.load(getClass().getResource("ProfessorConferencesListPage.fxml"));
        Scene ConferencesListScene = new Scene(ConferencesListParent);

        // This line gets stage information
        Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(ConferencesListScene);
        window.hide();
        window.setMaximized(true);
        window.show();

    }






    public void GoToMyAccount (ActionEvent actionEvent) throws IOException {
        Parent MyAccountParent = FXMLLoader.load(getClass().getResource("MyAccountPage.fxml"));
        Scene MyAccountPageScene = new Scene(MyAccountParent);

        // This line gets stage information
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(MyAccountPageScene);
        window.hide();
        window.setMaximized(true);
        window.show();
    }

    public void AddFromThesisStudentsList(ActionEvent actionEvent) throws IOException {

        Parent StudentsListParent = FXMLLoader.load(getClass().getResource("ThesisStudentsListPage.fxml"));
        Scene StudentsListScene = new Scene(StudentsListParent);

        // This line gets stage information
        Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(StudentsListScene);
        window.hide();
        window.setMaximized(true);
        window.show();

    }

    public void DeleteFromMyThesisStudents(ActionEvent actionEvent) throws SQLException {

        String studentName;



        // create two observable lists one will store the table and one the selected items
        ObservableList<ThesisStudent>  selectedStudent,allStudents;

        // store table conf into allConferences
        allStudents = MyThesisStudents_Table.getItems();

        // store the selected student
        selectedStudent = MyThesisStudents_Table.getSelectionModel().getSelectedItems();

        // store the name of student that has been chosen in order to deleted from the data base
        studentName=MyThesisStudents_Table.getSelectionModel().getSelectedItem().getStudent_name();

        System.out.println(studentName);
        // remove the selected conference from the table view
        if (selectedStudent != null) {
            ArrayList<ThesisStudent> rows = new ArrayList<>(selectedStudent);
            rows.forEach(row -> MyThesisStudents_Table.getItems().remove(row));
        }

        // remove the selected conference from the database

        // connect to the database
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection=connectionClass.getConnection();
        // update the professor column to null for our student
        PreparedStatement statement1 = connection.prepareStatement(" update user set professor = null where user_name = '"+studentName+"' ");
        statement1.executeUpdate();

        System.out.println("deleted successfully");
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




}
