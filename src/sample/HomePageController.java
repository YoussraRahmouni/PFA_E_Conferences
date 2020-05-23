package sample;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomePageController  implements Initializable {
    public ImageView chair_animation;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(3));
        transition.setToX(500);
        transition.setNode(chair_animation);
        transition.play();


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
        Parent ConferencesListParent = FXMLLoader.load(getClass().getResource("ConferencesListPage.fxml"));
        Scene ConferencesListScene = new Scene(ConferencesListParent);

        // This line gets stage information
        Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(ConferencesListScene);
        window.hide();
        window.setMaximized(true);

        window.show();

    }




    public void GoToMyConferences (ActionEvent actionEvent) throws IOException {
        Parent MyConferencesParent = FXMLLoader.load(getClass().getResource("MyConferences.fxml"));
        Scene MyConferencesPageScene = new Scene(MyConferencesParent);

        // This line gets stage information
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(MyConferencesPageScene);
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


}
