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
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static sample.OpenPageController.user_id;


public class ThesisStudentsListController implements Initializable {

    // our columns
    public TableColumn<ThesisStudent, String> STUDENT_NAME;


    // our table
    public TableView<ThesisStudent> ThesisStudents_Table;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        STUDENT_NAME.setCellValueFactory(new PropertyValueFactory<>("student_name"));


        try {

            ThesisStudents_Table.setItems(getStudents());
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public ObservableList<ThesisStudent> getStudents() throws SQLException {

        ObservableList<ThesisStudent> Students = FXCollections.observableArrayList();

        // connect to the database
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection=connectionClass.getConnection();

        // create sql statement

        Statement state = connection.createStatement();


        String query = "SELECT USER_NAME FROM USER WHERE PROFESSION = 'thesis student' ";

        // store result in result set
        ResultSet result1 = state.executeQuery(query);

        // loop on all the students that have been selected and add them to the observable list
        while (result1.next()){
            ThesisStudent student = new ThesisStudent(result1.getString("USER_NAME") );
            Students.add(student);
        }
        return Students;
    }


    public void AddToMyThesisStudents(ActionEvent actionEvent) throws SQLException {

        String student_Name;
        String user_name;

        // get the conf name from the selected row and store it in a local variable

        student_Name = ThesisStudents_Table.getSelectionModel().getSelectedItem().getStudent_name();
        // connect to the database
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection=connectionClass.getConnection();
        // create sql statement
        Statement statement = connection.createStatement();
        // sql query to get the user id
        String sql = "SELECT USER_NAME From user where user_id='"+user_id+"' ";
        // store result in result set
        ResultSet result = statement.executeQuery(sql);
        result.next();
        user_name = result.getString("user_name");
        // check if conference already exists

        // create sql statement
        Statement statement_check = connection.createStatement();
        // sql query to get the conference id
        String sql_check = "SELECT USER_ID FROM USER WHERE USER_NAME = '"+student_Name+"' AND PROFESSOR = '"+user_name+"' ";
        // store result in result set
        ResultSet result_check = statement_check.executeQuery(sql_check);
        if(result_check.next())
        {
            System.out.println("Student already exists");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setHeaderText(null);
            alert.setContentText("you already have this student in your thesis student page!");
            alert.showAndWait();

        }
        else {

            PreparedStatement statementUpdate = connection.prepareStatement("UPDATE USER SET PROFESSOR = '"+user_name+"' where user_name ='"+student_Name+"' ");
            statementUpdate.executeUpdate();
        }



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
        Parent MyAccountParent = FXMLLoader.load(getClass().getResource("MyAccountPageProfessor.fxml"));
        Scene MyAccountPageScene = new Scene(MyAccountParent);

        // This line gets stage information
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(MyAccountPageScene);
        window.hide();
        window.setMaximized(true);
        window.show();
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
