package eservice.ui.presentation;

import eservice.business.services.UpdatableRegistration;
import javafx.fxml.FXML;

public class RegistrationController {
    private UpdatableRegistration updatableRegistration;

    public void set(UpdatableRegistration updatableRegistration) {
        this.updatableRegistration = updatableRegistration;

        System.out.println("SAST");

    }

    @FXML
    void initialize() {
        System.out.println("init");
    }
}
