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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpController implements Initializable {
        // our sign up text fields
    public TextField user_name;
    public TextField country;
    public TextField email;
    public TextField password;
    public ComboBox profession;
    // choice box to determine the user
   

   

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            // set the values of the choiceBox through the ObservableList constructor
        ObservableList professions = FXCollections.observableArrayList("Professor","Thesis student","Independent User");
        profession.setItems(professions);



    }

    public void SignMeUp(ActionEvent actionEvent) throws SQLException, IOException {

        try {

            ConnectionClass connectionClass = new ConnectionClass();
            Connection connection=connectionClass.getConnection();
            Statement statement = connection.createStatement();


            String sql = "INSERT INTO USER (user_name,email,password,profession,country) VALUES('"+user_name.getText()+"','"+email.getText()+"','"+password.getText()+"','"+profession.getValue().toString()+"','"+country.getText()+"')";

            ResultSet result = statement.executeQuery("SELECT EMAIL FROM USER WHERE EMAIL='"+email.getText()+"'");
            if(result.next()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Validate email");
                alert.setHeaderText(null);
                alert.setContentText("this email already exists,please enter a new one");
                alert.showAndWait();
            }
            else {
                if (validateEmail() ) {
                    Statement state = connection.createStatement();
                    state.executeUpdate(sql);
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

       Parent signUpParent = FXMLLoader.load(getClass().getResource("OpenPage.fxml"));
        Scene signUpScene = new Scene(signUpParent);
        Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(signUpScene);
        window.hide();
        window.setMaximized(true);

        window.show();

    }
    private boolean validateEmail() {
        Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Matcher m = p.matcher(email.getText());
        if(m.find() && m.group().equals(email.getText())) {
            return true;
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate email");
            alert.setHeaderText(null);
            alert.setContentText("please enter valid email");
            alert.showAndWait();
            return false;
        }
    }


  
}

