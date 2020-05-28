package sample;

import com.jfoenix.controls.JFXButton;
import connectivity.ConnectionClass;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
        import java.net.URL;
        import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
        import javafx.fxml.Initializable;
        import javafx.scene.input.MouseEvent;
        import javafx.scene.layout.AnchorPane;
        import javafx.animation.TranslateTransition;
        import javafx.event.ActionEvent;
        import javafx.scene.control.Label;
        import javafx.scene.control.TextField;
        import javafx.util.Duration;
        import tray.animations.AnimationType;
        import tray.notification.NotificationType;
        import tray.notification.TrayNotification;

public class OpenPageSecController implements Initializable {

    public static int user_id;
    public static boolean alert;
    public TextField email;
    public PasswordField password;
    public Label f1;
    public Label f2;
    public Label f3;
    @FXML
    private AnchorPane layersignup;
    @FXML
    private AnchorPane layer2;
    @FXML
    private JFXButton signin;
    @FXML
    private Label l1;
    @FXML
    private Label l2;
    @FXML
    private Label l3;
    @FXML
    private Label s1;
    @FXML
    private Label s2;
    @FXML
    private Label s3;
    @FXML
    private JFXButton signup;
    @FXML
    private Label a2;
    @FXML
    private Label b2;
    @FXML
    private Label a1;
    @FXML
    private Label b1;
    @FXML
    private JFXButton btnsignup;
    @FXML
    private JFXButton btnsignin;
    @FXML
    private AnchorPane layer1;

    // our sign up text fields
    public TextField user_name;
    public TextField country;
    public TextField email2;
    public TextField password2;
    public ComboBox profession;
    // choice box to determine the user


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // set the values of the choiceBox through the ObservableList constructor
        ObservableList professions = FXCollections.observableArrayList("Professor","Thesis student","Independent User");
        profession.setItems(professions);
        f1.setVisible(true);
        f2.setVisible(true);
        f3.setVisible(false);
        l3.setVisible(true);
        s1.setVisible(false);
        s2.setVisible(false);
        s3.setVisible(false);
        signup.setVisible(false);
        b1.setVisible(false);
        b2.setVisible(false);
        btnsignin.setVisible(false);
        user_name.setVisible(true);
        profession.setVisible(true);
        country.setVisible(true);
        email2.setVisible(true);
        password2.setVisible(true);


        // set the values of the choiceBox through the ObservableList constructor

    }

    @FXML
    private void btn(MouseEvent event) {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.7));
        slide.setNode(layer2);

        slide.setToX(491);
        slide.play();

        layer1.setTranslateX(-309);
        btnsignin.setVisible(true);
        b1.setVisible(true);
        b2.setVisible(true);
        f1.setVisible(false);
        f2.setVisible(false);
        f3.setVisible(true);
        s1.setVisible(true);
        s2.setVisible(true);
        s3.setVisible(true);
        signup.setVisible(true);
        l1.setVisible(false);

        l3.setVisible(false);
        signin.setVisible(false);
        a1.setVisible(false);
        a2.setVisible(false);
        btnsignup.setVisible(false);
        user_name.setVisible(false);
        profession.setVisible(false);
        country.setVisible(false);
        email2.setVisible(false);
        password2.setVisible(false);


        slide.setOnFinished((e -> {


        }));
    }

    @FXML
    private void btn2(MouseEvent event) {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.7));
        slide.setNode(layer2);

        slide.setToX(0);
        slide.play();

        ObservableList professions = FXCollections.observableArrayList("Professor","Thesis student","Independent User");
        profession.setItems(professions);

        layer1.setTranslateX(0);
        btnsignin.setVisible(false);
        b1.setVisible(false);
        b2.setVisible(false);
        f1.setVisible(true);
        f2.setVisible(true);
        f3.setVisible(false);
        s1.setVisible(false);
        s2.setVisible(false);
        s3.setVisible(false);
        signup.setVisible(false);
        l1.setVisible(true);

        l3.setVisible(true);
        signin.setVisible(true);
        a1.setVisible(true);
        a2.setVisible(true);
        btnsignup.setVisible(true);
        user_name.setVisible(true);
        profession.setVisible(true);
        country.setVisible(true);
        email2.setVisible(true);
        password2.setVisible(true);


        slide.setOnFinished((e -> {


        }));
    }

    @FXML
    private void btnsignup(ActionEvent actionEvent) throws IOException {
        try {

            ConnectionClass connectionClass = new ConnectionClass();
            Connection connection = connectionClass.getConnection();
            Statement statement = connection.createStatement();


            String sql = "INSERT INTO USER (user_name,email,password,profession,country) VALUES('" + user_name.getText() + "','" + email2.getText() + "','" + password2.getText() + "','" + profession.getValue().toString() + "','" + country.getText() + "')";

            ResultSet result = statement.executeQuery("SELECT EMAIL FROM USER WHERE EMAIL='" + email2.getText() + "'");
            if (result.next()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Validate email");
                alert.setHeaderText(null);
                alert.setContentText("this email already exists,please enter a new one");
                alert.showAndWait();
                Parent signUpParent = FXMLLoader.load(getClass().getResource("OpenPageSec.fxml"));
                Scene signUpScene = new Scene(signUpParent);
                Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
                window.setScene(signUpScene);
                window.show();
            } else {
                if (validateEmail()) {
                    Statement state = connection.createStatement();
                    state.executeUpdate(sql);
                    Parent signUpParent = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
                    Scene signUpScene = new Scene(signUpParent);
                    Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
                    window.setScene(signUpScene);
                    window.show();
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    private boolean validateEmail() {
        Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Matcher m = p.matcher(email2.getText());
        if (m.find() && m.group().equals(email2.getText())) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate email");
            alert.setHeaderText(null);
            alert.setContentText("please enter valid email");
            alert.showAndWait();
            return false;
        }
    }

    @FXML
    private void sign(MouseEvent event) {

    }

    @FXML
    private void click(ActionEvent actionEvent) {
        try {
            ConnectionClass connectionClass = new ConnectionClass();
            Connection connection = connectionClass.getConnection();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT PROFESSION,USER_ID,EMAIL,PASSWORD FROM USER WHERE EMAIL='" + email.getText() + "' AND PASSWORD='" + password.getText() + "'");

            if (result.next()) {
                if ((email.getText().equals(result.getString("EMAIL"))) & (password.getText().equals(result.getString("PASSWORD")))) {
                    user_id = result.getInt("USER_ID");
                    System.out.println(user_id);
                    System.out.println("welcome to e-conference!");
                    if (result.getString("profession").equals("professor")) {
                        Parent HomePageParent = FXMLLoader.load(getClass().getResource("HomePageForProfessor.fxml"));
                        Scene HomePageScene = new Scene(HomePageParent);
                        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        window.setScene(HomePageScene);
                        window.hide();
                        window.setMaximized(true);
                        window.show();

                    } else {
                        Parent HomePageParent = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
                        Scene HomePageScene = new Scene(HomePageParent);
                        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        window.setScene(HomePageScene);
                        window.hide();
                        window.setMaximized(true);
                        window.show();
                        alert = thereIsAnAlert();

                    }

                }
            } else {
                System.out.println("your email or password are incorrect!");
                Parent signUpParent = FXMLLoader.load(getClass().getResource("OpenPageSec.fxml"));
                Scene signUpScene = new Scene(signUpParent);
                Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                window.setScene(signUpScene);
                window.hide();
                window.setMaximized(true);

                window.show();

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("password or email invalid");
                alert.setHeaderText(null);
                alert.setContentText("please enter valid email or password");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean thereIsAnAlert() throws SQLException {
        // connect to the database
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        // create statement to first get the conference id's of the user
        Statement statement = connection.createStatement();
        String sql = "SELECT conference_id FROM my_conference where user_id='" + user_id + "'";
        ResultSet result = statement.executeQuery(sql);
        while (result.next()) {
            // check if any of the user_conferences has a submission, notification, or confirmation date that is approaching
            // connect to the database
            ConnectionClass connectionClass_check = new ConnectionClass();
            Connection connection_check = connectionClass_check.getConnection();
            Statement statement_check = connection_check.createStatement();
            String sql_check = "SELECT conference_id FROM CONFERENCE WHERE conference_id='" + result.getInt("conference_id") + "' AND ( DATEDIFF(submission_date, CURDATE()) = 4 OR DATEDIFF(notification_date, CURDATE()) = 25 OR DATEDIFF(confirmation_date, CURDATE()) = 38) ";
            ResultSet result_check = statement_check.executeQuery(sql_check);
            if (result_check.next()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("YOU HAVE NEW NOTIFICATIONS");
                alert.setHeaderText(null);
                alert.setContentText("please go to my conferences page to check your notifications!");
                alert.showAndWait();
                return true;
            }
        }
        // case where user has no conference
        System.out.println("User has no conferences");
        return false;

    }

}