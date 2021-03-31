package eservice.ui.presentation;

import eservice.business.core.Car;
import eservice.business.core.Client;
import eservice.business.core.Registration;
import eservice.business.services.UpdatableRegistration;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RegistrationController implements ChangeListener<Registration> {
    private UpdatableRegistration updatableRegistration;

    @FXML
    private ComboBox<String> typeOfWorksBox;
    @FXML
    private TextField costField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<String> hourBox;
    @FXML
    private ComboBox<String> minutesBox;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private ComboBox<String> endHourBox;
    @FXML
    private ComboBox<String> endMinutesBox;
    @FXML
    private ComboBox<String> statusBox;
    @FXML
    private TextArea notesArea;

    @FXML
    private TextField surnameField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField patronymicField;
    @FXML
    private TextField phoneField;

    @FXML
    private TextField markField;
    @FXML
    private TextField modelField;
    @FXML
    private TextField yearField;
    @FXML
    private TextField gearField;
    @FXML
    private TextField engineVolumeField;

    public void set(UpdatableRegistration updatableRegistration) {
        this.updatableRegistration = updatableRegistration;
        updatableRegistration.getValue().addListener(this);
        setFields(updatableRegistration.getRegistration());
        System.out.println("SAST");

    }

    @FXML
    void initialize() {
        System.out.println("init");
        ObservableList<String> range = getClockTime(8, 22);
        ObservableList<String> range2 = getClockTime(0, 59);

        hourBox.setItems(range);
        minutesBox.setItems(range2);

        ObservableList<String> rangeEnd = getClockTime(8, 22);
        ObservableList<String> range2End = getClockTime(0, 59);

        endHourBox.setItems(rangeEnd);
        endMinutesBox.setItems(range2End);

    }

    @Override
    public void changed(ObservableValue<? extends Registration> observableValue, Registration registration, Registration newValue) {
        if (newValue != null) {
            setFields(newValue);
        }
    }

    private void setFields(Registration registration) {
        costField.setText(String.valueOf(registration.getCost()));
        datePicker.setValue(registration.getDateOfRegistration().toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        if (registration.getTimeOfWorks() != null) {
            endDatePicker.setValue(registration.getTimeOfWorks().toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }
        notesArea.setText(registration.getNotes());
        Client client = registration.getClient();
        if (client != null) {
            surnameField.setText(client.getSurname());
            nameField.setText(client.getName());
            patronymicField.setText(client.getPatronymic());
            phoneField.setText(client.getPhoneNumber());

            Car car = client.getCar(registration.getCarId());
            if (car != null) {
                markField.setText(car.getMark());
                modelField.setText(car.getModel());
                yearField.setText(String.valueOf(car.getProductionYear()));
                gearField.setText(car.getGear());
                engineVolumeField.setText(String.valueOf(car.getEngineVolume()));
            }

        }
    }

    private ObservableList<String> getClockTime(int start, int stop) {
        ObservableList<String> list = FXCollections.observableArrayList();
        for (int i = start; i <= stop; i++) {
            if (i < 10) {
                list.add("0" + i);
            } else {
                list.add(String.valueOf(i));
            }
        }
        return list;
    }


}
