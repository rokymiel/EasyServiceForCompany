package eservice.ui.presentation;

import eservice.business.core.Service;
import eservice.business.services.AutoservicesService;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.function.Consumer;

public class ServiceRegistrationController {

    @FXML
    private TextField codeTextField;
    @FXML
    private Button loginButton;

    public Consumer<Service> whenLogin;
    private AutoservicesService autoservicesService;

    @FXML
    void initialize() {
        autoservicesService = new AutoservicesService();
        codeTextField.textProperty().addListener(this::codeFieldChanged);
    }

    private void codeFieldChanged(ObservableValue<? extends String> observableValue, String oldCode, String newCode) {
        if (newCode == null || newCode.isBlank()) {
            loginButton.setVisible(false);
            return;
        }
        loginButton.setVisible(true);
    }


    @FXML
    private void login() {
        String code = codeTextField.getText();
        loginButton.setDisable(true);
        autoservicesService.getService(code, service -> {
            Platform.runLater(() -> {
                if (service != null) {
                    if (whenLogin != null) {
                        whenLogin.accept(service);
                    }
                } else {
                    loginButton.setDisable(false);
                }
            });

        });
    }
}
