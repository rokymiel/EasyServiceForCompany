package eservice.ui;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import eservice.ui.presentation.RegistrationController;
import eservice.ui.presentation.ServiceRegistrationController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(credentials)
                .setProjectId("easyservice-e5e67")
                .build();
        FirebaseApp.initializeApp(options);



        //Parent root = FXMLLoader.load(getClass().getResource("/MainPage.fxml"));
        Parent root;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ServiceRegistrationPage.fxml"));

        root = loader.load();

        ServiceRegistrationController controller = loader.getController();
        controller.whenLogin = code -> {
            FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/MainPage.fxml"));
            try {
                Parent rootMain = mainLoader.load();
                Scene scene = new Scene(rootMain, 1000, 800);
                stage.setTitle("FXML Menu Frame");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

//
        Scene scene = new Scene(root, 500, 250);

        stage.setTitle("FXML Menu Frame");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}