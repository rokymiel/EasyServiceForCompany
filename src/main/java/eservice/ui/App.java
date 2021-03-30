package eservice.ui;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(credentials)
                .setProjectId("easyservice-e5e67")
                .build();
        FirebaseApp.initializeApp(options);



        Parent root = FXMLLoader.load(getClass().getResource("/MainPage.fxml"));

        Scene scene = new Scene(root, 1000, 800);

        stage.setTitle("FXML Menu Frame");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}