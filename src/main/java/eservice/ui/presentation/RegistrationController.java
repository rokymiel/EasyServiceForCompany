package eservice.ui.presentation;

import eservice.business.core.Car;
import eservice.business.core.Client;
import eservice.business.core.Registration;
import eservice.business.services.StatusService;
import eservice.business.services.UpdatableRegistration;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
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
    private TextField statusField;
    @FXML
    private Button nextButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button backButton;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private ComboBox<String> endHourBox;
    @FXML
    private ComboBox<String> endMinutesBox;

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

    private final StatusService statusService = new StatusService();

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
        costField.setText(registration.getCost() == null ? "" : String.valueOf(registration.getCost()));
        setStatus(registration.getStatus());

        datePicker.valueProperty().addListener(this::registrationDateChanged);
        datePicker.setValue(registration.getDateOfRegistration().toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        int hour = registration.getDateOfRegistration().toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalTime().getHour();
        int minute = registration.getDateOfRegistration().toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalTime().getMinute();
        System.out.println(hour);
        System.out.println(minute);
        System.out.println(registration.getDateOfRegistration());
        System.out.println(registration.getDateOfRegistration().toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalTime());

        hourBox.valueProperty().addListener(this::registrationHourChanged);
        minutesBox.valueProperty().addListener(this::registrationMinuteChanged);
        hourBox.getSelectionModel().select(hour - 8);
        minutesBox.getSelectionModel().select(minute);

        endDatePicker.valueProperty().addListener(this::endRegistrationDateChanged);
        if (registration.getTimeOfWorks() != null) {
            endDatePicker.setValue(registration.getTimeOfWorks().toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }

        endHourBox.valueProperty().addListener(this::endRegistrationHourChanged);
        endMinutesBox.valueProperty().addListener(this::endRegistrationMinuteChanged);

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

    private void registrationDateChanged(ObservableValue<? extends LocalDate> observableValue, LocalDate oldDate, LocalDate newDate) {
        System.out.println("AA");
        if (newDate == null) {
            datePicker.setValue(oldDate);
            return;
        }
        if (endDatePicker.getValue() != null && endDatePicker.getValue().compareTo(newDate) < 0) {
            endDatePicker.setValue(null);
        }
        endDatePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                System.out.println("AAAaaA");
                System.out.println(newDate);
                setDisable(empty || date.compareTo(newDate) < 0);
            }
        });
    }

    private String status;

    private void setStatus(String status) {
        this.status = status;
        nextButton.setVisible(false);
        cancelButton.setVisible(false);
        backButton.setVisible(false);
        statusField.setText(statusService.getCurrent(status));

        String next = statusService.getNext(status);
        String cancellation = statusService.getCancellation(status);
        String back = statusService.getPrevious(status);

        if (next != null) {
            nextButton.setVisible(true);
            nextButton.setText(next);
        }
        if (cancellation != null) {
            cancelButton.setVisible(true);
            cancelButton.setText(cancellation);
        }
        if (back != null) {
            backButton.setVisible(true);
            backButton.setText(back);
        }

    }

    @FXML
    public void onNextClicked() {
        setStatus(statusService.getNextStatus(status));
    }

    @FXML
    public void onPreviousClicked() {
        setStatus(statusService.getPreviousStatus(status));
    }

    @FXML
    public void onCancelClicked() {
        setStatus(statusService.getCancellationStatus(status));
    }

    private void endRegistrationDateChanged(ObservableValue<? extends LocalDate> observableValue, LocalDate oldDate, LocalDate newDate) {
        if (endHourBox.getSelectionModel().getSelectedIndex() < 0 || endMinutesBox.getSelectionModel().getSelectedIndex() < 0) {
            endHourBox.getSelectionModel().select(hourBox.getSelectionModel().getSelectedIndex());
            endMinutesBox.getSelectionModel().select(minutesBox.getSelectionModel().getSelectedIndex());
            return;
        }
        if (Objects.equals(newDate, datePicker.getValue())) {
            if (endHourBox.getSelectionModel().getSelectedIndex() < hourBox.getSelectionModel().getSelectedIndex()) {
                endHourBox.getSelectionModel().select(hourBox.getSelectionModel().getSelectedIndex());
            }
            if (endHourBox.getSelectionModel().getSelectedIndex() == hourBox.getSelectionModel().getSelectedIndex() &&
                    endMinutesBox.getSelectionModel().getSelectedIndex() < minutesBox.getSelectionModel().getSelectedIndex()) {
                endMinutesBox.getSelectionModel().select(minutesBox.getSelectionModel().getSelectedIndex());
            }
        }
    }

    private void registrationHourChanged(ObservableValue<? extends String> observableValue, String oldHour, String newHour) {
        System.out.println("LOX2");
        if (endHourBox.getSelectionModel().getSelectedIndex() >= 0 && endMinutesBox.getSelectionModel().getSelectedIndex() >= 0) {
            if (Objects.equals(datePicker.getValue(), endDatePicker.getValue()) &&
                    endHourBox.getSelectionModel().getSelectedIndex() + 8 <= Integer.parseInt(newHour)) {
                endHourBox.getSelectionModel().select(newHour);
                if (endMinutesBox.getSelectionModel().getSelectedIndex() < minutesBox.getSelectionModel().getSelectedIndex()) {
                    endMinutesBox.getSelectionModel().select(minutesBox.getSelectionModel().getSelectedIndex());
                }
            }

        }
    }

    private void registrationMinuteChanged(ObservableValue<? extends String> observableValue, String oldMinute, String newMinute) {
        System.out.println("LOX");
        if (endHourBox.getSelectionModel().getSelectedIndex() >= 0 && endMinutesBox.getSelectionModel().getSelectedIndex() >= 0) {
            if (Objects.equals(datePicker.getValue(), endDatePicker.getValue()) &&
                    endHourBox.getSelectionModel().getSelectedIndex() <= hourBox.getSelectionModel().getSelectedIndex() &&
                    endMinutesBox.getSelectionModel().getSelectedIndex() <= Integer.parseInt(newMinute)) {
                endMinutesBox.getSelectionModel().select(newMinute);
            }
        }
    }

    private void endRegistrationHourChanged(ObservableValue<? extends String> observableValue, String oldHour, String newHour) {

        if (newHour == null) {
            return;
        }
        if (endDatePicker.getValue() == null) {
            Platform.runLater(() -> endHourBox.getSelectionModel().clearSelection());
            return;
        }
        if (Objects.equals(datePicker.getValue(), endDatePicker.getValue())) {
            if (hourBox.getSelectionModel().getSelectedIndex() + 8 > Integer.parseInt(newHour)) {
                endHourBox.getSelectionModel().select(oldHour);
            } else if (hourBox.getSelectionModel().getSelectedIndex() + 8 == Integer.parseInt(newHour) &&
                    endMinutesBox.getSelectionModel().getSelectedIndex() < minutesBox.getSelectionModel().getSelectedIndex()) {
                endMinutesBox.getSelectionModel().select(minutesBox.getSelectionModel().getSelectedIndex());
            }
        }

    }

    private void endRegistrationMinuteChanged(ObservableValue<? extends String> observableValue, String oldMinute, String newMinute) {
        if (newMinute == null) {
            return;
        }
        if (endDatePicker.getValue() == null) {
            Platform.runLater(() -> endMinutesBox.getSelectionModel().clearSelection());
            return;
        }
        if (Objects.equals(datePicker.getValue(), endDatePicker.getValue())) {
            if (hourBox.getSelectionModel().getSelectedIndex() == endHourBox.getSelectionModel().getSelectedIndex() &&
                    Integer.parseInt(newMinute) < minutesBox.getSelectionModel().getSelectedIndex()) {
                endMinutesBox.getSelectionModel().select(oldMinute);
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
