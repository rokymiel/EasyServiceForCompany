package eservice.ui.presentation;

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

    public Consumer<String> whenLogin;


    @FXML
    void initialize() {
        codeTextField.textProperty().addListener(this::mileageFieldChanged);
    }

    private void mileageFieldChanged(ObservableValue<? extends String> observableValue, String oldCode, String newCode){
        if (newCode == null || newCode.isBlank()) {
            loginButton.setVisible(false);
            return;
        }
        loginButton.setVisible(true);
    }



    @FXML
    private void login() {
        String code = codeTextField.getText();
        if(whenLogin != null) {
            whenLogin.accept(code);
        }
    }


}
